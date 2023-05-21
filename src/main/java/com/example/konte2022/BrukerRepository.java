package com.example.konte2022;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrukerRepository extends JpaRepository<Bruker,Long> {
    //Can create custom ''directions'' that you can use to get info out of the DB
    public List<Bruker> findBrukerByBrukernavnLike(String brukernavn);
}
