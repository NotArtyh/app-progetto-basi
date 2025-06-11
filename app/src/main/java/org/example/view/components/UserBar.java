package org.example.view.components;

import javax.swing.*;
import java.awt.*;

public class UserBar extends JPanel {
    private UserActionListener actionListener;

    public UserBar() {
        createUserBar();
    }

    public void setActionListener(UserActionListener actionListener) {
        this.actionListener = actionListener;
    }

    private void createUserBar() {
        // Implement the bar that contains the user info like name logout option user
        // icon etc
    }

    public interface UserActionListener {
        void onLogOut();
    }
}
