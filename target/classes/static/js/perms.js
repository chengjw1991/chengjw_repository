var $;
var tree;
var layer;
var form;
var laypage;
layui.use(['tree','form','layer','laypage'],function () {
    tree = layui.tree;
    $ = layui.jquery;
    form = layui.form;
    layer = layui.layer;
    laypage = layui.laypage;
})

$(function (){
    selectalluserinfo();
    selectallperimission();
})

//查询所有用户信息
function selectalluserinfo(pagenum, pagecount) {
    var pagenum;
    var pagecount;
    //通过laypage向服务端请求数据，初始的页码为1，初始的每页数据5条；
    if (pagenum == undefined && pagecount == undefined) {
        pagenum = 1;
        pagecount = 13;
    }
    $.ajax({
        url: "/userAdmin/selectuserroleperm",
        type: "post",
        data: {"pageindex": pagenum, "pagecount": pagecount},
        beforeSend: function (request) {
            request.setRequestHeader("Token", GetToken("Token"));
        },
        success: function (result, textStatus, request) {
            var token = request.getResponseHeader("Token");
            SetToken("Token", token);
            if (result.code == 100) {
                insertdata(result);
                page(result.data.sumcount, result.data.pagecurr, result.data.pagecount);
            } else if (result.code == 200) {
                top.layer.alert("获取用户信息失败", {icon: 0, title: '提示', time: 2500});
            } else if (result.code == 220){
                top.layer.msg(result.msg,{icon:5});
            } else if (result.code == 250) {
                top.layer.alert("会话已过期，请您重新登录", {icon: 0, title: '会话过期提示', time: 2500}, function () {
                    top.window.location.href = "/login";
                });
            }
        }
    })
}
//查询所有的权限
function selectallperimission() {
    $.ajax({
        url: "/permissionAdmin/selectallperms",
        type: 'post',
        beforeSend: function (request) {
            request.setRequestHeader("Token",GetToken("Token"));
        },
        success: function (result, textStatus, request) {
            var token = request.getResponseHeader("Token");
            SetToken("Token", token);
            if (result.code == 100) {
                //getData(result);
                tree.render({
                    elem: '#perms',
                    id:'treeID',
                    data: result.data,
                    showCheckbox: true
                });
                tree.setChecked('treeID',[7000]);
                //将result结果藏值到页面，后续用来重载使用
                $("input[name='rollbackperms']").val(result.data);
            }else if(result.code == 200){
                top.layer.alert("获取权限信息失败",{icon:0,title:'新增用户提示',time:2500});
            }else if(result.code == 250){
                top.layer.alert("会话已过期，请您重新登录",{icon:0,title: '会话过期提示',time: 2500},function (){
                    top.window.location.href="/login";
                });
            }
        }
    })
}
//往表格里填充数据
function insertdata(result){
    var html = "";
    $.each(result.data.users,function (i,item) {
        //alert(item.username+"--"+item.realname+"--"+item.dwname+"--"+item.rolename);
        html += "<tr>"+
            "<td id='"+item.id+"'>"+(item.username==undefined?'':item.username)+"</td>"+
            "<td>"+(item.realname==undefined?'':item.realname)+"</td>"+
            "<td>"+(item.dwcode==undefined?'':item.dwname)+"</td>"+
            "<td>"+(item.bmname==undefined?'':item.bmname)+"</td>"+
            "<td>"+(item.rolename==undefined?'':item.rolename)+"</td>"+
            "<td><a href='javascript:;' onclick='selectperms("+item.id+")'><i class='layui-icon layui-icon-search'></i></a></td>"+
            "</tr>";
    });
    $("tbody").html(html);
}
//分页组件
function page(sum,pagecurr,pagesize){
    laypage.render({
        elem:'lay_page',
        count:sum,
        curr:pagecurr,
        groups:3,
        limit:pagesize,
        limits:[7,13],
        layout:['prev','page','next','count','limit','skip'],
        jump:function (obj,first){
            // 首次不执行
            if(!first){
                var index = obj.curr;
                var count = obj.limit;
                selectalluserinfo(index,count);
            }
        }
    })
}
//根据user id回显权限
function selectperms(id) {
    var userid = "#"+id;
    var username = $(userid).html();
    $("#user_name").html("");
    $("#user_name").append(username);
   $.ajax({
       url: "/permissionAdmin/selectpermidbyuserid",
       type: "post",
       data: {"id": id},
       beforeSend: function (request) {
           request.setRequestHeader("Token", GetToken("Token"));
       },
       success: function (result, textStatus, request) {
           var token = request.getResponseHeader("Token");
           SetToken("Token", token);
           if (result.code == 100) {
               //重载数据
               var permdata = $("input[name='rollbackperms']").val();
               tree.reload('treeID',permdata);
               getRolePermID(result.data);
           } else if (result.code == 200) {
               top.layer.alert("获取用户信息失败", {icon: 0, title: '提示', time: 2500});
           } else if (result.code == 220){
               top.layer.msg(result.msg,{icon:5});
           } else if (result.code == 250) {
               top.layer.alert("会话已过期，请您重新登录", {icon: 0, title: '会话过期提示', time: 2500}, function () {
                   top.window.location.href = "/login";
               });
           }
       }
   })
}
//获取回显user信息的权限id
function getRolePermID(data) {
    $.each(data,function (index,item) {
        var permid = item.permid;
        tree.setChecked('treeID',[permid]);
    })
}