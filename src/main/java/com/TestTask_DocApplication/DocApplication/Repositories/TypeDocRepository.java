package com.TestTask_DocApplication.DocApplication.Repositories;

import com.TestTask_DocApplication.DocApplication.Entity.TypeDocEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeDocRepository extends JpaRepository<TypeDocEntity, Long> {

}
