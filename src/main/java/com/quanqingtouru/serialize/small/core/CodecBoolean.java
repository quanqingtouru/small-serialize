package com.quanqingtouru.serialize.small.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CodecBoolean extends Codec<Boolean> {
    @Override
    public void encode(Boolean object, OutputStream outputStream) throws IOException {
        if (object == null) {
            outputStream.write(2);
        } else {
            outputStream.write(object ? 1 : 0);
        }
    }

    @Override
    public Boolean decode(InputStream inputStream) throws IOException {
        int read = inputStream.read();
        if (read == 2) {
            return null;
        }

        return read == 1;
    }
}
