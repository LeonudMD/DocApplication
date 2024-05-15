package com.TestTask_DocApplication.DocApplication.Services;

import com.TestTask_DocApplication.DocApplication.DTO.DocumentDTO;
import com.TestTask_DocApplication.DocApplication.Entity.DocumentImplEntity;
import com.TestTask_DocApplication.DocApplication.Repositories.DocumentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public abstract class AbstractDocumentService {

    protected final DocumentRepository docRepository;
    protected final TypeDocService typeDocService;

    public abstract DocumentImplEntity createDoc(DocumentDTO dto) throws IOException;

    public List<DocumentImplEntity> readAllDocs() {
        return docRepository.findAll();
    }

    public List<DocumentImplEntity> readByTypeId(Long id) {
        return docRepository.findByTypeId(id);
    }

    public void deleteDoc(Long id) {
        docRepository.deleteById(id);
    }

    public DocumentImplEntity readDocById(Long id) {
        return docRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Документ с id:" + id + " не найден"));
    }

    public abstract DocumentImplEntity updateDoc(Long id, DocumentDTO dto) throws IOException;

    protected String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    protected boolean isDuplicate(DocumentDTO dto) {
        List<DocumentImplEntity> documents = docRepository.findAll();
        String dtoFormattedDate = formatDate(dto.getDate());
        return documents.stream().anyMatch(document ->
                document.getTitle().equals(dto.getTitle()) &&
                        formatDate(document.getDate()).equals(dtoFormattedDate) &&
                        document.getType().getId().equals(dto.getTypeId())
        );
    }

    public List<DocumentImplEntity> searchDocsByTitle(String title) {
        return docRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<DocumentImplEntity> filterDocs(Long typeId, Date startDate, Date endDate, String query) {
        if (typeId != null && startDate != null && endDate != null) {
            return docRepository.findByTypeIdAndDateBetweenAndTitleContainingIgnoreCase(typeId, startDate, endDate, query);
        } else if (typeId != null) {
            return docRepository.findByTypeIdAndTitleContainingIgnoreCase(typeId, query);
        } else if (startDate != null && endDate != null) {
            return docRepository.findByDateBetweenAndTitleContainingIgnoreCase(startDate, endDate, query);
        } else if (query != null && !query.isEmpty()) {
            return docRepository.findByTitleContainingIgnoreCase(query);
        } else {
            return docRepository.findAll();
        }
    }
}