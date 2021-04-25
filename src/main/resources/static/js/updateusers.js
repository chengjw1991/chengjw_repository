var $;
var tree;
var layer;
var form;
var upload;
layui.use(['tree','form','layer','upload'],function () {
    tree = layui.tree;
    $ = layui.jquery;
    form = layui.form;
    layer = layui.layer;
    upload = layui.upload;
    upload.render({
        elem:'#upload_btn',
        url:'/userinfo/uploadphoto',
        headers:{'Token':GetToken("Token")},
        done:function (result){
            if(result.code == 100){
                //layer.alert("上传成功",{icon:6,time:2500,title:'更换头像'});
                var str = "/images/"+result.data;
                $("#upload_img").attr("src",str);
                $("#p_photo").html(result.data);
            }else if (result.code ==200) {
                layer.alert("上传失败",{icon:5,time:2500,title:'上传头像'})
            }else if (result.code == 320){
                top.layer.alert("会话已过期，请您重新登录",{icon:0,title: '会话过期提示',time: 2500},function (){
                    top.window.location.href="/login";
                });
            }
        }
    });
    //监听单位下拉框，根据单位id回显角色值
    form.on('select(dwcode)',function (data) {
        var dwcode = data.value;
        //根据单位code回显角色
        getroles(dwcode);
        //根据单位code回显部门
        getBmxx(dwcode);
        //，当单位code=“wscjw”时，权限回归原始状态
        if (dwcode == "wscjw"){
            getPermission(0);
        }
    });
    //监听角色下拉框，根据role值回显权限值
    form.on('select(userrole)',function (data) {
        var roleid = data.value;
        getPermission(roleid);
    })
});

//初始化数据
$(function () {
    //全部设置成同步，否则会出现用户信息回显了，但是其他信息没有出现，导致加载不上
    //权限
    selectallperimission();
    //单位
    selectalldepartment();
    //角色
    selectallrole();
    //部门
    selectallbmxx();
    //回显用户信息
    getUserInfo();
})

