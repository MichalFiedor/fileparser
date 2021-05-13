package com.fiedormichal.RestFileParser.controller;

import com.fiedormichal.RestFileParser.dto.FileMetaDataDtoMapper;
import com.fiedormichal.RestFileParser.model.FileMetadata;
import com.fiedormichal.RestFileParser.service.FileMetadataService;
import com.fiedormichal.RestFileParser.service.FileService;
import com.fiedormichal.RestFileParser.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;
    private final PersonService personService;
    private final FileMetadataService fileMetadataService;

    @GetMapping("/files")
    public ResponseEntity<Object> getFiles() {
        return ResponseEntity.ok().body(FileMetaDataDtoMapper.getFileMetaDataDtos(fileService.getFiles()));
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<Object> getFiles(@PathVariable int id) {
        return ResponseEntity.ok().body(fileMetadataService.findById(id));
    }


    @PostMapping("/files")
    public ResponseEntity<Object> saveData(@RequestBody FileMetadata file) throws IOException {
        Integer numRows = personService.saveDataOfEachPerson(file.getUrl());
        FileMetadata fileMetadata = fileMetadataService.save(file);
        fileMetadata.setNumRows(numRows);
        return ResponseEntity.ok().body(fileMetadata);
    }
}
