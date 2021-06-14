package com.example.springboot.DTO.user;

import com.example.springboot.entity.Role;
import com.example.springboot.entity.User;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Collections;

@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Data
public class KakaoUserRequestDTO {
    @NotEmpty
    @Email(message = "아이디는 비밀번호 형식으로 입력하셔야 합니다.")
    private String userId;

    @NotEmpty
    private String userName;

    @NotEmpty
    private String provider;

    public User toEntity(){
        Role role = new Role();
        role.setRoleNo(1);
        return User.builder()
                .userId(userId)
                .userName(userName)
                .provider(provider)
                .roles(Collections.singleton(role))
                .build();
    }
}
