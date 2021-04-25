/**
 * 上传头像并回显
 */
layui.use(['upload','layer'],function (){
    var upload = layui.upload;
    var layer = layui.layer;
    var $ = layui.jquery;
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
                layer.alert("上传失败",{icon:5,time:2500,title:'更换头像'})
            }else if (result.code == 250){
                top.layer.alert("会话已过期，请您重新登录",{icon:0,title: '会话过期提示',time: 2500},function (){
                    top.window.location.href="/login";
                });
            }
        }
    })
})
$(function (){
    $.ajax({
        url:"/userinfo/showuserphoto",
        type:'post',
        beforeSend:function (request) {
            request.setRequestHeader("Token",GetToken("Token"));
        },
        success:function (result,textStatus,request) {
            var token = request.getResponseHeader("Token");
            SetToken("Token",token);
            if(result.code == 100){
                var str = "/images/"+result.data;
                //var str = result.data;
                $("#show_img").attr("src",str);
            }else if(result.code == 200) {
                $("#show_img").attr("src","/images/defaultPhoto.jpg");
            }else if (result.code == 320){
                top.layer.alert("会话已过期，请您重新登录",{icon:0,title: '会话过期提示',time: 2500},function (){
                    top.window.location.href="/login";
                });
            }
        }
    })
})
function sureuserphoto() {
    var photoname = $("#p_photo").html();
    //调用 core.js 里自定义方法，判断空值
    if(ValueIsEmpty(photoname)){
        layer.alert("请先上传头像",{icon:0,time:2500,title: '修改头像提示'});
        return false;
    }
    //alert(photoname);
    $.ajax({
        url:'/userinfo/sureupdatephoto',
        type:'post',
        data:{"photoname":photoname},
        beforeSend:function (request){
            request.setRequestHeader("Token",GetToken("Token"));
        },
        success:function (result,textStatus,request) {
            var token = request.getResponseHeader("Token");
            SetToken("Token",token);
            if(result.code == 100) {
                layer.alert("修改头像成功",{icon:6,tile:'修改头像',time:25000},function () {
                    parent.layer.closeAll();
                })
            }else if(result.code == 250){
                layer.alert("修改头像失败",{icon:5,tile:'修改头像',time:2500});
            }else if (result.code == 320){
                top.layer.alert("会话已过期，请您重新登录",{icon:0,title: '会话过期提示',time: 2500},function (){
                    top.window.location.href="/login";
                });
            }
        }
    })
}