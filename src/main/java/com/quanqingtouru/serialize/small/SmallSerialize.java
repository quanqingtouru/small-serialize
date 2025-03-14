package com.quanqingtouru.serialize.small;

import com.quanqingtouru.serialize.small.core.*;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

public class SmallSerialize {

    protected static final HashMap<Short, Class<?>> typeClass = new HashMap<>();
    protected static final HashMap<Class<?>, Short> classType = new HashMap<>();
    protected static final HashMap<Class<?>, Codec<?>> codecs = new HashMap<>();
    protected static final HashMap<String, List<Field>> fieldCache = new HashMap<>();

    static {
        registerCodec(new CodecBoolean(), Boolean.class, boolean.class);
        registerCodec(new CodecByte(), Byte.class, byte.class);
        registerCodec(new CodecShort(), Short.class, short.class);
        registerCodec(new CodecInteger(), Integer.class, int.class);
        registerCodec(new CodecLong(), Long.class, long.class);

        registerCodec(new CodecString(), String.class);
    }

    public static void registerCodec(Codec<?> codec, Class<?>... classes) {
        for (Class<?> aClass : classes) {
            codecs.put(aClass, codec);
        }
    }

    public static void registerClass(short type, Class<?> aClass) {
        typeClass.put(type, aClass);
        classType.put(aClass, type);
    }

    /**
     * 此种序类化方式，反序列化的时候需要指定类型
     */
    public static byte[] serialize(Object object) throws IOException, IllegalAccessException {
        if (object == null) {
            return new byte[]{0, 0};
        } else {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            serialize(object, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
    }

    /**
     * 完整序列化，包含类型信息，用15位来表示，反序列化时不需要指定类型
     */
    public static byte[] fullSerialize(Object object) throws IOException, IllegalAccessException {
        if (object == null) {
            return new byte[]{0, 0};
        } else {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Class<?> aClass = object.getClass();
            short type = getType(aClass);
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeShort((type << 1) | 1);
            serialize(object, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
    }

    /**
     * 第一个字节用以表示是否为null
     */
    public static void serialize(Object object, ByteArrayOutputStream dataStream) throws IOException, IllegalAccessException {
        if (object == null) {
            dataStream.write(0);
        } else {
            dataStream.write(1);
            Class<?> aClass = object.getClass();
            Codec<?> codec = getCodec(aClass);
            if (codec != null) {
                codec.encodeObject(object, dataStream);
                return;
            }

            List<Field> fields = getAllFields(aClass);
            for (Field field : fields) {
                writeField(field, object, dataStream);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserialize(byte[] bytes) throws Exception {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        short read = dataInputStream.readShort();
        if (read == 0) {
            return null;
        } else {
            Class<?> aClass = getClass((short) (read >> 1));
            return (T) deserialize(inputStream, aClass);
        }
    }

    public static <T> T deserialize(byte[] bytes, Class<T> clazz) throws Exception {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        return deserialize(inputStream, clazz);
    }

    @SuppressWarnings("all")
    private static <T> T deserialize(ByteArrayInputStream inputStream, Class<T> clazz) throws Exception {
        int read = inputStream.read();
        if (read == 0) {
            return null;
        } else {
            T object = clazz.newInstance();
            List<Field> fields = getAllFields(clazz);
            for (Field field : fields) {
                readField(field, object, inputStream);
            }

            return object;
        }
    }

    private static void writeField(Field field, Object object, ByteArrayOutputStream outputStream) throws IllegalAccessException, IOException {
        Class<?> type = field.getType();

        Codec<?> codec = getCodec(type);
        Object filedValue = field.get(object);
        if (filedValue == null) {
            outputStream.write(0);
        } else {
            outputStream.write(1);
            if (codec != null) {
                codec.encodeObject(filedValue, outputStream);
            } else {
                serialize(filedValue, outputStream);
            }
        }
    }

    private static void readField(Field field, Object object, ByteArrayInputStream inputStream) throws Exception {
        int read = inputStream.read();
        if (read == 0) {
            field.set(object, null);
        } else {
            Class<?> type = field.getType();
            Codec<?> codec = getCodec(type);
            Object fieldValue;
            if (codec != null) {
                fieldValue = codec.decode(inputStream);
            } else {
                fieldValue = deserialize(inputStream, type);
            }
            field.set(object, fieldValue);
        }
    }


    private static List<Field> getAllFields(Class<?> clazz) {
        String name = clazz.getName();
        if (!fieldCache.containsKey(name)) {
            Field[] fields = clazz.getDeclaredFields();
            List<Field> allFields = new ArrayList<>(fields.length);
            allFields.addAll(Arrays.asList(fields));
            allFields.sort(Comparator.comparing(Field::getName));
            for (Field field : allFields) {
                field.setAccessible(true);
            }
            fieldCache.put(name, allFields);
        }

        return fieldCache.get(name);
    }

    private static short getType(Class<?> aClass) {
        return classType.get(aClass);
    }

    private static Class<?> getClass(short type) {
        return typeClass.get(type);
    }

    private static Codec<?> getCodec(Class<?> aClass) {
        return codecs.get(aClass);
    }

}
