package com.scaffold.orm.mapper;

import com.scaffold.orm.po.HelloWorld;
import com.scaffold.orm.po.HelloWorldExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HelloWorldMapper {
    long countByExample(HelloWorldExample example);

    int deleteByExample(HelloWorldExample example);

    int insert(HelloWorld record);

    int insertSelective(HelloWorld record);

    List<HelloWorld> selectByExample(HelloWorldExample example);

    int updateByExampleSelective(@Param("record") HelloWorld record, @Param("example") HelloWorldExample example);

    int updateByExample(@Param("record") HelloWorld record, @Param("example") HelloWorldExample example);
}