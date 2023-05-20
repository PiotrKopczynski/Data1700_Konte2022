function loggInn() {
    const url = "/loggInn?brukernavn="+$("#brukernavn").val()+"&passord="+$("#passord").val();
    $.get(url, function(OK) {
        if(OK) {
            window.location.href = "registrering.html";
        }
        else{
            $("#info").html("Feil brukernavn eller passord!");
        }
    })
        .fail(function(jqXHR) {
            const json = $.parseJSON(jqXHR.responseText);
            $("#info").html(json.message);
    });
}

function registrer() {
 let bruker = {
     brukernavn: $("#brukernavn").val(),
     passord: $("#passord").val()
    }
    $.ajax({
        type:'POST',
        url:'/registrer',
        data: JSON.stringify(bruker),
        contentType:"application/json",
        traditional: true,
        success: function(result,status,xhr){
            console.log(result);
            $("#info").html(result)
        }
    });
}