package com.quanqingtouru.serialize.small.serialize.complex;

import com.alibaba.fastjson.JSONObject;
import com.quanqingtouru.serialize.small.SmallSerialize;
import com.quanqingtouru.serialize.small.serialize.SimpleObject;
import com.quanqingtouru.serialize.small.util.CodecUtil;
import junit.framework.TestCase;


public class SmallSerializeFullTest extends TestCase {

    @Override
    protected void setUp() throws Exception {
        SmallSerialize.registerClass((short) 1, LoginResponse.class);
    }

    public void testSimpleObject() throws Exception {
        LoginResponse response = new LoginResponse();
        response.setId(System.currentTimeMillis());
        response.setData("123456");

        System.out.println(response);
        System.out.println("---------------------");
        byte[] serialize = SmallSerialize.serialize(response);

        LoginResponse deserialize = SmallSerialize.deserialize(serialize);

        System.out.println(deserialize);
    }


}