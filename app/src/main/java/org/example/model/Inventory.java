package org.example.model;

/**
 * Inventory Entity Class
 * Represents a user from the INVENTORY table
 */
public class Inventory {
    private int inventoryId;
    private boolean pubblico;
    private boolean tipo;

    public Inventory() {
        this.pubblico = true;
        this.tipo = true;
    }

    public Inventory(boolean pubblico, boolean tipo) {
        this.pubblico = pubblico;
        this.tipo = tipo;
    }

    public Inventory(int inventoryId, boolean pubblico, boolean tipo) {
        this.inventoryId = inventoryId;
        this.pubblico = pubblico;
        this.tipo = tipo;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public boolean isPubblico() {
        return pubblico;
    }

    public void setPubblico(boolean pubblico) {
        this.pubblico = pubblico;
    }

    public boolean isTipo() {
        return tipo;
    }

    public void setTipo(boolean tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Inventory inv = (Inventory) obj;
        return inventoryId == inv.inventoryId;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(inventoryId);
    }
}