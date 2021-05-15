package com.fiedormichal.RestFileParser.csvWriter;

import com.fiedormichal.RestFileParser.model.FileMetadata;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.Csv;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class FileMetaDataWriterCSVWriterTest {


    @Autowired
    FileMetaDataWriterCSVWriter fileMetaDataWriterCSVWriter;

    @Test
    void givenFileMetadataAndHttpResponse_whenPrintFileMetadataToCSV_thenCSVFileIsCreated() throws IOException {
        //given
        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setId(1);
        fileMetadata.setFileName("test.txt");
        fileMetadata.setCreatedAt(LocalDateTime.of(2021, 05, 15, 15, 30));
        fileMetadata.setNumRows(5999);
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
    }

}