var layer;
var laypage;
var $;
var form;
var laydate;
layui.use(['layer','laypage','form','laydate'],function (){
    layer = layui.layer;
    laypage = layui.laypage;
    form = layui.form;
    $ = layui.jquery;
    laydate = layui.laydate;
    //渲染日期
    $(".date").each(function (i,item) {
        laydate.render({
            elem:item,
            max:getCurrentDate()
        });
    })
})
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
// 初始化
$(function (){
    var pageindex;
    var pagecount;
    init(pageindex,pagecount);
    //首次跳转到页面时查询数据并初始化回显查询日期
    $("input[name=startdate]").val(getCurrentDate());
    $("input[name=enddate]").val(getCurrentDate());

})
function init(pageindex,pagecount) {
    var username = $("input[name='username']").val().trim();
    var startdate = $("input[name='startdate']").val().trim();
    var enddate = $("input[name='enddate']").val().trim();
    if (pageindex == undefined && pagecount == undefined){
        pageindex = 1;
        pagecount = 20;
    }
    $.ajax({
        url: "/systemLog/selectoperationlogduringdate",
        type: "post",
        data:{"pageindex":pageindex,"pagecount":pagecount,"username":username,"startdate":startdate,"enddate":enddate},
        beforeSend: function (request) {
            request.setRequestHeader("Token", GetToken("Token"));
        },
        success: function (result, textStatus, request) {
            var token = request.getResponseHeader("Token");
            SetToken("Token", token);
            if (result.code == 100) {
                datatable(result.data.datalist);
                initpage(result.data.sumcount,result.data.pagecurr,result.data.pagecount);
            }else if (result.code == 200){
                layer.msg("获取登录日志失败",{icon: 0, time: 1500});
            }else if (result.code == 220){
                top.layer.alert("您要查询的日志数据不存在！",{icon:0});
                    var username = $("input[name='username']").val("");
                    var startdate = $("input[name='startdate']").val("");
                    var enddate = $("input[name='enddate']").val("");
            }
            else if (result.code == 250) {
                top.layer.alert("会话已过期，请您重新登录", {icon: 0, title: '会话过期提示', time: 2500}, function () {
                    top.window.location.href = "/login";
                });
            }
        }
    })
}
function datatable(data){
    var html = "";
    $.each(data,function (i,item) {
        html += "<tr>"+
            "<td>"+(i+1)+"</td>"+
            "<td>"+(item.username==undefined?'':item.username)+"</td>"+
            "<td>"+(item.realname==undefined?'':item.realname)+"</td>"+
            "<td>"+(item.clientip==undefined?'':item.clientip)+"</td>"+
            "<td style='word-wrap:break-word;word-break:break-all;text-align: left;'>"+(item.classname==undefined?'':item.classname)+"</td>"+
            "<td style='word-wrap:break-word;word-break:break-all;'>"+(item.fangfaname==undefined?'':item.fangfaname)+"</td>"+
            "<td style='word-wrap:break-word;word-break:break-all;'>"+(item.params==undefined?'':item.params)+"</td>"+
            "<td>"+(item.operationdate==undefined?'':item.operationdate)+"</td>"+
            "<td>"+(item.date==status?'':item.status)+"</td>"+
            "</tr>"
    });
    $("tbody").html(html);
}
//分页组件
function initpage(sum,pagecurr,pagecount) {
    laypage.render({
        elem:'loginlog_page',
        count:sum,
        curr:pagecurr,
        groups:3,
        limit:pagecount,
        limits:[20,50,100],
        layout:['prev','page','next','count','limit','skip'],
        jump:function (obj,first) {
            // 首次不执行
            if (!first) {
                var index = obj.curr;
                var count = obj.limit;
                init(index, count);
            }
        }
    });
}
function selectoperationlogbyname() {
    init(1,20);
}
