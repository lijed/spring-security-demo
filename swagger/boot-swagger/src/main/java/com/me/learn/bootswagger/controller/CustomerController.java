/*
 * Copyright 2020 tu.cn All right reserved. This software is the
 * confidential and proprietary information of tu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tu.cn
 */
package com.me.learn.bootswagger.controller;

import com.me.learn.bootswagger.api.R;
import com.me.learn.bootswagger.dto.CustomerDto;
import com.me.learn.bootswagger.vo.Customer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @date 2020/12/9 23:30
 * Project Name: boot-swagger
 */

@RestController
@RequestMapping("api")
@Api("swaggerDemoController相关的api")
public class CustomerController {

    @PostMapping("create")
    @ApiOperation(value="Create Customer")
    public R<Customer> addCategory(CustomerDto customerDto){
        final Customer customer = new Customer();
        customer.setAge(customerDto.getAge());
        customer.setCellphoneNumber(customerDto.getCellphoneNumber());
        customer.setName(customerDto.getName());
        return new R.Builder<Customer>().setData(customer).buildOk();
    }
}
