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
//初始化
$(function () {
    selectyfp(1,20);
})
//根据条件查询档案
function  selectyfp(pageindex,pagecount) {
    var condition = $("#condition").val();
    var content = $("input[name='content']").val().trim();
    var startdate = $("input[name='startdate']").val().trim();
    var enddate = $("input[name='enddate']").val().trim();
    var startdate_ = new Date(startdate);
    var enddate_ = new Date(enddate);
    if (startdate_>enddate_){
        top.layer.alert("截止日期不能小于起始日期！",{icon:0,title:'大人！请看！',time: 2500});
        $("input[name='startdate']").val("");
        $("input[name='enddate']").val("")
        return false;
    }
    //渲染表格
    table.render({
        elem:'#data',
        id:'dataTable',
        url:'/circulation/SelectInfoBycondition',
        headers:{'Token':GetToken("Token")},
        method:'post',
        request:{ //自定义分页参数名称
            pageName:'pageindex',
            limitName:'pagecount'
        },
        parseData:function(req){ //将原始数据进行转换
            return{
                "code":req.code,
                "msg":req.msg,
                "count":req.data.sumcount,
                "data":req.data.datalist
            };
        },
        where:{condition:condition,content:content,startdate:startdate,enddate:enddate},
        height:620,
        size:'sm',
        limit: 20,
        page:{
            curr:pageindex,
            limit:pagecount,
            limits:[20,50,100]
        },
        cols:[[
            {type:'checkbox',align:'center',width:50},
            {type:'numbers',align:'center',title:'ID',width:45},
            {field: 'slzt',align:'center',title:'受理状态',width: 80},
            {field: 'kh',align:'center',title:'捆号',width: 130,sort:true,templet:function (d) {
                    return '<div style="text-align: left">'+(d.kh==undefined?(d.dwjc+'-'):d.kh)+'</div>';
                }},
            {field: 'slh',align:'center',title:'受理号',width: 120,sort:true,templet:function (d) {
                    return '<div style="text-align: left">'+d.slh+'</div>';
                }},
            {field: 'ywlx',align:'center',title:'业务类型',width: 80},
            {field: 'slrq',align:'center',title:'受理日期',width: 105,sort:true},
            {field: 'slys',align:'center',title:'页数',width: 60},
            {field: 'qymc',align:'center',title:'企业名称',width: 260,templet:function (d) {
                    return '<div style="text-align: left;padding-right: 5px;overflow: hidden;white-space: nowrap;text-overflow:ellipsis;">'+d.qymc+'</div>';
                }},
            {field: 'tyshxydm',align:'center',title:'统一社会信用代码',width: 160,templet:function (d) {
                    return '<div style="text-align: left">'+d.tyshxydm+'</div>';
                }},
            {field: 'zch',align:'center',title:'注册号',width: 130,templet:function (d) {
                    return '<div style="text-align: left">'+d.zch+'</div>';
                }},
            {field: 'qylx',align:'left',title:'企业类型',width: 87},
            {field: 'clrq',align:'center',title:'成立日期',width: 105,sort:true},
            {field: 'gxjg',align:'center',title:'管辖机关',width: 110},
            {field: 'ztzt',align:'center',title:'状态',width: 60},
            {field: 'zrdd',align:'center',title:'转入待定',width: 90,templet:function (d) {
                  return '<div style="text-align: left">'+(d.zrdd==0?'未转入待定':'<span style="color: red;">已转入待定</span>')+'</div>';
            }}
        ]],
    })
}
//转入待定
function todaiding() {
    var checkStatus = table.checkStatus('dataTable');
    var slh = getChecked(checkStatus.data);
    //alert(slh);
    if (slh.length == 0){
        top.layer.alert("请选择您想要转入待定的档案",{icon:0,title:'大人！请看！',time:2500});
        return false;
    }
    $.ajax({
        url: "/circulation/updateinfolofisdd",
        type: "post",
        data:{"slh":slh},
        beforeSend: function (request) {
            request.setRequestHeader("Token",GetToken("Token"));
        },
        success: function (result, textStatus, request) {
            var token = request.getResponseHeader("Token");
            SetToken("Token", token);
            var html = "";
            if (result.code == 100) {
                top.layer.alert("档案转入待定成功",{icon:0,title:'提示',time:2500});
                selectyfp(1,20);
            }else if(result.code == 200){
                top.layer.alert("档案转入待定失败",{icon:0,title:'提示',time:2500});
            }else if (result.code == 230){
                top.layer.alert(result.msg,{icon:0,title:'提示',time:2500})
            }
            else if(result.code == 250){
                top.layer.alert("会话已过期，请您重新登录",{icon:0,title: '会话过期提示',time: 2500},function (){
                    top.window.location.href="/login";
                });
            }
        }
    })
}
//获取选中对象
function  getChecked(data) {
    var slhlist = new Array();
    $.each(data,function (i,item) {
        slhlist.push(item.slh);
    })
    return slhlist;
}