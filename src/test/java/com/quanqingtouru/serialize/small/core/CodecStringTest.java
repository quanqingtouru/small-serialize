package com.quanqingtouru.serialize.small.core;

public class CodecStringTest extends CodecTestBase {

    String a = null;
    String b = "";
    String c = "hello";
    String d = repeat("*", 65525);
    String e = repeat("*", 67738);

    public void testA() {
        check(a);
    }

    public void testB() {
        check(b);
    }

    public void testC() {
        check(c);
    }

    public void testD() {
        check(d);
    }

    public void testE() {
        check(e);
    }

    @Override
    protected Codec<?> prepareCodec() {
        return new CodecString();
    }

    public String repeat(String str, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
}