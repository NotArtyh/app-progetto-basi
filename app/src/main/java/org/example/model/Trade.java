package org.example.model;

import java.time.LocalDateTime;

public class Trade {
    private int Ricevente_inventory_id;
    private int Richiedente_inventory_id;
    private LocalDateTime DataScambio;
    private String Stato;

    public Trade() {
    }

    public Trade(int ricevente_inventory_id, int richiedente_inventory_id, LocalDateTime dataScambio, String stato) {
        Ricevente_inventory_id = ricevente_inventory_id;
        Richiedente_inventory_id = richiedente_inventory_id;
        DataScambio = dataScambio;
        Stato = stato;
    }

    public int getRicevente_inventory_id() {
        return Ricevente_inventory_id;
    }

    public void setRicevente_inventory_id(int ricevente_inventory_id) {
        Ricevente_inventory_id = ricevente_inventory_id;
    }

    public int getRichiedente_inventory_id() {
        return Richiedente_inventory_id;
    }

    public void setRichiedente_inventory_id(int richiedente_inventory_id) {
        Richiedente_inventory_id = richiedente_inventory_id;
    }

    public LocalDateTime getDataScambio() {
        return DataScambio;
    }

    public void setDataScambio(LocalDateTime dataScambio) {
        DataScambio = dataScambio;
    }

    public String getStato() {
        return Stato;
    }

    public void setStato(String stato) {
        Stato = stato;
    }
}
