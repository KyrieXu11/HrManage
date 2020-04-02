package com.kyriexu.controller;

import com.kyriexu.utils.VerifyCode;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author KyrieXu
 * @since 2020/3/29 19:33
 **/
@RestController
public class LoginController {

    @ApiOperation("登陆的接口，实际登陆不在这里")
    @GetMapping("/login")
    public String login(){
        return "请登陆！";
    }

    @ApiOperation("获取验证码")
    @GetMapping("/verifyCode")
    public void getVerifyCode(HttpServletResponse response, HttpSession session) throws IOException {
        VerifyCode verifyCode = new VerifyCode();
        // 获取生成的验证码
        BufferedImage image = verifyCode.getImage();
        VerifyCode.output(image,response.getOutputStream());
        // 将验证码放到session中
        session.setAttribute("verifyCode",verifyCode.getText());
    }

}
