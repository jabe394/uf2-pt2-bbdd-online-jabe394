package com.example.pt2_bbdd_online_bertomeu_jaume;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import java.util.List;
import java.util.Random;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class VehiclesAdapter extends RecyclerView.Adapter<VehiclesAdapter.ViewHolder> {
    private Context context;
    private DatabaseReference databaseReference;
    private List<Vehicle> vehicles;



    // Constructor
    public VehiclesAdapter(Context context, DatabaseReference databaseReference, List<Vehicle> vehicles) {
        this.context = context;
        this.databaseReference = databaseReference;
        this.vehicles  = vehicles;
    }

    // ViewHolder para los elementos individuales del RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvMatricula;

        public ViewHolder(View itemView) {
            super(itemView);
            tvMatricula = itemView.findViewById(R.id.tv_matricula); // Usa el id correspondiente

            itemView.setOnClickListener(this); // Establece el listener para el click
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mostraPopupMenu(v, position); // Muestra el popup menu al hacer click en un item
        }
    }

    // Inflar el layout para cada item
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vehicle, parent, false);
        return new ViewHolder(view);
    }

    // Enlazar los datos con los views del ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Vehicle item = vehicles.get(position);
        if (item == null) return;
        holder.tvMatricula.setText(item.getMatricula());
    }

    @Override
    public int getItemCount() {
        return vehicles.size();
    }

    // Método para mostrar el PopupMenu
    private void mostraPopupMenu(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.menu, popupMenu.getMenu()); // Usa el menú correspondiente
        popupMenu.setOnMenuItemClickListener(new PopupMenuClickListener(position)); // Cambio de nombre
        popupMenu.show();
    }

    // Clase interna para manejar los clicks en el menú
    private class PopupMenuClickListener implements PopupMenu.OnMenuItemClickListener {
        private int position;

        public PopupMenuClickListener(int position) {
            this.position = position;
        }

        public String generarTelefon() {
            Random random = new Random();

            // Prefixos comuns de números de telèfon mòbil a Espanya (6 o 7)
            int prefix = random.nextInt(2) + 6; // Genera 6 o 7

            // Genera els altres 8 dígits aleatòriament
            StringBuilder telefon = new StringBuilder();
            telefon.append(prefix); // Afegeix el prefix

            for (int i = 0; i < 8; i++) {
                int digit = random.nextInt(10); // Genera un dígit aleatori entre 0 i 9
                telefon.append(digit);
            }

            return telefon.toString();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Vehicle vehicle = vehicles.get(position);
            switch (item.getItemId()) {
                case R.id.menu_donar_alta:
                    Toast.makeText(context, "Vehicle amb matrícula: " + vehicle.getMatricula() +  " ha sigut donat d'alta", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.menu_donar_baixa:
                    vehicles.remove(vehicle);
                    databaseReference.child(vehicle.getMatricula()).removeValue();
                    Toast.makeText(context, "Vehicle eliminat", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    return true;
                case R.id.menu_modificar_telefon:
                    String nouTelefon = generarTelefon();
                    vehicle.setTelefon_titularVehicle(nouTelefon);

                    String missatgeTelefonModificat = "\nTelefon Modificat del vehicle: " + vehicle.getMatricula() + "  a Telefon: " + nouTelefon;

                    Toast.makeText(context, missatgeTelefonModificat,Toast.LENGTH_SHORT).show();

                    databaseReference.child(vehicle.getMatricula()).child(vehicle.getTelefon_titularVehicle()).setValue(nouTelefon);
                    return true;

                default:
                    return false;
            }
        }
    }
}
