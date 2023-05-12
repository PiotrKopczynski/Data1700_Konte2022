package com.example.konte2022;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    PakkeRepository pakkeRepository;

    @PostMapping("/lagrepakke")
    public String lagrepakke(Pakke pakke) {
        System.out.println(pakke);
        try{
            pakkeRepository.save(pakke);
            return "Pakken ble mottatt!";
        }
        catch(Exception e){
            System.out.println(e);
            return "Programmet feilet under lagringen av pakken!.";
        }
    }

}
