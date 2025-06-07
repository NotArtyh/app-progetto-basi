package org.example.model;
import java.time.LocalDateTime;

public class Media {
    private int mediaId;
    private String foto; 
    private String titolo;
    private LocalDateTime data_aggiunta;
    private String nome_formato;
    
    public Media() {
    }

    public Media(int mediaId, String titolo, String nome_formato, LocalDateTime data_aggiunta, String foto) {
        this.mediaId = mediaId;
        this.titolo = titolo;
        this.nome_formato = nome_formato;
        this.data_aggiunta = data_aggiunta;
        this.foto = foto;
    }

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getNome_formato() {
        return nome_formato;
    }

    public void setNome_formato(String nome_formato) {
        this.nome_formato = nome_formato;
    }

    public LocalDateTime getData_aggiunta() {
        return data_aggiunta;
    }

    public void setData_aggiunta(LocalDateTime data_aggiunta) {
        this.data_aggiunta = data_aggiunta;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Media [mediaId=" + mediaId + ", titolo=" + titolo + ", nome_formato=" + nome_formato
                + ", data_aggiunta=" + data_aggiunta + ", foto=" + foto + "]";
    }
}


