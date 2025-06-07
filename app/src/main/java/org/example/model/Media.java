package org.example.model;
import java.time.LocalDateTime;
import java.util.Arrays;

public class Media {
    private int mediaId;
    private byte[] foto; 
    private String titolo;
    private LocalDateTime data_aggiunta;
    private String nome_formato;
    
    public Media() {
    }

    public Media(int mediaId, String titolo, String nome_formato, LocalDateTime data_aggiunta, byte[] foto) {
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

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Media [mediaId=" + mediaId + ", titolo=" + titolo + ", nome_formato=" + nome_formato
                + ", data_aggiunta=" + data_aggiunta + ", foto=" + (foto != null ? foto.length + " bytes" : "null") + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Media other = (Media) obj;
        return mediaId == other.mediaId &&
               (titolo == null ? other.titolo == null : titolo.equals(other.titolo)) &&
               (nome_formato == null ? other.nome_formato == null : nome_formato.equals(other.nome_formato)) &&
               (data_aggiunta == null ? other.data_aggiunta == null : data_aggiunta.equals(other.data_aggiunta)) &&
               Arrays.equals(foto, other.foto);
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(mediaId);
        result = 31 * result + (titolo != null ? titolo.hashCode() : 0);
        result = 31 * result + (nome_formato != null ? nome_formato.hashCode() : 0);
        result = 31 * result + (data_aggiunta != null ? data_aggiunta.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(foto);
        return result;
    }
}
