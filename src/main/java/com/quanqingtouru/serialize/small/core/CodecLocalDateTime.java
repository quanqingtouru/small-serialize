package com.quanqingtouru.serialize.small.core;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class CodecLocalDateTime extends Codec<LocalDateTime> {
    @Override
    public void encode(LocalDateTime object, OutputStream outputStream) throws IOException {
        long seconds = object.toEpochSecond(ZoneOffset.UTC);
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt((int) seconds);
    }

    @Override
    public LocalDateTime decode(InputStream inputStream) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        long seconds = dataInputStream.readInt();
        if (seconds < 0) {
            seconds = ((2L << 31) + seconds);
        }

        return LocalDateTime.ofEpochSecond(seconds, 0, ZoneOffset.UTC);
    }
}
