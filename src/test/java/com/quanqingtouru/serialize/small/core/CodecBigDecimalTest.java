package com.quanqingtouru.serialize.small.core;

import java.math.BigDecimal;

public class CodecBigDecimalTest extends CodecTestBase {

    BigDecimal a = null;
    BigDecimal b = new BigDecimal("100.325");

    public void testA() {
        check(a);
    }

    public void testB() {
        check(b);
    }


    @Override
    protected Codec<?> prepareCodec() {
        return new CodecBigDecimal();
    }

}