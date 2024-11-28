package com.example.WebTimTroBA.Controller;

import com.example.WebTimTroBA.Filter.JwtTokenFilter;
import com.example.WebTimTroBA.Model.DTO.SetStatusDTO;
import com.example.WebTimTroBA.Service.MotelService;
import com.example.WebTimTroBA.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final MotelService motelService;



    @PostMapping("/markMotel/{id}")
    public ResponseEntity<String> markMotel(@PathVariable Integer id){
        motelService.markById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body("Thêm thành công!");
    }

    @PostMapping("/set-status")
    public ResponseEntity<?> setStatus(SetStatusDTO setStatusDTO) throws MalformedURLException {
        motelService.setStatus(setStatusDTO.getId(), setStatusDTO.getStatus());
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }


}
