package com.quanqingtouru.serialize.small.core;


public class CodecLongTest extends CodecTestBase {

    long a = Long.MAX_VALUE;
    long b = Long.MIN_VALUE;
    long c = 0;

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
        return new CodecLong();
    }
}