package com.TestTask_DocApplication.DocApplication.Services;

import com.TestTask_DocApplication.DocApplication.Entity.TypeDocEntity;
import com.TestTask_DocApplication.DocApplication.Repositories.TypeDocRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TypeDocService {

    private final TypeDocRepository typeDocRepository;

    public List<TypeDocEntity> readAllType() {
        return typeDocRepository.findAll();
    }

    public TypeDocEntity readDocById(Long id) {
        return typeDocRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Тип документа с id:" + id + " не найден"));
    }
}
