package com.quanqingtouru.serialize.small.core;


public class CodecByteTest extends CodecTestBase {

    Byte a = 0;
    Byte b = Byte.MAX_VALUE;
    Byte c = Byte.MIN_VALUE;

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
        return new CodecByte();
    }
}