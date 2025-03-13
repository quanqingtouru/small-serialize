package com.quanqingtouru.serialize.small.core;


import com.quanqingtouru.serialize.small.util.CodecUtil;
import junit.framework.TestCase;

public class CodecBooleanTest extends TestCase {

    CodecBoolean codec = new CodecBoolean();

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

    private void check(Boolean c) {
        String message = CodecUtil.check(c, codec);
        assertEquals(message, "", message);
    }
}