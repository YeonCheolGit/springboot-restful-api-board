package com.example.springboot.DTO.user;

import com.example.springboot.entity.Role;
import com.example.springboot.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Getter
@NoArgsConstructor @AllArgsConstructor
@Data
public class UserResponseDTO {

    private long userNo;

    @Email
    @NotEmpty
    private String userId;

    @NotEmpty
    private String userName;

    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
            message = "최소 1개 이상의 소문자, 대문자, 숫자, 특수기호가 포함되어야 합니다")
    private String userPwd;

    Set<Role> role;

    public UserResponseDTO(User user) {
        userNo = user.getUserNo();
        userId = user.getUserId();
        userName = user.getUsername();
        userPwd = user.getUserPwd();
        role = user.getRoles();
    }
}
