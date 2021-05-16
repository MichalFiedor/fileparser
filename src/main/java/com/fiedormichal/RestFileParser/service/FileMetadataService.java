package com.fiedormichal.RestFileParser.service;

import com.fiedormichal.RestFileParser.csvWriter.FileMetaDataWriterCSVWriter;
import com.fiedormichal.RestFileParser.exception.FileMetadataNotFoundException;
import com.fiedormichal.RestFileParser.model.FileMetadata;
import com.fiedormichal.RestFileParser.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class FileMetadataService {
    private final FileMetadataRepository fileMetadataRepository;
    private final FileMetaDataWriterCSVWriter fileMetaDataWriter;

    public FileMetadata save(FileMetadata fileMetadata) {
        return fileMetadataRepository.save(fileMetadata);
    }

    public List<FileMetadata> getFiles() {
        return fileMetadataRepository.findAll();
    }

    public void downloadFileMetadataAsCSVFile(int id, HttpServletResponse response) {
        FileMetadata fileMetadata = findById(id);
        fileMetaDataWriter.printFileMetaDataToCSV(fileMetadata, response);
    }

    public FileMetadata getFileMetaData(MultipartFile file) {
        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setCreatedAt(LocalDateTime.now());
        fileMetadata.setFileName(file.getOriginalFilename());
        return fileMetadata;
    }

    public FileMetadata findById(int id) {
        return fileMetadataRepository.findById(id).orElseThrow(
                () -> new FileMetadataNotFoundException("File with id: " + id + " not found."));
    }
}
