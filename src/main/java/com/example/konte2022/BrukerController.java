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


@RestController // - we expose the server's endpoints to whoever wants
public class BrukerController {

    //Enables sessions
    @Autowired
    private HttpSession session;

    //Creates logger, so we can use it to logg errors or info in the console
    private final Logger logger = LoggerFactory.getLogger(BrukerController.class);

    //Creates repositories for bruker, pakke and lager
    @Autowired //We autowire the repository interface to do all the DML operations on the database
    BrukerRepository brukerRepository;

    @Autowired
    PakkeRepository pakkeRepository;

    @Autowired
    LagerRepository lagerRepository;

    @GetMapping("/loggInn") //Login, takes inn brukernavn and passord
    public boolean loggInn(String brukernavn, String passord) {
        //Finds the user in DB
        List<Bruker> brukere = brukerRepository.findBrukerByBrukernavnLike(brukernavn);
        //If it finds the user
        if (!brukere.isEmpty()) {
            //It checks their password
            if(sjekkPassord(passord,brukere.get(0).getPassord())) {
                //And it starts a session
                session.setAttribute("loggetInn", brukere.get(0).getBrukernavn());
                //Logger info about who is logged in
                logger.info("Bruker " + brukere.get(0).getBrukernavn() + " logged in!");
                return true;
            }
        }
        return false;
    }

    //LogOut, ends session when logut button is clicked
    @GetMapping("/loggUt")
    public void loggUt() {
        session.invalidate();
    }


    @PostMapping("/registrer") //Register a new user
    public String registrer(@RequestBody Bruker bruker) {
        //Goes trhough a list to check if a user exist from before
        List<Bruker> brukere = brukerRepository.findBrukerByBrukernavnLike(bruker.getBrukernavn());
        try {
            //If user doesn't exist from before, the password that they write in will be encrypted
            //And the user will be saved
            if (brukere.isEmpty()) {
                String hashPassord = krypterPassord(bruker.getPassord());
                bruker.setPassord(hashPassord);
                brukerRepository.save(bruker);
                return "Bruker ble registrert!";
            } else {
                return "Brukeren finnes fra før!";
            }
        }
        //We will get a error message if it fails, and the error wil also be logged with logger.error
        catch(Exception e){
            String errorMessage = "Feil ved registrering av bruker ";
            logger.error(errorMessage + e);
            return errorMessage;
        }
    }

    @PostMapping("/lagrepakke") //POST here, because we are sending something to the server
    //Remember @RequestBody when doing an ajax call
    public String lagrepakke(@RequestBody Pakke pakke) {
        System.out.println(pakke);
        //Try/catch to catch if something goes wrong.
        try{
            //You can only add a package if you are logged in
            if(sjekkInnlogging()) {
                //Validation av eier input has to go through before a package can be added
                if(validerPakke(pakke)) {
                    //Finds lager through LID
                    Lager lager = lagerRepository.findByLidLike(pakke.getLid());
                    //and adds a package if lager exsist
                    if(lager != null) {
                        lager.getPakkeList().add(pakke);
                    }
                    //Saves package
                    pakkeRepository.save(pakke);
                    return "Pakken ble mottatt!";
                }
                return "Feil ved validering av eier!";
            }
            return "Må være innlogget for å registrere pakker!";
        }
        //We get a messege if something goes wrong
        catch(Exception e){
            System.out.println(e);
            return "Programmet feilet under lagringen av pakken!.";
        }
    }

    //Validation of eier attribute using regex, it supposed to follow a set of rules to get trough
    private boolean validerPakke(Pakke pakke) {
        String regexEier = "[a-zæøåA-ZÆØÅ.\\-]{2,10}";
        boolean eierOK = pakke.getEier().matches(regexEier);
        if (eierOK) {return true;}
        logger.error("Valideringsfeil på eier!");
        return false;
    }

    @GetMapping("/hentallepakker") //GET here, because we are getting something from the server/DB
    //Function hentAllePakker, only works if you are logged in
    public List<Pakke> hentallepakker() {
        if(sjekkInnlogging()) {
            //Finds all the packages
            List<Pakke> pakkeList = pakkeRepository.findAll();
            System.out.println(pakkeList);
            return pakkeList;
        }
        return null;
    }

    //Takes in password and encrypts it (need to import a library to use it)
    private String krypterPassord(String passord) {
        return BCrypt.hashpw(passord, BCrypt.gensalt(10));
    }

    //Password that was written in input get checked against the hashed password
    private boolean sjekkPassord(String passord, String hashPassord) {
        return BCrypt.checkpw(passord, hashPassord);
    }

    //Checks if a user is logged in
    public boolean sjekkInnlogging() {
        return session.getAttribute("loggetInn") != null;
    }
}
