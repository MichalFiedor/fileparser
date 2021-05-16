package com.fiedormichal.RestFileParser;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptableProperties
public class RestFileParserApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestFileParserApplication.class, args);
	}

}
