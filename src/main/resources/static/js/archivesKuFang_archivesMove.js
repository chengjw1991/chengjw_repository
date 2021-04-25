var element;
var laydate;
var $;
var table;
var form;
var layer;
layui.use(['element','laydate','table','form','layer'],function (){
    element = layui.element;
    laydate = layui.laydate;
    $ = layui.jquery;
    form = layui.form;
    table = layui.table;
    layer = layui.layer;
    $(".layuidate").each(function (k,obj){
        laydate.render({
            elem:obj,
            max:getCurrentDate()
        })
    })
});
// 通过受理号查询企业
function selectinfo() {
    var condition = $("#condition").val();
    var content = $("input[name='content']").val().trim();
    //验证是否为空
    if(ValueIsEmpty(content)){
        top.layer.alert("请输入您的查询条件",{icon:0,time:2500,title:'大人！'});
        return false;
    }
    //判断需要查询的数据是否存在
    if(!checkslh(slh)){
        top.layer.alert("您输入的受理号已经存在，请勿将重复添加",{icon:0,title:'大人！',time:2500});
        return false;
    }
    $.ajax({
        url: "/archivesKuFang/selectinfobyslh",
        type: 'post',
        data:{"slh":slh},
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
    var info = result.data;
    var html = "<tr id='"+info.slh+"'>"+
        "<td style='text-align: center'><a href='javascript:;' onclick='removedata("+info.slh+")'><i class='layui-icon layui-icon-close'></i></a></td>"+
        "<td style='text-align: center'><input class='checked' type='checkbox' onclick='getborrow()'></td>"+
        "<td class='slzt' style='text-align: center'>"+(info.slzt==undefined?'':info.slzt)+"</td>"+
        "<td>"+(info.kh==undefined?'':info.kh)+"</td>"+
        "<td class='slh'>"+(info.slh==undefined?'':info.slh)+"</td>"+
        "<td class='slys' style='text-align: center'>"+(info.slys==undefined?'':info.slys)+"</td>"+
        "<td class='slrq' style='text-align: center'>"+(info.slrq==undefined?'':info.slrq)+"</td>"+
        "<td style='text-align: center'>"+(info.ywlx==undefined?'':info.ywlx)+"</td>"+
        "<td class='qymc' style='word-wrap: break-word;word-break: break-all;'>"+(info.qymc==undefined?'':info.qymc)+"</td>"+
        "<td>"+(info.tyshxydm==undefined?'':info.tyshxydm)+"</td>"+
        "<td>"+(info.zch==undefined?'':info.zch)+"</td>"+
        "<td>"+(info.gxjg==undefined?'':info.gxjg)+"</td>"+
        "<td style='word-wrap: break-word;word-break: break-all;'>"+(info.qylx==undefined?'':info.qylx)+"</td>"+
        "<td style='text-align: center'>"+(info.ztzt==undefined?'':info.ztzt)+"</td>"+
        "<input type='hidden' name='isborrow' value='"+info.isborrow+"'>"
    "</tr>"
    $("tbody").append(html);
}
//遍历比较
function checkslh(qymc){
    var isTrue = true;
    //遍历表格中的行，并表里其中的class=slh的列，如果其中已经存在slh，则提示重复
    $("tbody tr").each(function () {
        var value = $(this).find(".slh").html();
        if(value == slh){
            isTrue = false;
        }
    })
    return isTrue;
}
//移除本行数据
function removedata(slh) {
    var id = "#"+slh;
    $(id).remove();
}
//移除所有数据
function removealldata() {
    var data = $("tbody tr");
    if (data.length == 0){
        return false;
    }
    top.layer.alert("您确定要移除所有的档案卷吗？",{icon:0,title:'大人请三思啊！',time:25000},function (){
        $("tbody").children().remove();
        parent.layer.closeAll();
    });
}
//确认迁出
function sureqc() {
    var elements = $("tbody tr");
    if (elements.length==0){
        top.layer.alert("请您先查询档案卷",{icon:0,title:'大人！请看！',time:2500});
        return false;
    }
    if (getSLZT("入库")>0){
        top.layer.alert("请先移除已经入库的档案",{icon:0,title:'大人！请看！',time:2500});
        return false;
    }
    var slh = getSLH();
    $.ajax({
        url: "/archivesAdmin/updateslxxofrk",
        type: 'post',
        data:{"slh":slh},
        beforeSend: function (request) {
            request.setRequestHeader("Token",GetToken("Token"));
        },
        success: function (result, textStatus, request) {
            var token = request.getResponseHeader("Token");
            SetToken("Token", token);
            if (result.code == 100) {
                top.layer.msg("入库成功",{icon:6});
                $("tbody").children().remove();
                $("input[name='slh']").val("");
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
//确认迁入
function sureqr() {
    var elements = $("tbody tr");
    if (elements.length==0){
        top.layer.alert("请您先查询档案卷",{icon:0,title:'大人！请看！',time:2500});
        return false;
    }
    if (getSLZT("已数字化")>0){
        top.layer.alert("请先移除还未入库的档案",{icon:0,title:'大人！请看！',time:2500});
        return false;
    }
    var slh = getSLH();
    $.ajax({
        url: "/archivesAdmin/updateslxxofck",
        type: 'post',
        data:{"slh":slh},
        beforeSend: function (request) {
            request.setRequestHeader("Token",GetToken("Token"));
        },
        success: function (result, textStatus, request) {
            var token = request.getResponseHeader("Token");
            SetToken("Token", token);
            if (result.code == 100) {
                top.layer.msg("数字化完成",{icon:6});
                $("tbody").children().remove();
                $("input[name='slh']").val("");
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
//获取所有的受理号
function getSLH() {
    var slhList = new Array();
    $("tbody tr").each(function () {
        var slh =  $(this).find(".slh").html();
        slhList.push(slh);
    })
    return slhList;
}
//判断所选的企业受理状态
function getSLZT(slzt) {
    var index = 0 ;
    $("tbody tr").each(function () {
        var value = $(this).find(".slzt").html();
        if (value == slzt){
            index += 1;
        }else {
            index += 0;
        }
    })
    return index;
}