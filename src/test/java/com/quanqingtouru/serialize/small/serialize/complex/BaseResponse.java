package com.quanqingtouru.serialize.small.serialize.complex;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public abstract class BaseResponse<T> {
    private long id;
    private String message;
    private T data;


    @Override
    public String toString() {
        return "BaseResponse{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
