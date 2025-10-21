package com.CrudPark.app.domain;

public class Operator {
    private int id;
    private String name;
    private String document;
    private String password;
    private String email;
    private boolean isActive;

    public Operator() {}

    public Operator(int id, String name, String document, String password, String email, boolean isActive) {
        this.id = id;
        this.name = name;
        this.document = document;
        this.password = password;
        this.email = email;
        this.isActive = isActive;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDocument() { return document; }
    public void setDocument(String document) { this.document = document; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    @Override
    public String toString() {
        return id + " Documento: " + document + ", Nombre: " + name + ", Email: " + email + ", Estado: " + (isActive ? "Activo" : "Inactivo");
    }
}
