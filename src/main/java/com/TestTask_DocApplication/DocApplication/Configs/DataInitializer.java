package com.TestTask_DocApplication.DocApplication.Configs;

import com.TestTask_DocApplication.DocApplication.Entity.TypeDocEntity;
import com.TestTask_DocApplication.DocApplication.Enums.DocumentType;
import com.TestTask_DocApplication.DocApplication.Repositories.TypeDocRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInitializer {

    private final TypeDocRepository typeDocRepository;

    @PostConstruct
    public void init() {
        if (typeDocRepository.count() == 0) {
            for (DocumentType documentType : DocumentType.values()) {
                TypeDocEntity typeDocEntity = new TypeDocEntity();
                typeDocEntity.setName(documentType.name());
                typeDocRepository.save(typeDocEntity);
            }
        }
    }
}