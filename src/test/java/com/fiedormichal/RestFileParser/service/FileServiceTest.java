package com.fiedormichal.RestFileParser.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class FileServiceTest {

    @Autowired
    private FileService fileService;

    @Test
    void givenStreamOfText_whenSplitEachRecordOfPersonData_thenReturnListWithSplitData(){
        //given
        List<String>records = new ArrayList<>();
                records.add("licenseNumber|lastName|firstName|middleName|city|state|status|issueDate|expirationDate|boardAction");
        records.add("10345|CHAPA|AARON|ANDRESS|LEAUGE CITY|TX|ACTIVE|6/1/2006|10/1/2022|NO");
        records.add("10374|HAMMONS|AARON||BELLEVUE|WA|NON-RENEWABLE|7/7/2006|5/1/2009|NO");
        records.add("10375|WEST|AARON|J|AMARILLO|TX|EXPIRED|7/13/2006|2/1/2021|NO");
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when(bufferedReader.lines()).thenReturn(records.stream());
        //when
        List<String[]> result = fileService.splitEachRecordOfPersonData(bufferedReader);
        //then
        assertEquals(3, result.size());
        assertEquals("10345", result.get(0)[0]);
        assertEquals("HAMMONS", result.get(1)[1]);
        assertEquals("AMARILLO", result.get(2)[4]);
    }

    @Test
    void givenMultipartFile_whenReadFile_thenReturnBufferedReader() throws IOException {
        //given
        MultipartFile file = mock(MultipartFile.class);
        String textFromFile = "licenseNumber|lastName|firstName|middleName|city|state|status|issueDate|expirationDate|boardAction" +
                "10345|CHAPA|AARON|ANDRESS|LEAUGE CITY|TX|ACTIVE|6/1/2006|10/1/2022|NO";
        InputStream inputStream = new ByteArrayInputStream(textFromFile.getBytes(StandardCharsets.UTF_8));
        when(file.getInputStream()).thenReturn(inputStream);
        //when
        BufferedReader result = fileService.readFile(file);
        //then
        assertEquals(BufferedReader.class, result.getClass());
    }

    @Test
    void givenText_whenPrepareRecordsFromFileToSave_thenReturnSplitData() throws Exception {
        //given
        BufferedReader bufferedReader = mock(BufferedReader.class);
        MultipartFile multipartFile = mock(MultipartFile.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        printWriter.println("licenseNumber|lastName|firstName|middleName|city|state|status|issueDate|expirationDate|boardAction");
        printWriter.println("10780|KRENEK|AARON|TAYLOR|BURLINGTON|IA|EXPIRED|11/19/2007|11/1/2020|NO");
        printWriter.println("12671|YOUNG|AARON|GRANT|TROPHY CLUB|TX|ACTIVE|6/13/2014|5/1/2022|NO");

        InputStream inputStream = new ByteArrayInputStream(stringWriter.toString().getBytes(StandardCharsets.UTF_8));
        when(multipartFile.getInputStream()).thenReturn(inputStream);

        when(bufferedReader.readLine()).thenReturn(stringWriter.toString());
        //when
        List<String[]> result = fileService.prepareRecordsFromFileToSave(multipartFile);
        //then
        assertEquals(2, result.size());
        assertEquals("10780", result.get(0)[0]);
        assertEquals("TAYLOR", result.get(0)[3]);
        assertEquals("6/13/2014", result.get(1)[7]);
    }

}