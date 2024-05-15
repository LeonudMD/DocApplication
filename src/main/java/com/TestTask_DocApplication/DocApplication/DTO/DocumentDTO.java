package com.TestTask_DocApplication.DocApplication.DTO;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class DocumentDTO {

    private Long id;

    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private MultipartFile content;

    private Long typeId;

    private String fileName;
}
