package com.fiedormichal.RestFileParser.service;

import com.fiedormichal.RestFileParser.csvWriter.FileMetaDataWriterCSVWriter;
import com.fiedormichal.RestFileParser.exception.FileMetaDataNotFoundException;
import com.fiedormichal.RestFileParser.model.FileMetadata;
import com.fiedormichal.RestFileParser.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileMetadataService {
    private final FileMetadataRepository fileMetadataRepository;
    private final FileMetaDataWriterCSVWriter fileMetaDataWriter;

    public FileMetadata save(MultipartFile file, int numRows) {
        return fileMetadataRepository.save(getPreparedCompleteFileMetaData(file, numRows));
    }

    public FileMetadata findById(int id){
        return findFileMetaData(id);
    }

    public void downloadFileMetaDataAsCSVFile(int id, HttpServletResponse response) throws IOException {
        FileMetadata fileMetadata = findFileMetaData(id);
        fileMetaDataWriter.printFileMetaDataToCSV(fileMetadata, response);
    }

    private FileMetadata getPreparedCompleteFileMetaData(MultipartFile file, int numRows) {
        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setCreatedAt(LocalDateTime.now());
        fileMetadata.setFileName(file.getOriginalFilename());
        fileMetadata.setNumRows(numRows);
        return fileMetadata;
    }

    private FileMetadata findFileMetaData(int id){
        return fileMetadataRepository.findById(id).orElseThrow(
                ()-> new FileMetaDataNotFoundException("File not found."));
    }




}
