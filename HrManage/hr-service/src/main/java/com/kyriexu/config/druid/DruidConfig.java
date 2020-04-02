package com.kyriexu.config.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

/**
 * @author KyrieXu
 * @since 2020/3/30 12:45
 **/
//@Configuration
public class DruidConfig {
    //@Bean
    public DruidDataSource druidDataSource() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.addFilters("stat");
        druidDataSource.setMaxActive(20);
        druidDataSource.setMaxWait(6000);
        return druidDataSource;
    }


    //@Bean
    public ServletRegistrationBean<StatViewServlet> druidStatViewServlet() {
        ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        // IP白名单 (没有配置或者为空，则允许所有访问)
        registrationBean.addInitParameter("allow", "127.0.0.1");
        // IP黑名单 (存在共同时，deny优先于allow)
        registrationBean.addInitParameter("deny", "");
        // 用户名
        registrationBean.addInitParameter("loginUsername", "root");
        // 密码
        registrationBean.addInitParameter("loginPassword", "123");
        registrationBean.addInitParameter("resetEnable", "false");
        return registrationBean;
    }
}
