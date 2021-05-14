package com.fiedormichal.RestFileParser.parser;

import com.fiedormichal.RestFileParser.model.FileMetadata;

import java.util.Arrays;
import java.util.List;

public class FileMetadataParser {

        public static List parseToList(FileMetadata fileMetadata){
            return Arrays.asList(
                    fileMetadata.getId().toString(),
                    fileMetadata.getFileName(),
                    fileMetadata.getNumRows().toString(),
                    fileMetadata.getCreatedAt());
        }
}
