package com.quanqingtouru.serialize.small.core;

import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public abstract class CodecTestBase extends TestCase {
    protected Codec<?> codec;

    protected abstract Codec<?> prepareCodec();

    public CodecTestBase() {
        this.codec = prepareCodec();
    }

    protected void check(Object value) {
        Object decode = null;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            codec.encodeObject(value, outputStream);
            decode = codec.decode(new ByteArrayInputStream(outputStream.toByteArray()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals(value, decode);
    }
}
