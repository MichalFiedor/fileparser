package com.fiedormichal.RestFileParser.ApiError;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApiErrorMsg {
    FILE_METADATA_NOT_FOUND("File metadata not found. Check errors list for details."),
    METHOD_NOT_FOUND("Method with this URL not found."),
    MISMATCH_TYPE("Mismatch type has been occurred. Check if path variable has correct type."),
    WRONG_FORMAT("Input file has wrong type."),
    PRINTING_TO_CSV_PROBLEM("Something went wrong with printing file metadata to .CSV file: "),
    READING_FILE_PROBLEM("Something went wrong with reading file: "),
    INCORRECT_CONTENT("Passed file has incorrect content."),
    PARSING_PROBLEM("Something went wrong with date parsing. "),
    ERROR_OCCURRED("Some exceptions occurred. Check errors list.");

    private String value;
}
