package com.example.konte2022;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LagerRepository extends JpaRepository<Lager,Integer> {
    public Lager findByLidLike(Integer lid);
}
