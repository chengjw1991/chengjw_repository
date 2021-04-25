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
    var pagenum;
    var pagecount;
    selectallbmxx(pagenum, pagecount);
    selectalldepartment();
})

//查询所有用户信息
function selectallbmxx(pagenum, pagecount) {
    //通过laypage向服务端请求数据，初始的页码为1，初始的每页数据5条；
    if (pagenum == undefined && pagecount == undefined) {
        pagenum = 1;
        pagecount = 10 ;
    }
    $.ajax({
        url: "/bmxx/selectbmxxbypage",
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
                top.layer.alert("获取部门信息失败", {icon: 0, title: '提示', time: 2500});
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
    $.each(result.data.bmxx,function (i,item) {
        html += "<tr>"+
            "<td>"+(item.id==undefined?'':item.id)+"</td>"+
            "<td>"+(item.name==undefined?'':item.name)+"</td>"+
            "<td>"+(item.code==undefined?'':item.code)+"</td>"+
            "<td style='color:"+(item.del==0?'black':'red')+"'>"+(item.del==0?'<span>启用</span>':'<span>禁用</span>')+
            "<td><a href='javascript:;' onclick='updatebmxx("+item.id+")'><button class='layui-btn layui-btn-normal layui-btn-xs'>修改</button></a>&nbsp;&nbsp;"+
            "<a href='javascript:;' onclick='kaibmxx("+item.id+","+item.del+")'><button class='layui-btn layui-btn-xs'>开启</button></a>&nbsp;&nbsp;"+
            "<a href='javascript:;' onclick='jinbmxx("+item.id+","+item.del+")'><button class='layui-btn layui-btn-danger layui-btn-xs'>禁用</button></a></td>"+
            "</tr>";
    });
    $("tbody").html(html);
}
//分页组件
function page(sum,pagecurr,pagesize){
    laypage.render({
        elem:'bmxx_page',
        count:sum,
        curr:pagecurr,
        groups:2,
        limit:pagesize,
        limits:[5,10,12],
        layout:['prev','page','next','count','limit','skip'],
        jump:function (obj,first){
            // 首次不执行
            if(!first){
                var index = obj.curr;
                var count = obj.limit;
                selectallbmxx(index,count);
            }
        }
    })
}
function kaibmxx(id,del) {
    if (del == 0){
        top.layer.msg("部门已经启用，无法再次启用",{icon:0,time:1500});
        return false;
    }
    $.ajax({
        url:"/bmxx/kaiqidel",
        type: "post",
        data:{"id":id},
        beforeSend: function (request) {
            request.setRequestHeader("Token", GetToken("Token"));
        },
        success: function (result, textStatus, request) {
            var token = request.getResponseHeader("Token");
            SetToken("Token", token);
            if (result.code == 100) {
               top.layer.msg("启用部门成功！",{icon:6,time:1500});
                selectallbmxx(1, 10);
            } else if (result.code == 200) {
                top.layer.alert("启用部门失败", {icon: 0, title: '提示', time: 2500});
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
function jinbmxx(id,del){
    if (del == 1){
        top.layer.msg("部门已经禁用，无法再次禁用",{icon:0,time:1500});
        return false;
    }
    $.ajax({
        url:"/bmxx/jinyongdel",
        type: "post",
        data:{"id":id},
        beforeSend: function (request) {
            request.setRequestHeader("Token", GetToken("Token"));
        },
        success: function (result, textStatus, request) {
            var token = request.getResponseHeader("Token");
            SetToken("Token", token);
            if (result.code == 100) {
                top.layer.msg("禁用部门成功！",{icon:6,time:1500});
                selectallbmxx(1, 10);
            } else if (result.code == 200) {
                top.layer.alert("启用部门失败", {icon: 0, title: '提示', time: 2500});
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
//查询所有部门信息
function selectalldepartment(){
    $.ajax({
        url: "/dwxx/selectalldwxx",
        type: "post",
        beforeSend: function (request) {
            request.setRequestHeader("Token",GetToken("Token"));
        },
        success: function (result, textStatus, request) {
            var token = request.getResponseHeader("Token");
            SetToken("Token", token);
            if (result.code == 100) {
                var res = result.data;
                $.each(result.data,function (i,item){
                    //alert(item.name);
                    var html = "<option value='"+item.code+"'>"+item.name+"</option>";
                    $("#dwcode").append(html);
                })
                form.render("select");
            }else if(result.code == 200){
                top.layer.alert("获取单位信息失败",{icon:0,title:'提示',time:2500});
            }else if(result.code == 250){
                top.layer.alert("会话已过期，请您重新登录",{icon:0,title: '会话过期提示',time: 2500},function (){
                    top.window.location.href="/login";
                });
            }
        }
    })
}
//修改部门信息回显
function updatebmxx(id) {
    $("#updateinfo").css("color","red");
    $.ajax({
        url:"/bmxx/selectbmxxbybmid",
        type:"post",
        data:{"bmid":id},
        beforeSend: function (request) {
            request.setRequestHeader("Token", GetToken("Token"));
        },
        success: function (result, textStatus, request) {
            var token = request.getResponseHeader("Token");
            SetToken("Token", token);
            if (result.code == 100) {
                $("input[name = 'updatebmname']").val(result.data.name);
                $("input[name='id']").val(result.data.id);
                $("input[name = 'code']").val(result.data.code);
                //刷新启用|禁用按钮展示
                $("#deldiv").html("<input name='del' type='checkbox' lay-skin='switch' lay-text='启用|禁用' checked>");
                result.data.del == 0?$("input[name='del']").attr("checked",true):$("input[name='del']").attr("checked",false);
                form.render("checkbox");
            }else if(result.code == 200){
                top.layer.alert("获取单位信息失败",{icon:0,title:'新增角色提示',time:2500});
            }else if(result.code == 250){
                top.layer.alert("会话已过期，请您重新登录",{icon:0,title: '会话过期提示',time: 2500},function (){
                    top.window.location.href="/login";
                });
            }
        }
    })
}
//提交修改
function sureupdatebmxx() {
    var bmid = $("input[name='id']").val();
    var bmname = $("input[name='updatebmname']").val().trim();
    var del = $("input[name='del']:checked").val();
    var bmdel;
    if (del == "on"){
        bmdel = 0;
    }else {
        bmdel = 1;
    }
    if (ValueIsEmpty(bmname)){
        $("#icon_updatename").html("");
        $("#icon_updatename").html("<i class='layui-icon layui-icon-close-fill'></i>");
        layer.alert("部门名称不能为空",{icon:0,title:'修改部门信息提示',time:2500});
        return false;
    }else {
        $("#icon_updatename").html("");
    }
    $.ajax({
        url:"/bmxx/updatebmxxbyid",
        type:"post",
        data:{"bmid":bmid,"bmname":bmname,"del":bmdel},
        beforeSend: function (request) {
            request.setRequestHeader("Token", GetToken("Token"));
        },
        success: function (result, textStatus, request) {
            var token = request.getResponseHeader("Token");
            SetToken("Token", token);
            if (result.code == 100) {
                top.layer.msg("修改部门信息成功",{icon:6,time:1500});
                selectallbmxx(1, 10);
                $(".updatebminfo").val("");
                $("#updateinfo").css("color","black");
            }else if(result.code == 200){
                top.layer.alert("获取单位信息失败",{icon:0,title:'新增角色提示',time:2500});
            }else if(result.code == 250){
                top.layer.alert("会话已过期，请您重新登录",{icon:0,title: '会话过期提示',time: 2500},function (){
                    top.window.location.href="/login";
                });
            }
        }
    })
}
//新增部门
function sureinsertbmxx() {
    var bmname = $("input[name='bmname']").val().trim();
    var bmcode = $("select[name='dwcode']").val();
    var del = $("input[type='radio']:checked").val();
    alert(bmname+"--"+bmcode+"--"+del);
    if (ValueIsEmpty(bmname)){
        $("#icon_updatename").html("");
        $("#icon_updatename").html("<i class='layui-icon layui-icon-close-fill'></i>");
        layer.alert("部门名称不能为空",{icon:0,title:'修改部门信息提示',time:2500});
        return false;
    }else {
        $("#icon_updatename").html("");
    }
    $.ajax({
        url:"/bmxx/insertbmxx",
        type:"post",
        data:{"bmcode":bmcode,"bmname":bmname,"del":del},
        beforeSend: function (request) {
            request.setRequestHeader("Token", GetToken("Token"));
        },
        success: function (result, textStatus, request) {
            var token = request.getResponseHeader("Token");
            SetToken("Token", token);
            if (result.code == 100) {
                top.layer.msg("新增部门成功",{icon:6,time:1500});
                selectallbmxx(1, 10);
            }else if(result.code == 200){
                top.layer.alert("新增部门失败",{icon:0,title:'提示',time:2500});
            }else if(result.code == 250){
                top.layer.alert("会话已过期，请您重新登录",{icon:0,title: '会话过期提示',time: 2500},function (){
                    top.window.location.href="/login";
                });
            }
        }
    })
}