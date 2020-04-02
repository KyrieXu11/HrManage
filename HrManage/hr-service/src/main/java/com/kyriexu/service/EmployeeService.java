package com.kyriexu.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kyriexu.config.mq.ProducerConfig;
import com.kyriexu.exception.BaseException;
import com.kyriexu.exception.ResultSatus;
import com.kyriexu.mapper.EmployeeMapper;
import com.kyriexu.model.Employee;
import com.kyriexu.model.RespPageBean;
import com.kyriexu.mq.ProducerFactory;
import com.kyriexu.utils.ObjectMapperUtil;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author KyrieXu
 * @since 2020/3/29 13:02
 **/
@Service
public class EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private ProducerFactory factory;

    @Autowired
    private ProducerConfig config;

    /**
     * 分页查询的预处理
     *
     * @param page 页数
     * @param size 每页的大小
     * @return 偏移量
     */
    private int pagePreHandler(@NonNull int page, @NonNull int size) {
        return (page - 1) * size;
    }

    /**
     * 分页查询员工信息
     *
     * @param page           当前页数
     * @param size           一页的大小
     * @param employee       雇员信息
     * @param beginDateScope 开始的时间
     * @return 分页对象
     */
    public RespPageBean getEmployeeByPage(@NonNull int page, @NonNull int size, Employee employee, Date[] beginDateScope) {
        RespPageBean pageBean = new RespPageBean();
        // 设置当前页
        pageBean.setCurrent(page);
        // 分页查询预处理
        page = pagePreHandler(page, size);
        // 查询所有员工信息
        List<Employee> employees = employeeMapper.getEmployeeByPage(page, size, employee, beginDateScope);
        // 查询总数
        Long total = employeeMapper.getTotal(employee, beginDateScope);
        // 设置雇员信息
        pageBean.setData(employees);
        // 设置总数
        pageBean.setTotal(total);
        return pageBean;
    }

    /**
     * 批量导入职员
     *
     * @param employees 职员列表
     * @return 影响的行数
     */
    public int addEmps(List<Employee> employees) throws InterruptedException, RemotingException, MQClientException, MQBrokerException, JsonProcessingException {
        return employeeMapper.addEmps(employees);
    }

    /**
     * 查询职工的信息(包含薪水)
     *
     * @param page page
     * @param size size
     * @return 分页对象
     */
    public RespPageBean getEmployeeByPageWithSalary(@NonNull Integer page, @NonNull Integer size) {
        page = pagePreHandler(page, size);
        List<Employee> list = employeeMapper.getEmployeeByPageWithSalary(page, size);
        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(list);
        respPageBean.setTotal(employeeMapper.getTotal(null, null));
        return respPageBean;
    }

    /**
     * 更新员工的薪水
     *
     * @param eid 员工id
     * @param sid 薪水的id
     * @return 更新影响的行数
     */
    public Integer updateEmployeeSalaryById(Integer eid, Integer sid) {
        return employeeMapper.updateEmployeeSalaryById(eid, sid);
    }

    /**
     * 获取最大的workid
     *
     * @return workid
     */
    public Integer maxWorkID() {
        return employeeMapper.maxWorkID();
    }

    /**
     * 添加职员
     *
     * @param employee 职员
     * @return 添加的数量
     * @throws JsonProcessingException
     * @throws InterruptedException
     * @throws RemotingException
     * @throws MQClientException
     * @throws MQBrokerException
     */
    public int addEmp(Employee employee) throws JsonProcessingException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
        int res = employeeMapper.addEmp(employee);
        if (res == 0)
            return res;
        sendEmailToEmployee(employee);
        return res;
    }

    /**
     * 删除职工
     *
     * @param id 职工的工号(id)
     * @return 删除的行数
     */
    public int deleteEmpByEid(Integer id) {
        return employeeMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新员工信息
     *
     * @param employee 员工信息
     * @return 更新影响的条数
     */
    public int updateEmp(Employee employee) {
        return employeeMapper.updateEmp(employee);
    }

    /**
     * 批量导出职员
     *
     * @param employee 员工信息
     * @return 分页对象
     */
    public RespPageBean getEmployeeByPage(Employee employee) {
        List<Employee> employees = employeeMapper.getEmployeeByPage(null, null, employee, null);
        // 查询总数
        Long total = employeeMapper.getTotal(employee, null);
        RespPageBean pageBean = new RespPageBean();
        // 设置雇员信息
        pageBean.setData(employees);
        // 设置总数
        pageBean.setTotal(total);
        return pageBean;
    }


    /**
     * 发送消息给员工
     *
     * @param employee 员工
     * @throws JsonProcessingException
     * @throws InterruptedException
     * @throws RemotingException
     * @throws MQClientException
     * @throws MQBrokerException
     */
    private void sendEmailToEmployee(Employee employee) throws JsonProcessingException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
        // 获取生产者
        DefaultMQProducer producer = factory.getProducer();
        // 转换实体为字符串
        String beanStr = ObjectMapperUtil.BeanToString(employee);
        // 组装消息体
        Message message = new Message(config.getTopic(), beanStr.getBytes());
        SendResult sendRes = producer.send(message);
        if (sendRes == null)
            throw new BaseException(ResultSatus.MESSAGE_SEND_EXCEPTION);
    }
}
