var $;
var layer;
var form;
var laydate;
layui.use(['tree','form','layer','laydate'],function () {
    $ = layui.jquery;
    form = layui.form;
    layer = layui.layer;
    laydate = layui.laydate;

    $(".layuidate").each(function (i,item) {
        laydate.render({
            elem:item,
            max:getCurrentDate()
        })
    })
});
//获取当天日期
function getCurrentDate(){
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth()+1;
    var strdate = date.getDate();
    if (month>0 && month<10){
        month = "0"+ month;
    }
    if (strdate>0 && strdate<10){
        strdate = "0"+ strdate;
    }
    var currdate = year +"-" + month + "-" + strdate;
    return currdate;
}
//初始化数据
$(function () {
    selectalldepartment();
    selectCount();
})

//查询所有单位信息
function selectalldepartment(){
    $.ajax({
        url: "/dwxx/selectalldwxx",
        type: "post",
        async: false,
        beforeSend: function (request) {
            request.setRequestHeader("Token",GetToken("Token"));
        },
        success: function (result, textStatus, request) {
            var token = request.getResponseHeader("Token");
            SetToken("Token", token);
            if (result.code == 100) {
                $.each(result.data,function (i,item){
                    //alert(item.name);
                    var html = "<option value='"+item.code+"'>"+item.name+"</option>";
                    $("#dwmc").append(html);
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
function selectCount() {
    var loding = layer.lo
    var code = $("#dwmc").val();
    var startdate = $("input[name='startdate']").val().trim();
    var enddate = $("input[name='enddate']").val().trim();
    //比较时间大小
    var startdate_ = new Date(startdate);
    var enddate_ = new Date(enddate);
    if (startdate_>enddate_){
        top.layer.alert("起止时间不能大于截止时间，请您重新输入",{icon:0,title:'大人请看！',time:2500});
        return false;
    }
    $.ajax({
        url: "/ArchivesStatistics/selectCount",
        type: "post",
        data: {"code":code,"startdate":startdate,"enddate":enddate},
        beforeSend: function (request) {
            request.setRequestHeader("Token",GetToken("Token"));
        },
        success: function (result, textStatus, request) {
            var token = request.getResponseHeader("Token");
            SetToken("Token", token);
            if (result.code == 100) {
                $("#tr_td").html(getDWMC(code));
                createtable(result.data);
            }else if(result.code == 220){
                top.layer.alert("查询不到数据",{icon:0,title:'统计',time:2500});
                $("tbody").html("<tr><td colspan='10' style='color: red;'>对不起！没有您想要查询的数据！</td></tr>");
            }else if(result.code == 250){
                top.layer.alert("会话已过期，请您重新登录",{icon:0,title: '会话过期提示',time: 2500},function (){
                    top.window.location.href="/login";
                });
            }
        }
    })
}
//获取单位名称
function getDWMC(code) {
    if (code ==""){
        return "厦门全市档案统计";
    }else  if (code == "350200"){
        return "厦门市市场监督管理局档案统计";
    }else  if (code == "350203"){
        return "厦门市思明区市场监督管理局档案统计";
    }else  if (code == "350206"){
        return "厦门市湖里区市场监督管理局档案统计";
    }else  if (code == "350211"){
        return "厦门市集美区市场监督管理局档案统计";
    }else  if (code == "350212"){
        return "厦门市海沧区市场监督管理局档案统计";
    }else  if (code == "350213"){
        return "厦门市翔安区市场监督管理局档案统计";
    }else  if (code == "350223"){
        return "厦门市同安区市场监督管理局档案统计";
    }else  if (code == "350297"){
        return "厦门市大嶝工商处档案统计";
    }else  if (code == "350298"){
        return "厦门市火炬自贸区市场监督管理局档案统计";
    }else  if (code == "350200"){
        return "厦门市海空港保税区市场监督管理局档案统计";
    }
}
function createtable(data) {
    var html = ""
    var yslcount = 0;
    var yjscount = 0;
    var szhzcount = 0;
    var yszhcount = 0;
    var ckcount  = 0;
    var rkcount = 0;
    var qrcount = 0;
    var qccount = 0;
    var allcount = 0;
    $.each(data,function (i,item){
        var sum = parseInt(item.ysl)+parseInt(item.yjs)+parseInt(item.szhz)+parseInt(item.yszh)+parseInt(item.ck)+parseInt(item.rk);
        html += "<tr>"+
            "<td>"+item.qylx+"</td>"+
            "<td>"+item.ysl+"</td>"+
            "<td>"+item.yjs+"</td>"+
            "<td>"+item.szhz+"</td>"+
            "<td>"+item.yszh+"</td>"+
            "<td>"+item.ck+"</td>"+
            "<td>"+item.rk+"</td>"+
            // "<td>"+item.qr+"</td>"+
            // "<td>"+item.qc+"</td>"+
            "<td style='color: black;font-weight: bolder;'>"+sum+"</td>"+
            "</tr>";
        yslcount += parseInt(item.ysl);
        yjscount += parseInt(item.yjs);
        szhzcount += parseInt(item.szhz);
        yszhcount += parseInt(item.yszh);
        ckcount += parseInt(item.ck);
        rkcount += parseInt(item.rk);
        // qrcount += parseInt(item.qr);
        // qccount += parseInt(item.qc);
    })
    allcount += yslcount+yjscount+szhzcount+yszhcount+ckcount+rkcount;
    html += "<tr style='color: red;font-weight: bolder;'>"+
        "<td>总计</td>"+
        "<td>"+yslcount+"</td>"+
        "<td>"+yjscount+"</td>"+
        "<td>"+szhzcount+"</td>"+
        "<td>"+yszhcount+"</td>"+
        "<td>"+ckcount+"</td>"+
        "<td>"+rkcount+"</td>"+
        // "<td>"+qrcount+"</td>"+
        // "<td>"+qccount+"</td>"+
        "<td>"+allcount+"</td>"+
        "</tr>";
    $("tbody").html(html);
}