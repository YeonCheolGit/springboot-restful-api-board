package com.example.springboot.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "board")
@Getter
@NoArgsConstructor @AllArgsConstructor
@Builder
@ToString
public class Board implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boardNo;

    @Column(nullable = false, length = 100)
    private String name;
}