//查询所有的权限
function selectallperimission() {
    $.ajax({
        url: "/permissionAdmin/selectallperms",
        type: 'post',
        async:false,
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
                    showCheckbox: true,
                    accordion: true
                });
                tree.setChecked('treeID',[7000]);
                //将result结果藏值到页面，后续用来重载使用
                $("input[name='permission']").val(result.data);
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
//根据单位code，回显角色值
function getroles(code) {
    $.ajax({
        url: "/rolesAdmin/selectrolesbydwcode",
        type: 'post',
        data:{"dwcode":code},
        beforeSend: function (request) {
            request.setRequestHeader("Token",GetToken("Token"));
        },
        success: function (result, textStatus, request) {
            var token = request.getResponseHeader("Token");
            SetToken("Token", token);
            if (result.code == 100) {
                var res = result.data;
                //清空下拉框的值
                $("#userrole").empty();
                $("#userrole").append("<option value=\"0\">-----请选择角色-----</option>");
                $.each(result.data,function (i,item){
                    //alert(item.name);
                    var html = "<option value='"+item.id+"'>"+item.name+"</option>";
                    $("#userrole").append(html);
                })
                form.render("select");
            }else if(result.code == 220){
                top.layer.alert("请您选择想要的单位",{icon:0,title:'新增用户提示',time:2500});
                $("#userrole").empty();
                $("#userrole").append("<option value=\"0\">-----请选择角色-----</option>");
                //将单位信息藏值，方便后续使用
                var userrole =$("input[name='userroles']").val();
                $("#userrole").append(userrole);
                form.render("select");
            }else if(result.code == 250){
                top.layer.alert("会话已过期，请您重新登录",{icon:0,title: '会话过期提示',time: 2500},function (){
                    top.window.location.href="/login";
                });
            }
        }
    })
}
//根据单位code，回显部门信息
function getBmxx(dwcode) {
    $.ajax({
        url: "/bmxx/selectbmxxbydwcode",
        type: 'post',
        data:{"dwcode":dwcode},
        beforeSend: function (request) {
            request.setRequestHeader("Token",GetToken("Token"));
        },
        success: function (result, textStatus, request) {
            var token = request.getResponseHeader("Token");
            SetToken("Token", token);
            if (result.code == 100) {
                var res = result.data;
                //清空下拉框的值
                $("#bmid").empty();
                $("#bmid").append("<option value=\"0\">-----请选择角色-----</option>");
                $.each(result.data,function (i,item){
                    //alert(item.name);
                    var html = "<option value='"+item.id+"'>"+item.name+"</option>";
                    $("#bmid").append(html);
                })
                form.render("select");
            }else if(result.code == 220){
                //top.layer.alert("请您选择想要的单位",{icon:0,title:'新增用户提示',time:2500});
                $("#bmid").empty();
                $("#bmid").append("<option value=\"0\">-----请选择角色-----</option>");
                //将单位信息藏值，方便后续使用
                var bmid =$("input[name='userbmxx']").val();
                $("#bmid").append(bmid);
                form.render("select");
            }else if(result.code == 250){
                top.layer.alert("会话已过期，请您重新登录",{icon:0,title: '会话过期提示',time: 2500},function (){
                    top.window.location.href="/login";
                });
            }
        }
    })
}
//根据role值，回显权限的选中状态
function getPermission(id) {
    if(id==0){
        var permdata = $("input[name='permission']").val();
        tree.reload('treeID',permdata);
        tree.setChecked('treeID',7000);
        return false;
    }
    $.ajax({
        url: "/permissionAdmin/selectpermsbyroleid",
        type: 'post',
        data:{"roleid":id},
        beforeSend: function (request) {
            request.setRequestHeader("Token",GetToken("Token"));
        },
        success: function (result, textStatus, request) {
            var token = request.getResponseHeader("Token");
            SetToken("Token", token);
            if (result.code == 100) {
                //先重新加载一下tree,数据来源于页面加载权限列表时的藏值
                var permdata = $("input[name='permission']").val();
                tree.reload('treeID',permdata);
                getPermChecked(result);
            }else if(result.code == 220){
                top.layer.alert("请您选择想要的角色",{icon:0,title:'新增角色提示',time:2500});
                tree.reload('treeID',permdata);
                tree.setChecked('treeID',7000);
            }else if(result.code == 250){
                top.layer.alert("会话已过期，请您重新登录",{icon:0,title: '会话过期提示',time: 2500},function (){
                    top.window.location.href="/login";
                });
            }
        }
    })
}
//遍历roleid下获取到的权限集合
function getPermChecked(result) {
    //permisson集合
    var res = result.data;
    $.each(res,function (i,item) {
        var permid = item.permid;
        tree.setChecked('treeID',[permid]);
    })
}
//查询所有单位信息
function selectalldepartment(){
    $.ajax({
        url: "/dwxx/selectalldwxx",
        type: "post",
        async:false,
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
                top.layer.alert("获取单位信息失败",{icon:0,title:'新增角色提示',time:2500});
            }else if(result.code == 250){
                top.layer.alert("会话已过期，请您重新登录",{icon:0,title: '会话过期提示',time: 2500},function (){
                    top.window.location.href="/login";
                });
            }
        }
    })
}
//查询所有的角色
function selectallrole() {
    $.ajax({
        url: "/rolesAdmin/selectallrole",
        type: "post",
        async:false,
        beforeSend: function (request) {
            request.setRequestHeader("Token",GetToken("Token"));
        },
        success: function (result, textStatus, request) {
            var token = request.getResponseHeader("Token");
            SetToken("Token", token);
            var html = "";
            if (result.code == 100) {
                $.each(result.data,function (i,item){
                    //alert(item.name);
                    html += "<option value='"+item.id+"'>"+item.name+"</option>";
                })
                $("#userrole").append(html);
               form.render("select");
                //将角色信息藏值，方便后续使用
                $("input[name='userroles']").val(html);
            }else if(result.code == 200){
                top.layer.alert("获取单位信息失败",{icon:0,title:'新增用户提示',time:2500});
            }else if(result.code == 250){
                top.layer.alert("会话已过期，请您重新登录",{icon:0,title: '会话过期提示',time: 2500},function (){
                    top.window.location.href="/login";
                });
            }
        }
    })
}
//查询所有部门
function selectallbmxx() {
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
            var html = "";
            if (result.code == 100) {
                $.each(result.data,function (i,item){
                    //alert(item.name);
                    html += "<option value='"+item.id+"'>"+item.name+"</option>";
                })
                $("#bmid").append(html);
                form.render("select");
                //藏值部门信息
                $("input[name='userbmxx']").val(html);
            }else if(result.code == 200){
                top.layer.alert("获取部门信息失败",{icon:0,title:'新增用户提示',time:2500});
            }else if(result.code == 250){
                top.layer.alert("会话已过期，请您重新登录",{icon:0,title: '会话过期提示',time: 2500},function (){
                    top.window.location.href="/login";
                });
            }
        }
    })
}
//根据id回显user信息
function getUserInfo() {
    var id = getUserID("UserID");
    $.ajax({
        url: "/userAdmin/selectuserinfobyid",
        type: "post",
        async:false,
        data:{"id":id},
        beforeSend: function (request) {
            request.setRequestHeader("Token",GetToken("Token"));
        },
        success: function (result, textStatus, request) {
            var token = request.getResponseHeader("Token");
            SetToken("Token", token);
            var html = "";
            if (result.code == 100) {
                var info = result.data.info;
                var roleid = result.data.roleid;
                var perms = result.data.perms;
                //回显用户名
                $("input[name='username']").val(info.username);
                //回显姓名
                $("input[name=realname]").val(info.realname);
                //回显性别
                (info.sex == "男")?$("input[title='男']").attr("checked",true):$("input[title='女']").attr("checked",true);
                //渲染单选框
                form.render("radio");
                //回显单位
                $("#dwcode").children("option").each(function() {
                    // 判断需要对那个选项进行回显
                    var value = $(this).val();
                    if ( value == info.dwcode) {
                        // 进行回显
                        $(this).attr("selected",true);
                    }else {
                        $(this).attr("selected",false);
                    }
                });
                //渲染下拉框
                form.render("select");
                //回显部门
                $("#bmid").children("option").each(function (){
                   var value = $(this).val();
                   if (value==info.bmid){
                       $(this).attr("selected",true);
                   }else {
                       $(this).attr("selected",false);
                   }
                });
                form.render("select");
                //回显角色
                $("#userrole").children("option").each(function (){
                    var value = $(this).val();
                    if (value==roleid){
                        $(this).attr("selected",true);
                    }else {
                        $(this).attr("selected",false);
                    }
                });
                form.render("select");
                //回显权限
                getRolePermID(perms);
                //回显电话
                $("input[name='phone']").val(info.phone);
                //回显头像
                //alert(info.prifilephoto);
                $("#upload_img").attr("src","/images/"+info.prifilephoto);
                $("#p_photo").html(info.prifilephoto);
                //回显是否启用
                info.delflag == 0?$("input[name='delflag']").attr("checked",true):$("input[name='delflag']").attr("checked",false);
                form.render("checkbox");
            }else if(result.code == 200){
                top.layer.msg("回显用户信息失败",{icon:0});
            }else if(result.code == 250){
                top.layer.alert("会话已过期，请您重新登录",{icon:0,title: '会话过期提示',time: 2500},function (){
                    top.window.location.href="/login";
                });
            }
        }
    })

}

