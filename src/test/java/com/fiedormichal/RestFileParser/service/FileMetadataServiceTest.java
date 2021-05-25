package com.fiedormichal.RestFileParser.service;

import com.fiedormichal.RestFileParser.csvWriter.FileMetaDataWriterCSVWriter;
import com.fiedormichal.RestFileParser.exception.FileMetadataNotFoundException;
import com.fiedormichal.RestFileParser.model.FileMetadata;
import com.fiedormichal.RestFileParser.repository.FileMetadataRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class FileMetadataServiceTest {

    @MockBean
    private FileMetadataRepository fileMetadataRepository;

    @MockBean
    private FileMetaDataWriterCSVWriter fileMetaDataWriter;

    @Autowired
    private FileMetadataService fileMetadataService;

    @Test
    void givenMultipartFile_whenGetFileMetaData_thenReturnFileMetadata(){
        //given
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getOriginalFilename()).thenReturn("test.txt");
        //when
        FileMetadata result = fileMetadataService.getFileMetaData(multipartFile);
        //then
        assertEquals("test.txt", result.getFileName());
        assertEquals(LocalDateTime.class, result.getCreatedAt().getClass());
        assertEquals(null, result.getId());
        assertEquals(null, result.getNumRows());
    }

    @Test
    void whenDownloadFileMetadataAsCSVFile_thenCalledOncePrintFileMetaDataToCSVMethod(){
        FileMetadata fileMetadata = mock(FileMetadata.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        //when
        when(fileMetadataRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(fileMetadata));
        fileMetadataService.downloadFileMetadataAsCSVFile(1,httpServletResponse);
        //then
        verify(fileMetaDataWriter, times(1)).printFileMetaDataToCSV(fileMetadata, httpServletResponse);
    }

    @Test
    void whenFileMetadataNotFound_thenThrowFileMetadataNotFoundException(){
        when(fileMetadataRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(FileMetadataNotFoundException.class,  ()-> fileMetadataService.findById(1));
    }
}