package org.example.model;

import java.time.LocalDateTime;

public class ItemInTrade {
    private int ScambioId;
    private int RiceventeInventoryId;
    private int RichiedenteInventoryId;
    private int itemId;
    private int mediaId;
    private LocalDateTime DataScambio;

    public ItemInTrade() {
    }

    public ItemInTrade(int scambioId, int riceventeInventoryId, int richiedenteInventoryId, int itemId, int mediaId,
            LocalDateTime dataScambio) {
        ScambioId = scambioId;
        RiceventeInventoryId = riceventeInventoryId;
        RichiedenteInventoryId = richiedenteInventoryId;
        this.itemId = itemId;
        this.mediaId = mediaId;
        DataScambio = dataScambio;
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

    public void setRiceventeInventoryId(int riceventeInventoryId) {
        RiceventeInventoryId = riceventeInventoryId;
    }

    public int getRichiedenteInventoryId() {
        return RichiedenteInventoryId;
    }

    public void setRichiedenteInventoryId(int richiedenteInventoryId) {
        RichiedenteInventoryId = richiedenteInventoryId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public LocalDateTime getDataScambio() {
        return DataScambio;
    }

    public void setDataScambio(LocalDateTime dataScambio) {
        DataScambio = dataScambio;
    }
}
