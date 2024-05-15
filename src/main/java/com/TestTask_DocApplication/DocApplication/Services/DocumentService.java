package com.TestTask_DocApplication.DocApplication.Services;

import com.TestTask_DocApplication.DocApplication.DTO.DocumentDTO;
import com.TestTask_DocApplication.DocApplication.Entity.DocumentImplEntity;
import com.TestTask_DocApplication.DocApplication.Repositories.DocumentRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DocumentService extends AbstractDocumentService {

    public DocumentService(DocumentRepository docRepository, TypeDocService typeDocService) {
        super(docRepository, typeDocService);
    }

    @Override
    public DocumentImplEntity createDoc(DocumentDTO dto) throws IOException {
        if (isDuplicate(dto)) {
            throw new IllegalArgumentException("Документ с такими данными уже существует.");
        }

        DocumentImplEntity document = DocumentImplEntity.builder()
                .title(dto.getTitle())
                .content(dto.getContent().getBytes())
                .date(dto.getDate())
                .type(typeDocService.readDocById(dto.getTypeId()))
                .fileName(dto.getContent().getOriginalFilename())
                .build();
        return docRepository.save(document);
    }

    @Override
    public DocumentImplEntity updateDoc(Long id, DocumentDTO dto) throws IOException {
        if (isDuplicate(dto)) {
            throw new IllegalArgumentException("Документ с такими данными уже существует.");
        }

        DocumentImplEntity existingDocument = docRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Документ с id:" + id + " не найден"));

        existingDocument.setTitle(dto.getTitle());
        existingDocument.setDate(dto.getDate());
        existingDocument.setType(typeDocService.readDocById(dto.getTypeId()));

        if (dto.getContent() != null && !dto.getContent().isEmpty()) {
            existingDocument.setContent(dto.getContent().getBytes());
            existingDocument.setFileName(dto.getContent().getOriginalFilename());
        }

        return docRepository.save(existingDocument);
    }
}
