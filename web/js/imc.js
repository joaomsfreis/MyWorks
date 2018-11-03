function validField(field, alert, label) {

    var valueString = $(field).val();
    var value = parseFloat(valueString.replace(",","."));
  
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



function getResult(imc){
    if (imc<18.5) {
        return "Subnutrido";
    } else if (imc>=18.5 && imc<25.0) {
        return "Peso saudável";
    }else if (imc>=25.0 && imc<30.0) {
        return "Sobre peso";
    }else if (imc>=30.0 && imc<35.0) {
        return "Obesidade grau 1";
    }else if (imc>=35.0 && imc<40.0) {
        return "Obesidade grau 2";
    }else if (imc>=40.0) {
        return "Obesidade grau 3";
    }

        
}

$(document).ready(function () {
    $("#btnCalc").click(function () { 
        if ( validField("input[name='height']", "#alert1", "#label1") &&
        validField("input[name='weight']", "#alert2", "#label2")) {
            
            var input1 = $("input[name='height']").val();
            var input2 = $("input[name='weight']").val();
    
            var hgt = parseFloat(input1.replace(",","."));
            var wgt = parseFloat(input2.replace(",","."));
    
            var imc = wgt/(hgt*hgt);
    
            $("input[name='imc']").val(imc);
            $("input[name='diagnostic']").val(getResult(imc));
        }
    });

    
    $("input[name='height']").focusout(function(){
        validField("input[name='height']", "#alert1", "#label1");
    });

    $("input[name='weight']").focusout(function(){
        validField("input[name='weight']", "#alert2", "#label2");
    });
});