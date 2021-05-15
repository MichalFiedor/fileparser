package com.fiedormichal.RestFileParser.exception;

import com.fiedormichal.RestFileParser.ApiError.ApiError;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static com.fiedormichal.RestFileParser.ApiError.ApiErrorMsg.*;

@RestControllerAdvice
@Primary
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
        List<String> errors = new ArrayList<>();
        errors.add(String.format("Could not find the %s method for URL %s",
                ex.getHttpMethod(), ex.getRequestURL()));
        return buildResponseEntity(getApiError(errors, HttpStatus.NOT_FOUND, METHOD_NOT_FOUND.getValue()));
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<Object> handleDateParsing(DateTimeParseException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage() + " " + ex.getParsedString());

        return buildResponseEntity(getApiError(errors, HttpStatus.NOT_ACCEPTABLE, PARSING_PROBLEM.getValue()));
    }

    @ExceptionHandler(IncorrectFileContentException.class)
    public ResponseEntity<Object> handleIncorrectFileContent(IncorrectFileContentException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        return buildResponseEntity(getApiError(errors, HttpStatus.NOT_ACCEPTABLE, INCORRECT_CONTENT.getValue()));
    }

    @ExceptionHandler(WrongFormatException.class)
    public ResponseEntity<Object> handleWrongFormat(WrongFormatException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        return buildResponseEntity(getApiError(errors, HttpStatus.NOT_ACCEPTABLE, WRONG_FORMAT.getValue()));
    }

    @ExceptionHandler(FileMetadataNotFoundException.class)
    public ResponseEntity<Object> handleFileMetaDataNotFound(FileMetadataNotFoundException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        return buildResponseEntity(getApiError(errors, HttpStatus.NOT_FOUND, FILE_METADATA_NOT_FOUND.getValue()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        return buildResponseEntity(getApiError(errors, HttpStatus.NOT_FOUND, MISMATCH_TYPE.getValue()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleAll(Exception ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getLocalizedMessage());

        return buildResponseEntity(getApiError(errors, HttpStatus.BAD_REQUEST, ERROR_OCCURRED.getValue()));
    }


    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    private ApiError getApiError(List<String> errors, HttpStatus status, String message) {
        return new ApiError(
                LocalDateTime.now(),
                status,
                message,
                errors);
    }
}
