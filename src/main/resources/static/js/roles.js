var laypage;
var layer;
layui.use(['layer','laypage'],function (){
    var $ = layui.jquery;
    layer = layui.layer;
    laypage = layui.laypage;
})

$(function () {
    var pagenum;
    var pagecount;
    createtable(pagenum,pagecount);

})
function createtable(pagenum,pagecount) {
    //通过laypage向服务端请求数据，初始的页码为1，初始的每页数据5条；
    if(pagenum == undefined && pagecount == undefined){
        pagenum = 1;
        pagecount = 6;
    }
    $.ajax({
        url:"/rolesAdmin/rolesSelect",
        type:'post',
        data:{"pageindex":pagenum,"pagecount":pagecount},
        beforeSend:function (request){
            request.setRequestHeader("Token",GetToken("Token"));
        },
        success: function (result,textStatus,request){
            var token = request.getResponseHeader("Token");
            SetToken("Token",token);
            if (result.code == 100){
                //展示数据
                insertdata(result);
                //进行分页
                page(result.data.sumcount,result.data.pagecurr,result.data.pagecount);
            }else if(result.code == 200) {
                layer.alert(result.msg);
            }else if(result.code == 250){
                top.layer.alert("会话已过期，请您重新登录",{icon:0,title: '会话过期提示',time: 2500},function (){
                    top.window.location.href="/login";
                });
            }
        }
    });
}
//往表格里填充数据
function insertdata(result){
    var html = "";
    $.each(result.data.roles,function (i,item) {
        //alert(item.id);
        html += "<tr>"+
            "<td>"+(item.id==undefined?'':item.id)+"</td>"+
            "<td>"+(item.name==undefined?'':item.name)+"</td>"+
            "<td>"+(item.type==undefined?'':item.type)+"</td>"+
            "<td>"+(item.dwmc==undefined?'':item.dwmc)+"</td>"+
            "<td>"+(item.code==undefined?'':item.code)+"</td>"+
            "<td>"+(item.explain==undefined?'':item.explain)+"</td>"+
            "<td>"+(item.createdate==undefined?'':item.createdate)+"</td>"+
            "<td style='color: "+(item.del==0?'black':'red')+"'>"+(item.del==0?'启用':'禁用')+"</td>"+
            "<td><shiro:hasPermission name='rolesAdmin:rolesUpdate'><a href='javascript:;' onclick='editrole("+item.id+")'><i class='layui-icon layui-icon-edit'></i></a></shiro:hasPermission>" +
            "<shiro:hasPermission name='rolesAdmin:rolesUpdate'><a href='javascript:;' onclick='deleteroles("+item.id+","+item.del+")'><i class='layui-icon layui-icon-close'></i></a></shiro:hasPermission></td>"+"</tr>";
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
                createtable(index,count);
            }
        }
    })
}
//新增角色
function insertroles(){
    top.layer.open({
        title: '新增角色',
        type: 2,
        anim:0,
        offset:100,
        area:['700px','600px'],
        resize:false,
        content:'/rolesAdmin/rolesAdd',
        end:function (){
            createtable(1,6);
        }
    })
}
//禁用角色
function deleteroles(id,del){
    if(del==1){
        top.layer.msg("此角色已经被禁用，无法再次禁用",{icon:5});
        return false;
    }
    if (id==1){
        top.layer.msg("Super administrator cannot be disabled!",{icon:0});
        return false;
    }
    top.layer.confirm("如果您禁用了此角色，则与此角色关联的用户将无法正常使用，您确定要禁用角色吗？",{icon:2,title:'大人！请您三思啊！'},function () {
        $.ajax({
            url:"/rolesAdmin/deleterole",
            type:'post',
            data:{"id":id},
            beforeSend:function (request){
                request.setRequestHeader("Token",GetToken("Token"));
            },
            success: function (result,textStatus,request){
                var token = request.getResponseHeader("Token");
                SetToken("Token",token);
                if (result.code == 100){
                    top.layer.msg("禁用成功",{icon:6,time: 1500});
                    //禁用成功之后，重新查询
                    createtable(1,6);
                }else if(result.code == 200) {
                    layer.msg(result.msg,{icon:5,time: 1500});
                }else if(result.code == 250){
                    top.layer.alert("会话已过期，请您重新登录",{icon:0,title: '会话过期提示',time: 2500},function (){
                        top.window.location.href="/login";
                    });
                }
            }
        })
    })
}
//修改角色
function editrole(id) {
    //自定义将roleid存入layui.sessionData()中
    if (id == 1){
        //超级管理员账号不允许修改
        top.layer.msg("Super administrator cannot be modified!",{icon:0});
        return false;
    }
    setRoleID('RoleID',id);
    top.layer.open({
        title:'修改角色信息',
        area:['700px','600px'],
        type:2,
        offset:100,
        resize: false,
        content:'/rolesAdmin/rolesUpdate',
        end:function (){
            createtable(1,6);
        }
    })
}