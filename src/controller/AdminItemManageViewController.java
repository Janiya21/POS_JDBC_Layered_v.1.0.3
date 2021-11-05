package controller;

import bo.BOFactory;
import bo.custom.ItemBO;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import dto.ItemDTO;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminItemManageViewController implements Initializable {

    @FXML
    private JFXTextField txtItemId;

    @FXML
    private JFXTextField txtDesc;

    @FXML
    private JFXTextField txtPackSize;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    private JFXTextField txtQtyOnHand;

    @FXML
    private JFXTextField txtUnitProfit;

    @FXML
    private TableView<ItemDTO> tblItemDetails;

    @FXML
    private TableColumn<ItemDTO, ?> colItemCode;

    @FXML
    private TableColumn<ItemDTO, ?> colDesc;

    @FXML
    private TableColumn<ItemDTO, ?> colPackSize;

    @FXML
    private TableColumn<ItemDTO, ?> colQtyOnHand;

    @FXML
    private TableColumn<ItemDTO, ?> colUnitPrice;

    @FXML
    private TableColumn<ItemDTO, ?> colUnitProfit;

    private int tblSelectedRowForRemove=-1;
    private String deleteRowId;
    private final ObservableList<ItemDTO> obList = FXCollections.observableArrayList();
    private final ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BoTypes.ITEM);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packageSize"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitProfit"));
        colUnitProfit.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        try {
            setItemToTable(itemBO.getAllItems());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        tblItemDetails.setItems(obList);

        tblItemDetails.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            tblSelectedRowForRemove = (int) newValue;
            System.out.println(tblSelectedRowForRemove);
        });

        tblItemDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            deleteRowId = newValue.getCode();
        });

        tblItemDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            txtItemId.setText(newValue.getCode());
            txtDesc.setText(newValue.getDescription());
            txtPackSize.setText(newValue.getPackageSize());
            txtQtyOnHand.setText(String.valueOf(newValue.getQtyOnHand()));
            txtUnitProfit.setText(String.valueOf(newValue.getUnitProfit()));
            txtUnitPrice.setText(String.valueOf(newValue.getUnitPrice()));
        });
    }

    @FXML
    void addItemOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        if(txtItemId.getText().trim().isEmpty() && txtDesc.getText().trim().isEmpty() && txtPackSize.getText().trim().isEmpty()
                && txtQtyOnHand.getText().trim().isEmpty() && txtUnitProfit.getText().trim().isEmpty() && txtUnitPrice.getText().trim().isEmpty()){

            new Alert(Alert.AlertType.ERROR,"Fill All Fields..").show();

        }else{

            ItemDTO i1 = new ItemDTO(
                    txtItemId.getText(),txtDesc.getText(),txtPackSize.getText(),Integer.parseInt(txtQtyOnHand.getText()),
                    Double.parseDouble(txtUnitProfit.getText()),Double.parseDouble(txtUnitPrice.getText())
            );

            obList.add(i1);

            if(itemBO.addItem(i1))
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
            else
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();

            tblItemDetails.refresh();
        }
    }

    @FXML
    void deleteItemOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (tblSelectedRowForRemove==-1){
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        }else{
            deleteItemFromTable(deleteRowId);
            obList.remove(tblSelectedRowForRemove);
            tblItemDetails.refresh();
        }
    }

    @FXML
    void updateItemOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (tblSelectedRowForRemove==-1){
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        }else{
            updateItemOfTable();
            tblItemDetails.refresh();
        }
    }

    private void setItemToTable(ArrayList<ItemDTO> itemDTO) {
        itemDTO.forEach(e->{
            obList.add(
                    new ItemDTO(e.getCode(),e.getDescription(),e.getPackageSize(),e.getQtyOnHand(),
                            e.getUnitProfit(),e.getUnitPrice()));
        });
        tblItemDetails.setItems(obList);
    }

    private void deleteItemFromTable(String code) throws SQLException, ClassNotFoundException {
        if (itemBO.deleteItem(code)){
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    private void updateItemOfTable() throws SQLException, ClassNotFoundException {
        ItemDTO i1= new ItemDTO(txtItemId.getText(),txtDesc.getText(),txtPackSize.getText(),Integer.parseInt(txtQtyOnHand.getText()),
                Double.parseDouble(txtUnitProfit.getText()),Double.parseDouble(txtUnitPrice.getText())
        );

        obList.remove(tblSelectedRowForRemove);
        obList.add((tblSelectedRowForRemove),i1);
        if (itemBO.updateItem(i1))
            new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
        else
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
    }

    public void clearFieldsOnAction(ActionEvent event) {
        txtItemId.clear();
        txtUnitPrice.clear();
        txtUnitProfit.clear();
        txtQtyOnHand.clear();
        txtPackSize.clear();
        txtDesc.clear();
    }
}
