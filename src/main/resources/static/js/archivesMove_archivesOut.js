var element;
var $;
var form;
var layer;
layui.use(['element','form','layer'],function (){
    element = layui.element;
    laydate = layui.laydate;
    $ = layui.jquery;
    form = layui.form;
    layer = layui.layer;
    //下拉框改变事件
    form.on('select(condition)',function (data) {
        //获取下拉框的值
        var value = data.value;
        $("input[name='content']").val("");
    })
});
//初始化数据
$(function () {
    selectallDW();
})
//查询所有单位信息
function selectallDW(){
    $.ajax({
        url: "/dwxx/selectinfo",
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
                    $("#dwxx").append(html);
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
// 通过条件查询
function selectinfo() {
    var condition = $("#condition").val();
    var content = $("input[name='content']").val().trim();
    //alert(condition+"---");
    //验证是否为空
    if(ValueIsEmpty(content)){
        top.layer.alert("请输入您的查询条件",{icon:0,time:2500,title:'大人！'});
        return false;
    }
    // //判断需要查询的数据是否存在
    // if(!checkslh(condition)){
    //     top.layer.alert("您输入的受理号已经存在，请勿将重复添加",{icon:0,title:'大人！',time:2500});
    //     return false;
    // }
    $.ajax({
        url: "/archivesmove/selectqyinfo",
        type: 'post',
        data:{"condition":condition,"content":content},
        beforeSend: function (request) {
            request.setRequestHeader("Token",GetToken("Token"));
        },
        success: function (result, textStatus, request) {
            var token = request.getResponseHeader("Token");
            SetToken("Token", token);
            if (result.code == 100) {
                initTable(result);
            }else if(result.code == 200){
                top.layer.alert("获取数据失败，单位代码为空",{icon:0,title:'提示',time:2500});
            }else if(result.code == 220){
                top.layer.alert("没有查询到您想要的数据",{icon:0,title:'提示',time:2500});
            }else if(result.code == 230){
                top.layer.alert("您输入的数据为空",{icon:0,title:'提示',time:2500});
            } else if(result.code == 250){
                top.layer.alert("会话已过期，请您重新登录",{icon:0,title: '会话过期提示',time: 2500},function (){
                    top.window.location.href="/login";
                });
            }
        }
    })
}
//往表格里填充数据
function initTable(result) {
    var list = result.data;
    var html = "";
    $.each(list,function (i,info) {
        html += "<tr id='"+info.qyid+"'>"+
            "<td style='text-align: center'>"+(i+1)+"</td>"+
            "<td style='text-align: center'><a href='javascript:;' onclick='removedata("+info.qyid+")'><i class='layui-icon layui-icon-close'></i></a></td>"+
            "<td style='text-align: center'><input class='checked' type='checkbox'></td>"+
            "<td style='text-align: center'><a href='javascript:;' onclick='selectDAJ(\""+info.tyshxydm+"\")' style='color: #0000FF;'>查看档案卷</a></td>"+
            "<td class='qymc' style='word-wrap: break-word;word-break: break-all;'>"+(info.qymc==undefined?'':info.qymc)+"</td>"+
            "<td class='tyshxydm'>"+(info.tyshxydm==undefined?'':info.tyshxydm)+"</td>"+
            "<td>"+(info.zch==undefined?'':info.zch)+"</td>"+
            "<td style='text-align: center'>"+(info.frdm==undefined?'':info.frdm)+"</td>"+
            "<td>"+(info.dwmc==undefined?'':info.dwmc)+"</td>"+
            "<td style='word-wrap: break-word;word-break: break-all;'>"+(info.qylx==undefined?'':info.qylx)+"</td>"+
            "<input id='qyid' type='hidden' value='"+info.qyid+"'>"
            "</tr>"
    });
    $("tbody").html(html);
}
// //遍历比较
// function checkslh(qymc){
//     var isTrue = true;
//     //遍历表格中的行，并表里其中的class=slh的列，如果其中已经存在slh，则提示重复
//     $("tbody tr").each(function () {
//         var value = $(this).find(".slh").html();
//         if(value == slh){
//             isTrue = false;
//         }
//     })
//     return isTrue;
// }
//移除本行数据
function removedata(qyid) {
    var id = "#"+qyid;
    $(id).remove();
}
//移除所有数据
function removealldata() {
    var data = $("tbody tr");
    if (data.length == 0){
        return false;
    }
    layer.confirm("您确定要移除所有的档案卷吗？",{icon:0,title:'大人请三思啊！',time:2500},function (){
        layer.closeAll();
        $("tbody").children().remove();
    });
}
//确认迁出
function sureqc() {
    var tyshxydm = getCheckedTYSHXYDM();
    if (tyshxydm.length==0){
        top.layer.alert("请您先选择要迁出的档案卷",{icon:0,title:'大人！请看！',time:2500});
        return false;
    }
    var dwcode = $("#dwxx").val();
    if (dwcode == "wscjw"){
        top.layer.alert("请您先选择要迁入的单位",{icon:0,title:'大人！请看！',time:2500});
        return false;
    }
    var qyid = getCheckedQYID();
    //alert(tyshxydm+"--"+dwcode);
    $.ajax({
        url: "/archivesmove/archivesQC",
        type: 'post',
        data:{"tyshxydm":tyshxydm,"dwcode":dwcode},
        beforeSend: function (request) {
            request.setRequestHeader("Token",GetToken("Token"));
        },
        success: function (result, textStatus, request) {
            var token = request.getResponseHeader("Token");
            SetToken("Token", token);
            if (result.code == 100) {
                top.layer.msg("迁出成功！",{icon:6});
                removeinfo(qyid);
            }else if(result.code == 200){
                top.layer.alert("获取数据失败，单位代码为空",{icon:0,title:'提示',time:2500});
            }else if(result.code == 220){
                top.layer.alert("没有查询到您想要的数据",{icon:0,title:'提示',time:2500});
            }else if(result.code == 230){
                top.layer.alert("您输入的数据为空",{icon:0,title:'提示',time:2500});
            } else if(result.code == 250){
                top.layer.alert("会话已过期，请您重新登录",{icon:0,title: '会话过期提示',time: 2500},function (){
                    top.window.location.href="/login";
                });
            }
        }
    })
}
//全选或者全部取消
function setchecked() {
    //判断全选复选框是否选中
    var ischecked = $("#checkedall").prop("checked");
    //alert(ischecked);
    if (ischecked){
        $(".checked").each(function (){
            $(this).prop("checked",true);
        });
    }else {
        $(".checked").each(function (){
            $(this).prop("checked",false);
        });
    }
}
//获取选中行的shtyxydm的值
function getCheckedTYSHXYDM() {
    var list = new Array();
    $("tbody input[type='checkbox']:checked").each(function () {
        var tyshxydm = $(this).parent().parent().find(".tyshxydm").html();
        list.push(tyshxydm);
    })
    return list;
}
//获取选中行的qyid的值
function getCheckedQYID() {
    var list = new Array();
    $("tbody input[type='checkbox']:checked").each(function () {
        var qyid = $(this).parent().parent().find("#qyid").val();
        list.push(qyid);
    })
    return list;
}
//查看档案卷
function selectDAJ(tyshxydm) {
    //alert(tyshxydm);
    SetTYSHXYDM("TYSHXYDM",tyshxydm);
    top.layer.open({
        title:'档案卷信息',
        type:2,
        area:['1100px','450px'],
        content:'/archivesMove/archivesInfo'
    })
}
//移除选中的企业信息
function removeinfo(data) {
    $.each(data,function (i,item) {
        removedata(item);
    })
}
