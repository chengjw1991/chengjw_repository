package com.cheng.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;

import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author ChengJW
 * 2020/11/22/022
 */
@Configuration
public class ShiroConfig {

    @Bean
    public Authenticator authenticator(){
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setRealms(Arrays.asList(loginRealm(),checkRealm()));
        authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
        return authenticator;
    }

    /**
     * 登录 验证
     * @return
     */
    @Bean
    public PasswordMatcher PasswordMatcher(){
        return new PasswordMatcher();
    }
    @Bean
    public LoginRealm loginRealm(){
        LoginRealm realm = new LoginRealm();
        //realm.setCredentialsMatcher(PasswordMatcher());
        return  realm;
    }

    /**
     * 登录之后，请求携带token验证
     * @return
     */
    @Bean
    public JwtMatcher jwtMatcher(){
        return new JwtMatcher();
    }
    @Bean
    public CheckRealm checkRealm(){
        CheckRealm realm = new CheckRealm();
        realm.setCredentialsMatcher(jwtMatcher());
        return realm;
    }


    /**
     * 注册安全管理器
     * @return
     */
    @Bean
    public DefaultWebSecurityManager manager(){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        List<Realm> realms = new ArrayList<>();
        realms.add(loginRealm());
        realms.add(checkRealm());
        manager.setRealms(realms);
        manager.setAuthenticator(authenticator());
        manager.setCacheManager(cacheManager());
        return manager;
    }

