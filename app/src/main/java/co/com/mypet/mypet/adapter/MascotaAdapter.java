package co.com.mypet.mypet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;
import co.com.mypet.mypet.modelos.Adopcion;
import co.com.mypet.mypet.core.Datos;
import co.com.mypet.mypet.modelos.Mascota;
import co.com.mypet.mypet.R;

public class MascotaAdapter extends RecyclerView.Adapter<MascotaAdapter.ViewHolder> {
    private ArrayList<Mascota> mascotas;
    private Context context;
    FirebaseAuth auth = FirebaseAuth.getInstance();
   

    public MascotaAdapter(ArrayList<Mascota> mascotas, Context ctx) {
        this.mascotas = mascotas;
        this.context = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_adopcion,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Mascota m = mascotas.get(position);
        holder.textViewNombre.setText(m.getNombre());
        holder.textViewDescripcion.setText(m.getDescripcion());
        holder.textViewRaza.setText(m.getRaza());
        holder.textViewCiudad.setText(m.getCiudad());
        holder.textViewEdad.setText(m.getEdad());
        Glide.with(context).load(m.getFoto()).into(holder.imageViewFoto);
        holder.buttonAdoptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = Datos.getId();
                String mascota = m.getId();
                String usuario = auth.getUid();
                Adopcion a = new Adopcion(id,mascota,usuario);
                a.adoptar();
                Toast.makeText(context,"Adoptaras a "+m.getNombre(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mascotas.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNombre;
        private TextView textViewRaza;
        private TextView textViewCiudad;
        private TextView textViewEdad;
        private ImageView imageViewFoto;
        private TextView textViewDescripcion;
        private Button buttonAdoptar;
        public ViewHolder(View itemView) {
            super(itemView);
            textViewCiudad = itemView.findViewById(R.id.ubicacionMascota);
            textViewDescripcion = itemView.findViewById(R.id.descripcionMascota);
            textViewEdad = itemView.findViewById(R.id.edadMascota);
            textViewRaza = itemView.findViewById(R.id.razaMascota);
            textViewNombre = itemView.findViewById(R.id.nombreMascota);
            imageViewFoto = itemView.findViewById(R.id.fotoMascota);
            buttonAdoptar = itemView.findViewById(R.id.btnAdoptarMascota);
        }
    }
}
