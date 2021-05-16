package com.fiedormichal.RestFileParser.controller;

import com.fiedormichal.RestFileParser.csvWriter.FileMetaDataWriter;
import com.fiedormichal.RestFileParser.exception.FileMetadataNotFoundException;
import com.fiedormichal.RestFileParser.exception.WrongFormatException;
import com.fiedormichal.RestFileParser.model.FileMetadata;
import com.fiedormichal.RestFileParser.service.ChiropractorService;
import com.fiedormichal.RestFileParser.service.FileMetadataService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@WithMockUser(username = "user")
class FileControllerTest {

    @MockBean
    private FileMetadataService fileMetadataService;

    @MockBean
    private FileMetaDataWriter fileMetaDataWriter;

    @MockBean
    private ChiropractorService chiropractorService;

    @MockBean
    private FileMetadata fileMetadata;

    @MockBean
    private MultipartFile multipartFile;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FileController fileController;


    @Test
    void shouldReturnFilesList() throws Exception {
        List<FileMetadata> fileMetadataList = new ArrayList<>();
        FileMetadata fileMetadata1 = new FileMetadata();
        fileMetadata1.setFileName("test1.txt");
        fileMetadata1.setNumRows(5000);
        fileMetadata1.setId(1);
        fileMetadata1.setCreatedAt(LocalDateTime.of(2020, 12, 4, 12, 34));
        FileMetadata fileMetadata2 = new FileMetadata();
        fileMetadata2.setFileName("test2.txt");
        fileMetadata2.setNumRows(10000);
        fileMetadata2.setId(2);
        fileMetadata2.setCreatedAt(LocalDateTime.of(2021, 11, 5, 11, 11));
        fileMetadataList.add(fileMetadata1);
        fileMetadataList.add(fileMetadata2);
        when(fileMetadataService.getFiles()).thenReturn(fileMetadataList);

        mockMvc.perform(get("/files")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("test1.txt")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("test2.txt")));
    }


    @Test
    void shouldReturnFileMetadata() throws Exception {
        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setFileName("test1.txt");
        fileMetadata.setNumRows(5000);
        fileMetadata.setId(1);
        fileMetadata.setCreatedAt(LocalDateTime.of(2020, 12, 4, 12, 34));

        when(fileMetadataService.findById(1)).thenReturn(fileMetadata);

        mockMvc.perform(get("/files/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(fileMetadata.getId())))
                .andExpect(jsonPath("$.fileName", is(fileMetadata.getFileName())))
                .andExpect(jsonPath("$.numRows", is(fileMetadata.getNumRows())))
                .andExpect(jsonPath("$.createdAt", is("2020-12-04T12:34:00")));
    }

    @Test
    void shouldRespondNotFoundStatusWhenFileMetadataWithGivenIdNotExist() throws Exception {

        when(fileMetadataService.findById(1)).thenThrow(FileMetadataNotFoundException.class);

        mockMvc.perform(get("/files/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json"));

    }

    @Test
    void shouldRespondOkStatusWhenGivenCorrectId() throws Exception {

        mockMvc.perform(get("/files/{id}/download", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(FileController.class))
                .andExpect(handler().methodName("downloadFileMetadata"));
    }

    @Test
    void shouldRespondOkStatusWhenDataFromFileSavedSuccessfully() throws Exception {

        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setFileName("test.txt");
        fileMetadata.setId(1);
        fileMetadata.setNumRows(5000);
        fileMetadata.setCreatedAt(LocalDateTime.of(2020, 12, 4, 12, 34));

        when(multipartFile.getContentType()).thenReturn("text/plain");
        when(fileMetadataService.getFileMetaData(multipartFile)).thenReturn(fileMetadata);
        when(fileMetadataService.save(fileMetadata)).thenReturn(fileMetadata);
        when(chiropractorService.saveDataOfEachChiropractor(multipartFile, fileMetadata)).thenReturn(5000);

        ResponseEntity<Object> response = fileController.saveData(multipartFile);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(fileMetadata.toString(), response.getBody().toString());

    }

    @Test
    void shouldRespondNotAcceptableStatusWhenWrongFileFormat() throws Exception {
        when(multipartFile.getContentType()).thenReturn("jpg");
        assertThrows(WrongFormatException.class, () -> fileController.saveData(multipartFile));
    }

}
