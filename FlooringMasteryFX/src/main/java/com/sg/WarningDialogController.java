/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

/**
 *
 * @author Ben Norman
 */
public class WarningDialogController implements Initializable{

    @FXML
    private Text text;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // do nothing
    }
    
    public void setText(String text){
        this.text.setText(text);
    }
}
