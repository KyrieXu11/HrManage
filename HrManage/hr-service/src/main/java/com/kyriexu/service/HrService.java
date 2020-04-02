package com.kyriexu.service;

import com.kyriexu.mapper.HrMapper;
import com.kyriexu.mapper.HrRoleMapper;
import com.kyriexu.model.Hr;
import com.kyriexu.utils.HrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author KyrieXu
 * @since 2020/3/27 17:30
 **/
@Service
public class HrService implements UserDetailsService {
    @Autowired
    private HrMapper hrMapper;

    @Autowired
    private HrRoleMapper hrRoleMapper;

    /**
     * 加载用户的方法
     *
     * @param username 用户名
     * @return 找到的用户
     * @throws UsernameNotFoundException 用户未找到的异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Hr hr = hrMapper.loadUserByUsername(username);
        if (hr == null)
            throw new UsernameNotFoundException("用户名未找到");
        hr.setRoles(hrMapper.getHrRolesById(hr.getId()));
        return hr;
    }

    /**
     * 模糊搜索的hr信息
     *
     * @param keywords 搜索的关键字
     * @return hr的集合
     */
    public List<Hr> getAllHrs(String keywords) {
        Hr currentHr = ((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return hrMapper.getAllHrs(currentHr.getId(), keywords);
    }

    /**
     * 更新Hr信息
     *
     * @param hr 前端传过来的hr信息
     * @return 是否更新成功
     */
    public int updateHr(Hr hr) {
        return hrMapper.updateHrInfo(hr);
    }

    /**
     * 更新员工的角色信息
     *
     * @param hrid hr的id
     * @param rids 角色id
     * @return 是否更新成功
     */
    @Transactional
    public boolean updateHrRole(Integer hrid, Integer[] rids) {
        hrRoleMapper.deleteByHrid(hrid);
        return hrRoleMapper.addRole(hrid, rids) == rids.length;
    }

    /**
     * 删除hr
     *
     * @param id hr的id
     * @return 受影响的行数
     */
    public Integer deleteHrById(int id) {
        return hrMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取除了当前登陆的hr之外的信息
     *
     * @return hr的列表
     */
    public List<Hr> getAllHrsExceptCurrentHr() {
        return hrMapper.getAllHrsExceptCurrentHr(HrUtil.getHr().getId());
    }

    /**
     * 更新hr的密码
     *
     * @param oldpass 原来的密码
     * @param pass    新的密码
     * @param hrid    hr的id
     * @return 是否更新成功
     */
    public boolean updateHrPasswd(String oldpass, String pass, Integer hrid) {
        Hr hr = hrMapper.selectByPrimaryKey(hrid);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(oldpass, hr.getPassword())) {
            String encodePass = encoder.encode(pass);
            Integer result = hrMapper.updatePasswd(hrid, encodePass);
            return result == 1;
        }
        return false;
    }

    /**
     * 更新用户的头像
     *
     * @param url 头像的url
     * @param id  hr的id
     * @return 更新影响的行数
     */
    public int updateUserface(String url, Integer id) {
        return hrMapper.updateUserface(url, id);
    }
}
