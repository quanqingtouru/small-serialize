package com.quanqingtouru.serialize.small.core;


import com.quanqingtouru.serialize.small.util.CodecUtil;

public class CodecBooleanTest extends CodecTestBase {

    Boolean a = null;
    Boolean b = true;
    Boolean c = false;

    public void testA() {
        check(a);
    }

    public void testB() {
        check(b);
    }

    public void testC() {
        check(c);
    }


    @Override
    protected Codec<?> prepareCodec() {
        return new CodecBoolean();
    }
}