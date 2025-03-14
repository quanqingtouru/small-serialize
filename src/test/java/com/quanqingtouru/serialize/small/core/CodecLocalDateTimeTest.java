package com.quanqingtouru.serialize.small.core;


import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class CodecLocalDateTimeTest extends CodecTestBase {

    LocalDateTime a = LocalDateTime.ofEpochSecond(System.currentTimeMillis() / 1000, 0, ZoneOffset.UTC);
    LocalDateTime b = LocalDateTime.ofEpochSecond(System.currentTimeMillis() / 1000 + 86400L * 365 * 30, 0, ZoneOffset.UTC);
    LocalDateTime c = LocalDateTime.ofEpochSecond(System.currentTimeMillis() / 1000 - 86400L * 365 * 30, 0, ZoneOffset.UTC);

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
        return new CodecLocalDateTime();
    }
}