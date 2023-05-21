function loggInn() {
    //Gets user details, so they can be sent to the server
    //Here we want to post and get details at the same time
    const url = "/loggInn?brukernavn="+$("#brukernavn").val()+"&passord="+$("#passord").val();
    $.get(url, function(OK) {
        if(OK) {
            //Changes window to a different html file
            window.location.href = "registrering.html";
        }
        else{
            //Error messege pops up under div created in our html file for error/info messages
            $("#info").html("Feil brukernavn eller passord!");
        }
    })
        //If registrations fails, a messege will be generated and showed to client
        .fail(function(jqXHR) {
            const json = $.parseJSON(jqXHR.responseText);
            $("#info").html(json.message);
    });
}

//Takes username and user password details out of inputs and sends them to the server
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