package com.fiedormichal.RestFileParser.dto;

import com.fiedormichal.RestFileParser.model.FileMetadata;
import java.util.List;
import java.util.stream.Collectors;

public class FileMetaDataDtoMapper {

    private FileMetaDataDtoMapper(){

    }

    public static List<FileMetaDataDto> getFileMetaDataDtos(List<FileMetadata>fileMetadataList){
        return fileMetadataList.stream()
                .map(fileMetadata -> getFileMetaDataDto(fileMetadata))
                .collect(Collectors.toList());
    }

    public static FileMetaDataDto getFileMetaDataDto(FileMetadata fileMetadata){
        FileMetaDataDto fileMetaDataDto = new FileMetaDataDto();
        fileMetaDataDto.setId(fileMetadata.getId());
        fileMetaDataDto.setName(fileMetadata.getFileName());
        return fileMetaDataDto;
    }
}
