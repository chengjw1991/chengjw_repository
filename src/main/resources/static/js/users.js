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
    selectalldepartment();
    selectalluserinfo(1, 6);
})
//查询所有部门信息
function selectalldepartment(){
    $.ajax({
        url: "/bmxx/selectallbmxx",
        type: "post",
        async:false,
        beforeSend: function (request) {
            request.setRequestHeader("Token",GetToken("Token"));
        },
        success: function (result, textStatus, request) {
            var token = request.getResponseHeader("Token");
            SetToken("Token", token);
            if (result.code == 100) {
                $.each(result.data,function (i,item){
                    //alert(item.name);
                    var html = "<option value='"+item.name+"'>"+item.name+"</option>"
                    $("#bmid").append(html);
                })
                form.render("select");
            }else if(result.code == 200){
                top.layer.alert("获取部门信息失败",{icon:0,title:'提示',time:2500});
            }else if(result.code == 250){
                top.layer.alert("会话已过期，请您重新登录",{icon:0,title: '会话过期提示',time: 2500},function (){
                    top.window.location.href="/login";
                });
            }
        }
    })
}
//查询所有用户信息
function selectalluserinfo(pagenum, pagecount) {
    var bmname = $("#bmid").val();
    var realname = $("input[name='realname']").val().trim();
    var pagenum;
    var pagecount;
    //通过laypage向服务端请求数据，初始的页码为1，初始的每页数据5条；
    if (pagenum == undefined && pagecount == undefined) {
        pagenum = 1;
        pagecount = 6;
    }
    $.ajax({
        url: "/userAdmin/selectalluserinfo",
        type: "post",
        data: {"pageindex": pagenum, "pagecount": pagecount,"bmname":bmname,"realname":realname},
        beforeSend: function (request) {
            request.setRequestHeader("Token", GetToken("Token"));
        },
        success: function (result, textStatus, request) {
            var token = request.getResponseHeader("Token");
            SetToken("Token", token);
            //alert(token);
            if (result.code == 100) {
                //alert("token");
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
//往表格里填充数据
function insertdata(result){
    var html = "";
    $.each(result.data.users,function (i,item) {
        html += "<tr>"+
            "<td>"+(item.username==undefined?'':item.username)+"</td>"+
            "<td>"+(item.realname==undefined?'':item.realname)+"</td>"+
            "<td>"+(item.dwcode==undefined?'':item.dwname)+"</td>"+
            "<td>"+(item.bmname==undefined?'':item.bmname)+"</td>"+
            "<td>"+(item.rolename==undefined?'':item.rolename)+"</td>"+
            "<td>"+(item.sex==undefined?'':item.sex)+"</td>"+
            "<td>"+(item.phone==undefined?'':item.phone)+"</td>"+
            "<td>"+(item.registdate==undefined?'':item.registdate)+"</td>"+
            "<td style='color: "+(item.delflag==0?'black':'red')+"'>"+(item.delflag==0?'启用':'禁用')+"</td>"+
            "<td>"+(item.userid==undefined?'':item.userid)+"</td>"+
            "<td><shiro:hasPermission name='rolesAdmin:rolesUpdate'><a href='javascript:;' onclick='editrole("+item.id+")'><i class='layui-icon layui-icon-edit'></i></a></shiro:hasPermission>" +
            "<shiro:hasPermission name='rolesAdmin:rolesUpdate'><a href='javascript:;' onclick='deleteroles("+item.id+","+item.delflag+")'><i class='layui-icon layui-icon-close'></i></a></shiro:hasPermission></td>"+"</tr>";
    })
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
        limits:[6,12],
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
//禁用用户
function deleteroles(id,delflag) {
    if (delflag == 1){
        top.layer.msg("此用户已经被禁用，无法再次禁用",{icon:5});
        return false;
    }
    if (id==1){
        top.layer.msg("Super administrator cannot be disabled!",{icon:0});
        return false;
    }
    top.layer.confirm("您确定要禁用此用户吗？",{icon:0,title:'大人！还请三思啊！',time:25000},function (){
        $.ajax({
            url:'/userAdmin/deletebyid',
            type: "post",
            data: {"id":id},
            beforeSend: function (request) {
                request.setRequestHeader("Token", GetToken("Token"));
            },
            success: function (result, textStatus, request) {
                var token = request.getResponseHeader("Token");
                SetToken("Token", token);
                //alert(token);
                if (result.code == 100) {
                    top.layer.msg("禁用成功",{icon:6});
                    //location.reload();
                    selectalluserinfo(1, 6);
                } else if (result.code == 200) {
                    top.layer.alert("禁用用户失败", {icon: 0, title: '禁用用户提示', time: 2500});
                } else if (result.code == 250) {
                    top.layer.alert("会话已过期，请您重新登录", {icon: 0, title: '会话过期提示', time: 2500}, function () {
                        top.window.location.href = "/login";
                    });
                }
            }
        })
    })
}
//修改用户信息
function editrole(id) {
    if (id==1){
        //超级管理员账号不允许修改
        top.layer.msg("Super administrator cannot be modified!",{icon:0});
        return false;
    }
    setUserID("UserID",id);
    top.layer.open({
        title: '修改用户信息',
        type: 2,
        anim:0,
        offset:70,
        area:['700px','750px'],
        resize:false,
        content:'/userAdmin/userUpdate',
        end:function (){
            selectalluserinfo(1,6);
        }
    })
}
//新增用户
function insertuser(){
    top.layer.open({
        title: '新增用户',
        type: 2,
        anim:0,
        offset:70,
        area:['700px','750px'],
        resize:false,
        content:'/userAdmin/userAdd',
        end:function (){
            selectalluserinfo(1,6);
        }
    })
    //location.href="/userAdmin/userAdd";
}