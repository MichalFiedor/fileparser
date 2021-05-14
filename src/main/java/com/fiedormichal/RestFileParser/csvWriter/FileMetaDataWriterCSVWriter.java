package com.fiedormichal.RestFileParser.csvWriter;

import com.fiedormichal.RestFileParser.model.FileMetadata;
import com.fiedormichal.RestFileParser.service.FileMetadataService;
import com.opencsv.CSVWriter;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;


@Service
@RequiredArgsConstructor
public class FileMetaDataWriterCSVWriter implements FileMetaDataWriter{
    private final FileMetadataService fileMetadataService;

    public void printFileMetaDataToCSV(FileMetadata fileMetadata, HttpServletResponse response) throws IOException {
        CSVPrinter csvPrinter = new CSVPrinter(response.getWriter(),
                CSVFormat.EXCEL.withHeader("Id", "Url", "File name", "Number of rows", "Created at"));
        List data = fileMetadataService.getListWithFileMetadata(fileMetadata);
        csvPrinter.printRecord(data);
        csvPrinter.flush();
    }

    public void prepareResponse(HttpServletResponse response,int id){
        String csvFileName = "file_id_" + id + ".csv";
        String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", headerValue);
    }
}
