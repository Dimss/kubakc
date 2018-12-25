$( document ).ready(function() {

    setInterval(()=>{
      $.ajax({
            url: '/topic/state',
            processData : false,
        }).always(function(b64data){
            if (b64data.hasOwnProperty("status")){
                console.log("Bad response, showing previous image...")
            }else{
                $("#cm1").attr("src", "data:image/png;base64,"+b64data);
            }
        });
    },1000)

    $("#refresh-state").on('click', function(){
        $.ajax({
            url: '/topic/state',
            type: 'DELETE',
            success: function(result) {
                console.log("Refreshed")
            }
        });
    });

    $.ajax({
        url: '/metadata',
        type: 'get',
        success: function(result) {
            $("#consumer-name").text(result.data.hostname);
        }
    });

});