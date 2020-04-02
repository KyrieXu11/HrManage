package com.kyriexu.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.kyriexu.mapper.HrMapper;
import com.kyriexu.model.*;
import com.kyriexu.model.Menu;
import com.kyriexu.redisservice.RedisService;
import com.kyriexu.service.EmployeeService;
import com.kyriexu.service.TestMailService;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.Header;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

/**
 * @author KyrieXu
 * @since 2020/3/26 11:12
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestApplication {
    @Autowired
    private RedisService  redisService;
    @Autowired
    private HrMapper hrMapper;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TestMailService mailService;

    @Test
    public void JedisGetTest() throws JsonProcessingException {
        List<Menu> menuList = redisService.get("menus", new TypeReference<>() {});
        for (Menu menu : menuList) {
            System.out.println(menu.getId());
        }
    }

    @Test
    public void JedisSetexTest() throws JsonProcessingException {
        MsgContent msg = new MsgContent();
        msg.setId(123);
        msg.setTitle("标题");
        msg.setMessage("信息");
        msg.setCreatedate(new Date(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli()));
        redisService.set("testkey1",msg);
    }
    @Test
    public void TestHrMapper(){
        String menus = "menus:all";
        System.out.println(menus.split(":")[1]);
    }

    @Test
    public void Testmail() throws MessagingException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper= new MimeMessageHelper(msg);
        helper.setFrom("kyriexu11@126.com");
        helper.setTo("www1468008857@126.com");
        helper.setSubject("测试邮件");
        helper.setText("你好啊");
        mailSender.send(msg);
    }

    @Test
    public void Testmail2() throws InterruptedException, RemotingException, MQClientException, MQBrokerException, JsonProcessingException {
        Nation nation=  new Nation();
        nation.setId(123);
        nation.setName("123");
        //mailService.add(nation);
    }

    @Test
    public void TestFastDFS() throws IOException, MyException {
        ClientGlobal.initByProperties("fastdfs-client.properties");
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;
        StorageClient1 client = new StorageClient1(trackerServer, storageServer);
        NameValuePair nvp[] = null;
        //上传到文件系统
        String fileId = client.upload_file1("F:\\截图\\JUC\\Volatile\\jmm.png", "png", nvp);
        System.out.println(fileId);
    }
}
