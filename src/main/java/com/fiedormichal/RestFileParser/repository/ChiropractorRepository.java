package com.fiedormichal.RestFileParser.repository;

import com.fiedormichal.RestFileParser.model.Chiropractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiropractorRepository extends JpaRepository<Chiropractor, Long> {

}
