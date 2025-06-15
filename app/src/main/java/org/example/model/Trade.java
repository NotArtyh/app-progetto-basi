package org.example.model;

import java.time.LocalDateTime;

public class Trade {
    private int ScambioId;
    private int RiceventeInventoryId;
    private int RichiedenteInventoryId;
    private LocalDateTime DataScambio;
    private String Stato;

    public Trade() {
    }

    public Trade(int RiceventeInventoryId, int RichiedenteInventoryId, LocalDateTime dataScambio, String stato) {
        this.RiceventeInventoryId = RiceventeInventoryId;
        this.RichiedenteInventoryId = RichiedenteInventoryId;
        this.DataScambio = dataScambio;
        this.Stato = stato;
    }

    public int getScambioId() {
        return ScambioId;
    }

    public void setScambioId(int scambioId) {
        ScambioId = scambioId;
    }

    public int getRiceventeInventoryId() {
        return RiceventeInventoryId;
    }

    public void setRiceventeInventoryId(int RiceventeInventoryId) {
        RiceventeInventoryId = RiceventeInventoryId;
    }

    public int getRichiedenteInventoryId() {
        return RichiedenteInventoryId;
    }

    public void setRichiedenteInventoryId(int RichiedenteInventoryId) {
        RichiedenteInventoryId = RichiedenteInventoryId;
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
