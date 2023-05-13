package com.example.konte2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@RestController
public class PakkeController {

    @Autowired
    PakkeRepository pakkeRepository;

    private Logger logger = LoggerFactory.getLogger(PakkeController.class);

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

    @GetMapping("/hentallepakker")
    public List<Pakke> hentallepakker() {
        List<Pakke> pakkeList = pakkeRepository.findAll();
        System.out.println(pakkeList);
        return pakkeList;
    }

    private boolean validerPakke(Pakke pakke) {
        String regexEier = "[a-zæøåA-ZÆØÅ.\\-]{2,10}";
        boolean eierOK = pakke.getEier().matches(regexEier);
        if (eierOK) {return true;}
        logger.error("Valideringsfeil på eier!");
        return false;
    }
}
