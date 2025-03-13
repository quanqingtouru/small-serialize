package com.quanqingtouru.serialize.small.core;

import java.io.*;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class Codec<T> {
    public Codec() {
    }

    @SuppressWarnings("unchecked")
    public void encodeObject(Object object, OutputStream outputStream) throws IOException {
        encode((T) object, outputStream);
    }

    public abstract void encode(T object, OutputStream outputStream) throws IOException;

    public abstract T decode(InputStream inputStream) throws IOException;


}
