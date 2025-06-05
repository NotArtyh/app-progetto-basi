package org.example.controller;

import java.sql.SQLException;

import org.example.database.PersonalDataDAO;
import org.example.model.PersonalData;
import org.example.view.UserView;

/**
 * PersonalData Controller Class
 * Handles business logic and coordinates between Model and View
 */
public class PersonalDataController {
    private PersonalDataDAO personalDataDAO;
    private UserView personalDataView;
    private PersonalData currentPersonalData = null;

    public PersonalData getCurrentPersonalData() {
        return currentPersonalData;
    }

    /**
     * Constructor
     * 
     * @param personalDataDAO  Data Access Object for person
     * @param personalDataView View for personal data interface
     */
    public PersonalDataController(PersonalDataDAO personalDataDAO, UserView personalDataView) {
        this.personalDataDAO = personalDataDAO;
        this.personalDataView = personalDataView;
    }

    /**
     * Create a new person
     */
    public int createPersonalData(String nome, String cognome, String sesso, String telefono, String stato,
            String provincia, String cap, String via, String civico) {
        try {
            // Create person with the provied params - params have already been checked
            PersonalData person = new PersonalData(nome, cognome, sesso, telefono, provincia, stato, cap, via, civico);

            personalDataDAO.createPerson(person);
            personalDataView.displayMessage("User created successfully with ID: " + person.getPersonaId());
            return person.getPersonaId();
        } catch (SQLException e) {
            personalDataView.displayError("Error creating user: " + e.getMessage());
            return -1;
        } catch (Exception e) {
            personalDataView.displayError("Unexpected error: " + e.getMessage());
            return -1;
        }
    }

}
