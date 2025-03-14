package com.quanqingtouru.serialize.small.core;


import java.util.Date;

public class CodecDateTest extends CodecTestBase {

    Date a = new Date(System.currentTimeMillis() / 1000 * 1000);
    Date b = new Date(System.currentTimeMillis() / 1000 * 1000 + 86400000L * 365 * 30);
    Date c = new Date(System.currentTimeMillis() / 1000 * 1000 - 86400000L * 365 * 30);

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
        return new CodecDate();
    }
}