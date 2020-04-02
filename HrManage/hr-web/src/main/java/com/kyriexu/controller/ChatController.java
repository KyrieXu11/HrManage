package com.kyriexu.controller;

import com.kyriexu.model.Hr;
import com.kyriexu.service.HrService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author KyrieXu
 * @since 2020/4/2 9:18
 **/
@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private HrService hrService;

    @ApiOperation("获取所有的hr信息")
    @GetMapping("/hrs")
    public List<Hr> getAllHrs() {
        return hrService.getAllHrsExceptCurrentHr();
    }
}