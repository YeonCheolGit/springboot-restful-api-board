package com.example.springboot.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleNo;

    @Column
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
