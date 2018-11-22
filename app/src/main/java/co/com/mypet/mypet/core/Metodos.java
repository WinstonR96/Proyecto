package co.com.mypet.mypet.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Metodos {

    public static boolean validarEmail(String email){
        Boolean valido;
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(email);

        if (mather.find() == true) {
            valido = true;
        } else {
            valido = false;
        }
        return valido;
    }

    public static boolean ValidarPasswordIguales(String pass, String repass){
        Boolean valido;
        if(pass.equals(repass)){
            valido = true;
        }else{
            valido = false;
        }
        return valido;
    }

    public static boolean ValidarLongitudPassword(String pass){
        Boolean valido;
        if(pass.length()>= 6){
            valido = true;
        }else{
            valido = false;
        }
        return valido;
    }
}
