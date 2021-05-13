package com.fiedormichal.RestFileParser.repository;

import com.fiedormichal.RestFileParser.model.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileMetadataRepository extends JpaRepository<FileMetadata,Integer> {


}
