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
     * 序列化，包含类型信息，用15位来表示
     */
    public static byte[] serialize(Object object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        serialize(object, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * 前两个字节用以表示是否为null|具体的类型
     */
    public static void serialize(Object object, OutputStream dataStream) {
        try {
            if (object == null) {
                dataStream.write(new byte[]{0, 0});
            } else {
                Class<?> aClass = object.getClass();
                short type = getType(aClass);
                DataOutputStream dataOutputStream = new DataOutputStream(dataStream);
                dataOutputStream.writeShort((type << 1) | 1);

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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T deserialize(byte[] bytes) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            return deserialize(inputStream, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T deserialize(byte[] bytes, Class<T> clazz) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            return deserialize(inputStream, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("all")
    private static <T> T deserialize(ByteArrayInputStream inputStream, Class<T> clazz) throws Exception {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        short read = dataInputStream.readShort();
        if (read == 0) {
            return null;
        } else {
            Class<?> aClass = clazz;
            if (clazz == null) {
                aClass = getClass((short) (read >> 1));
            }

            Object object = aClass.newInstance();
            List<Field> fields = getAllFields(aClass);
            for (Field field : fields) {
                readField(field, object, inputStream);
            }

            return (T) object;
        }
    }

    private static void writeField(Field field, Object object, OutputStream outputStream) throws IOException {
        Class<?> type = field.getType();

        Codec<?> codec = getCodec(type);
        Object filedValue = null;
        try {
            filedValue = field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
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

            for (Class<?> superclass = clazz.getSuperclass(); superclass != null; superclass = superclass.getSuperclass()) {
                allFields.addAll(0, Arrays.asList(superclass.getDeclaredFields()));
            }

            allFields.sort(Comparator.comparing(Field::getName));
            for (Field field : allFields) {
                field.setAccessible(true);
            }
            fieldCache.put(name, allFields);
        }

        return fieldCache.get(name);
    }

    private static short getType(Class<?> aClass) {
        Short value = classType.get(aClass);
        return value == null ? 0 : value;
    }

    private static Class<?> getClass(short type) {
        return typeClass.get(type);
    }

    private static Codec<?> getCodec(Class<?> aClass) {
        return codecs.get(aClass);
    }

}
