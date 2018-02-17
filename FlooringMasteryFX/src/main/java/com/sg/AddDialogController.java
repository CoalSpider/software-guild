/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg;

import com.sg.dto.Order;
import com.sg.dto.Product;
import com.sg.dto.State;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Ben Norman
 */
public class AddDialogController implements Initializable {
    @FXML
    private ChoiceBox<State> stateChoices;
    @FXML
    private ChoiceBox<Product> productChoices;
    @FXML
    private TextField name;
    @FXML
    private TextField area;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        area.textProperty().addListener((arg,oldVal,newVal) ->{
            if (!newVal.matches("\\d*(\\.\\d*)?")) {
                area.setText(oldVal);
            }
        });
    }
    
    public void setStateChoices(ObservableList<State> states){
        stateChoices.getItems().setAll(states);
        stateChoices.setConverter(new StringConverter<State>(){
            @Override
            public State fromString(String string) {
                return states.stream().filter((state)->state.getName().equals(string)).collect(Collectors.toList()).get(0);
            }

            @Override
            public String toString(State object) {
                return object.getName();
            }
        });
    }
    
    public void setProductChoices(ObservableList<Product> products){
        productChoices.getItems().setAll(products);
        productChoices.setConverter(new StringConverter<Product>(){
            @Override
            public Product fromString(String string) {
                return products.stream().filter((product)->product.getType().equals(string)).collect(Collectors.toList()).get(0);
            }

            @Override
            public String toString(Product object) {
                return object.getType();
            }
        });
    }
    
    public void setResultConverter(Dialog<Order> dialog){
        dialog.setResultConverter(buttonType->{
            if(buttonType.equals(ButtonType.CANCEL)||buttonType.equals(ButtonType.CLOSE)){
                return null;
            } else {
                Order order = new Order();
                order.setCustomerName(name.getText());
                // area validates for decimals
                order.setAreaInSquareFeet(new BigDecimal(area.getText()));
                order.setProduct(productChoices.getValue());
                order.setState(stateChoices.getValue());
                // force total calc
                order.getTotal();
                return order;
            }
        });
    }
}
