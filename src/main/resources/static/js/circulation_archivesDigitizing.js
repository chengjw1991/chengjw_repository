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
function selectinfobyslh() {
    var slh = $("input[name='slh']").val().trim();
    //验证是否为空
    if(ValueIsEmpty(slh)){
        top.layer.alert("请您输入正确的受理号",{icon:0,time:2500,title:'大人！'});
        return false;
    }
    //判断需要查询的数据是否存在
    if(!checkslh(slh)){
        top.layer.alert("您输入的受理号已经存在，请勿将重复添加",{icon:0,title:'大人！',time:2500});
        return false;
    }
    $.ajax({
        url: "/circulation/selectinfobyslhofyjs",
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
        "<td style='text-align: center'>"+(info.clrq==undefined?'':info.clrq)+"</td>"+
        "<td style='text-align: center'>"+(info.ztzt==undefined?'':info.ztzt)+"</td>"+
        "<input type='hidden' name='isborrow' value='"+info.isborrow+"'>"
    "</tr>"
    $("tbody").append(html);
}
//遍历比较
function checkslh(slh){
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
    top.layer.confirm("您确定要移除所有的档案卷吗？",{icon:0,title:'大人请三思啊！',time:25000},function (){
        parent.layer.closeAll();
        $("tbody").children().remove();
    });
}
//确认数字化移交
function szhyj() {
    var elements = $("tbody tr");
    if (elements.length==0){
        top.layer.alert("请您先查询档案卷",{icon:0,title:'大人！请看！',time:2500});
        return false;
    }
    if (getSLZT("数字化中")>0){
        top.layer.alert("请先移除已经数字化移交过的档案",{icon:0,title:'大人！请看！',time:2500});
        return false;
    }
    var slh = getSLH();
    $.ajax({
        url: "/circulation/updateslxxofszhz",
        type: 'post',
        data:{"slh":slh},
        beforeSend: function (request) {
            request.setRequestHeader("Token",GetToken("Token"));
        },
        success: function (result, textStatus, request) {
            var token = request.getResponseHeader("Token");
            SetToken("Token", token);
            if (result.code == 100) {
                top.layer.msg("数字化移交完成",{icon:6});
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
//确认数字化完成
function szhwc() {
    var elements = $("tbody tr");
    if (elements.length==0){
        top.layer.alert("请您先查询档案卷",{icon:0,title:'大人！请看！',time:2500});
        return false;
    }
    if (getSLZT("已接收")>0){
        top.layer.alert("请先移除还未数字化移交的档案",{icon:0,title:'大人！请看！',time:2500});
        return false;
    }
    var slh = getSLH();
    $.ajax({
        url: "/circulation/updateslxxtoyszh",
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
//修改页数
function updateys() {
    //获取所有的选中的行的受理号
    var slh = getChecked();
    var qymc = getCheckedQYMC();
    var slys = getCheckedSLYS();
    if(slh.length == 0){
        top.layer.alert("请您先选择想要修改的档案卷",{icon:0,title:'大人！请看！',time:2500});
        return false;
    }
    if (slh.length>1){
        top.layer.alert("一次性只能修改一卷档案的页数",{icon:0,title:'大人！请看！',time:2500});
        return false;
    }
    SetSLH("SLH",slh[0]);
    SetQYMC("QYMC",qymc[0]);
    SetSLYS("SLYS",slys[0]);
    //alert(slh[0]+"---"+qymc[0]);
    top.layer.open({
        title:'修改档案卷页数',
        type:2,
        resize:false,
        area:['500px','300px'],
        content: '/acceptAdmin/UpdateCompanyInfo',
        end:function () {

            $(".checked").each(function (){
                //如果是选中状态，则修改页码
                if ($(this).prop("checked")){
                    $(this).parent().parent().find(".slys").html(getSLYS("SLYS"));
                }
                //取消选中状态
                $(this).prop("checked",false);
            });
        }
    });
}
//修改日期
function updaterq() {
    //获取所有的选中的行的受理号
    var slh = getChecked();
    var qymc = getCheckedQYMC();
    var slrq = getCheckedSLRQ();
    if(slh.length == 0){
        top.layer.alert("请您先选择想要修改的档案卷",{icon:0,title:'大人！请看！',time:2500});
        return false;
    }
    if (slh.length>1){
        top.layer.alert("一次性只能修改一卷档案的页数",{icon:0,title:'大人！请看！',time:2500});
        return false;
    }
    SetSLH("SLH",slh[0]);
    SetQYMC("QYMC",qymc[0]);
    SetSLRQ("SLRQ",slrq[0]);
    //alert(slh[0]+"--"+slrq[0]);
    top.layer.open({
        title:'修改受理日期',
        type:2,
        resize:false,
        area:['500px','300px'],
        content: '/acceptAdmin/UpdateSLRQ',
        end:function () {
            $(".checked").each(function (){
                //如果是选中状态，则修改页码
                if ($(this).prop("checked")){
                    $(this).parent().parent().find(".slrq").html(getSLRQ("SLRQ"));
                }
                //取消选中状态
                $(this).prop("checked",false);
            });
        }
    });
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
//借阅
function borrow() {
    var slh = getChecked();
    var isborrow = getborrow();
    //alert(isborrow);
    if (slh == ""){
        top.layer.alert("请选择您要借阅档案卷",{icon:0,title:'大人！请看！',time:2500});
        return false;
    }
    if (slh.length>1){
        top.layer.alert("一次性只能借阅一卷档案",{icon:0,title:'大人！请看！',time:2500});
        return false;
    }
    if(isborrow[0] == 1){
        top.layer.alert("本卷档案已经借出",{icon:0,title:'大人！请看！',time:2500});
        $(".checked").each(function (){
            //取消选中状态
            $(this).prop("checked",false);
        });
        return false;
    }
    //将slh存入浏览器缓存中
    SetSLH("SLH",slh[0]);
    //弹窗
    top.layer.open({
        title:'档案借阅',
        type:2,
        offset:100,
        area:['800px','650px'],
        content:'/read/archivesBorrow',
        end:function () {
            $(".checked").each(function (){
                //取消选中状态
                $(this).prop("checked",false);
            });
        }
    })
}
//获取选中行的slh的值
function getChecked() {
    var slhlist = new Array();
    $("tbody input[type='checkbox']:checked").each(function () {
        var slh = $(this).parent().parent().find(".slh").html();
        slhlist.push(slh);
    })
    return slhlist;
}
//获取选中行的qymc的值
function getCheckedQYMC() {
    var qymclist = new Array();
    $("tbody input[type='checkbox']:checked").each(function () {
        var qymc = $(this).parent().parent().find(".qymc").html();
        qymclist.push(qymc);
    })
    return qymclist;
}
//获取选中行的slys的值
function getCheckedSLYS() {
    var slyslist = new Array();
    $("tbody input[type='checkbox']:checked").each(function () {
        var slys = $(this).parent().parent().find(".slys").html();
        slyslist.push(slys);
    })
    return slyslist;
}
//获取选中行的slrq的值
function getCheckedSLRQ() {
    var slrqlist = new Array();
    $("tbody input[type='checkbox']:checked").each(function () {
        var slrq = $(this).parent().parent().find(".slrq").html();
        slrqlist.push(slrq);
    })
    return slrqlist;
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
//点击借阅时，获取选中行的 借阅值 isborrow
function getborrow() {
    var borrowlist = new Array();
    $("tbody input[type='checkbox']:checked").each(function () {
        var borrow = $(this).parent().parent().find("input[name='isborrow']").val();
        borrowlist.push(borrow);
    })
    return borrowlist;
}