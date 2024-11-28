package com.example.WebTimTroBA.Repository;


import com.example.WebTimTroBA.Model.Entity.MotelEntity;
import com.example.WebTimTroBA.Model.Response.MotelResponse;
import com.example.WebTimTroBA.Repository.Custom.MotelRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface MotelRepository extends JpaRepository<MotelEntity, Integer>, MotelRepositoryCustom, PagingAndSortingRepository<MotelEntity, Integer> {
    Optional<List<MotelEntity>> findByUserId(Integer id);
    MotelEntity findById (int id);
    List<MotelEntity> findMotelByStatus(Integer status);

}
