package com.quanqingtouru.serialize.small.core;

import java.io.*;

public class CodecShort extends Codec<Short> {
    @Override
    public void encode(Short object, OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeShort(object);
    }

    @Override
    public Short decode(InputStream inputStream) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        return dataInputStream.readShort();
    }
}
