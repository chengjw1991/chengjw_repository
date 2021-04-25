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
    //监听单元格
    table.on('edit(data-table)',function (obj) {
        var value = obj.value;  //编辑之后的单元格的值
        var field = obj.field; // 被编辑的字段
        var slh = obj.data.slh; //被编辑的所在行的数据的slh；
        if (ValueIsEmpty(value)){
            top.layer.alert("捆号不能为空值",{icon:5});
            return false;
        }
        $.ajax({
            url:'/acceptAdmin/UpdateSlxxBySlh',
            type: "post",
            data: {"slh":slh,"kh":value},
            beforeSend: function (request) {
                request.setRequestHeader("Token", GetToken("Token"));
            },
            success: function (result, textStatus, request) {
                var token = request.getResponseHeader("Token");
                SetToken("Token", token);
                //alert(token);
                if (result.code == 100) {
                    top.layer.msg("修改捆号成功",{icon:6});
                } else if (result.code == 200) {
                    top.layer.msg("修改捆号失败",{icon:5});
                } else if (result.code == 230){
                    top.layer.msg("修改捆号失败,输入出现空数据",{icon:5});
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
    selectcompanyinfo(1,20);
    $("input[name='startdate']").val();
})
function selectcompanyinfo(pageindex,pagecount){
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
        url:'/acceptAdmin/SelectCompanyInfo',
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
            {type:'numbers',align:'center',title:'ID',width:55},
            {field: 'kh',align:'center',title:'捆号(点击编辑)',width: 135,edit:'text',sort:true,templet:function (d) {
                    return '<div style="text-align: left">'+(d.kh==undefined?(d.dwjc+'-'):d.kh)+'</div>';
                }},
            {field: 'slr',align:'center',title:'受理人',width: 70},
            {field: 'slh',align:'center',title:'受理号',width: 120,sort:true,templet:function (d) {
                    return '<div style="text-align: left">'+d.slh+'</div>';
                }},
            {field: 'ywlx',align:'center',title:'业务类型',width: 80},
            {field: 'slrq',align:'center',title:'受理日期',width: 105,sort:true},
            {field: 'slys',align:'center',title:'页数',width: 60},
            {field: 'qymc',align:'center',title:'企业名称',width: 320,templet:function (d) {
                    return '<div style="text-align: left;padding-right: 5px;overflow: hidden;white-space: nowrap;text-overflow:ellipsis;">'+d.qymc+'</div>';
                }},
            {field: 'tyshxydm',align:'center',title:'统一社会信用代码',width: 160,templet:function (d) {
                    return '<div style="text-align: left">'+d.tyshxydm+'</div>';
                }},
            {field: 'zch',align:'center',title:'注册号',width: 130,templet:function (d) {
                    return '<div style="text-align: left">'+d.zch+'</div>';
                }},
            {field: 'qylx',align:'center',title:'企业类型',width: 100,templet:function (d) {
                    return '<div style="text-align: left;">'+d.qylx+'</div>';
                }},
            {field: 'gxjg',align:'center',title:'管辖机关',width: 115,templet:function (d) {
                    return '<div style="text-align: left;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">'+d.gxjg+'</div>';
                }},
            {field: 'ztzt',align:'center',title:'主体状态',width: 80},
            {field: 'ewm',align:'center',title:'二维码',width: 100,templet:function (d) {
                    return '<div style="text-align: left;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">'+d.ewm+'</div>';
                }},
        ]],
    })
}
//修改档案卷页数
function updateslys() {
    var checkedStatus = table.checkStatus('dataTable');
    if (checkedStatus.data.length==0){
        top.layer.alert("请先选择您想要修改的档案",{icon:0,title:'大人！请看！',time:2500});
        return false;
    }
    if (checkedStatus.data.length>1){
        top.layer.alert("一次性只能修改一卷档案页数",{icon:0,title:'大人！请看！',time:2500}) ;
        return false;
    }
    var slh = "";
    $.each(checkedStatus.data,function (i,item) {
        slh += item.slh;
    });
    var qymc = "";
    $.each(checkedStatus.data,function (i,item) {
        qymc += item.qymc;
    });
    SetSLH("SLH",slh);
    SetQYMC("QYMC",qymc);
    top.layer.open({
        title:'修改档案卷页数',
        type:2,
        resize:false,
        area:['500px','300px'],
        content: '/acceptAdmin/UpdateCompanyInfo',
        end:function (obj) {
            selectcompanyinfo(1,20);
        }
    });
}
//删除档案卷
function deletecompanyinfo() {
    var checkedStatus = table.checkStatus('dataTable');
    if (checkedStatus.data.length==0){
        top.layer.alert("请先选择您想要删除的档案",{icon:0,title:'大人！请看！',time:2500});
        return false;
    }
    var slh = new Array();
    slh = getChecked(checkedStatus.data);
    top.layer.confirm("您确定要删除所选的档案吗？",{icon:0,title:'大人！请三思啊！',time:25000},function () {
        $.ajax({
            url:'/acceptAdmin/DeleteCompanyinfo',
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
                    top.layer.msg("删除成功",{icon:6});
                    selectcompanyinfo(1,20);
                } else if (result.code == 200) {
                    top.layer.msg("删除失败",{icon:5});
                } else if (result.code == 250) {
                    top.layer.alert("会话已过期，请您重新登录", {icon: 0, title: '会话过期提示', time: 2500}, function () {
                        top.window.location.href = "/login";
                    });
                }
            }
        })
    })
}
//获取选中对象
function  getChecked(data) {
    var slh = "";
    $.each(data,function (i,item) {
        if (slh != ""){
            slh += ","+item.slh
        }else {
            slh += item.slh;
        }
    })
    return slh;
}
//转入待定
function todaiding() {
    var checkStatus = table.checkStatus('dataTable');
    var slh = getCheck(checkStatus.data);
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
function  getCheck(data) {
    var slhlist = new Array();
    $.each(data,function (i,item) {
        slhlist.push(item.slh);
    })
    return slhlist;
}