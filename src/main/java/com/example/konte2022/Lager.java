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
    @OneToMany //Relation between lager and pakke
    @JoinColumn(name="lid") //Gets rid of the "joined" table that gets generated when using relations. LID is a FK
    //Needs to put a pakkelist in Lager class, because of relations
    private List<Pakke> pakkeList;

    public Lager(String navn, String adresse) {
        this.navn = navn;
        this.adresse = adresse;
    }
}
