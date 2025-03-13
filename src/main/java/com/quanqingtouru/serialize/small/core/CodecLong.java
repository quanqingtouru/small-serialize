package com.quanqingtouru.serialize.small.core;

import java.io.*;

public class CodecLong extends Codec<Long> {
    @Override
    public void encode(Long object, OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeLong(object);
    }

    @Override
    public Long decode(InputStream inputStream) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        return dataInputStream.readLong();
    }
}
