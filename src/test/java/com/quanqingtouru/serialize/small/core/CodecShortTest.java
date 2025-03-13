package com.quanqingtouru.serialize.small.core;

public class CodecShortTest extends CodecTestBase {

    short a = Short.MAX_VALUE;
    short b = Short.MIN_VALUE;
    short c = 0;

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
        return new CodecShort();
    }
}