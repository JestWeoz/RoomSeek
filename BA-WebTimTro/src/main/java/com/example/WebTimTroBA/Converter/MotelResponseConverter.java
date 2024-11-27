package com.example.WebTimTroBA.Converter;

import com.example.WebTimTroBA.Model.DTO.FileDTO;
import com.example.WebTimTroBA.Model.Entity.FileEntity;
import com.example.WebTimTroBA.Model.Entity.MotelEntity;
import com.example.WebTimTroBA.Model.Response.MotelResponse;
import com.example.WebTimTroBA.Service.FileService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MotelResponseConverter {
    private final ModelMapper modelMapper;
    private final FileService fileService;
    @Autowired
    private final OwnerResponseConverter ownerResponseConverter;


    public List<MotelResponse> toMotelResponse(List<MotelEntity> motelEntities) throws MalformedURLException {
        List<MotelResponse> result = new ArrayList<>();
        for(MotelEntity motelEntity : motelEntities){
            MotelResponse motelResponse = modelMapper.map(motelEntity, MotelResponse.class);
            List<FileDTO> filesDTO = new ArrayList<>();
            List<FileEntity> fileEntities = fileService.findByMotelId(motelEntity.getId());
            for(FileEntity fileEntity : fileEntities){
                FileDTO fileDTO = FileDTO
                        .builder()
                        .name(fileEntity.getName())
                        .fileId(fileEntity.getFileId())
                        .fileUrl(fileEntity.getFileUrl())
                        .build();
                filesDTO.add(fileDTO);
            }
            motelResponse.setFilesDTO(filesDTO);
            motelResponse.setCreateAt(motelEntity.getCreatedAt().toLocalDate());
            motelResponse.setOwner(ownerResponseConverter.toOwnerResponse(motelEntity.getUser()));
            motelResponse.setAddress("Số " + motelEntity.getHouseNumber() + ", " + motelEntity.getStreet() + ", "
                                        + motelEntity.getWard() + ", " + motelEntity.getDistrict() + ", " + motelEntity.getProvince());
            if(motelEntity.getStatus() == 1){
                motelResponse.setStatus("đã duyệt");
            }
            else motelResponse.setStatus("chưa duyệt");

            result.add(motelResponse);
        }
        Collections.sort(result);
        return result;
    }
    public MotelResponse toMotelResponse(MotelEntity motelEntity) {
        MotelResponse motelResponse = modelMapper.map(motelEntity, MotelResponse.class);
        List<FileDTO> filesDTO = new ArrayList<>();
        List<FileEntity> fileEntities = fileService.findByMotelId(motelEntity.getId());
        for(FileEntity fileEntity : fileEntities){
            FileDTO fileDTO = FileDTO
                    .builder()
                    .id(fileEntity.getId())
                    .name(fileEntity.getName())
                    .fileId(fileEntity.getFileId())
                    .fileUrl(fileEntity.getFileUrl())
                    .build();
            filesDTO.add(fileDTO);
        }
        motelResponse.setCreateAt(motelEntity.getCreatedAt().toLocalDate());
        motelResponse.setFilesDTO(filesDTO);
        motelResponse.setOwner(ownerResponseConverter.toOwnerResponse(motelEntity.getUser()));
        return motelResponse;
    }
}
