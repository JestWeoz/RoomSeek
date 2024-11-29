package com.example.WebTimTroBA.Service.Implement;

import com.example.WebTimTroBA.Converter.UserResponseConverter;
import com.example.WebTimTroBA.Model.DTO.ChangePasswordDTO;
import com.example.WebTimTroBA.Model.DTO.UserDTO;
import com.example.WebTimTroBA.Model.Entity.RoleEntity;
import com.example.WebTimTroBA.Model.Entity.UserEntity;
import com.example.WebTimTroBA.Model.Response.MotelResponse;
import com.example.WebTimTroBA.Model.Response.UserResponse;
import com.example.WebTimTroBA.Repository.RoleRepository;
import com.example.WebTimTroBA.Repository.UserRepository;
import com.example.WebTimTroBA.Service.UserService;
import com.example.WebTimTroBA.Utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserResponseConverter userResponseConverter;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;


    @Override
    public ResponseEntity<?> createUser(UserDTO user) {
        if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new DataIntegrityViolationException("Số Điện Thoại Đã Tồn Tại");
        }

        if (userRepository.existsByUserName(user.getUserName())) {
            throw new DataIntegrityViolationException("Tên Đăng Nhập Đã Tồn Tại");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DataIntegrityViolationException("Email Đã Tồn Tại");
        }

        if (!user.getPassword().equals(user.getRetypePassword())) {
            throw new DataIntegrityViolationException("Mật Khẩu Không Khớp");
        }
        RoleEntity roleEntity = roleRepository.findByRole("USER");
        UserEntity userEntity = UserEntity.builder().
                userName(user.getUserName())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .passWord(passwordEncoder.encode(user.getPassword()))
                .phoneNumber(user.getPhoneNumber())
                .build();
        userEntity.setRoleEntity(roleEntity);
        userRepository.save(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created user");
    }

    @Override
    public String login(String username, String password) throws Exception {
        Optional<UserEntity> user = userRepository.findByUserName(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Sai Tên Đăng Nhập Hoặc Mật Khẩu");
        }
        UserEntity userEntity = user.get();
        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            throw new BadCredentialsException("Sai Tên Đăng Nhập Hoặc Mật Khẩu");
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(username, password, userEntity.getAuthorities());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return jwtTokenUtils.generateToken(userEntity);

    }

    @Override
    public Optional<UserEntity> findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public Optional<UserEntity> findById(Integer id) {
        return userRepository.findById(id);
    }


    @Override
    public UserResponse getUserDetail(String token) {
        Integer id = jwtTokenUtils.extractUserId(token);
        UserEntity userEntity = userRepository.findById(id).get();
        return userResponseConverter.toUserResponse(userEntity);
    }

    @Override
    public void changePassword(Integer id, ChangePasswordDTO changePasswordDTO) {
        UserEntity userEntity = userRepository.findById(id).get();
        if(passwordEncoder.matches(changePasswordDTO.getOldPassword(), userEntity.getPassword())) {
            if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword())) {
                throw new DataIntegrityViolationException("mật khẩu nhập lại không chính xác");
            }
            else {
                userEntity.setPassWord(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
                userRepository.save(userEntity);
            }
        } else {
            throw new DataIntegrityViolationException("Mật khẩu không chính xác");
        }
    }

    @Override
    public void editUserDetail(Integer id, UserDTO userDTO) {
        UserEntity userEntity = userRepository.findById(id).get();
        userEntity.setFullName(userDTO.getFullName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPhoneNumber(userDTO.getPhoneNumber());
        userRepository.save(userEntity);
    }
    @Override
    public void saveResetToken(String email, String resetToken) throws Exception {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if(!userEntityOptional.isPresent()){
            throw new Exception("Email không tồn tại");
        }
        else{
            UserEntity userEntity = userEntityOptional.get();
            userEntity.setResetToken(resetToken);
            userEntity.setTokenExpiryDate(LocalDateTime.now().plusMinutes(30));
            userRepository.save(userEntity);
        }

    }

    @Override
    public void resetPassword(String token, String newPassword) throws Exception {
        Optional<UserEntity> userEntityOptional = userRepository.findByResetToken(token);
        if(!userEntityOptional.isPresent()){
            throw new UsernameNotFoundException("Không tìm thấy tài khoản");
        }
        else{
            UserEntity userEntity = userEntityOptional.get();
            LocalDateTime now = LocalDateTime.now();
            if(now.isBefore(userEntity.getTokenExpiryDate())){
                userEntity.setPassWord(passwordEncoder.encode(newPassword));
                userEntity.setTokenExpiryDate(now);
                userRepository.save(userEntity);
            }
            else{
                throw new Exception("Token đã hết hạn. Vui lòng yêu cầu gửi lại email.");
            }

        }
    }
}
