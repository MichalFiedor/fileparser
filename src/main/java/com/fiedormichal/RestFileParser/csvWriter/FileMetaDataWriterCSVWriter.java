package com.fiedormichal.RestFileParser.csvWriter;

import com.fiedormichal.RestFileParser.dateParser.FileMetadataParser;
import com.fiedormichal.RestFileParser.model.FileMetadata;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class FileMetaDataWriterCSVWriter implements FileMetaDataWriter{

    public void printFileMetaDataToCSV(FileMetadata fileMetadata, HttpServletResponse response) throws IOException {
        CSVPrinter csvPrinter = new CSVPrinter(response.getWriter(),
                CSVFormat.newFormat('|')
                        .withRecordSeparator("\r\n")
                        .withHeader("Id", "Url", "File name", "Number of rows", "Created at"));
        List data = FileMetadataParser.parseToList(fileMetadata);
        csvPrinter.printRecord(data);
        csvPrinter.flush();
    }

    public void prepareResponse(HttpServletResponse response,int id){
        String csvFileName = "fmd_id_" + id + ".csv";
        String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", headerValue);
    }
}
