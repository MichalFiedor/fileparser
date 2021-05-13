package com.fiedormichal.RestFileParser.controller;

import com.fiedormichal.RestFileParser.model.TextFile;
import com.fiedormichal.RestFileParser.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FileController {
    private final PersonService personService;
    @PostMapping("/files")
    public ResponseEntity<Object> saveDataFromFile(@RequestBody TextFile fileToRead) throws IOException {
        personService.batchPeople(fileToRead);
        return ResponseEntity.ok().body("");
    }
}
