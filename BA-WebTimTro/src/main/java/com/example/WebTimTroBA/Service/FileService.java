package com.example.WebTimTroBA.Service;

import com.example.WebTimTroBA.Model.Entity.FileEntity;

import java.util.List;
import java.util.Map;

public interface FileService {
    void save(Map result);
    List<FileEntity> findByMotelId(Integer id);
    FileEntity findById(Integer id);
    void delete(Integer id);
}
