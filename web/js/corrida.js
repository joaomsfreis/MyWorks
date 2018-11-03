var listCompetitors = [];
var count = 0;

class Competitor{
    constructor(time, name, start){
        this.time = time;
        this.name = name;
        this.start = start;
    }

    setTime(time){
        this.time = time;
    }
    
    setName(name){
        this.name = name;
    }
}

function orderList(){
    listCompetitors.sort(function (c1, c2){
        if (c1.time > c2.time){
            return 1;
        }
        if (c1.time < c2.time){
            return -1;
        }
        return 0
    });
}

function clearTable(){
    var table = document.getElementById("tableResult");
        
    for(let i = table.rows.length-1; i>0; i--)
    {
        table.deleteRow(i);
    }
}

function validTimeField(field, alert, label) {


    var value = parseInt($(field).val());
  
    if ( isNaN(value) ) {
      $(alert).slideDown();
      $(field).addClass("is-invalid");
      $(label).addClass("text-danger");
      $(field).val("");
      $(field).focus();
      return false;
    }
  
    $(alert).hide();
    $(field).removeClass("is-invalid");
    $(label).removeClass("text-danger");
    $(field).addClass("is-valid");
    return true;  
}

function validNameField(field, alert, label) {

    var value = $(field).val();
  
    if ( value == "" ) {
      $(alert).slideDown();
      $(field).addClass("is-invalid");
      $(label).addClass("text-danger");
      $(field).val("");
      $(field).focus();
      return false;
    }
    $(alert).hide();
    $(field).removeClass("is-invalid");
    $(label).removeClass("text-danger");
    $(field).addClass("is-valid");
    return true;  
}


$(document).ready(function(){
    $("#btnAdd").click(function(){
        if(count < 6){
            if ( validNameField("input[name='nameCompetitor']", "#alert1", "#label1") &&
            validTimeField("input[name='time']", "#alert2", "#label2")) {
            
                var name = $("input[name='nameCompetitor']").val();
                var time = parseInt($("input[name='time']").val());
                count = count + 1;
                var competitor = new Competitor(time, name, count);
                listCompetitors.push(competitor);

                $("input[name='nameCompetitor']").val("");
                $("input[name='time']").val("");
        
                alert("Competidor "+name+" cadastrado com sucesso"
                +"\nCompetidores cadastrados: ("+count+"/6)");
            }
        }else alert("Número máximo de compedidores alcançado!\nPor favor, clique em ver resultado.");
    });

    $("input[name='nameCompetitor']").focusout(function(){
        validNameField("input[name='nameCompetitor']", "#alert1", "#label1");
    });

    $("input[name='time']").focusout(function(){
        validTimeField("input[name='time']", "#alert2", "#label2");
    });


    $("#btnResult").click(function(){
        clearTable();
        orderList();
        var pos = 1;
        previousTime = listCompetitors[0].time;

        for (let i = 0; i < listCompetitors.length; i++) {
            
            if(listCompetitors[i].time != previousTime){
                pos = i+1;
                previousTime = listCompetitors[i].time;
            }
            var newRow = $("<tr>");
            var cols="";

            cols += '<td>'+pos+'</td>';
            cols += '<td>'+listCompetitors[i].start+'</td>';
            cols += '<td>'+listCompetitors[i].name+'</td>';
            cols += '<td>'+listCompetitors[i].time+'</td>';
            
            if(pos == 1){
                cols += '<td>Vencedor(a)!</td>';
            }else cols += '<td>-</td>';
    
            newRow.append(cols);
    
            $("#tableResult").append(newRow);
        }
        return false;
    });

    $("#btnRestart").click(function () { 
        clearTable();
        count = 0;
        listCompetitors = [];
        
    });
});
