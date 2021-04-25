package com.cheng.beans;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author ChengJW
 * 2020/11/23/023
 *  登录之后响应给前端的用户信息
 */
public class LoginVO implements Serializable {

    private static final long serialVersionUID = 8997125426615779293L;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "用户的角色信息type")
    private List<String> roles;
    @ApiModelProperty(value = "用户的权限信息code")
    private List<String> perms;
    @ApiModelProperty(value = "用户头像")
    private String prifilePhoto;

    public LoginVO() {
    }

    public LoginVO(UserInfo user){
        List<Roles> rolesList = user.getRolesList();
        List<String> rolesType = new ArrayList<>();
        for (Roles role : rolesList){
            String role_type = role.getType();
            rolesType.add(role_type);
        }
        List<Permission> permissionList = user.getPermList();
        List<String> permCode = new ArrayList<>();
        for (Permission perm : permissionList){
            String perm_code = perm.getCode();
            permCode.add(perm_code);
        }
        this.username = user.getUsername();
        this.roles = rolesType;
        this.perms = permCode;
        this.prifilePhoto = user.getPrifilephoto();
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getPerms() {
        return perms;
    }

    public void setPerms(List<String> perms) {
        this.perms = perms;
    }

    public String getPrifilePhoto() {
        return prifilePhoto;
    }

    public void setPrifilePhoto(String prifilePhoto) {
        this.prifilePhoto = prifilePhoto;
    }

    @Override
    public String toString() {
        return "LoginVO{" +
                "username='" + username + '\'' +
                ", roles=" + roles +
                ", perms=" + perms +
                ", prifilePhoto='" + prifilePhoto + '\'' +
                '}';
    }
}
