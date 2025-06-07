package org.example.model;

import java.time.LocalDateTime;

public class Item {
    private int mediaId;
    private int itemId;
    private int inventoryId;
    private String condizioni;
    private String note;
    private LocalDateTime data_acquisizione;

    public Item() {
    }

    public Item(int mediaId, int inventoryId, int itemId, String condizioni, String note, LocalDateTime data_acquisizione) {
        this.mediaId = mediaId;
        this.itemId = itemId;
        this.inventoryId = inventoryId;
        this.condizioni = condizioni;
        this.note = note;
        this.data_acquisizione = data_acquisizione;
    }

    public Item(int mediaId, int inventoryId, String condition, String note, LocalDateTime dataAcquisizione) {
        this.mediaId = mediaId;
        this.inventoryId = inventoryId;
        this.condizioni = condizioni;
        this.note = note;
        this.data_acquisizione = data_acquisizione;
    }

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getCondizioni() {
        return condizioni;
    }

    public void setCondizioni(String condizioni) {
        this.condizioni = condizioni;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getData_acquisizione() {
        return LocalDateTime.now();
    }

    public void setData_acquisizione(LocalDateTime data_acquisizione) {
        this.data_acquisizione = data_acquisizione;
    }

    @Override
    public String toString() {
        return "Item [mediaId=" + mediaId + ", itemId=" + itemId + ", inventoryId=" + inventoryId + ", condizioni="
                + condizioni + ", note=" + note + ", data_acquisizione=" + data_acquisizione + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Item other = (Item) obj;
        if (mediaId != other.mediaId)
            return false;
        if (itemId != other.itemId)
            return false;
        if (inventoryId != other.inventoryId)
            return false;
        if (condizioni == null) {
            if (other.condizioni != null)
                return false;
        } else if (!condizioni.equals(other.condizioni))
            return false;
        if (note == null) {
            if (other.note != null)
                return false;
        } else if (!note.equals(other.note))
            return false;
        if (data_acquisizione == null) {
            if (other.data_acquisizione != null)
                return false;
        } else if (!data_acquisizione.equals(other.data_acquisizione))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(itemId);
    }
}