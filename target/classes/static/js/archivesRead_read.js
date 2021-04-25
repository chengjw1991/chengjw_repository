var element;
var laydate;
var $;
var table;
var form;
layui.use(['element','laydate','table','form'],function (){
    element = layui.element;
    laydate = layui.laydate;
    $ = layui.jquery;
    form = layui.form;
    table = layui.table;
    $(".layuidate").each(function (k,obj){
        laydate.render({
            elem:obj,
            max:getCurrentDate()
        })
    })
    //监听单元格,修改备注
    table.on('edit(data-table)',function (obj) {
        var value = obj.value;  //编辑之后的单元格的值
        var field = obj.field; // 被编辑的字段
        var slh = obj.data.slh; //被编辑的所在行的数据的slh；
        if (ValueIsEmpty(value)){
            top.layer.alert("捆号不能为空值",{icon:5});
            return false;
        }
        $.ajax({
            url:'/archivesRead/UpdateBzBySlh',
            type: "post",
            data: {"slh":slh,"bz":value},
            beforeSend: function (request) {
                request.setRequestHeader("Token", GetToken("Token"));
            },
            success: function (result, textStatus, request) {
                var token = request.getResponseHeader("Token");
                SetToken("Token", token);
                //alert(token);
                if (result.code == 100) {
                    top.layer.msg("修改备注成功",{icon:6});
                } else if (result.code == 200) {
                    top.layer.msg("修改备注失败",{icon:5});
                } else if (result.code == 230){
                    top.layer.msg("修改备注失败,输入出现空数据",{icon:5});
                } else if (result.code == 250) {
                    top.layer.alert("会话已过期，请您重新登录", {icon: 0, title: '会话过期提示', time: 2500}, function () {
                        top.window.location.href = "/login";
                    });
                }
            }
        })
    });
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
    var pageindex;
    var pagecount;
    selectallborrowreadinfo(1,20);
})
function selectallborrowreadinfo(pageindex,pagecount){
    var condition = $("#condition").val();
    var content = $("input[name='content']").val().trim();
    var startdate = $("input[name='startdate']").val().trim();
    var enddate = $("input[name='enddate']").val().trim();
    //渲染表格
    table.render({
        elem:'#data',
        id:'dataTable',
        url:'/archivesRead/selectallborrowreadinfo',
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
            {type:'checkbox',align:'center',title:'ID',width:40,fixed:'left'},
            {type:'numbers',align:'center',title:'ID',width:50},
            {field: 'isback',align:'center',title:'状态',width: 80,sort:true,templet:function (d) {
                    return '<div style="text-align: center;color:'+(d.isback==0?'black':'red')+'">'+(d.isback==0?'已归还':'已借出')+'</div>';
                }},
            {field: 'borrowname',align:'center',title:'借阅人',width: 80},
            {field: 'borrowdate',align:'center',title:'借阅日期',width: 100},
            {field: 'backtime',align:'center',title:'约定归还日期',width: 130,sort:true},
            {field: 'actualbackdate',align:'center',title:'实际归还日期',width: 130,sort:true},
            {field: 'username',align:'center',title:'借阅操作人',width: 100},
            {field: 'qymc',align:'center',title:'企业名称',width: 400,templet:function (d) {
                    return '<div style="text-align: left;padding-right: 5px;overflow: hidden;white-space: nowrap;text-overflow:ellipsis;">'+d.qymc+'</div>';
                }},
            {field: 'slh',align:'center',title:'受理号',width: 130,sort: true},
            {field: 'tyshxydm',align:'center',title:'统一社会信用代码',width: 170},
            {field: 'code',align:'center',title:'所属单位代码',width: 110},
            {field: 'bz',align:'center',title:'备注(点击编辑)',width: 150,edit:'text'},
        ]],
    })
}
//归还档案
function backarchives() {
    //获取选中项
    var checkedStatus = table.checkStatus('dataTable');
    //获取选中项的数据
    var slh = getChecked(checkedStatus.data);
    if(slh.length <1){
        top.layer.alert("请先选择您要归还的档案",{icon:0,title:'提示',time: 2500});
        return false;
    }
    //判断是否存在勾选未借出的档案
    var info = isback(checkedStatus.data);
    if (info){
        top.layer.alert("请勿勾选已归还的档案！",{icon:0,time:2500,title:'大人！请看！'});
        return false;
    }
    $.ajax({
        url:'/archivesRead/UpdateIsBackBySlh',
        type: "post",
        data: {"slh":slh},
        beforeSend: function (request) {
            request.setRequestHeader("Token", GetToken("Token"));
        },
        success: function (result, textStatus, request) {
            var token = request.getResponseHeader("Token");
            SetToken("Token", token);
            //alert(token);
            if (result.code == 100) {
                top.layer.msg("归还档案成功",{icon:6});
                selectallborrowreadinfo(1,20);
            } else if (result.code == 200) {
                top.layer.msg("归还档案失败",{icon:5});
            } else if (result.code == 230){
                top.layer.msg("归还档案失败,输入出现空数据",{icon:5});
            } else if (result.code == 250) {
                top.layer.alert("会话已过期，请您重新登录", {icon: 0, title: '会话过期提示', time: 2500}, function () {
                    top.window.location.href = "/login";
                });
            }
        }
    })
}
//获取选中对象的受理号
function  getChecked(data) {
    var slhList = new Array();
    $.each(data,function (i,item) {
        slhList.push(item.slh);
    })
    return slhList;
}
//判断是否勾选了未出借的档案
function isback(data){
    var info = false;
    $.each(data,function (i,item) {
        if (item.isback == 0){
            info = true;
        }
    })
    return info;
}
//获取当前勾选的信息的isback字段的值
function getIsBack(data) {
    var isbackList = new Array();
    $.each(data,function (i,item) {
        isbackList.push(item.isback);
    })
    return isbackList;
}
//档案借阅
function borrowread() {
    var checkedStatus = table.checkStatus('dataTable');
    var slh = getChecked(checkedStatus.data);
    if (slh.length == 0){
        top.layer.alert("请选择您要借阅档案卷!",{icon:0,title:'大人！请看！',time:2500});
        return false;
    }
    if (slh.length>1){
        top.layer.alert("一次性只能借阅一卷档案!",{icon:0,title:'大人！请看！',time:2500});
        return false;
    }
    var isback = getIsBack(checkedStatus.data)[0];
    if(isback == 1){
        top.layer.alert("本卷档案已经借出!",{icon:0,title:'大人！请看！',time:2500});
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
            selectallborrowreadinfo(1,20);
        }
    })
}