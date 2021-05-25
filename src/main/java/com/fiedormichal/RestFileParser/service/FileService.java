package com.fiedormichal.RestFileParser.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.fiedormichal.RestFileParser.ApiError.ApiErrorMsg.READING_FILE_PROBLEM;

@Service
@RequiredArgsConstructor
@Log4j2
public class FileService {

    public static final Predicate<MultipartFile> isTextFile =
            file -> !Objects.isNull(file.getContentType()) && file.getContentType().equals("text/plain");

    public List<String[]> prepareRecordsFromFileToSave(MultipartFile file) throws Exception {
        List<String[]> splitPeopleData;
        try {
            BufferedReader bufferedReader = readFile(file);
            splitPeopleData = splitEachRecordOfPersonData(bufferedReader);
        } catch (IOException io) {
            log.debug(READING_FILE_PROBLEM.getValue() + io.getMessage());
            throw new Exception(READING_FILE_PROBLEM.getValue() + io.getMessage());
        }
        return splitPeopleData;
    }

    protected BufferedReader readFile(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    protected List<String[]> splitEachRecordOfPersonData(BufferedReader bufferedReader) throws IOException {
        List<String[]> splitPeopleData = bufferedReader.lines().
                map(line -> line.split("\\|"))
                .collect(Collectors.toList());
        bufferedReader.close();
        splitPeopleData.remove(0);
        return splitPeopleData;

    }

}
