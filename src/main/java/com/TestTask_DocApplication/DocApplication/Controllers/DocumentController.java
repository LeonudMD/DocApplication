package com.TestTask_DocApplication.DocApplication.Controllers;

import com.TestTask_DocApplication.DocApplication.DTO.DocumentDTO;
import com.TestTask_DocApplication.DocApplication.Entity.DocumentImplEntity;
import com.TestTask_DocApplication.DocApplication.Entity.TypeDocEntity;
import com.TestTask_DocApplication.DocApplication.Services.DocumentService;
import com.TestTask_DocApplication.DocApplication.Services.TypeDocService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/documents")
@AllArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final TypeDocService typeDocService;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        List<TypeDocEntity> typeDocList = typeDocService.readAllType();
        model.addAttribute("document", new DocumentDTO());
        model.addAttribute("typeDocList", typeDocList);
        return "documentForm";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("document") DocumentDTO dto, @RequestParam("content") MultipartFile file, Model model) {
        try {
            dto.setContent(file);
            dto.setFileName(file.getOriginalFilename());
            documentService.createDoc(dto);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "errorPage";
        } catch (IOException e) {
            e.printStackTrace();
            return "errorPage";
        }
        return "redirect:/documents";
    }

    @GetMapping
    public String readAll(Model model) {
        List<DocumentImplEntity> documents = documentService.readAllDocs();
        List<TypeDocEntity> typeDocList = typeDocService.readAllType();
        model.addAttribute("documents", documents);
        model.addAttribute("typeDocList", typeDocList);
        return "documentList";
    }

    @GetMapping("/{id}")
    public String readByCategoryId(@PathVariable Long id, Model model) {
        List<DocumentImplEntity> documents = documentService.readByTypeId(id);
        model.addAttribute("documents", documents);
        return "documentList";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        DocumentImplEntity document = documentService.readDocById(id);
        List<TypeDocEntity> typeDocList = typeDocService.readAllType();
        DocumentDTO dto = new DocumentDTO();
        dto.setId(document.getId());  // Set the id
        dto.setTitle(document.getTitle());
        dto.setDate(document.getDate());
        dto.setTypeId(document.getType().getId());
        dto.setFileName(document.getFileName());
        model.addAttribute("document", dto);
        model.addAttribute("typeDocList", typeDocList);

        LocalDate localDate = document.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = localDate.format(formatter);
        model.addAttribute("dateCurr", formattedDate);

        return "documentUpdateForm";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("document") DocumentDTO dto, @RequestParam("content") MultipartFile file, Model model) {
        try {
            dto.setContent(file);
            dto.setFileName(file.getOriginalFilename());
            documentService.updateDoc(id, dto);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "errorPage";
        } catch (IOException e) {
            e.printStackTrace();
            return "errorPage";
        }
        return "redirect:/documents";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        documentService.deleteDoc(id);
        return "redirect:/documents";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Long id) {
        DocumentImplEntity document = documentService.readDocById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + document.getFileName())
                .body(new ByteArrayResource(document.getContent()));
    }

    @GetMapping("/filter")
    public String filterDocs(@RequestParam(required = false) String query,
                             @RequestParam(required = false) Long typeId,
                             @RequestParam(required = false) String startDate,
                             @RequestParam(required = false) String endDate,
                             Model model) {
        Date start = null;
        Date end = null;
        try {
            if (startDate != null && !startDate.isEmpty()) {
                start = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
            }
            if (endDate != null && !endDate.isEmpty()) {
                end = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<DocumentImplEntity> documents = documentService.filterDocs(typeId, start, end, query);
        List<TypeDocEntity> typeDocList = typeDocService.readAllType();
        model.addAttribute("documents", documents);
        model.addAttribute("typeDocList", typeDocList);
        model.addAttribute("query", query);
        model.addAttribute("typeId", typeId);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        if (typeId != null) {
            TypeDocEntity typeDoc = typeDocService.readDocById(typeId);
            model.addAttribute("typeDocName", typeDoc != null ? typeDoc.getName() : null);
        } else {
            model.addAttribute("typeDocName", null);
        }

        return "documentList";
    }

}
