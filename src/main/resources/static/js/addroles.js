var $;
var tree;
var layer;
var form;
layui.use(['tree','form','layer'],function () {
    tree = layui.tree;
    $ = layui.jquery;
    form = layui.form;
    layer = layui.layer;
});

//初始化数据
$(function () {
    selectallperimission();
    selectalldepartment();
})

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
                    showCheckbox: true,
                    accordion: true,
                    children:false
                });
                tree.setChecked('treeID',[7000]);
            }else if(result.code == 200){
                top.layer.alert("获取权限信息失败",{icon:0,title:'新增角色提示',time:2500});
            }else if(result.code == 250){
                top.layer.alert("会话已过期，请您重新登录",{icon:0,title: '会话过期提示',time: 2500},function (){
                    top.window.location.href="/login";
                });
            }
        }
    })
}
//查询所有单位信息
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
                $.each(result.data,function (i,item){
                    //alert(item.name);
                    var html = "<option value='"+item.code+"'>"+item.name+"</option>"
                    $("#dwmc").append(html);
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
//验证角色是否重复及是否为空
function  checkrole() {
    var rolename = $("input[name='rolename']").val().trim();
    if(rolename == ""){
        $("#form_icon").html("");
        $("#form_icon").html("<i class='layui-icon layui-icon-close-fill'></i>");
        layer.alert("角色名称不能为空",{icon:0,title:'新增角色提示',time:2500});
    }else {
        $.ajax({
            url: "/rolesAdmin/checkrole",
            data: {"rolename": rolename},
            type: "post",
            beforeSend: function (request) {
                request.setRequestHeader("Token", GetToken("Token"));
            },
            success: function (result, textStatus, request) {
                var token = request.getResponseHeader("Token");
                SetToken("Token", token);
                if (result.code == 100) {
                    $("#form_icon").html("");
                    $("#form_icon").html("<i class='layui-icon layui-icon-ok-circle'></i>");
                } else if (result.code == 200) {
                    layer.alert("您输入的角色已存在", {icon: 0, title: '新增角色提示', time: 25000});
                    $("#form_icon").html("");
                    $("#form_icon").html("<i class='layui-icon layui-icon-close-fill'></i>");
                    $("input[name='username']").val();
                } else if (result.code == 320) {
                    top.layer.alert("会话已过期，请您重新登录", {icon: 0, title: '会话过期提示', time: 2500}, function () {
                        top.window.location.href = "/login";
                    });
                }
            }
        })
    }
}
//提交新增
function makesureinsert() {
    var rolename = $("input[name='rolename']").val().trim();
    var dwid = $("select[name='dwid']").val().trim();
    //获取选中的所有节点。
    var nodeIds = tree.getChecked('treeID');
    //获取选中节点中的id值的集合
    var perms = new Array();
    perms = getCheckedId(nodeIds);
    //alert(perms);
    var explain =$("textarea[name='explain']").val().trim();
    if (ValueIsEmpty(rolename)){
        $("#form_icon").html("");
        $("#form_icon").html("<i class='layui-icon layui-icon-close-fill'></i>");
        layer.alert("角色名称不能为空",{icon:0,title:'新增角色提示',time:2500});
        return false;
    }else {
        $("#form_icon").html("");
        $("#form_icon").html("<i class='layui-icon layui-icon-ok-circle'></i>");
    }
    if (dwid == 0){
        layer.alert("请为您的角色选择单位",{icon:0,title:'新增角色提示',time:2500});
        return false;
    }
    if(perms == ""){
        layer.alert("请为您的角色选择权限",{icon:0,title:'新增角色提示',time:2500});
        return false;
    }
    $.ajax({
        url: "/rolesAdmin/addrole",
        data: {"rolename": rolename,"dwxxcode":dwid,"perms":perms,"explain":explain},
        type: "post",
        beforeSend: function (request) {
            request.setRequestHeader("Token", GetToken("Token"));
        },
        success: function (result, textStatus, request) {
            var token = request.getResponseHeader("Token");
            SetToken("Token", token);
            if (result.code == 100) {
               layer.alert("新增角色成功",{icon: 0, title: '新增角色提示', time: 25000},function () {
                   parent.layer.closeAll();
               })
            } else if (result.code == 210) {
                layer.alert("您输入的角色已存在", {icon: 0, title: '新增角色提示', time: 25000});
                $("#form_icon").html("");
                $("#form_icon").html("<i class='layui-icon layui-icon-close-fill'></i>");
            }else if (result.code == 200){
                layer.alert("新增角色失败",{icon: 0, title: '新增角色提示', time: 25000});
            }else if (result.code == 320) {
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
