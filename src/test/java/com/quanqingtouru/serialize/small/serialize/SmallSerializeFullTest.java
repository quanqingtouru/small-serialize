package com.quanqingtouru.serialize.small.serialize;

import com.quanqingtouru.serialize.small.SmallSerialize;
import com.quanqingtouru.serialize.small.util.CodecUtil;
import junit.framework.TestCase;


public class SmallSerializeFullTest extends TestCase {

    @Override
    protected void setUp() throws Exception {
        SmallSerialize.registerClass((short) 1, SimpleObject.class);
    }

    public void testNullObject() throws Exception {
        SimpleObject simpleObject = null;

        byte[] serialize = SmallSerialize.serialize(simpleObject);
        SimpleObject deserialize = SmallSerialize.deserialize(serialize);
        byte[] destination = SmallSerialize.serialize(deserialize);

        boolean same = CodecUtil.same(serialize, destination);
        assertTrue(same);
    }

    public void testSimpleObjectWithNullField() throws Exception {
        SimpleObject simpleObject = new SimpleObject();

        byte[] serialize = SmallSerialize.serialize(simpleObject);
        SimpleObject deserialize = SmallSerialize.deserialize(serialize);
        byte[] destination = SmallSerialize.serialize(deserialize);

        boolean same = CodecUtil.same(serialize, destination);
        assertTrue(same);
    }

    public void testSimpleObject() throws Exception {
        SimpleObject simpleObject = new SimpleObject();
        simpleObject.setAge((byte) 21);
        simpleObject.setName("a");

        byte[] serialize = SmallSerialize.serialize(simpleObject);

        System.out.println(serialize.length);
//        int length = JSONObject.toJSONString(simpleObject).getBytes().length;
//        System.out.println(length);

        SimpleObject deserialize = SmallSerialize.deserialize(serialize);
        byte[] destination = SmallSerialize.serialize(deserialize);

        boolean same = CodecUtil.same(serialize, destination);
        assertTrue(same);
    }


}