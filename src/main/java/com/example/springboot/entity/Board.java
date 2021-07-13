package com.example.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Table(name = "board")
public class Board implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boardNo;

    @Column(nullable = false, length = 100)
    private String name;
}
