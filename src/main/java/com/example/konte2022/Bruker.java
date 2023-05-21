package com.example.konte2022;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //instead of @Getter and @Setter (we don't need to write the code for those below)
@NoArgsConstructor
@AllArgsConstructor // we use those adnotations for eliminating boilerplate code such as getters, setters, constructors
@Entity // this adnotation will let Hibernate know that we want a SQL table that contains the exact attributes we defined below
@Table //Creates table, can also give a different name to the table with (name = "table_name")
public class Bruker {
    @Id// annotation that defines the PK for the SQL table
    @GeneratedValue //PK will get an AUTO_INCREMENT attribute
    private int bid;
    private String brukernavn;
    private String passord;
}
