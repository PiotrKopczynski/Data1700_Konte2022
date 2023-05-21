package com.example.konte2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LagerController {

    @Autowired
    LagerRepository lagerRepository;

    private final Logger logger = LoggerFactory.getLogger(LagerRepository.class);


    @GetMapping("/genererStatistikk")
    public StringBuilder genererStatistikk() {
        //Finds all lagere
        List<Lager> lagere = lagerRepository.findAll();
        //Using StringBuilder instead String, beacuse of the complexity and length of the messege
        StringBuilder resultat = new StringBuilder("");
        //Goes through all lagere
        for(Lager lager : lagere) {
            //Finds all pakkeList connected to each lager
            List<Pakke> pakker = lager.getPakkeList();
            int antall = 0;
            int totalvolum = 0;
            int totalvekt = 0;
            //Using av for-løkke to add antall, totalvolum and totalvekt for all packages using each lager
            for(Pakke pakke : pakker) {
                antall++;
                totalvolum += pakke.getVolum();
                totalvekt += pakke.getVekt();
            }
            //Generates a message about lager statisitc
            resultat.append(lager.getNavn()).append(" innholder ").append(antall)
                    .append(" pakker med et totalvolum på ").append(totalvolum).append(" kubikkmeter og en totalvekt på ")
                    .append(totalvekt).append(" kg. <br>");

        }
        return resultat;
    }

    @GetMapping("/genererLagere")
    //Generates lagere when button is clicked, so a package can be registered and added to it
    //Has a @OneToMany relationship with pakke
    public void genererLagere() {
        lagerRepository.save(new Lager("Langkaia", "nissegata"));
        lagerRepository.save(new Lager("Moss kai", "hundremeterskogen"));
        lagerRepository.save(new Lager("Det er", "det as"));
    }
}
