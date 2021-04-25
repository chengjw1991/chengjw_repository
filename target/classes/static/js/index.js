var element;
var $;
var layer
layui.use(['element','layer'], function(){
    element = layui.element;
    layer = layui.layer;
    $ = layui.jquery;
    //监听 选项卡，点击选项卡时，左侧导航栏会自动跳转到对应的导航上
    element.on('tab(demo)',function (){
        //获取选中卡片的id
        var tab_id = $(".layui-tab-title .layui-this").attr("lay-id");
        //遍历导航栏中菜单的id
        $(".layui-nav-child dd a").each(function (k,obj){
            var menuid = $(obj).attr("id");
            if (tab_id==menuid){
                $(".layui-nav-child dd").removeClass("layui-this");
                $(obj).parent().addClass("layui-this");
            }
        });
    });
    showTab(81,"/userinfo/home","首页");
});

function showTab(id,url,title) {
    //alert(id);
    // 验证内容部分是否有选项卡
    var tabLi = $(".layui-tab-title li").length;
    //如果没有，那么新增一个
    var tabList = new Array();
    if (tabLi==0){
        createTab(id,url,title);
    }else {
        //遍历 layui-tab-title li
        $(".layui-tab-title li").each(function (){
            //获取 lay-id的值
            var layId = $(this).attr("lay-id");
            //添加到数组中
            tabList.push(layId);
        });
        //如果大于等于0 ，则表示有选项卡
        if(tabList.indexOf(id)>=0){
            element.tabChange('demo',id);
        }else {
            createTab(id,url,title)
        }
    }
}
function createTab(id,url,title) {
    element.tabAdd('demo',{
        title:title,
        content:'<iframe data-frameid="'+id+'" scrolling="yes" frameborder="0" src="'+url+'"></iframe>',
        id:id
    })
    element.tabChange('demo',id);
    element.render();
}
/**
 * 注销
 */
function  logout() {
    top.layer.confirm('您确认要退出吗？',{icon:0,title: '系统提示'},function (){
        $.ajax({
            url:'/logout',
            type:'post',
            beforeSend:function (request){
                request.setRequestHeader("Token",GetToken("Token"));
            },
            success:function (result){
                if(result.code == 100){
                    //删除缓存中的token
                    DeleteToken();
                    window.location.href = "/login";
                    // 强识刷新页面
                    window.location.reload();
                }else if (result.code == 250){
                    top.layer.alert("会话已过期，请您重新登录",{icon:0,title: '会话过期提示',time: 2500},function (){
                        top.window.location.href="/login";
                    });
                }
            },
            error:function (result, textStatus, errorThrown){
                top.layer.close(loading);
                if (result == 404 ){
                    top.window.location.href="/to_404";
                }else {
                    top.layer.msg('服务器好像出现问题了，请稍后再试',{icon:6,time:1500,title:'提示'});
                }
            }
        })
    })
}

/**
 * 修改密码
 */
function updatePassword() {
    top.layer.open({
        title:'修改密码',
        type:2,
        content:'/userinfo/updatePassword',
        area:['600px','350px'],
        offset:'150px',
        closeBtn:2,
    });
}
function updatephoto(){
    top.layer.open({
        title:'更新头像',
        type:2,
        content:'/userinfo/updatePrifilePhoto',
        area:['600px','400px'],
        offset:'150px',
    });
}