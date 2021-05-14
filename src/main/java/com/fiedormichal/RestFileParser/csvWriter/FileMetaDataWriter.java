package com.fiedormichal.RestFileParser.csvWriter;

import com.fiedormichal.RestFileParser.model.FileMetadata;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public interface FileMetaDataWriter {
    void printFileMetaDataToCSV(FileMetadata fileMetadata, HttpServletResponse response) throws Exception;
    void prepareResponse(HttpServletResponse response,int id);
}
