package com.example.WebTimTroBA.Service;

import com.example.WebTimTroBA.Model.DTO.MotelDTO;
import com.example.WebTimTroBA.Model.Response.MotelResponse;
import com.example.WebTimTroBA.Model.Search.MotelSearchBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface MotelService {
    List<MotelResponse> findByParam(MotelSearchBuilder motelSearchBuilder) throws MalformedURLException;
    void save(MotelDTO motelDTO, String token) throws IOException;
    List<MotelResponse> findAll() throws MalformedURLException;
    void markById(Integer Id);
    List<MotelResponse> findAndPagination(Integer page) throws MalformedURLException;
    List<MotelResponse> findByUserName(String name) throws MalformedURLException;
    void deleteById(Integer Id);
    MotelResponse getById(Integer Id);
    void editById(Integer Id, MotelDTO motelDTO) throws IOException;
    List<MotelResponse>getMotelsByUserId (Integer Id);
}
