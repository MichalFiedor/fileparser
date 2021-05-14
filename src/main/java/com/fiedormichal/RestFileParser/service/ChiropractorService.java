package com.fiedormichal.RestFileParser.service;

import com.fiedormichal.RestFileParser.dateParser.LocalDateParser;
import com.fiedormichal.RestFileParser.model.Chiropractor;
import com.fiedormichal.RestFileParser.repository.ChiropractorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChiropractorService {
    private final FileService fileService;
    private final ChiropractorRepository chiropractorRepository;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    public int saveDataOfEachChiropractor(String fileUrl) throws IOException {
        List<String[]> peopleDataFromFile = fileService.readFile(fileUrl);
        List<Chiropractor> peopleToSave = new ArrayList<>();
        int numberOfRows = peopleDataFromFile.size();

        for (int i = 0; i < peopleDataFromFile.size(); i++) {
            String[] singlePersonData = peopleDataFromFile.get(i);
            peopleToSave.add(createChiropractor(singlePersonData));
            if (i % batchSize == 0 && i > 0) {
                chiropractorRepository.saveAll(peopleToSave);
                peopleToSave.clear();
            }
        }
        if (peopleToSave.size() > 0) {
            chiropractorRepository.saveAll(peopleToSave);
            peopleToSave.clear();
        }
        return numberOfRows;
    }

    private Chiropractor createChiropractor(String[] personData) {
        return Chiropractor.builder()
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
