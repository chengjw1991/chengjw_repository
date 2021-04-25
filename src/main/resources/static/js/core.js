/*  自定义一个js工具类 */

/**
 * elem jquery选择器
 * num 验证码的个数
 * @param str
 * @param num
 */
function createCheckCode(elem,num){
    var createCode = "";
    var codeChars = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'); //所有候选组成验证码的字符，当然也可以用中文的
    //取4个数字
    for (i = 0; i < num; i++) {
        //获取数组下标
        var charNum = Math.floor(Math.random() * 52);
        createCode += codeChars[charNum];
    }
    elem.html(createCode);
}

/**
 *  封装的 ajax 异步请求
 * @param url      请求地址
 * @param params   请求参数
 * @param datatype  请求类型
 * @param async    是否异步
 * @param method   回调函数
 */
function sendAjax(url,params,method,type,datatype,async) {
    var loading =  top.layer.msg('数据提交中，请稍后...');
    $.ajax({
        url:url,
        data:params,
        dataType:datatype == undefined?'json':datatype,
        type:type == undefined?'post':type,
        async:async == undefined?true:false,
        cache:false,
        beforeSend:function (Request){
            // 每次发送请求，再请求头部放入token
            Request.setRequestHeader("Token",GetToken("Token"));
        },
        success:function (result,textStatus,request){
            top.layer.close(loading);
            //从响应头部信息中取出token
            var token = request.getResponseHeader("Token");
           // layer.alert(token);
            //将token存入缓存中
            SetToken("Token",token);
            //alert(GetToken("Token"));
            if (result.code == 250){
                top.layer.alert("会话已过期，请您重新登录",{icon:0,title: '会话过期提示',time: 2500},function (){
                    top.window.location.href="/login";
                });
            }
            method(result);
        },
        error:function (XMLHttpRequest, textStatus, errorThrown){
            top.layer.close(loading);
            if (XMLHttpRequest.status == 404 ){
                top.window.location.href="/to_404";
            }else {
                top.layer.msg('服务器好像出现问题了，请稍后再试',{icon:6,time:1500,title:'提示'});
            }
        }
    })
}
/**
 * 将token值存入缓存
 * @param key
 * @param value
 */
function SetToken(key,value){
    layui.sessionData('localToken',{
        key:key,
        value:value
    })
}

/**
 * 从缓存中获取token值
 * @param key
 * @returns {*}
 */
function  GetToken(key){
    var localToken = layui.sessionData('localToken');
    return localToken[key];
}
function DeleteToken() {
    layui.sessionData('localToken',null);
}

/**
 * 判断字符串是否为空，true表示空值
 * @param str   需要验证的字符串
 * @returns {boolean}
 * @constructor
 */
function ValueIsEmpty(str) {
    if(str == "" || str == null || str == undefined){
       return true;
    }else {
        return false;
    }
}
//往缓存中存入roleid值
function setRoleID(key,value) {
    layui.sessionData('localRoleID',{
        key:key,
        value:value
    })
}
//从缓存中获取roleid值
function getRoleID(key) {
    var localRoleID = layui.sessionData('localRoleID');
    return localRoleID[key];
}
//往缓存中存入userid的值
function setUserID(key,value) {
    layui.sessionData('localUserID',{
        key:key,
        value:value
    })
}
//从缓存中取出userid值
function getUserID(key) {
    var localUserID = layui.sessionData('localUserID');
    return localUserID[key];
}
//将企业SLH存入缓存
function SetSLH(key,value) {
    layui.sessionData('localSLH',{
        key:key,
        value:value
    })
}
//从缓存中取出SLH
function getSLH(key) {
    var localSLH = layui.sessionData('localSLH');
    return localSLH[key];
}
//将企业名称存入缓存
function SetQYMC(key,value) {
    layui.sessionData('localQYMC',{
        key:key,
        value:value
    })
}
//从缓存中取出企业名称
function getQYMC(key) {
    var localQYMC = layui.sessionData('localQYMC');
    return localQYMC[key];
}
//将修改后的页码存入缓存
function SetSLYS(key,value) {
    layui.sessionData('localSLYS',{
        key:key,
        value:value
    })
}
//从缓存中取出修改后的页码
function getSLYS(key) {
    var localSLYS = layui.sessionData('localSLYS');
    return localSLYS[key];
}
//将修改后的日期存入缓存
function SetSLRQ(key,value) {
    layui.sessionData('localSLRQ',{
        key:key,
        value:value
    })
}
//从缓存中取出修改后的日期
function getSLRQ(key) {
    var localSLRQ = layui.sessionData('localSLRQ');
    return localSLRQ[key];
}
//将统一社会信用代码存入缓存
function SetTYSHXYDM(key,value) {
    layui.sessionData('localTYSHXYDM',{
        key:key,
        value:value
    })
}
//从缓存中取出统一社会信用代码
function getTYSHXYDM(key) {
    var localTYSHXYDM = layui.sessionData('localTYSHXYDM');
    return localTYSHXYDM[key];
}
