package com.fiedormichal.RestFileParser.controller;

import com.fiedormichal.RestFileParser.csvWriter.FileMetaDataWriter;
import com.fiedormichal.RestFileParser.dto.FileMetaDataDtoMapper;
import com.fiedormichal.RestFileParser.exception.WrongFormatException;
import com.fiedormichal.RestFileParser.model.FileMetadata;
import com.fiedormichal.RestFileParser.service.ChiropractorService;
import com.fiedormichal.RestFileParser.service.FileMetadataService;
import com.fiedormichal.RestFileParser.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

import static com.fiedormichal.RestFileParser.ApiError.ApiErrorMsg.WRONG_FORMAT;

@RestController
@RequiredArgsConstructor
@Log4j2
public class FileController {
    private final ChiropractorService chiropractorService;
    private final FileMetadataService fileMetadataService;
    private final FileMetaDataWriter fileMetaDataWriter;

    @GetMapping("/files")
    public ResponseEntity<Object> getFiles() {
        return ResponseEntity.ok().body(FileMetaDataDtoMapper.getFileMetaDataDtos(fileMetadataService.getFiles()));
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<Object> getFile(@PathVariable int id) {
        return ResponseEntity.ok().body(fileMetadataService.findById(id));
    }

    @GetMapping("/files/{id}/download")
    public void downloadFileMetadata(@PathVariable int id, HttpServletResponse response) {
        fileMetaDataWriter.prepareResponse(response, id);
        fileMetadataService.downloadFileMetadataAsCSVFile(id, response);
    }

    @PostMapping(value = "/files")
    public ResponseEntity<Object> saveData(@RequestParam("file") MultipartFile file) throws Exception {
        if (!FileService.isTextFile.test(file)) {
            log.debug(WRONG_FORMAT.getValue());
            throw new WrongFormatException("File has wrong format. Only .txt files are allowed.");
        }
        FileMetadata fileMetadata = fileMetadataService.save(fileMetadataService.getFileMetaData(file));
        Integer numRows = chiropractorService.saveDataOfEachChiropractor(file, fileMetadata);
        fileMetadata.setNumRows(numRows);
        fileMetadataService.save(fileMetadata);
        log.info("Reading the file " + file.getOriginalFilename() + " was successful.");

        return ResponseEntity.ok().body(fileMetadata);
    }
}