package com.stellarisapi.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 转换器类型枚举
 *
 * @author sanqi
 */
public enum ParameterAdapterEnum {

    ZELINAI("泽林AI", 1695848994195935237l);
//    USER("用户", "user"),
//    PICTURE("图片", "picture");


    private final String text;

    private final Long value;

    ParameterAdapterEnum(String text, Long value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<Long> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static ParameterAdapterEnum getEnumByValue(Long value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (ParameterAdapterEnum anEnum : ParameterAdapterEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public Long getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
