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
        let pakke = {
            lid:lid,
            eier:eier,
            vekt:vekt,
            volum:volum
        };
        console.log(pakke)
        $.ajax({
            type:'POST',
            url:'/lagrepakke',
            data: JSON.stringify(pakke),
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

$(function() {
    $("#registrer").click(function() {
        html = "<table><tr></tr><th>PID</th><th>LID</th><th>Eier</th><th>Vekt</th><th>Volum</th></tr>"
        $.get("/hentallepakker", function (pakker) {
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
        });
    });
});

function loggUt() {
    $.get("/loggUt", function() {
        window.location.href = "index.html";
    });
}