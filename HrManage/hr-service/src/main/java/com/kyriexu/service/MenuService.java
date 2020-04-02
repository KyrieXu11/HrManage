package com.kyriexu.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.kyriexu.exception.BaseException;
import com.kyriexu.exception.ResultSatus;
import com.kyriexu.mapper.MenuMapper;
import com.kyriexu.mapper.MenuRoleMapper;
import com.kyriexu.model.Menu;
import com.kyriexu.redisservice.RedisService;
import com.kyriexu.utils.HrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Transaction;

import java.util.List;

/**
 * @author KyrieXu
 * @since 2020/3/27 17:30
 **/
@Service
public class MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MenuRoleMapper menuRoleMapper;

    /**
     * 获取所有的menu
     *
     * @return 所有的menu@return
     * @throws JsonProcessingException json转化异常
     */
    public List<Menu> getAllMenus() throws JsonProcessingException {
        // redis query
        List<Menu> menus = redisService.get("menus:all", new TypeReference<>() {
        });
        if (menus != null)
            return menus;
        // db query
        menus = menuMapper.getAllMenus();
        // 过期时间为2小时
        int expireTime = 2 * 60 * 60;
        // set menus into redis
        boolean isSuccess = redisService.set("menus:all", expireTime, menus);
        if (isSuccess && menus != null)
            return menus;
        return null;
    }

    /**
     * 查询url所需要的角色
     *
     * @return 查询的结果集
     * @throws JsonProcessingException json转换异常
     */
    public List<Menu> getAllMenusWithRole() throws JsonProcessingException {
        List<Menu> menus = redisService.get("menus:role", new TypeReference<>() {
        });
        if (menus != null)
            return menus;
        menus = menuMapper.getAllMenusWithRole();
        // 设置过期时间是2小时
        int expireTime = 2 * 60 * 60;
        // set menus into redis
        boolean isSuccess = redisService.set("menus:role", expireTime, menus);
        if (isSuccess && menus != null)
            return menus;
        return null;
    }


    /**
     * 根据hr的id获取对应的menu
     *
     * @return menu列表
     */
    public List<Menu> getMenusById() throws JsonProcessingException {
        // 获取hr的id
        int id = HrUtil.getHr().getId();
        // redis中查找有没有menu
        List<Menu> menus = redisService.get("menus:id:" + id, new TypeReference<>() {
        });
        if (menus != null)
            return menus;
        // 这里不需要校验id，因为能调用这个方法的是必须要登陆才能调用
        menus = menuMapper.getMenusByHrId(id);
        int expireTime = 2 * 60 * 60;
        // 插入到redis中
        boolean isSuccess = redisService.set("menus:id:" + id, expireTime, menus);
        // 如果插入不成功，抛出异常，controller捕获
        if (!isSuccess)
            throw new BaseException(ResultSatus.REDIS_INSERT_EXCEPTION);
        return menus;
    }

    /**
     * 更新缓存
     *
     * @param key 缓存的key
     * @return 是否更新成功
     * @throws JsonProcessingException json转换异常
     */
    public boolean updateMenus(String key) throws JsonProcessingException {
        // 开启事务
        Transaction transaction = redisService.getTransaction();
        boolean isSetSuccess = false;
        List<Menu> menus = null;
        // 是否删除成功
        boolean isDelSuccess = redisService.removeKey(key);
        // 如果删除成功了就继续后面的操作
        if (isDelSuccess) {
            // 获取后缀
            String postFix = key.split(":")[1];
            switch (postFix) {
                case "id":
                    menus = menuMapper.getMenusByHrId(HrUtil.getHr().getId());
                    break;
                case "role":
                    menus = menuMapper.getAllMenusWithRole();
                    break;
                case "all":
                    menus = menuMapper.getAllMenus();
                    break;
            }
            // db query
            int expireTime = 2 * 60 * 60;
            // set menus into redis
            isSetSuccess = redisService.set(key, expireTime, menus);
        }
        // 结束事务
        boolean isTransSuccess = redisService.exec(transaction);
        // 返回事务的结果
        return isSetSuccess && isTransSuccess;
    }

    /**
     * 获取menu的id
     *
     * @param rid 角色id
     * @return menu id集合
     */
    public List<Integer> getMidsByRid(Integer rid) {
        return menuMapper.getMidsByRid(rid);
    }

    /**
     * 更新menu_role表中的信息
     *
     * @param rid  角色id
     * @param mids 菜单id
     * @return 是否更新成功
     */
    @Transactional
    public boolean updateMenuRole(Integer rid, Integer[] mids) {
        // 首先根据角色id删除menu_role表中的数据
        menuRoleMapper.deleteByRid(rid);
        if (mids == null || mids.length == 0) {
            return true;
        }
        // 然后再在menu_role表中插入数据
        Integer result = menuRoleMapper.insertRecord(rid, mids);
        // 看是否全部插入了
        return result == mids.length;
    }
}
