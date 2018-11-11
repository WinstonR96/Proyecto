package co.com.mypet.mypet;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Datos {
    private static String db = "Mascotas";
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
}
