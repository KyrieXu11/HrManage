package com.kyriexu.controller;

import com.kyriexu.model.Department;
import com.kyriexu.model.RespBean;
import com.kyriexu.service.DepartmentService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @PostMapping("/")
    @ApiOperation("添加部门")
    public RespBean addDep(@RequestBody Department dep) {
        departmentService.addDep(dep);
        if (dep.getResult() == 1) {
            return RespBean.ok("添加成功", dep);
        }
        return RespBean.error("添加失败");
    }

    @ApiOperation("通过id删除部门")
    @DeleteMapping("/{id}")
    public RespBean deleteDepById(@PathVariable Integer id) {
        Department dep = new Department();
        dep.setId(id);
        departmentService.deleteDepById(dep);
        if (dep.getResult() == -2) {
            return RespBean.error("该部门下有子部门，删除失败");
        }
        else if (dep.getResult() == -1) {
            return RespBean.error("该部门下有员工，删除失败");
        }
        else if (dep.getResult() == 1) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }
}