    /**
     * 注册shiro全局过滤器工厂
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean factoryBean(){
        ShiroFilterFactoryBean factory = new ShiroFilterFactoryBean();
        factory.setSecurityManager(manager());  // 设置安全管理器
        Map<String, Filter> map = new ConcurrentHashMap<>();
        map.put("token",new JwtAuthFilter()); //添加过滤器
        map.put("rolesFilter",new RolesAuthFilter());
        map.put("logoutFilter",new ShiroLogoutFilter());
        factory.setFilters(map);

        //设置登录页面
        factory.setLoginUrl("/login");

        Map<String,String> chainmap = new LinkedHashMap<>();

        //设置过滤项
        //登录页放行
        chainmap.put("/login","anon");
        //chainmap.put("/logout","authc");
        chainmap.put("/UserLogin","anon");
        chainmap.put("/index","authc");
        chainmap.put("/acceptAdmin/UpdateSLRQ","authc");
        //静态资源放行
        chainmap.put("/layuiadmin/**","anon");
        chainmap.put("/images/**","anon");
        chainmap.put("/css/**","anon");
        chainmap.put("/js/**","anon");
        //swagger 放行
        chainmap.put("/swagger/**", "anon");
        chainmap.put("/v2/api-docs", "anon");
        chainmap.put("/swagger-ui.html", "anon");
        chainmap.put("/swagger-resources/**", "anon");
        chainmap.put("/webjars/**", "anon");
        chainmap.put("/favicon.ico", "anon");
        chainmap.put("/captcha.jpg", "anon");
        // druid 监控
        chainmap.put("/druid/**","anon");
        // 需要验证登录的(菜单导航)
        //业务流程菜单
        chainmap.put("/business","perms[sys:business]");
        chainmap.put("/business/archivesAccept","perms[business:archivesAccept]");
        chainmap.put("/business/archivesSearch","perms[business:archivesSearch]");
        chainmap.put("/business/archivesAdmSearch","perms[business:archivesAdmSearch]");
        chainmap.put("/business/acceptAdmin","perms[business:acceptAdmin]");
        //流转过程菜单
        chainmap.put("/circulation","perms[sys:circulation]");
        chainmap.put("/circulation/archivesAccept","perms[circulation:archivesAccept]");
        chainmap.put("/circulation/archivesDigitizing","perms[circulation:archivesDigitizing]");
        chainmap.put("/circulation/archivesInBatches","perms[circulation:archivesInBatches]");
        chainmap.put("/circulation/inBatchesAdmin","perms[circulation:inBatchesAdmin]");
        chainmap.put("/circulation/specialArchivesAdmin","perms[circulation:specialArchivesAdmin]");
        chainmap.put("/circulation/archivesExport","perms[circulation:archivesExport]");
        chainmap.put("/circulation/archivesAdminSearch","perms[circulation:archivesAdminSearch]");
        chainmap.put("/circulation/archivesSearch","perms[circulation:archivesSearch]");
        //库房管理菜单
        chainmap.put("/archivesKuFang","perms[sys:archivesKuFang]");
        chainmap.put("/archivesKuFang/archivesAdmin","perms[archivesKuFang:archivesAdmin]");
        chainmap.put("/archivesKuFang/archivesSearch","perms[archivesKuFang:archivesSearch]");
        chainmap.put("/archivesKuFang/archivesMove","perms[archivesKuFang:archivesMove]");
        //库房管理-档案管理-操作流程页面跳转
        chainmap.put("/archivesAdmin/archivesPath","perms[archivesAdmin:archivesPath]");
        //库房管理-档案迁移相关页面跳转
        chainmap.put("/archivesMove/archivesIn","perms[archivesMove:archivesIn]");
        chainmap.put("/archivesMove/archivesOut","perms[archivesMove:archivesOut]");
        chainmap.put("/archivesMove/archivesInfo","perms[archivesMove:archivesInfo]");
        //档案统计菜单
        chainmap.put("/archivesStatistics","perms[sys:archivesStatistics]");
        chainmap.put("/archivesStatistics/statistics","perms[archivesStatistics:statistics]");
        //档案借阅菜单
        chainmap.put("/archivesRead","perms[sys:archivesRead]");
        chainmap.put("/archivesRead/read","perms[archivesRead:read]");
        chainmap.put("/read/archivesBorrow","perms[read:archivesBorrow]");
        chainmap.put("/read/archivesBack","perms[read:archivesBack]");
        //系统维护菜单
        chainmap.put("/systemDefend","perms[sys:systemDefend]");
        chainmap.put("/systemDefend/rolesAdmin","perms[systemDefend:rolesAdmin]");
        chainmap.put("/systemDefend/userAdmin","perms[systemDefend:userAdmin]");
        chainmap.put("/systemDefend/permissionAdmin","perms[systemDefend:permissionAdmin]");
        chainmap.put("/systemDefend/departmentAdmin","perms[systemDefend:departmentAdmin]");
        chainmap.put("/systemDefend/basicAdmin","perms[systemDefend:basicAdmin]");
        chainmap.put("/systemDefend/tableAdmin","perms[systemDefend:tableAdmin]");
        chainmap.put("/systemDefend/systemAdmin","perms[systemDefend:systemAdmin]");
        chainmap.put("/systemDefend/systemlog","perms[systemDefend:systemlog]");
        chainmap.put("/systemDefend/backUpOnline","perms[systemDefend:backUpOnline]");
        //用户信息菜单
        chainmap.put("/userinfo","perms[sys:userinfo]");
        chainmap.put("/userinfo/updatePrifilePhoto","perms[userinfo:updatePrifilePhoto]");
        chainmap.put("/userinfo/updateUserinfo","perms[userinfo:updateUserinfo]");
        chainmap.put("/userinfo/updatePassword","perms[userinfo:updatePassword]");
        chainmap.put("/userinfo/home","perms[userinfo:home]");
        //角色管理-具体权限管理
        chainmap.put("/rolesAdmin/rolesAdd","perms[rolesAdmin:rolesAdd]");
        chainmap.put("/rolesAdmin/rolesSelect","token,perms[rolesAdmin:rolesSelect]");
        chainmap.put("/rolesAdmin/rolesUpdate","perms[rolesAdmin:rolesUpdate]");
        chainmap.put("/rolesAdmin/rolesDelete","perms[rolesAdmin:rolesDelete]");
        //用户管理-具体权限管理
        chainmap.put("/userAdmin/userAdd","perms[userAdmin:userAdd]");
        chainmap.put("/userAdmin/userUpdate","perms[userAdmin:userUpdate]");
        chainmap.put("/userAdmin/userDelete","perms[userAdmin:userDelete]");
        chainmap.put("/userAdmin/userSelect","token,perms[userAdmin:userSelect]");
        //日志管理-具体权限管理
        chainmap.put("/systemLog/loginLog","perms[systemLog:loginLog]");
        chainmap.put("/systemLog/operationLog","perms[systemLog:operationLog]");
        //受理管理菜单 具体权限
        chainmap.put("/acceptAdmin/SelectCompanyInfo","token,perms[acceptAdmin:SelectCompanyInfo]");
        chainmap.put("/acceptAdmin/UpdateCompanyInfo","perms[acceptAdmin:UpdateCompanyInfo]");
        chainmap.put("/acceptAdmin/DeleteCompanyInfo","token,perms[acceptAdmin:DeleteCompanyInfo]");
        //其他的所有请求都需要经过token过滤器跟认证
        chainmap.put("/**","token,authc");
        factory.setFilterChainDefinitionMap(chainmap);
        return factory;
    }

    /**
     * 开启shiro标签
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect(){
        return  new ShiroDialect();
    }

    /**
     * 注册缓存
     * @return
     */
    @Bean
    public CacheManager cacheManager(){
        return new MemoryConstrainedCacheManager();
    }
}