//验证姓名是否为空
function checkrealname() {
    var password = $("input[name='realname']").val().trim();
    if (ValueIsEmpty(password)){
        $("#icon_realname").html("");
        $("#icon_realname").html("<i class='layui-icon layui-icon-close-fill'></i>");
        layer.alert("姓名不能为空",{icon:0,title:'新增用户提示',time:2500});
    }else {
        $("#icon_realname").html("");
        $("#icon_realname").html("<i class='layui-icon layui-icon-ok-circle'></i>");
    }
}
//手机号格式验证
function checkphone() {
    //简单的手机号码正则表达式
    var str = /^[1](\d){10}$/;
    var phone = $("input[name='phone']").val().trim();
    if (!str.test(phone)){
        $("icon_phone").html("");
        $("#icon_phone").html("<i class='layui-icon layui-icon-close-fill'></i>");
        layer.alert("您输入的手机号码格式不对",{icon:0,title:'新增用户提示',time:2500});
    }else {
        $("#icon_phone").html("");
        $("#icon_phone").html("<i class='layui-icon layui-icon-ok-circle'></i>");
    }

}
//提交修改
function makesureupdate() {
    var username = $("input[name='username']").val().trim();
    var password = $("input[name='password']").val().trim();
    var realname = $("input[name='realname']").val().trim();
    var sex = $("input:radio:checked").val();
    var dwcode = $("select[name='dwcode']").val();
    var bmid = $("select[name='bmid']").val();
    var roleid = $("select[name='roles']").val();
    //获取选中的所有节点。
    var nodeIds = tree.getChecked('treeID');
    //获取选中节点中的id值的集合
    var perms = new Array();
    perms = getCheckedId(nodeIds);
    var phone = $("input[name='phone']").val().trim();
    var photo = $("#p_photo").html();
    var delflag = $("input[name='delflag']:checked").val();
    var del;
    if (delflag == "on"){
        del = 0;
    }else {
        del = 1;
    }
    //判断realname
    if (ValueIsEmpty(realname)){
        $("#icon_realname").html("");
        $("#icon_realname").html("<i class='layui-icon layui-icon-close-fill'></i>");
        layer.alert("姓名不能为空",{icon:0,title:'提示',time:2500});
        return false;
    }else {
        $("#icon_realname").html("");
        $("#icon_realname").html("<i class='layui-icon layui-icon-ok-circle'></i>");
    }
    //判断单位
    if (dwcode == "wscjw"){
        layer.alert("请选择单位",{icon:0,title:'提示',time:2500});
        return false;
    }
    //判断部门
    if (bmid == 0){
        layer.alert("请选择部门",{icon:0,title:'提示',time:2500});
        return false;
    }
    //判断角色
    if (roleid == 0){
        layer.alert("请选择权限",{icon:0,title:'提示',time:2500});
        return false;
    }
    //判断权限
    if(perms.length == 0){
        layer.alert("请选择权限",{icon:0,title:'提示',time:2500});
        return false;
    }
    $.ajax({
        url: "/userAdmin/updateuser",
        data: {"username":username,"password":password,"realname":realname,"sex":sex,"dwcode":dwcode,"bmid":bmid,"roleid":roleid,"phone":phone,"photo":photo,"delflag":del},
        type: "post",
        beforeSend: function (request) {
            request.setRequestHeader("Token", GetToken("Token"));
        },
        success: function (result, textStatus, request) {
            var token = request.getResponseHeader("Token");
            SetToken("Token", token);
            if (result.code == 100) {
               layer.alert("修改用户信息成功",{icon: 0, title: '修改用户提示', time: 25000},function () {
                   parent.layer.closeAll();
               })
            }else if (result.code == 200){
                layer.alert("修改用户信息失败",{icon: 0, title: '修改用户提示', time: 25000});
            }else if(result.code == 230){
                layer.alert(result.msg,{icon: 0, title: '修改用户提示', time: 25000});
            }
            else if (result.code == 320) {
                top.layer.alert("会话已过期，请您重新登录", {icon: 0, title: '会话过期提示', time: 2500}, function () {
                    top.window.location.href = "/login";
                });
            }
        }
    })
}
//获取所有的tree选中的id
function getCheckedId(data) {
    var id = "";
    $.each(data, function (index, item) {
        if (id != "") {
            id = id + "," + item.id;
        }
        else {
            id = item.id;
        }
        //item 没有children属性
        if (item.children != null) {
            var i = getCheckedId(item.children);
            if (i != "") {
                id = id + "," + i;
            }
        }
    });
    return id;
}
//获取回显role信息的权限id
function getRolePermID(data) {
    $.each(data,function (index,item) {
        tree.setChecked('treeID',[item]);
    })
}
