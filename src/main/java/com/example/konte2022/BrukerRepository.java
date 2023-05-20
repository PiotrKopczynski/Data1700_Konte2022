package com.example.konte2022;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrukerRepository extends JpaRepository<Bruker,Long> {
    public List<Bruker> findBrukerByBrukernavnLike(String brukernavn);
}
