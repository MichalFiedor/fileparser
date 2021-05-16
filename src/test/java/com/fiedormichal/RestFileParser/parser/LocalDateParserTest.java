package com.fiedormichal.RestFileParser.parser;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class LocalDateParserTest {

    @Test
    void givenDataAsString_whenParse_thenReturnLocalDate() {
        //given
        String date = "10/3/2002";
        //when
        LocalDate result = LocalDateParser.parse(date);
        //then
        assertEquals(LocalDate.class, result.getClass());
        assertEquals(10, result.getMonthValue());
        assertEquals(2002, result.getYear());
        assertEquals(3, result.getDayOfMonth());
    }

    @Test
    void givenIncorrectFormatOfDataAsString_whenParse_thenThrowRunTimeException() {
        String date = "15/12/2002";
        assertThrows(DateTimeParseException.class, () -> LocalDateParser.parse(date));
    }
}