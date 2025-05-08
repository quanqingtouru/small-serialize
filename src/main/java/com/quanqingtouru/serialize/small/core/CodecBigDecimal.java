package com.quanqingtouru.serialize.small.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

import static com.quanqingtouru.serialize.small.util.CodecUtil.length2Stream;
import static com.quanqingtouru.serialize.small.util.CodecUtil.stream2Length;


public class CodecBigDecimal extends Codec<BigDecimal> {
    @Override
    public void encode(BigDecimal object, OutputStream outputStream) throws IOException {
        if (object == null) {
            outputStream.write(0);
            return;
        }

        String string = object.toString();
        byte[] data = string.getBytes();
        int length = data.length;

        outputStream.write(length);
        outputStream.write(data);
    }

    @Override
    public BigDecimal decode(InputStream inputStream) throws IOException {
        int length = inputStream.read();

        if (length == 0) {
            return null;
        }

        byte[] bytes = new byte[length];
        int read = inputStream.read(bytes);

        return new BigDecimal(new String(bytes));
    }

}


