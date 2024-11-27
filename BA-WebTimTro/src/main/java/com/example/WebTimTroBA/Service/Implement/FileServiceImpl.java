package com.example.WebTimTroBA.Service.Implement;

import com.example.WebTimTroBA.Model.Entity.FileEntity;
import com.example.WebTimTroBA.Repository.FileRepository;
import com.example.WebTimTroBA.Service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    @Override
    public void save(Map result)  {
        FileEntity fileEntity = FileEntity
                .builder()
                .name(result.get("original_filename").toString())
                .fileUrl(result.get("url").toString())
                .fileId(result.get("public_id").toString())
                .build();
        fileRepository.save(fileEntity);
    }

    @Override
    public List<FileEntity> findByMotelId(Integer id) {
        return fileRepository.findByMotelId(id);
    }

    @Override
    public FileEntity findById(Integer id) {
        return fileRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Integer id) {
        fileRepository.deleteById(id);
    }
}
