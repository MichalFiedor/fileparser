package com.fiedormichal.RestFileParser.dto;

import com.fiedormichal.RestFileParser.model.FileMetadata;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileMetaDataDtoMapperTest {

    @Test
    void should_return_FileMetadataDto(){
        //given
        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setId(1);
        fileMetadata.setFileName("test.txt");
        fileMetadata.setCreatedAt(LocalDateTime.of(2021, 05, 15, 15, 30));
        fileMetadata.setNumRows(5999);
        //when
        FileMetaDataDto result = FileMetaDataDtoMapper.getFileMetaDataDto(fileMetadata);
        //then
        assertEquals(FileMetaDataDto.class, result.getClass());
        assertEquals(1, result.getId());
        assertEquals("test.txt", result.getName());

    }

    @Test
    void should_return_FileMetadataDtos_list(){
        //given
        List<FileMetadata> fileMetadataList = getFileMetadataDtos();
        //when
        List<FileMetaDataDto>result = FileMetaDataDtoMapper.getFileMetaDataDtos(fileMetadataList);
        //then
        assertEquals(2, result.size());
        assertEquals("test1.txt", result.get(0).getName());
        assertEquals(2, result.get(1).getId());
    }

    private List<FileMetadata>getFileMetadataDtos(){
        List<FileMetadata> fileMetadataList = new ArrayList<>();
        FileMetadata fileMetadata1 = new FileMetadata();
        fileMetadata1.setId(1);
        fileMetadata1.setFileName("test1.txt");
        fileMetadata1.setCreatedAt(LocalDateTime.now());
        fileMetadata1.setNumRows(4444);
        fileMetadataList.add(fileMetadata1);
        FileMetadata fileMetadata2 = new FileMetadata();
        fileMetadata2.setId(2);
        fileMetadata2.setFileName("test2.txt");
        fileMetadata2.setCreatedAt(LocalDateTime.now());
        fileMetadata2.setNumRows(5555);
        fileMetadataList.add(fileMetadata2);
        return fileMetadataList;
    }
}