package com.quanqingtouru.serialize.small.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class CodecList extends Codec<List<?>> {
    @Override
    public void encode(List<?> object, OutputStream outputStream) throws IOException {

    }

    @Override
    public List<?> decode(InputStream inputStream) throws IOException {
        return List.of();
    }
}
