package com.TestTask_DocApplication.DocApplication.Repositories;

import com.TestTask_DocApplication.DocApplication.Entity.DocumentImplEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentImplEntity, Long> {

    List<DocumentImplEntity> findByTypeId(Long typeId);



    List<DocumentImplEntity> findByTitleContainingIgnoreCase(String title);

    List<DocumentImplEntity> findByTypeIdAndTitleContainingIgnoreCase(Long typeId, String title);

    List<DocumentImplEntity> findByDateBetweenAndTitleContainingIgnoreCase(Date startDate, Date endDate, String title);

    List<DocumentImplEntity> findByTypeIdAndDateBetweenAndTitleContainingIgnoreCase(Long typeId, Date startDate, Date endDate, String title);
}

