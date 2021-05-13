package com.fiedormichal.RestFileParser.service;

import com.fiedormichal.RestFileParser.model.FileMetadata;
import com.fiedormichal.RestFileParser.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileMetadataRepository fileRepository;

    public List<String[]> readFile(String fileUrl) throws IOException {
        String filePath = fileUrl.replace("/", "\\");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        List<String[]> splitPeopleData = bufferedReader.lines().
                map(line -> line.split("\\|"))
                .collect(Collectors.toList());
        splitPeopleData.remove(0);
        return splitPeopleData;
    }

    public List<FileMetadata> getFiles() {
       return fileRepository.findAll();
    }
}
