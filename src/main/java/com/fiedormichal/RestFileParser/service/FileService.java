package com.fiedormichal.RestFileParser.service;

import com.fiedormichal.RestFileParser.model.FileMetadata;
import com.fiedormichal.RestFileParser.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.fiedormichal.RestFileParser.ApiError.ApiErrorMsg.READING_FILE_PROBLEM;

@Service
@RequiredArgsConstructor
@Log4j2
public class FileService {
    private final FileMetadataRepository fileRepository;

    public static final Predicate<MultipartFile> isTextFile =
            file -> !Objects.isNull(file.getContentType()) && file.getContentType().equals("text/plain");

    public List<String[]> readFile(MultipartFile file) throws Exception {
        List<String[]> splitPeopleData;
        try {
            InputStream  inputStream = file.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            splitPeopleData = bufferedReader.lines().
                    map(line -> line.split("\\|"))
                    .collect(Collectors.toList());
            splitPeopleData.remove(0);
        } catch (IOException io) {
            log.debug(READING_FILE_PROBLEM.getValue() + io.getMessage());
            throw new Exception(READING_FILE_PROBLEM.getValue() + io.getMessage());
        }
        log.info("Reading the file " + file.getOriginalFilename() + " was successful.");
        return splitPeopleData;
    }

    public List<FileMetadata> getFiles() {
       return fileRepository.findAll();
    }
}
