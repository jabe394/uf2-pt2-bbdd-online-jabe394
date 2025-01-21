package com.example.pt2_bbdd_online_bertomeu_jaume;

public class Vehicle {
    String nom_titularVehicle;
    String cognoms_titularVehicle;
    String telefon_titularVehicle;
    String marca_vehicle;
    String model_vehicle;
    String matricula;

    public Vehicle(){}

    public Vehicle(String nom_titularVehicle, String cognoms_titularVehicle, String telefon_titularVehicle, String marca_vehicle, String model_vehicle, String matricula) {
        this.nom_titularVehicle = nom_titularVehicle;
        this.cognoms_titularVehicle = cognoms_titularVehicle;
        this.telefon_titularVehicle = telefon_titularVehicle;
        this.marca_vehicle = marca_vehicle;
        this.model_vehicle = model_vehicle;
        this.matricula = matricula;
    }

    //Getters
    public String getNom_titularVehicle() {
        return nom_titularVehicle;
    }

    public String getCognoms_titularVehicle() {
        return cognoms_titularVehicle;
    }

    public String getTelefon_titularVehicle() {
        return telefon_titularVehicle;
    }

    public String getMarca_vehicle() {
        return marca_vehicle;
    }

    public String getModel_vehicle() {
        return model_vehicle;
    }

    public String getMatricula() {
        return matricula;
    }

    //Setters


    public void setNom_titularVehicle(String nom_titularVehicle) {
        this.nom_titularVehicle = nom_titularVehicle;
    }

    public void setCognoms_titularVehicle(String cognoms_titularVehicle) {
        this.cognoms_titularVehicle = cognoms_titularVehicle;
    }

    public void setTelefon_titularVehicle(String telefon_titularVehicle) {
        this.telefon_titularVehicle = telefon_titularVehicle;
    }

    public void setMarca_vehicle(String marca_vehicle) {
        this.marca_vehicle = marca_vehicle;
    }

    public void setModel_vehicle(String model_vehicle) {
        this.model_vehicle = model_vehicle;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
