package com.TestTask_DocApplication.DocApplication.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentImplEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private TypeDocEntity type;

    @Column(nullable = false)
    private Date date;

    @Lob
    @Column(nullable = false)
    private byte[] content;

    @Column(nullable = false)
    private String fileName;
}