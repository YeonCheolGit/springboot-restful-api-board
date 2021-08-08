package com.example.springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "role")
@Getter @Setter
@RequiredArgsConstructor
@ToString
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleNo;

    @Column
    private String roleName;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
