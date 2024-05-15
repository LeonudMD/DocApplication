package com.TestTask_DocApplication.DocApplication.Controllers;

import com.TestTask_DocApplication.DocApplication.Entity.TypeDocEntity;
import com.TestTask_DocApplication.DocApplication.Services.TypeDocService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/allTypeDocs")
@AllArgsConstructor
public class TypeDocController {

    private final TypeDocService typeDocService;


    @GetMapping
    public ResponseEntity<List<TypeDocEntity>> getAllDocumentTypes() {
        return new ResponseEntity<>(typeDocService.readAllType(), HttpStatus.OK);
    }

}
