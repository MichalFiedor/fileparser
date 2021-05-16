package com.fiedormichal.RestFileParser.service;

import com.fiedormichal.RestFileParser.model.FileMetadata;
import com.fiedormichal.RestFileParser.repository.ChiropractorRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class ChiropractorServiceTest {

    @MockBean
    private ChiropractorRepository chiropractorRepository;

    @MockBean
    private FileService fileService;

    @MockBean
    private MultipartFile multipartFile;

    @MockBean
    private FileMetadata fileMetadata;

    @Autowired
    private ChiropractorService  chiropractorService;

    @Test
    void shouldReturnNumberOfRecordsInFile() throws Exception {
        //given
        List<String[]>peopleDataFromFile = new ArrayList<>();
        String[]person1 = {"12810","BARNWELL","ABIGAIL","FAITH","HOUSTON","TX","ACTIVE","12/11/2014","7/1/2022","YES"};
        String[]person2 = {"12894","DAVIDSON","HEATHER","NAOMI","ABBEVILLE","LA","ACTIVE","3/12/2015","6/1/2022","NO"};
        peopleDataFromFile.add(person1);
        peopleDataFromFile.add(person2);
        when(fileService.prepareRecordsFromFileToSave(multipartFile)).thenReturn(peopleDataFromFile);
        //when
        int result = chiropractorService.saveDataOfEachChiropractor(multipartFile, fileMetadata);
        //then
        assertEquals(2, result);
    }

}