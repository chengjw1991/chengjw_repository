package com.cheng;

import com.cheng.beans.RolesPermission;
import com.cheng.service.OperationLogService;
import com.cheng.service.RolesPermissionService;
import com.cheng.service.UserService;
import com.cheng.token.JwtData;
import com.cheng.token.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author ChengJW
 * 2020/11/20/020
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UtilTest {

    @Resource(name = "UserServiceImpl")
    private UserService service;
    @Resource(name = "RolesPermissionServiceImpl")
    private RolesPermissionService rpservice;
    @Resource(name = "OperationLogServiceImpl")
    private OperationLogService logService;

    @Test
    public void testMybatis(){
        //System.out.println(service.SelectByName("chengjw"));
        System.out.println(service.SelectByUserId("82149cab-70f7-45a4-a67e-401987f33582"));
    }
    @Test
    public void testJwtToken(){
        System.out.println(JwtData.getAcceptTokenValidDate());
    }
    @Test
    public void uuidtest(){

        for (int i = 0;i<50;i++){
            String str = UUID.randomUUID().toString();
            System.out.println(str+"-----"+i);
        }
    }

    @Test
    public void insertdata(){
        List<RolesPermission> list = new ArrayList<>();
        //管理员
        int [] p1 = new int[] {1000,1001,1002,1003,1004,2000,2001,2002,2003,2004,2005,2006,2007,2008,3000,3001,3002,4000,4001,5000,5001,6000,6001,6002,6003,6004,6005,6006,6007,6008,6009,7000,7001,7002,7003,8001,8002,8003,8004,9001,9002,9003,9004};
        //接收员
        int [] p2 = new int [] {1000,1001,1002,1003,1004,4000,4001,7000,7001,7002,7003};
        //数字化人员
        int [] p3= new int [] {2000,2001,2002,2003,2004,2005,2006,2007,2008,4000,4001,7000,7001,7002,7003};
        //统计人员
        int [] p4 = new int [] {4000,4001,7000,7001,7002,7003};
        //库房管理
        int [] p5 = new int [] {3000,3001,3002,4000,4001,7000,7001,7002,7003,5000,5001};

        for(int i : p1){
            RolesPermission rp = new RolesPermission(0,28,i);
            list.add(rp);
        }
        int count = rpservice.InsertRolePerms(list);
        System.out.println(count+"----");
    }
    @Test
    public void testOperationLog(){
        System.out.println(logService.SelectLog("2020-12-16 00:00:00","2020-12-16 23:59:00","350200","chengjw",4,10));
    }
    @Test
    public void date(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH,-6);
        System.out.println(format.format(c.getTime()));
    }
}
