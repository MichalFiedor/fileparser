package com.fiedormichal.RestFileParser.repository;

import com.fiedormichal.RestFileParser.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
