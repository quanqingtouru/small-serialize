package com.quanqingtouru.serialize.small.core;

public class CodecIntegerTest extends CodecTestBase {

    int a = Integer.MAX_VALUE;
    int b = Integer.MIN_VALUE;
    int c = 0;

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
        return new CodecInteger();
    }
}