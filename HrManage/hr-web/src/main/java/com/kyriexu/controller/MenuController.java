package com.kyriexu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kyriexu.model.Menu;
import com.kyriexu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author KyrieXu
 * @since 2020/3/28 19:54
 **/
@RestController
@RequestMapping("/system/config")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/menu")
    public List<Menu> getAllMenus() throws JsonProcessingException {
        return menuService.getMenusById();
    }
}
