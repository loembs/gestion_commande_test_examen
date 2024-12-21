package com.patrick.controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import com.patrick.App;


public class ClientMenuController extends logoutController {
    
    @FXML
    public void loadCommandeView() {
        try {
            App.loadView("client/CommandeView");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
}

