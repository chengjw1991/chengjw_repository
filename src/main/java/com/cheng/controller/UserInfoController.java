package com.cheng.controller;


import com.cheng.beans.Bmxx;
import com.cheng.beans.Dwxx;
import com.cheng.beans.UserInfo;
import com.cheng.beans.UserInfoVo;
import com.cheng.service.BmxxService;
import com.cheng.service.DwxxService;
import com.cheng.service.UserService;
import com.cheng.token.JwtUtil;
import com.cheng.utils.EncryptPassword;
import com.cheng.utils.PhotoPathUtil;
import com.cheng.utils.data.CodeAndMsgImpl;
import com.cheng.utils.data.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

/**
 * @Author ChengJW
 * 2020/12/1/001
 *  用户信息接口
 */
@Api(value = "用户信息相关接口")
@Controller("UserInfoController")
@RequestMapping("/userinfo")
public class UserInfoController {

    @Resource(name = "UserServiceImpl")
    private UserService service;
    @Resource(name = "BmxxServiceImpl")
    private BmxxService bmxxService;
    @Resource(name = "DwxxServiceImpl")
    private DwxxService dwxxService;

    @GetMapping("/home")
    @ApiOperation(value = "首页",tags = "首页")
    public String home(){
        return "home";
    }
    @GetMapping("/updatePassword")
    @ApiOperation(value = "修改密码",tags = "修改密码")
    public String UserinfoUpdatePassword(){
        return "updatePassword";
    }

    @GetMapping("/updatePrifilePhoto")
    @ApiOperation(value = ("修改头像"),tags = "修改头像")
    public String updatePrifilePhoto(){
        return "updatephoto";
    }

    @ApiOperation(value = "验证输入密码是否正确", tags = "验证输入的密码是否正确")
    @PostMapping("/checkpassword")
    @ResponseBody
    public DataResult CheckOldPassword(String oldpassword){
        String userid = getUserId();
        String password = service.SelectPasswordById(userid);
        String encryptpassword = EncryptPassword.Encrypt(oldpassword);
        if (encryptpassword.equals(password)){
            return  DataResult.getDataResult(CodeAndMsgImpl.SUCCESS);
        }else {
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
    }
    @ApiOperation(value = "修改账号密码",tags = "修改账号密码")
    @PostMapping("/updatethepassword")
    @ResponseBody
    public DataResult UpdateThePassword(String newpassword){
        String encryptPassword = EncryptPassword.Encrypt(newpassword);
        //获取主体信息
        Subject subject = SecurityUtils.getSubject();
        String token = (String) subject.getPrincipal();
        String userid = JwtUtil.ParseUserId(token);
        int count = service.updatePassword(encryptPassword,userid);
        if (count == 1){
            return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS);
        }else {
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
    }

    @ApiOperation(value = "上传头像",tags = "上传头像")
    @PostMapping("/uploadphoto")
    @ResponseBody
    public DataResult UploadPhoto(MultipartFile file){
        try {
            //获取文件原始名称
            String fileName = file.getOriginalFilename();
            //System.out.println("原始文件名---"+fileName);
            //对文件名字符串进行切分，获取后缀名
            String [] fileStr = fileName.split("\\.");
            String str = fileStr[fileStr.length-1];
            //通过UUI随机一个字符串
            String uuidStr = UUID.randomUUID().toString();
            //拼接一个新的名称
            String newfilename = uuidStr+"."+str;
            //System.out.println("新文件名--"+newfilename);
            ////获取文件要上传的相对路径
            String realPath = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
            realPath = realPath + "/images/" + newfilename;
            //映射图片存放位置
            //String path = PhotoPathUtil.getPath()+"\\"+newfilename;
            //System.out.println("文件上传路径--"+realPath);
            //文件上传
            file.transferTo(new File(realPath));
            //System.out.println("文件上传成功----");
            //System.out.println(path);
            return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,newfilename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DataResult.FAILURE();
    }
    @PostMapping("/showuserphoto")
    @ResponseBody
    @ApiOperation(value = "回显用户当前头像",tags = "回显用户当前头像")
    public DataResult ShowUserPhoto(){
        String userid = getUserId();
        UserInfo info = service.SelectUserInfoByUserid(userid);
        String photoPath = info.getPrifilephoto();
        if (photoPath == null || photoPath.equals("")){
            return DataResult.FAILURE();
        }else {
            return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,photoPath);
        }
    }
    @PostMapping("/sureupdatephoto")
    @ResponseBody
    @ApiOperation(value = "确认修改头像",tags = "确认修改头像")
    public DataResult SureUpdatePhoto(String photoname){
        String userid = getUserId();
        int count = service.updatePrifilePhoto(photoname,userid);
        if(count == 1){
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("photo",photoname);
            return  DataResult.getDataResult(CodeAndMsgImpl.SUCCESS);
        }else {
            return  DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
    }
    @GetMapping("/updateUserinfo")
    @ApiOperation(value = "跳转至查看信息页面",tags = "跳转至查看信息页面")
    public String UpdateUserInfo(){
        return "userinfo_updateUserInfo";
    }

    @PostMapping("/selectalluserinfo")
    @ApiOperation(value = "回显用户信息",tags = "回显用户信息")
    @ResponseBody
    public DataResult SelectAllUserInfo(){
        String userid = getUserId();
        UserInfo info = service.SelectByUserId(userid);
        if (info != null){
            Bmxx bmxx = bmxxService.SelectBmxxById(info.getDepartmentid());
            Dwxx dwxx = dwxxService.SelectByCode(info.getDwcode());
            String bmxxName =bmxx.getName();
            String dwname = dwxx.getName();
            UserInfoVo vo = new UserInfoVo(info.getUsername(),bmxxName,dwname,info.getSex(),info.getPhone(),info.getRegistdate(),info.getPrifilephoto());
            return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,vo);
        }else {
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
    }
    public String getUserId(){
        //获取主体信息
        Subject subject = SecurityUtils.getSubject();
        String token = (String) subject.getPrincipal();
        if(token == null){
            return  null;
        }
        String userid = JwtUtil.ParseUserId(token);
        return userid;
    }

}
