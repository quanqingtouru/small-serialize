package com.quanqingtouru.serialize.small.core;

import java.io.*;
import java.util.Date;

public class CodecDate extends Codec<Date> {
    @Override
    public void encode(Date object, OutputStream outputStream) throws IOException {
        long time = object.getTime();
        int seconds = (int) (time / 1000);
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(seconds);
    }

    @Override
    public Date decode(InputStream inputStream) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        long seconds = dataInputStream.readInt();
        if (seconds < 0) {
            seconds = ((2L << 31) + seconds);
        }
        return new Date(seconds * 1000L);
    }
}
