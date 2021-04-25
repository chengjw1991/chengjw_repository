var element;
var $;
var table;
var form;
var layer;
layui.use(['element','laydate','table','form','layer'],function (){
    element = layui.element;
    $ = layui.jquery;
    form = layui.form;
    table = layui.table;
    layer = layui.layer;
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
        url: "/circulation/selectinfobyszhwc",
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
        "<td style='text-align: center'>"+(info.slys==undefined?'':info.slys)+"</td>"+
        "<td style='text-align: center'>"+(info.slrq==undefined?'':info.slrq)+"</td>"+
        "<td style='text-align: center'>"+(info.ywlx==undefined?'':info.ywlx)+"</td>"+
        "<td style='word-wrap: break-word;word-break: break-all;'>"+(info.qymc==undefined?'':info.qymc)+(info.yfp==1?'<span style="color: red;">(已预分批)</span>':'')+"</td>"+
        "<td>"+(info.tyshxydm==undefined?'':info.tyshxydm)+"</td>"+
        "<td>"+(info.zch==undefined?'':info.zch)+"</td>"+
        "<td>"+(info.gxjg==undefined?'':info.gxjg)+"</td>"+
        "<td style='word-wrap: break-word;word-break: break-all;'>"+(info.qylx==undefined?'':info.qylx)+"</td>"+
        "<td style='text-align: center'>"+(info.clrq==undefined?'':info.clrq)+"</td>"+
        "<td style='text-align: center'>"+(info.ztzt==undefined?'':info.ztzt)+"</td>"+
        "<input type='hidden' name='isborrow' value='"+info.isborrow+"'>"+
        "<input type='hidden' name='isyfp' value='"+info.yfp+"'>"+
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
//确认预分批
function sureyfp() {
    var elements = $("tbody tr");
    if (elements.length==0){
        top.layer.alert("请您先查询档案卷",{icon:0,title:'大人！请看！',time:2500});
        return false;
    }
    if (getyfp()>0){
        top.layer.alert("请先移除已经预分批过的档案",{icon:0,title:'大人！请看！',time:2500});
        return false;
    }
    //alert(getyfp()+"----");
    var slh = getSLH();
    $.ajax({
        url: "/circulation/updateslxxofyfp",
        type: 'post',
        data:{"slh":slh},
        beforeSend: function (request) {
            request.setRequestHeader("Token",GetToken("Token"));
        },
        success: function (result, textStatus, request) {
            var token = request.getResponseHeader("Token");
            SetToken("Token", token);
            if (result.code == 100) {
                top.layer.msg("预分批完成",{icon:6});
                $("tbody").children().remove();
                $("input[name='slh']").val("");
            }else if(result.code == 200){
                top.layer.alert("获取数据失败，单位代码为空",{icon:0,title:'提示',time:2500});
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
//确认接收,获取所有的受理号
function getSLH() {
    var slhList = new Array();
    $("tbody tr").each(function () {
        var slh =  $(this).find(".slh").html();
        slhList.push(slh);
    })
    return slhList;
}
//借阅
function borrow() {
    var slh = getChecked();
    var isborrow = getborrow();
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
        content:'/read/archivesBorrow'
    })
}
//点击借阅时，获取选中行的slh的值
function getChecked() {
    var slhlist = new Array();
    $("tbody input[type='checkbox']:checked").each(function () {
        var slh = $(this).parent().parent().find(".slh").html();
        slhlist.push(slh);
    })
    return slhlist;
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
//点击预分批时，判断是否已经是预分批的档案
function getyfp() {
    var yfpnum = 0;
    $("tbody tr").each(function () {
        var yfp = $(this).parent().parent().find("input[name='isyfp']").val();
        yfpnum += yfp;
    })
    return yfpnum;
}