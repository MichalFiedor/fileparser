package com.fiedormichal.RestFileParser.service;

import com.fiedormichal.RestFileParser.csvWriter.FileMetaDataWriterCSVWriter;
import com.fiedormichal.RestFileParser.exception.FileMetaDataNotFoundException;
import com.fiedormichal.RestFileParser.model.FileMetadata;
import com.fiedormichal.RestFileParser.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

@Service
@RequiredArgsConstructor
public class FileMetadataService {
    private final FileMetadataRepository fileMetadataRepository;
    private final FileMetaDataWriterCSVWriter fileMetaDataWriter;

    public FileMetadata save(FileMetadata fileMetadata) throws IOException {
        return fileMetadataRepository.save(getCompleteFileMetaData(fileMetadata));
    }

    public FileMetadata findById(int id){
        return findFileMetaData(id);
    }

    public void downloadFileMetaDataAsCSVFile(int id, HttpServletResponse response) throws IOException {
        FileMetadata fileMetadata = findFileMetaData(id);
        fileMetaDataWriter.printFileMetaDataToCSV(fileMetadata, response);
    }

    public List getListWithFileMetadata(FileMetadata fileMetadata){
        return Arrays.asList(
                    fileMetadata.getId().toString(),
                    fileMetadata.getUrl(),
                    fileMetadata.getFileName(),
                    fileMetadata.getNumRows().toString(),
                    fileMetadata.getCreatedAt());
    }

    private FileMetadata getCompleteFileMetaData(FileMetadata fileMetadata) throws IOException {
        Path path = Paths.get(fileMetadata.getUrl());
        BasicFileAttributes basicFileAttributes = Files.readAttributes(path, BasicFileAttributes.class);
        fileMetadata.setCreatedAt(basicFileAttributes.creationTime().toString());
        fileMetadata.setFileName(path.getFileName().toString());
        return fileMetadata;
    }

    private FileMetadata findFileMetaData(int id){
        return fileMetadataRepository.findById(id).orElseThrow(
                ()-> new FileMetaDataNotFoundException("File not found."));
    }




}
