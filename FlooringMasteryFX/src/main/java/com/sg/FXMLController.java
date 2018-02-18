package com.sg;

import com.sg.dao.OrderDaoFileImpl;
import com.sg.dao.ProductDaoFileImpl;
import com.sg.dao.StateDaoFileImpl;
import com.sg.dto.Order;
import com.sg.exceptions.AlreadyDeletedException;
import com.sg.exceptions.PersistenceException;
import com.sg.service.Service;
import com.sg.service.ServiceFileImpl;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class FXMLController implements Initializable {

    // to be injected by spring by setter
    private Service service;
    // fxml controllers
    private EditDialogController editController;
    private AddDialogController addController;
    // user action dialogs and alerts
    private Dialog<Order> addOrderDialog;
    private Dialog<Order> editOrderDialog;
    private Alert warning;
    private Alert errorMsg;
    private Alert confirmDelete;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TableView<Order> orderTable;
    @FXML
    private Button add;
    @FXML
    private Button save;
    @FXML
    private Button edit;
    @FXML
    private Button delete;
    @FXML
    private Text title;

    @FXML
    private void addOrderAction(ActionEvent event) {
        // create order if user did not cancel
        addOrderDialog.showAndWait().ifPresent((order) -> {
            try {
                service.createOrder(order);
                orderTable.getItems().add(order);
                orderTable.refresh();
                save.setDisable(false);
            } catch (PersistenceException ex) {
                // should only happen if there was a file issue
                displayErrorAlert("Error creating order " + ex.getMessage());
            }
        });
    }

    @FXML
    private void editOrderAction(ActionEvent event) {
        Order selected = getSelectedOrder();

        if (selected.isDeleted()) {
            displayWarning("Cant Edit Canceled Order");
            return;
        }

        editController.setDialog(selected);

        editOrderDialog.showAndWait().ifPresent((order) -> {
            selected.setCustomerName(order.getCustomerName());
            selected.setAreaInSquareFeet(order.getAreaInSquareFeet());
            selected.setState(order.getState());
            selected.setProduct(order.getProduct());
            // we changed data so refresh
            orderTable.refresh();
            save.setDisable(false);
        });
    }

    @FXML
    private void deleteOrderAction(ActionEvent event) {
        Order selected = getSelectedOrder();
        if (selected.isDeleted()) {
            displayWarning("Order Already Canceled");
            return;
        }
        confirmDelete.showAndWait().ifPresent(buttonType -> {
            // if user confirmed then delete
            if (buttonType.equals(ButtonType.OK)) {
                try {
                    service.deleteOrder(getSelectedOrder(), getDate());
                    orderTable.refresh();
                    save.setDisable(false);
                } catch (PersistenceException | AlreadyDeletedException ex) {
                    displayErrorAlert(ex.getMessage());
                }
            }
        });

    }

    @FXML
    private void saveAction(ActionEvent event) {
        try {
            service.save();
            save.setDisable(true);
        } catch (PersistenceException ex) {
            displayErrorAlert(ex.getMessage());
        }
    }

    @FXML
    private void onChooseDate() {
        try {
            // set date
            orderTable.setItems(FXCollections.observableArrayList(service.getOrders(getDate())));
            // disable edit as were not selecting anything
            edit.setDisable(true);
            // disable add if were not on todays date
            add.setDisable(!getDate().equals(LocalDate.now()));
            // disbale delete as were not selecting anything
            delete.setDisable(true);
        } catch (PersistenceException ex) {
            displayErrorAlert(ex.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // disable save as theres nothing to save
        save.setDisable(true);
        // disable edit as were not selecting anything
        edit.setDisable(true);
        // disbale delete as were not selecting anything
        delete.setDisable(true);
        // on selction enable edit and delete button
        orderTable.getSelectionModel().getSelectedCells().addListener((ListChangeListener.Change<? extends TablePosition> c) -> {
            edit.setDisable(false);
            delete.setDisable(false);
        });
        // set the date to today
        datePicker.setValue(LocalDate.now());
        // TODO: inject in setter with spring
        service = new ServiceFileImpl(new OrderDaoFileImpl(), new ProductDaoFileImpl(), new StateDaoFileImpl(), "training");

        initAlerts();
        try {
            initAddDialog();
            initEditDialog();
        } catch (IOException ex) {
            displayErrorAlert(ex.getMessage());
        } catch (PersistenceException ex) {
            displayErrorAlert("error loading products or states file");
        }

        //initTitleNeon();
    }

    private void initAlerts() {
        warning = new Alert(Alert.AlertType.WARNING);
        errorMsg = new Alert(Alert.AlertType.ERROR);
        confirmDelete = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDelete.setHeaderText("You sure? Delete Order?");
    }

    private void initAddDialog() throws IOException, PersistenceException {
        // load FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddDialog.fxml"));
        DialogPane dialogPaneAdd = loader.load();
        addController = loader.getController();
        // set dropdowns
        addController.setProductChoices(FXCollections.observableArrayList(service.getProducts()));
        addController.setStateChoices(FXCollections.observableArrayList(service.getStates()));
        // init dialog
        addOrderDialog = new Dialog<>();
        addOrderDialog.setTitle("ADD ORDER");
        addOrderDialog.setDialogPane(dialogPaneAdd);
        addOrderDialog.getDialogPane().getButtonTypes().addAll(ButtonType.FINISH, ButtonType.CLOSE);
        // set converter
        addController.setResultConverter(addOrderDialog);
    }

    private void initEditDialog() throws IOException, PersistenceException {
        // load FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EditDialog.fxml"));
        DialogPane dialogPaneEdit = loader.load();
        editController = loader.getController();
        // set dropdowns
        editController.setProductChoices(FXCollections.observableArrayList(service.getProducts()));
        editController.setStateChoices(FXCollections.observableArrayList(service.getStates()));
        // init dialog
        editOrderDialog = new Dialog<>();
        editOrderDialog.setTitle("EDIT ORDER");
        editOrderDialog.setDialogPane(dialogPaneEdit);
        editOrderDialog.getDialogPane().getButtonTypes().addAll(ButtonType.FINISH, ButtonType.CLOSE);
        // set converter
        editController.setResultConverter(editOrderDialog);

    }

    private void initTitleNeon() {
        Blend blend = new Blend();
        blend.setMode(BlendMode.MULTIPLY);

        DropShadow ds = new DropShadow();
        ds.setColor(Color.rgb(254, 235, 66, 0.3));
        ds.setOffsetX(5);
        ds.setOffsetY(5);
        ds.setRadius(5);
        ds.setSpread(0.2);

        blend.setBottomInput(ds);

        DropShadow ds1 = new DropShadow();
        ds1.setColor(Color.web("#f13a00"));
        ds1.setRadius(20);
        ds1.setSpread(0.2);

        Blend blend2 = new Blend();
        blend2.setMode(BlendMode.MULTIPLY);

        InnerShadow is = new InnerShadow();
        is.setColor(Color.web("#feeb42"));
        is.setRadius(9);
        is.setChoke(0.8);
        blend2.setBottomInput(is);

        InnerShadow is1 = new InnerShadow();
        is1.setColor(Color.web("#f13a00"));
        is1.setRadius(5);
        is1.setChoke(0.4);
        blend2.setTopInput(is1);

        Blend blend1 = new Blend();
        blend1.setMode(BlendMode.MULTIPLY);
        blend1.setBottomInput(ds1);
        blend1.setTopInput(blend2);

        blend.setTopInput(blend1);

        title.setEffect(blend);
        title.setFill(Color.WHITE);
        title.setFont(Font.font("Impact", 50));
    }
    
    // TODO injected by spring
    public void setService(Service service) {
        this.service = service;
    }

    private void displayErrorAlert(String msg) {
        errorMsg.setHeaderText("Error: " + msg);
        errorMsg.showAndWait();
    }

    private void displayWarning(String msg) {
        warning.setHeaderText(msg);
        warning.showAndWait();
    }

    private LocalDate getDate() {
        return datePicker.getValue();
    }

    private Order getSelectedOrder() {
        return orderTable.getSelectionModel().getSelectedItem();
    }
}
