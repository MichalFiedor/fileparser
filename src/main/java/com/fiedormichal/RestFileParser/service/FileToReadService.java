package com.fiedormichal.RestFileParser.service;

import com.fiedormichal.RestFileParser.model.FileToRead;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileToReadService {

    public List<String[]> readFile(FileToRead fileToRead) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("TXChiropractor.txt"));
        List<String[]> splitPeopleData = bufferedReader.lines().
                map(line -> line.split("\\|"))
                .collect(Collectors.toList());
        splitPeopleData.remove(0);
        return splitPeopleData;
    }
}
