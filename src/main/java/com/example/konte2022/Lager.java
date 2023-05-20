package com.example.konte2022;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Lager {
    @Id
    @GeneratedValue
    private Integer lid;
    private String navn;
    private String adresse;
    @OneToMany
    @JoinColumn(name="lid")
    private List<Pakke> pakkeList;

    public Lager(String navn, String adresse) {
        this.navn = navn;
        this.adresse = adresse;
    }
}
