package com.cheng.service.Impl;

import com.cheng.beans.Roles;
import com.cheng.beans.RolesVO;
import com.cheng.mapper.RolesMapper;
import com.cheng.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author ChengJW
 * 2020/12/3/003
 */
@Service("RolesServiceImpl")
public class RolesServiceImpl implements RolesService {
    @Autowired
    private RolesMapper mapper;

    @Override
    public List<Roles> SelectAllRoles(Integer pageindex,Integer pagecount,String code) {
        return mapper.SelectAllRoles(pageindex,pagecount,code);
    }

    @Override
    public int SelectSumRoles(String code) {
        return mapper.SelectSumRoles(code);
    }

    @Override
    public int SelectRolesByName(String rolename) {
        return mapper.SelectRolesByName(rolename);
    }

    @Override
    public int InsertRole(Roles role) {
        return mapper.InsertRole(role);
    }

    @Override
    public int DeleteRole(Integer id) {
        return mapper.DeleteRole(id);
    }

    @Override
    public Roles SelectRoleById(Integer id) {
        return mapper.SelectRoleById(id);
    }

    @Override
    public int UpateRole(Roles role) {
        return mapper.UpdateRoleById(role);
    }

    @Override
    public int SelectIDByRoleName(String rolename) {
        return mapper.SelectIDByRoleName(rolename);
    }

    @Override
    public String SelectNameById(Integer id) {
        return mapper.SelectNameById(id);
    }

    @Override
    public List<String> SelectTpyeList(Integer id) {
        return mapper.SelectTpyeList(id);
    }

    @Override
    public List<Roles> SelectAllRolesInfo(String code) {
        return mapper.SelectAllRolesInfo(code);
    }

    @Override
    public List<Roles> SelectRolesByDwcode(String dwcode) {
        return mapper.SelectRolesByDwcode(dwcode);
    }

    @Override
    public List<Roles> SelectRolesByUserId(Integer id) {
        return mapper.SelectRolesByUserId(id);
    }


}
