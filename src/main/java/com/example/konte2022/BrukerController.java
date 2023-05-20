package com.example.konte2022;


import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BrukerController {

    @Autowired
    private HttpSession session;

    private final Logger logger = LoggerFactory.getLogger(BrukerController.class);

    @Autowired
    BrukerRepository brukerRepository;

    @Autowired
    PakkeRepository pakkeRepository;

    @Autowired
    LagerRepository lagerRepository;

    @GetMapping("/loggInn")
    public boolean loggInn(String brukernavn, String passord) {
        List<Bruker> brukere = brukerRepository.findBrukerByBrukernavnLike(brukernavn);
        if (!brukere.isEmpty()) {
            if(sjekkPassord(passord,brukere.get(0).getPassord())) {
                session.setAttribute("loggetInn", brukere.get(0).getBrukernavn());
                logger.info("Bruker " + brukere.get(0).getBrukernavn() + " logged in!");
                return true;
            }
        }
        return false;
    }

    @GetMapping("/loggUt")
    public void loggUt() {
        session.invalidate();
    }


    @PostMapping("/registrer")
    public String registrer(@RequestBody Bruker bruker) {
        List<Bruker> brukere = brukerRepository.findBrukerByBrukernavnLike(bruker.getBrukernavn());
        try {
            if (brukere.isEmpty()) {
                String hashPassord = krypterPassord(bruker.getPassord());
                bruker.setPassord(hashPassord);
                brukerRepository.save(bruker);
                return "Bruker ble registrert!";
            } else {
                return "Brukeren finnes fra før!";
            }
        }
        catch(Exception e){
            String errorMessage = "Feil ved registrering av bruker ";
            logger.error(errorMessage + e);
            return errorMessage;
        }
    }

    @PostMapping("/lagrepakke")
    public String lagrepakke(@RequestBody Pakke pakke) {
        System.out.println(pakke);
        try{
            if(sjekkInnlogging()) {
                if(validerPakke(pakke)) {
                    Lager lager = lagerRepository.findByLidLike(pakke.getLid());
                    if(lager != null) {
                        lager.getPakkeList().add(pakke);
                    }
                    pakkeRepository.save(pakke);
                    return "Pakken ble mottatt!";
                }
                return "Feil ved validering av eier!";
            }
            return "Må være innlogget for å registrere pakker!";
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

    @GetMapping("/hentallepakker")
    public List<Pakke> hentallepakker() {
        if(sjekkInnlogging()) {
            List<Pakke> pakkeList = pakkeRepository.findAll();
            System.out.println(pakkeList);
            return pakkeList;
        }
        return null;
    }

    private String krypterPassord(String passord) {
        return BCrypt.hashpw(passord, BCrypt.gensalt(10));
    }

    private boolean sjekkPassord(String passord, String hashPassord) {
        return BCrypt.checkpw(passord, hashPassord);
    }

    public boolean sjekkInnlogging() {
        return session.getAttribute("loggetInn") != null;
    }
}
