package com.example.WebTimTroBA.Service;

import com.example.WebTimTroBA.Model.DTO.ChangePasswordDTO;
import com.example.WebTimTroBA.Model.DTO.UserDTO;
import com.example.WebTimTroBA.Model.Entity.UserEntity;
import com.example.WebTimTroBA.Model.Response.MotelResponse;
import com.example.WebTimTroBA.Model.Response.UserResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserResponse> getAllUsers(Integer page);
    ResponseEntity<?> createUser(UserDTO user) throws Exception;
    String login(String username, String password) throws Exception;
    Optional<UserEntity> findByUserName(String username);
    Optional<UserEntity> findById(Integer id);
    MotelResponse findMotelById(Integer id);
    UserResponse getUserDetail(String token);
    void changePassword(Integer id, ChangePasswordDTO changePasswordDTO);
    void editUserDetail(Integer id, UserDTO userDTO);
}
