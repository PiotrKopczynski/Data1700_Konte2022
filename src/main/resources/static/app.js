//Function that get info out of inputs
function fetchInput() {
    let validation = true;

    let lid = parseInt($("#LID").val());
    //Validation of inputs
    if(isNaN(lid) || lid < 0) {
        validation = false;
        //Error messege is showed next to inputs
        $("#LID_Error").html("Feil i LID feltet! Skriv inn et positivt tall!");
    }
    //Error messege is wiped out
    else {$("#LID_Error").html("")};

    let eier = $("#Eier").val();
    if(eier === "") {
        validation = false;
        $("#Eier_Error").html("Feil i Eier feletet! Feltet kan ikke være tomt!");
    }
    else {$("#Eier_Error").html("")}

    let vekt = parseFloat($("#Vekt").val());
    if(isNaN(vekt) || vekt <= 0) {
        validation = false;
        $("#Vekt_Error").html("Feil i Vekt feltet! Skriv inn et positivt tall!");
    }
    else {$("#Vekt_Error").html("")}

    let volum = parseFloat($("#Volum").val());
    if(isNaN(volum) || volum <= 0) {
        validation = false;
        $("#Volum_Error").html("Feil i Volum feltet! Skriv inn et positivt tall!");
    }
    else {$("#Volum_Error").html("")}

    //If validation is completed, an object is created
    if (validation) {
        let pakke = {
            lid:lid,
            eier:eier,
            vekt:vekt,
            volum:volum
        };
        console.log(pakke)
        $.ajax({
            type:'POST', ///this is where you define the HTTP request you will use
            url:'/lagrepakke',
            data: JSON.stringify(pakke), //we package the information as a JSON in order to send it to the Spring server
            contentType:"application/json",
            traditional: true,
            success: function(result,status,xhr){
                console.log(result);
                $("#informasjon").html(result)
            }
        });
        //$.post("/lagrepakke", pakke, function(){console.log()});
    }
    else{$("#informasjon").html("Noe gikk galt under valideringen av pakken.")}
}

//Function to see all packages
$(function() {
    $("#visPakker").click(function() {
        $.get("/hentallepakker", function (pakker) {
            //If it doesn't receive any packages it will tell us that we need to be logged in to see them
            if(!pakker) {
                $("#pakkeliste").html("Du må være innlogget for å se pakkeliste!")
            }
            else {
                //Shows all packages and info about them in a html style table
                html = "<table><tr></tr><th>PID</th><th>LID</th><th>Eier</th><th>Vekt</th><th>Volum</th></tr>"
                for (let pakke of pakker) {
                    html += "<tr>" +
                        "<th>" + pakke.pid + "</th>" +
                        "<th>" + pakke.lid + "</th>" +
                        "<th>" + pakke.eier + "</th>" +
                        "<th>" + pakke.vekt + "</th>" +
                        "<th>" + pakke.volum + "</th>" +
                        "</tr>";
                }
                html += "<table>"
                $("#pakkeliste").html(html)
            }
        });
    });
});

//Logout function
function loggUt() {
    console.log("Logg ut funker.")
    //sends you to the ''beginning'' - index.html when clicked
    $.get("/loggUt", function() {window.location.href = "index.html";});
}

//Function to get lager statistics
function statistikk() {
    $.get("/genererStatistikk", function (data) {
        $("#statistikk").html(data);
    });
}

//Generate lager function
$(function() {
    //.click(function() - same functionality as onclick() in a html button
    $("#lager").click(function() {
        $.get("/genererLagere", function () {
        });
    });
});