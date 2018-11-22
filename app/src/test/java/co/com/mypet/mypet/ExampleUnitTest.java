package co.com.mypet.mypet;

import org.junit.Test;

import co.com.mypet.mypet.core.Metodos;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void email_valido(){
        String email = "wijurost@gmail.com";
        assertTrue(Metodos.validarEmail(email));
    }

    @Test
    public void validar_password_iguales(){
        String pass = "123456";
        String repass = "123456";
        assertTrue(Metodos.ValidarPasswordIguales(pass,repass));
    }

    @Test
    public void validar_longitud_password(){
        String pass = "123456";
        assertTrue(Metodos.ValidarLongitudPassword(pass));
    }
}