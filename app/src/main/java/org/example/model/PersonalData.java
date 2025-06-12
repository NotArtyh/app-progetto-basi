package org.example.model;

/**
 * User Entity Class
 * Represents a user from the DATI_UTENTE table
 */
public class PersonalData {
    private int personaId;
    private String nome;
    private String cognome;
    private String sesso;
    private String telefono;
    private String provincia;
    private String statoResidenza;
    private String cap;
    private String via;
    private String civico;

    // Default Constructor
    public PersonalData() {
    }

    // Full Constructor (for existing data)
    public PersonalData(int personaId, String nome, String cognome, String sesso, String telefono, String provincia,
            String statoResidenza, String cap, String via, String civico) {
        this.personaId = personaId;
        this.nome = nome;
        this.cognome = cognome;
        this.sesso = sesso;
        this.telefono = telefono;
        this.provincia = provincia;
        this.statoResidenza = statoResidenza;
        this.cap = cap;
        this.via = via;
        this.civico = civico;
    }

    // Constructor without personaId (for creating new data)
    public PersonalData(String nome, String cognome, String sesso, String telefono, String provincia,
            String statoResidenza, String cap, String via, String civico) {
        this.nome = nome;
        this.cognome = cognome;
        this.sesso = sesso;
        this.telefono = telefono;
        this.provincia = provincia;
        this.statoResidenza = statoResidenza;
        this.cap = cap;
        this.via = via;
        this.civico = civico;
    }

    // Getters and Setters
    public int getPersonaId() {
        return personaId;
    }

    public void setPersonaId(int personaId) {
        this.personaId = personaId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getStato_residenza() {
        return statoResidenza;
    }

    public void setStato_residenza(String statoResidenza) {
        this.statoResidenza = statoResidenza;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getCivico() {
        return civico;
    }

    public void setCivico(String civico) {
        this.civico = civico;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        PersonalData that = (PersonalData) obj;
        return personaId == that.personaId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(personaId);
    }

}