package com.example.konte2022;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Pakke {
    @Id
    @GeneratedValue
    private Integer pid;

    private Integer lid;

    private String eier;

    private Float vekt;

    private Float volum;
}
