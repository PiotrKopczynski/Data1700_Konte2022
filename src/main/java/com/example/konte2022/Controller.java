package com.example.konte2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@RestController
public class Controller {

    @Autowired
    PakkeRepository pakkeRepository;

    Logger logger = LoggerFactory.getLogger(Controller.class);

    @PostMapping("/lagrepakke")
    public String lagrepakke(@RequestBody Pakke pakke) {
        System.out.println(pakke);
        try{
            if(validerPakke(pakke)) {
                pakkeRepository.save(pakke);
                return "Pakken ble mottatt!";
            }
            return "Feil ved validering av eier!";
        }
        catch(Exception e){
            System.out.println(e);
            return "Programmet feilet under lagringen av pakken!.";
        }
    }

    private boolean validerPakke(Pakke pakke) {
        String regexEier = "[a-zæøåA-ZÆØÅ.\\-]{2,10}";
        boolean eierOK = pakke.getEier().matches(regexEier);
        if (eierOK) {return true;}
        logger.error("Valideringsfeil på eier!");
        return false;
    }
}
