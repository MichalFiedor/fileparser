package com.fiedormichal.RestFileParser.service;

import com.fiedormichal.RestFileParser.model.FileMetadata;
import com.fiedormichal.RestFileParser.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileMetadataRepository fileRepository;

    public static final Predicate<MultipartFile> isTextFile =
            file -> !Objects.isNull(file.getContentType()) && file.getContentType().endsWith("text/plain");

    public List<String[]> readFile(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
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
