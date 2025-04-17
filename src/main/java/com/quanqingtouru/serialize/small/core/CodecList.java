package com.quanqingtouru.serialize.small.core;

import com.quanqingtouru.serialize.small.SmallSerialize;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class CodecList extends Codec<List<?>> {
    @Override
    public void encode(List<?> object, OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        if (object == null) {
            dataOutputStream.write(0);
        } else {
            int size = object.size();
            dataOutputStream.write(1);
            dataOutputStream.writeShort(size);
            for (Object o : object) {
                SmallSerialize.serialize(o, dataOutputStream);
            }
        }
    }

    @Override
    public List<?> decode(InputStream inputStream) throws IOException {
        int read = inputStream.read();
        if (read == 0) {
            return null;
        } else {

            return new ArrayList<>();
        }
    }
}
