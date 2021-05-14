package com.fiedormichal.RestFileParser.controller;

import com.fiedormichal.RestFileParser.csvWriter.FileMetaDataWriter;
import com.fiedormichal.RestFileParser.dto.FileMetaDataDtoMapper;
import com.fiedormichal.RestFileParser.exception.WrongFormatException;
import com.fiedormichal.RestFileParser.model.FileMetadata;
import com.fiedormichal.RestFileParser.service.ChiropractorService;
import com.fiedormichal.RestFileParser.service.FileMetadataService;
import com.fiedormichal.RestFileParser.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;
    private final ChiropractorService chiropractorService;
    private final FileMetadataService fileMetadataService;
    private final FileMetaDataWriter fileMetaDataWriter;

    @GetMapping("/files")
    public ResponseEntity<Object> getFiles() {
        return ResponseEntity.ok().body(FileMetaDataDtoMapper.getFileMetaDataDtos(fileService.getFiles()));
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<Object> getFiles(@PathVariable int id) {
        return ResponseEntity.ok().body(fileMetadataService.findById(id));
    }

    @GetMapping("/files/{id}/download")
    public void downloadFileMetaData(@PathVariable int id, HttpServletResponse response) throws IOException {
        fileMetaDataWriter.prepareResponse(response, id);
        fileMetadataService.downloadFileMetaDataAsCSVFile(id, response);
    }

    @PostMapping("/files")
    public ResponseEntity<Object> saveData(@RequestParam("file") MultipartFile file) throws IOException {
        if (!FileService.isTextFile.test(file)) {
            throw new WrongFormatException("File has wrong format.");
        }
        Integer numRows = chiropractorService.saveDataOfEachChiropractor(file);
        FileMetadata fileMetadata = fileMetadataService.save(file, numRows);
        return ResponseEntity.ok().body(fileMetadata);
    }
}