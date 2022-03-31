package com.example.jpa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Boolean을 YN으로 변환해주는 컨버터
 */
@Converter
//@Converter(autoApply = true)    // 모든 Boolean 타입에 컨버터를 적용(글로벌 설정시 필드에 @Convert를 지정하지 않아도 컨버터가 적용된다.)
public class BooleanToYNConverter implements AttributeConverter<Boolean, String> {

    //Entity -> DB
    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return (attribute != null && attribute) ? "Y" : "N";
    }

    //DB -> Entity
    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return "Y".equals(dbData);
    }
}
