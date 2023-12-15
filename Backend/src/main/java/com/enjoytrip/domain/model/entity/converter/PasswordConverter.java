package com.enjoytrip.domain.model.entity.converter;

import javax.persistence.AttributeConverter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.crypto.password.PasswordEncoder;

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
