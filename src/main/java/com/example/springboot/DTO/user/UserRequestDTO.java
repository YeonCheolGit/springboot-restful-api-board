package com.example.springboot.DTO.user;

import com.example.springboot.entity.Role;
import com.example.springboot.entity.User;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Collections;

@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Data
public class UserRequestDTO implements Serializable {
    @NotEmpty
    @Email(message = "아이디는 비밀번호 형식으로 입력하셔야 합니다.")
    @Length(min = 1, max = 50)
    private String userId;

    @NotEmpty
    @Length(min = 1, max = 50)
    private String userName;

    @NotEmpty
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
            message = "최소 1개 이상의 소문자, 대문자, 숫자, 특수기호가 포함되어야 합니다.")
    @Length(max = 100)
    private String userPwd;

    public User toEntity(){
        Role role = new Role();
        role.setRoleNo(1);
        return User.builder()
                .userId(this.userId)
                .userName(this.userName)
                .userPwd(this.userPwd)
                .roles(Collections.singleton(role))
                .build();
    }
}
