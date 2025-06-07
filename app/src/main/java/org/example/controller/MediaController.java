package org.example.controller;

import org.example.database.MediaDAO;
import org.example.view.UserView;

public class MediaController {
    private MediaDAO mediaDAO;
    private UserView mediaView; // Usiamo UserView per la visualizzazione

    public MediaController(MediaDAO mediaDAO, UserView mediaView) {
        this.mediaDAO = mediaDAO;
        this.mediaView = mediaView;
    }

}
