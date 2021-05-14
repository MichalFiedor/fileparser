package com.fiedormichal.RestFileParser.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateParser {
    public static LocalDate parse(String date){
       return LocalDate.parse(date, DateTimeFormatter.ofPattern("M/d/yyyy"));

    }
}
