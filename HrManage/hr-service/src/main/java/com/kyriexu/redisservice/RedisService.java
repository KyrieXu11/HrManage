package com.kyriexu.redisservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyriexu.config.redis.JedisConfig;
import com.kyriexu.exception.BaseException;
import com.kyriexu.exception.ResultSatus;
import com.kyriexu.utils.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Set;

/**
 * @author KyrieXu
 * @since 2020/3/27 13:39
 **/
@Service
public class RedisService {
    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private JedisConfig config;

    private Jedis getJedis() {
        Jedis jedis = jedisPool.getResource();
        if (jedis == null)
            throw new BaseException(ResultSatus.REDIS_CONNECTION_EXCEPTION);
        jedis.auth(config.getAuth());
        return jedis;
    }

    private void close(Jedis jedis) {
        if (jedis != null)
            jedis.close();
    }

    /**
     * set方法，没有涉及到过期时间
     *
     * @param key     string类型的key
     * @param beanVal 任意类型的value
     * @return 是否set成功
     * @throws JsonProcessingException json转化失败
     */
    public <T> boolean set(String key, T beanVal) throws JsonProcessingException {
        Jedis jedis = getJedis();
        //jedis.setex()
        String value = ObjectMapperUtil.BeanToString(beanVal);
        // 将转换后的string和value一起set到redis中去
        String res = jedis.set(key, value);
        close(jedis);
        return res != null;
    }

    public boolean contains(String key){
        Jedis jedis = getJedis();
        Set<String> keys = jedis.keys(key);
        return keys.size()!=0;
    }

    /**
     * set指令,有涉及到设置过期时间的命令
     * 是原子操作，不会出现获取分布式锁失败之后redis挂掉没有释放锁的情形
     *
     * @param key        key
     * @param expireTime 过期时间
     * @param beanVal    对象
     * @return 是否set成功
     * @throws JsonProcessingException json转化失败
     */
    public <T> boolean set(String key, int expireTime, T beanVal) throws JsonProcessingException {
        Jedis jedis = getJedis();
        String value = ObjectMapperUtil.BeanToString(beanVal);
        String res = jedis.setex(key, expireTime, value);
        return res != null;
    }

    /**
     * 获取key对应的value，将value转换成对象
     *
     * @param key       key
     * @param reference 对象的类型
     * @return 获取到的对象
     * @throws JsonProcessingException json转化失败
     */
    public <T> T get(String key, TypeReference<T> reference) throws JsonProcessingException {
        Jedis jedis = getJedis();
        // 根据key获取string类型的value
        String strBean = jedis.get(key);
        T t = null;
        if (strBean != null)
            // 根据上面获取的value转换成对应类型的bean
            t = ObjectMapperUtil.StringToBean(strBean, reference);
        close(jedis);
        return t;
    }

    /**
     * 删除key的方法
     *
     * @param keys 多个key也可以
     * @return 是否删除成功
     */
    public boolean removeKey(String... keys) {
        Jedis jedis = getJedis();
        Long del = jedis.del(keys);
        return del != null;
    }

    /**
     * 获取事务
     * @return redis事务
     */
    public Transaction getTransaction(){
        return getJedis().multi();
    }

    /**
     * 提交事务
     * @param transaction 事务
     * @return 是否提交成功
     */
    public boolean exec(Transaction transaction){
        List<Object> res = transaction.exec();
        return res!=null;
    }

}
