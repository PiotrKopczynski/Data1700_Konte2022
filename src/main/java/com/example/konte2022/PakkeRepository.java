package com.example.konte2022;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PakkeRepository extends JpaRepository<Pakke,Long>{
}
