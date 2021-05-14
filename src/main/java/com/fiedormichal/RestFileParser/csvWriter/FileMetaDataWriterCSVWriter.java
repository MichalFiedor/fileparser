package com.fiedormichal.RestFileParser.csvWriter;

import com.fiedormichal.RestFileParser.parser.FileMetadataParser;
import com.fiedormichal.RestFileParser.model.FileMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import static com.fiedormichal.RestFileParser.ApiError.ApiErrorMsg.PRINTING_TO_CSV_PROBLEM;

@Service
@RequiredArgsConstructor
@Log4j2
public class FileMetaDataWriterCSVWriter implements FileMetaDataWriter{

    public void printFileMetaDataToCSV(FileMetadata fileMetadata, HttpServletResponse response) {
        CSVPrinter csvPrinter;
        try {
            csvPrinter = new CSVPrinter(response.getWriter(),
                    CSVFormat.newFormat('|')
                            .withRecordSeparator("\r\n")
                            .withHeader("Id", "File name", "Number of rows", "Created at"));
            List data = FileMetadataParser.parseToList(fileMetadata);
            csvPrinter.printRecord(data);
            csvPrinter.flush();
            log.info("File metadata with id: " + fileMetadata.getId() + " has been printed to .CSV file.");
        } catch (IOException io) {
            log.debug(PRINTING_TO_CSV_PROBLEM.getValue() + io.getMessage());
            throw new RuntimeException(PRINTING_TO_CSV_PROBLEM.getValue() + io.getMessage());
        }
    }

    public void prepareResponse(HttpServletResponse response,int id){
        String csvFileName = "fmd_id_" + id + ".csv";
        String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", headerValue);
    }
}
