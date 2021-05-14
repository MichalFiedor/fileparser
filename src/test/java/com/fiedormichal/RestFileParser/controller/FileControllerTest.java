package com.fiedormichal.RestFileParser.controller;

import com.fiedormichal.RestFileParser.config.WebConfig;
import jdk.net.SocketFlow;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
class FileControllerTest {

    @Autowired
    private FileController fileController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    void whenGivenTextFile_thenShouldReturnStatusOk() throws Exception {

        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "test.txt",
                "",
                "text/plain",
                "06253|ROOT|AARON||SAN ANTONIO|TX|ACTIVE|7/30/1993|12/1/2022|YES".getBytes(StandardCharsets.UTF_8));

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/files")
                .file(mockMultipartFile))
                .andExpect(status().isOk());
    }
}