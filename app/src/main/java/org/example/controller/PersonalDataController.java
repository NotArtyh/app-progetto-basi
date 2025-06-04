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
    
    /**
     * Constructor
     * @param personalDataDAO Data Access Object for person
     * @param personalDataView View for personal data interface
     */
    
    
    /**
     * Create a new person
     */

    public void createPersonalData(String nome, String cognome, String sesso, String telefono, String stato, String provincia, String cap, String via, String civico) {
        try {
            
            
            // Validate input
            if (nome == null || nome.trim().isEmpty()) {
                personalDataView.displayError("nome cannot be empty.");
                return;
            }

            // Validate input
            if (cognome == null || cognome.trim().isEmpty()) {
                personalDataView.displayError("cognome cannot be empty.");
                return;
            }
            
            if (sesso == null || sesso.trim().isEmpty()) {
                personalDataView.displayError("sesso cannot be empty.");
                return;
            }

            if (telefono == null || telefono.trim().isEmpty()) {
                personalDataView.displayError("telefono cannot be empty.");
                return;
            }
            
            if (stato == null || stato.trim().isEmpty()) {
                personalDataView.displayError("stato cannot be empty.");
                return;
            }
            
            if (provincia == null || provincia.trim().isEmpty()) {
                personalDataView.displayError("provincia cannot be empty.");
                return;
            }
            
            if (cap == null || cap.trim().isEmpty()) {
                personalDataView.displayError("cap cannot be empty.");
                return;
            }


            if (via == null || via.trim().isEmpty()) {
                personalDataView.displayError("via cannot be empty.");
                return;
            }
            

            if (civico == null || civico.trim().isEmpty()) {
                personalDataView.displayError("civico cannot be empty.");
                return;
            }
        
            
            // Create personaldata
            PersonalData person;

            // Assuming the correct constructor is: PersonalData(int id, String nome, String cognome, String sesso, String telefono, String provincia, String stato, String cap, String via, String civico)
            // If id is auto-generated, you can pass 0 or -1 as a placeholder
            person = new PersonalData(0, nome, cognome, sesso, telefono, provincia, stato, cap, via, civico);
            
            
            personalDataDAO.createPerson(person);
            personalDataView.displayMessage("User created successfully with ID: " + person.getPersonaId());
            
        } catch (SQLException e) {
            personalDataView.displayError("Error creating user: " + e.getMessage());
        } catch (Exception e) {
            personalDataView.displayError("Unexpected error: " + e.getMessage());
        }
    }
}

    

