package com.scaffold.service.api;

import com.scaffold.orm.po.HelloWorld;

public interface HelloWorldService {
    public String getHello();

    public HelloWorld getHello(int pk);
}
