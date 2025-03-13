package com.quanqingtouru.serialize.small.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

public class CodecByte extends Codec<Byte> {
    @Override
    public void encode(Byte object, OutputStream outputStream) throws IOException {
        outputStream.write(Objects.requireNonNullElse(object, (byte) 0));
    }

    @Override
    public Byte decode(InputStream inputStream) throws IOException {
        return (byte) inputStream.read();
    }
}
