package com.example.WebTimTroBA.Controller;


import com.example.WebTimTroBA.Converter.SearchConverter;
import com.example.WebTimTroBA.CustomException.NotNullException;
import com.example.WebTimTroBA.Model.DTO.ChangePasswordDTO;
import com.example.WebTimTroBA.Model.DTO.MotelDTO;
import com.example.WebTimTroBA.Model.DTO.UserDTO;
import com.example.WebTimTroBA.Model.DTO.UserLoginDTO;
import com.example.WebTimTroBA.Model.Entity.UserEntity;
import com.example.WebTimTroBA.Model.Response.MotelResponse;
import com.example.WebTimTroBA.Model.Search.MotelSearchBuilder;
import com.example.WebTimTroBA.Service.MotelService;
import com.example.WebTimTroBA.Service.UserService;
import com.example.WebTimTroBA.Utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class UserController {
    private final MotelService motelService;
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final SearchConverter searchConverter;

    @GetMapping("/search")
    public ResponseEntity<?>searchByMotelSearchBuilder(@RequestParam Map<String, String> search) throws MalformedURLException {
        List<MotelResponse> motelResponses = motelService.findByParam(searchConverter.toMotelSearchBuilder(search));
        return ResponseEntity.ok().body(motelResponses);
    }
    @GetMapping( value = "/dashboard")
    public ResponseEntity<?> getAll() throws MalformedURLException {
        List<MotelResponse> motelResponses = motelService.findAll();
        return ResponseEntity.ok().body(motelResponses);
    }
    @GetMapping("/get-info")
    public ResponseEntity<?> getUserDetail(@RequestHeader("Authorization") String authorization) {
        String token = authorization.replace("Bearer ", "");
        return ResponseEntity.ok(userService.getUserDetail(token));
    }
    @PostMapping("/create")
    public ResponseEntity<?> addMotel(MotelDTO motelDTO, @RequestHeader("Authorization") String authorization) throws IOException {

        try{
            String token = authorization.replace("Bearer ", "");
            motelService.save(motelDTO, token);
            return ResponseEntity.status(HttpStatus.CREATED).body("Success");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/motel/{id}")
    public ResponseEntity<?> getMotelById(@PathVariable("id") Integer id) throws MalformedURLException {
        MotelResponse motelResponse = motelService.getById(id);
        return motelResponse != null ? ResponseEntity.ok().body(motelResponse) : ResponseEntity.notFound().build();
    }
    @PostMapping(value="/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){
        try{
            userService.createUser(userDTO);
            return ResponseEntity.accepted().body("Success");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value="/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDTO userDTO){
        try{
            String token = userService.login(userDTO.getUserName(), userDTO.getPassword());
            return ResponseEntity.ok(token);
        }
        catch (Exception e){
                return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMotel(@PathVariable("id") Integer id){
        motelService.deleteById(id);
        return ResponseEntity.ok().body("Success");
    }
    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword (ChangePasswordDTO changePasswordDTO, @RequestHeader("Authorization") String authorization) {
        Integer id = jwtTokenUtils.extractUserId(authorization.replace("Bearer ", ""));
        try{
            userService.changePassword(id, changePasswordDTO);
            return ResponseEntity.ok().body("Success");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/editMotel")
    public ResponseEntity<?> editMotel( MotelDTO motelDTO, @RequestHeader("Authorization") String authorization) {
        Integer id = jwtTokenUtils.extractUserId(authorization.replace("Bearer ", ""));
        try{
            motelService.editById(id, motelDTO);

            return ResponseEntity.ok().body("Success");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("account/motels")
    public ResponseEntity<?> getUserMotel(String token, @RequestHeader("Authorization") String authorization) {
        Integer id = jwtTokenUtils.extractUserId(authorization.replace("Bearer ", ""));
        try {
            List<MotelResponse> motelResponses = motelService.getMotelsByUserId(id);
            return ResponseEntity.ok().body(motelResponses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("account/user-info")
    public ResponseEntity<?> editUserInfo(UserDTO userDTO, @RequestHeader("Authorization") String authorization) {
        Integer id = jwtTokenUtils.extractUserId(authorization.replace("Bearer ", ""));
        try {
            userService.editUserDetail(id, userDTO);
            return ResponseEntity.ok().body("Success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
