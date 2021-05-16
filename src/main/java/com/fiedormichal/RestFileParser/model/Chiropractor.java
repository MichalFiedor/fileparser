package com.fiedormichal.RestFileParser.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
public class Chiropractor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hilo_sequence_generator")
    @GenericGenerator(
            name = "hilo_sequence_generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "hilo_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "100"),
                    @Parameter(name = "optimizer", value = "hilo")
            })
    private Long id;
    private String licenseNumber;
    private String lastName;
    private String firstName;
    private String middleName;
    private String city;
    private String state;
    private String status;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private String boardAction;
    @ManyToOne
    @JoinColumn(name = "file_id")
    private FileMetadata fileMetadata;
}
