package com.fiedormichal.RestFileParser.repository;

import com.fiedormichal.RestFileParser.model.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FileMetadataRepository extends JpaRepository<FileMetadata,Integer> {

}
