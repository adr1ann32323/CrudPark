package com.CrudPark.app.domain;

import java.sql.Timestamp;

public class VehicleRegister {
    private int id;
    private String plate;
    private int operatorId;
    private Timestamp entryDate;
    private Timestamp outDate;
    private boolean membresy;
    private String ticket;
    private String documentUser;
    private int subtotal;
    private int total;

    // Constructor vacío
    public VehicleRegister() {}

    // Constructor para crear un nuevo registro de entrada
    public VehicleRegister(String plate, int operatorId, boolean membresy, String documentUser) {
        this.plate = plate;
        this.operatorId = operatorId;
        this.membresy = membresy;
        this.documentUser = documentUser;
        this.entryDate = new Timestamp(System.currentTimeMillis());
    }

    // ===== Getters y Setters =====
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPlate() { return plate; }
    public void setPlate(String plate) { this.plate = plate; }

    public int getOperatorId() { return operatorId; }
    public void setOperatorId(int operatorId) { this.operatorId = operatorId; }

    public Timestamp getEntryDate() { return entryDate; }
    public void setEntryDate(Timestamp entryDate) { this.entryDate = entryDate; }

    public Timestamp getOutDate() { return outDate; }
    public void setOutDate(Timestamp outDate) { this.outDate = outDate; }

    public boolean isMembresy() { return membresy; }
    public void setMembresy(boolean membresy) { this.membresy = membresy; }

    public String getTicket() { return ticket; }
    public void setTicket(String ticket) { this.ticket = ticket; }

    public String getDocumentUser() { return documentUser; }
    public void setDocumentUser(String documentUser) { this.documentUser = documentUser; }

    public int getSubtotal() { return subtotal; }
    public void setSubtotal(int subtotal) { this.subtotal = subtotal; }

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }

    @Override
    public String toString() {
        return "Registro #" + id +
                " | Placa: " + plate +
                " | Operador ID: " + operatorId +
                " | Membresía: " + (membresy ? "Sí" : "No") +
                " | Fecha entrada: " + entryDate +
                (outDate != null ? " | Fecha salida: " + outDate : "") +
                " | Documento usuario: " + documentUser +
                " | Subtotal: " + subtotal +
                " | Total: " + total;
    }
}
