package com.example.pt2_bbdd_online_bertomeu_jaume;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ArrayList<Vehicle> vehicles;

    public boolean vehicleExisteix(ArrayList<Vehicle> vehicles, Vehicle vehicleT){
        for(Vehicle vehicle : vehicles){
            if (vehicle.getMatricula().equals(vehicleT.getMatricula())){
                return true;
            }
        }
        return false;
    }

    public void afegirVehicleBaseDades(DatabaseReference vehiclesRef, Vehicle vehicle){
        if (!vehicleExisteix(vehicles, vehicle)){
            Toast.makeText(this, "Vehicle amb matrícula:" + vehicle.getMatricula() + " afegit correctament!", Toast.LENGTH_SHORT).show();
            vehiclesRef.child(vehicle.getMatricula()).setValue(vehicle);
        }
        else{
            Toast.makeText(this, "Vehicle amb matrícula:" + vehicle.getMatricula() + " ja existeix!", Toast.LENGTH_SHORT).show();
        }
    }

    public void afegirVehicles(DatabaseReference vehiclesRef){
        Vehicle vehicle1 = new Vehicle("Jaume", "Bertomeu", "234243434", "VolkWaguen", "Toyota", "12094871J");
        Vehicle vehicle2 = new Vehicle("Jordi", "Bertomeu", "234243434", "VolkWaguen", "Toyota", "12093470G");

        afegirVehicleBaseDades(vehiclesRef, vehicle1);
        afegirVehicleBaseDades(vehiclesRef, vehicle2);



    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rv_vehicles), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        vehicles = new ArrayList<Vehicle>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference vehiclesRef = database.getReference("vehicles");

        RecyclerView rv_vehicles = findViewById(R.id.rv_vehicles);

        rv_vehicles.setLayoutManager(new LinearLayoutManager(this));

        VehiclesAdapter vehiclesAdapter = new VehiclesAdapter(this, vehiclesRef, vehicles);

        rv_vehicles.setAdapter(vehiclesAdapter);


        vehiclesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot datasnapshot : snapshot.getChildren()) {
                    Vehicle vehicle = datasnapshot.getValue(Vehicle.class);
                    if (vehicle != null) {
                        System.out.print("\nMatricula Vehicle Obtingut: " + vehicle.getMatricula());
                        vehicles.add(vehicle);
                    }
                }
                vehiclesAdapter.notifyDataSetChanged();
                rv_vehicles.scrollToPosition(vehicles.size() - 1);
                afegirVehicles(vehiclesRef);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Maneja cualquier error aquí
                System.out.println("Error al leer los datos: " + error.getMessage());
            }
        });

    }
}