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
});
//初始化数据
$(function () {
    var pageindex;
    var pagecount;
    selectcompanyinfo(1,20);
})
function selectcompanyinfo(pageindex,pagecount){
    var condition = $("#condition").val();
    var content = $("input[name='content']").val().trim();
    //渲染表格
    table.render({
        elem:'#dataTable',
        id:'dataTable',
        url:'/acceptAdmin/SelectCompanyInfobyCondition',
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
        where:{condition:condition,content:content},
        height:620,
        size:'sm',
        limit: 20,
        page:{
            curr:pageindex,
            limit:pagecount,
            limits:[20,50,100]
        },
        cols:[[
            {title: '企业信息',colspan:8,align: "center"},
            {title: '受理信息',colspan:8,align: "center"}
        ],[
            {type:'numbers',align:'center',title:'ID',width:50,fixed:'left'},
            {field: 'qymc',align:'center',title:'企业名称',width: 230,templet:function (d) {
                    return '<div style="text-align: left;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">'+d.qymc+'</div>';
                }},
            {field: 'tyshxydm',align:'center',title:'统一社会信用代码',width: 160,templet:function (d) {
                    return '<div style="text-align: left">'+d.tyshxydm+'</div>';
                }},
            {field: 'zch',align:'center',title:'注册号',width: 125,templet:function (d) {
                    return '<div style="text-align: left">'+d.zch+'</div>';
                }},
            {field: 'qylx',align:'center',title:'企业类型',width: 100,templet:function (d) {
                    return '<div style="text-align: left">'+d.qylx+'</div>';
                }},
            {field: 'gxjg',align:'center',title:'管辖机关',width: 97,templet:function (d) {
                    return '<div style="text-align: left;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;">'+d.gxjg+'</div>';
                }},
            {field: 'clrq',align:'center',title:'成立日期',width: 105,sort:true},
            {field: 'ztzt',align:'center',title:'主体状态',width: 80},
            {field: 'slh',align:'center',title:'受理号',width: 120,sort:true,templet:function (d) {
                    return '<div style="text-align: left">'+d.slh+'</div>';
                }},
            {field: 'kh',align:'center',title:'捆号',width: 125,sort:true,templet:function (d) {
                    return '<div style="text-align: left;">'+(d.kh==undefined?'':d.kh)+'</div>';
                }},
            {field: 'slr',align:'center',title:'受理人',width: 70},
            {field: 'ywlx',align:'center',title:'业务类型',width: 80},
            {field: 'slrq',align:'center',title:'受理日期',width: 105,sort:true,templet:function (d) {
                    return '<div style="text-align: left">'+d.slrq+'</div>';
                }},
            {field: 'slys',align:'center',title:'页数',width: 55},
            {field: 'slzt',align:'left',title:'受理状态',width: 80,templet:function (d) {
                    return '<div style="text-align: left">'+d.slzt+'</div>';
                }},
            {field: 'ewm',align:'center',title:'二维码',width: 85}
        ]]
    })
}