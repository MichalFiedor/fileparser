package com.fiedormichal.RestFileParser.service;

import com.fiedormichal.RestFileParser.dateParser.LocalDateParser;
import com.fiedormichal.RestFileParser.model.FileToRead;
import com.fiedormichal.RestFileParser.model.Person;
import com.fiedormichal.RestFileParser.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PersonService {
    private final FileToReadService fileToReadService;
    private final PersonRepository personRepository;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    public void batchPeople(FileToRead fileToRead) throws IOException {
        List<String[]> peopleDataFromFile = fileToReadService.readFile(fileToRead);
        List<Person> peopleToSave = new ArrayList<>();

        for (int i = 0; i < peopleDataFromFile.size(); i++) {
            String[] singlePersonData = peopleDataFromFile.get(i);
            peopleToSave.add(Person.builder()
                    .licenseNumber(singlePersonData[0])
                    .lastName(singlePersonData[1])
                    .firstName(singlePersonData[2])
                    .middleName(singlePersonData[3])
                    .city(singlePersonData[4])
                    .state(singlePersonData[5])
                    .status(singlePersonData[6])
                    .issueDate(LocalDateParser.parse(singlePersonData[7]))
                    .expirationDate(LocalDateParser.parse(singlePersonData[7]))
                    .boardAction(singlePersonData[9])
                    .build());
            if (i % batchSize == 0 && i > 0) {
                personRepository.saveAll(peopleToSave);
                peopleToSave.clear();
            }
        }

        if (peopleToSave.size() > 0) {
            personRepository.saveAll(peopleToSave);
            peopleToSave.clear();
        }
    }
}
