package co.com.mypet.mypet.core;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import co.com.mypet.mypet.modelos.Adopcion;
import co.com.mypet.mypet.modelos.Mascota;
import co.com.mypet.mypet.modelos.Usuario;

public class Datos {
    private static String db = "Mascotas";
    private static String dbUser = "Usuarios";
    private static String dbAdopciones = "Adopciones";
    private static String dbAyudas = "Ayudas";
    private static FirebaseAuth auth = FirebaseAuth.getInstance();
    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        public static ArrayList<Mascota> mascotas = new ArrayList<>();

    public static void agregar(Mascota p){
       databaseReference.child(db).child(p.getId()).setValue(p);
    }

    public static String getId(){
        return databaseReference.push().getKey();
    }

    public static ArrayList<Mascota> obtener(){
      return mascotas;
    }

    public static void setMascotas(ArrayList<Mascota> mascotas) {
        Datos.mascotas = mascotas;
    }

    public static void agregarUsuario(Usuario u){
        databaseReference.child(dbUser).child(u.getId()).setValue(u);
    }

    public static String getIdUser(){
       return auth.getUid();
    }

    public static void editarUsuario(Usuario u){
        databaseReference.child(dbUser).child(u.getId()).setValue(u);
    }

    public static void agregarAdopcion(Adopcion a){
        databaseReference.child(dbAdopciones).child(a.getId()).setValue(a);
    }
}
