package com.quanqingtouru.serialize.small.core;

import com.quanqingtouru.serialize.small.SmallSerialize;

import java.io.*;
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
                byte[] serialize = SmallSerialize.serialize(o);
                dataOutputStream.writeShort(serialize.length);
                outputStream.write(serialize);
            }
        }
    }

    @Override
    public List<?> decode(InputStream inputStream) throws IOException {
        int read = inputStream.read();
        if (read == 0) {
            return null;
        } else {
            ArrayList<Object> items = new ArrayList<>();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            int size = dataInputStream.readShort();
            for (int i = 0; i < size; i++) {
                int length = dataInputStream.readUnsignedShort();
                byte[] bytes = new byte[length];
                int r = inputStream.read(bytes);
                Object deserialize = SmallSerialize.deserialize(bytes);
                items.add(deserialize);
            }

            return items;
        }
    }
}
