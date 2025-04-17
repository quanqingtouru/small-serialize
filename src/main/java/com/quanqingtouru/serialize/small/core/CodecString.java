package com.quanqingtouru.serialize.small.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.quanqingtouru.serialize.small.util.CodecUtil.length2Stream;
import static com.quanqingtouru.serialize.small.util.CodecUtil.stream2Length;


public class CodecString extends Codec<String> {
    @Override
    public void encode(String object, OutputStream outputStream) throws IOException {
        if (object == null) {
            outputStream.write(0);
            return;
        }

        byte[] data = object.getBytes();
        int length = data.length;

        byte[] bytes = length2Stream(length);
        outputStream.write(bytes);
        outputStream.write(data);
    }

    @Override
    public String decode(InputStream inputStream) throws IOException {
        int b1 = inputStream.read();

        if (((b1 >> 7) & 1) == 0) {
            return null;
        }

        int length = stream2Length(inputStream, b1);

        byte[] bytes = new byte[length];
        int read = inputStream.read(bytes);

        return new String(bytes);
    }

}


