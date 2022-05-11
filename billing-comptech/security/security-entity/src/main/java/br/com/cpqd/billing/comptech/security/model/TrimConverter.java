package br.com.cpqd.billing.comptech.security.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class TrimConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {

        if (attribute != null) {
            return attribute.trim();           
        }
        
        return attribute;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {

        if (dbData == null) {
            return null;
        }

        return dbData.trim();
    }

}
