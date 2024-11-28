package com.example.WebTimTroBA.Service.Implement;

import com.example.WebTimTroBA.Converter.MotelResponseConverter;
import com.example.WebTimTroBA.CustomException.NotFoundException;
import com.example.WebTimTroBA.Model.DTO.MotelDTO;
import com.example.WebTimTroBA.Model.Entity.FileEntity;
import com.example.WebTimTroBA.Model.Entity.MotelEntity;
import com.example.WebTimTroBA.Model.Entity.UserEntity;
import com.example.WebTimTroBA.Repository.MotelRepository;
import com.example.WebTimTroBA.Model.Response.MotelResponse;
import com.example.WebTimTroBA.Model.Search.MotelSearchBuilder;
import com.example.WebTimTroBA.Service.CloudinaryService;
import com.example.WebTimTroBA.Service.FileService;
import com.example.WebTimTroBA.Service.MotelService;
import com.example.WebTimTroBA.Service.UserService;
import com.example.WebTimTroBA.Utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MotelServiceImpl implements MotelService {
    private final MotelRepository motelRepository;
    private final MotelResponseConverter motelResponseConverter;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final FileService fileService;

    @Override
    public List<MotelResponse> findByParam(MotelSearchBuilder motelSearchBuilder) throws MalformedURLException {
        List<MotelEntity> motelEntities = motelRepository.searchByMotelSearchBuilder(motelSearchBuilder);
        return motelResponseConverter.toMotelResponse(motelEntities);
    }

    @Override
    public void save(MotelDTO motelDTO, String token) throws IOException {
        Integer id = jwtTokenUtils.extractUserId(token);
        Optional<UserEntity> userEntityOptional = userService.findById(id);
        if(userEntityOptional.isEmpty()) throw new NotFoundException("User not found");
        if(!jwtTokenUtils.isTokenUserNameValid(token, userEntityOptional.get().getUsername())){
            throw new NotFoundException("Unauthorized");
        }
        MotelEntity motelEntity = modelMapper.map(motelDTO, MotelEntity.class);

        motelEntity.setUserId(id);
        motelEntity.setUser(userEntityOptional.get());
        motelRepository.save(motelEntity);

        List<MultipartFile> files = motelDTO.getFiles();
        for(MultipartFile file : files){
            Map result = cloudinaryService.uploadFile(file);
            FileEntity fileEntity = FileEntity
                    .builder()
                    .name(result.get("original_filename").toString())
                    .fileUrl(result.get("url").toString())
                    .fileId(result.get("public_id").toString())
                    .motelId(motelEntity.getId())
                    .motelEntity(motelEntity)
                    .build();
            motelEntity.getFileEntities().add(fileEntity);
        }
        motelRepository.save(motelEntity);
    }

    @Override
    public List<MotelResponse> findAll() throws MalformedURLException {
//        List<MotelEntity> motelEntities = motelRepository.findAll(Sort.by("created_at").descending().and(Sort.by("updated_at").descending().and(Sort.by("status").ascending())));

        return motelResponseConverter.toMotelResponse(motelRepository.findAll());
    }

    @Override
    public void markById(Integer Id) {
        MotelEntity motelEntity = motelRepository.findById(Id).get();
        motelEntity.setStatus(1-motelEntity.getStatus());
        motelRepository.save(motelEntity);
    }

    @Override
    public List<MotelResponse> findAndPagination(Integer page) throws MalformedURLException {
        Page<MotelEntity> motelResponses = motelRepository.findAll(PageRequest.of(page-1, 4, Sort.by("created_at").descending().and(Sort.by("updated_at").descending().and(Sort.by("status").ascending()))));
        return motelResponseConverter.toMotelResponse(motelResponses.getContent());
    }

    @Override
    public List<MotelResponse> findByUserName(String name) throws MalformedURLException {
        Optional<UserEntity> user = userService.findByUserName(name);
        if(user.isEmpty()) throw new NotFoundException("User not found");
        Optional<List<MotelEntity>> motelEntities = motelRepository.findByUserId(user.get().getId());
        if(motelEntities.isEmpty()) throw new NotFoundException("Empty");
        return motelResponseConverter.toMotelResponse(motelEntities.get());
    }

    @Override
    public void deleteById(Integer Id) {
        motelRepository.deleteById(Id);
    }

    @Override
    public MotelResponse getById(Integer Id) {
        MotelResponse motelResponse = motelResponseConverter.toMotelResponse(motelRepository.findById(Id).get());

        return motelResponse;
    }

    @Override
    public void editById(Integer Id, MotelDTO motelDTO) throws IOException {
        if (Objects.equals(Id, motelDTO.getUserId())) {
            MotelEntity motelEntity = motelRepository.findById(motelDTO.getId()).get();
            modelMapper.map(motelDTO, motelEntity);
            if(motelDTO.getListIdDelete() != null){
                List<Integer> ListIdDelete = motelDTO.getListIdDelete();
                for (Integer IdDelete : ListIdDelete) {
                    FileEntity fileEntity = fileService.findById(IdDelete);
                    fileService.delete(IdDelete);
                }
            }
            List<MultipartFile> files = motelDTO.getFiles();
            if(files != null){
                for(MultipartFile file : files){
                    Map result = cloudinaryService.uploadFile(file);
                    FileEntity fileEntity = FileEntity
                            .builder()
                            .name(result.get("original_filename").toString())
                            .fileUrl(result.get("url").toString())
                            .fileId(result.get("public_id").toString())
                            .motelId(motelEntity.getId())
                            .motelEntity(motelEntity)
                            .build();
                    motelEntity.getFileEntities().add(fileEntity);
                }
            }
            motelRepository.save(motelEntity);
        } else {
            throw new NotFoundException("Ban khong co quyen");
        }
    }

    @Override
    public List<MotelResponse> getMotelsByUserId(Integer Id) {
        Optional<List<MotelEntity>> motelEntities = motelRepository.findByUserId(Id);
        List<MotelResponse> result = new ArrayList<>();
        for(MotelEntity motelEntity : motelEntities.get()){
            result.add(motelResponseConverter.toMotelResponse(motelEntity));
        }

        return result;
    }

    @Override
    public List<MotelResponse> getMotelsByStatus(Integer status) throws MalformedURLException {
        return motelResponseConverter.toMotelResponse(motelRepository.findMotelByStatus(status));
    }

    @Override
    public void setStatus(List<Integer> Id, Integer status) throws MalformedURLException {
        for (Integer Ids : Id) {
            MotelEntity motelEntity = motelRepository.findById(Ids).get();
            motelEntity.setStatus(status);
            motelRepository.save(motelEntity);
        }

    }
}
