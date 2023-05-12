function fetchInput() {
    let validation = true;

    let lid = parseInt($("#LID").val());
    if(isNaN(lid) || lid < 0) {
        validation = false;
        $("#LID_Error").html("Feil i LID feltet! Skriv inn et positivt tall!");
    }
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

    if (validation) {
        pakke = {
            lid,
            eier,
            vekt,
            volum
        };
        console.log(pakke)
        /*$.ajax({
            type:'post',
            url:'/lagrepakke',
            async:false,
            data:JSON.stringify(pakke),
            contentType:"application/json",
            dataType:"json",
            success: function(result,status,xhr){
                console.log(result);
            }
        });*/
        $.post("/lagrepakke", pakke, function(){console.log("added")});
        $("#informasjon").html("Registreringen var vellykket!")
    }
    else{$("#informasjon").html("Noe gikk galt under valideringen av pakken.")}
}