package com.scaffold.service.impl;

import com.scaffold.orm.mapper.HelloWorldMapper;
import com.scaffold.orm.po.HelloWorld;
import com.scaffold.orm.po.HelloWorldExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scaffold.service.api.HelloWorldService;

@Service
public class HelloWorldServiceImpl implements HelloWorldService {
    @Autowired
    private HelloWorldMapper helloWorldMapper;

    @Override
    public String getHello() {
        return "Hello World!";
    }

    @Override
    public HelloWorld getHello(int pk) {
        HelloWorldExample example = new HelloWorldExample();
        HelloWorldExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(pk);
        return helloWorldMapper.selectByExample(example).stream().findFirst().orElse(null);
    }
}
