package com.enjoytrip.domain.model.entity.converter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.persistence.AttributeConverter;

public class PasswordConverter implements AttributeConverter<String, String>, ApplicationContextAware {

    private PasswordEncoder passwordEncoder;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        passwordEncoder = applicationContext.getBean(PasswordEncoder.class);
    }

    @Override
    public String convertToDatabaseColumn(String raw) {
        return passwordEncoder.encode(raw);
    }

    @Override
    public String convertToEntityAttribute(String encoded) {
        return encoded;
    }
}
