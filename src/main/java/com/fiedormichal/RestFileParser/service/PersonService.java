package com.fiedormichal.RestFileParser.service;

import com.fiedormichal.RestFileParser.dateParser.LocalDateParser;
import com.fiedormichal.RestFileParser.model.FileMetadata;
import com.fiedormichal.RestFileParser.model.Person;
import com.fiedormichal.RestFileParser.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final FileService fileService;
    private final PersonRepository personRepository;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    public int saveDataOfEachPerson(String fileUrl) throws IOException {
        List<String[]> peopleDataFromFile = fileService.readFile(fileUrl);
        List<Person> peopleToSave = new ArrayList<>();
        int numberOfRows = peopleDataFromFile.size();

        for (int i = 0; i < peopleDataFromFile.size(); i++) {
            String[] singlePersonData = peopleDataFromFile.get(i);
            peopleToSave.add(createPerson(singlePersonData));
            if (i % batchSize == 0 && i > 0) {
                personRepository.saveAll(peopleToSave);
                peopleToSave.clear();
            }
        }
        if (peopleToSave.size() > 0) {
            personRepository.saveAll(peopleToSave);
            peopleToSave.clear();
        }
        return numberOfRows;
    }

    private Person createPerson(String[] personData) {
        return Person.builder()
                .licenseNumber(personData[0])
                .lastName(personData[1])
                .firstName(personData[2])
                .middleName(personData[3])
                .city(personData[4])
                .state(personData[5])
                .status(personData[6])
                .issueDate(LocalDateParser.parse(personData[7]))
                .expirationDate(LocalDateParser.parse(personData[7]))
                .boardAction(personData[9])
                .build();
    }
}
