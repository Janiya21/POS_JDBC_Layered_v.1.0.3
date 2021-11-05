package controller;

import bo.BOFactory;
import bo.custom.CustomerBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import dto.CustomerDTO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomerRootController implements Initializable {

    @FXML
    public JFXTextField txtTitle;

    @FXML
    public TableView<CustomerDTO> tblCustomer;

    @FXML
    public Button goToNextBtn;

    @FXML
    public Button goBackBtn;

    @FXML
    public JFXButton btnOrderDetails;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtCity;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtProvince;

    @FXML
    private JFXTextField txtPostalCode;

    @FXML
    private TableColumn<CustomerDTO, String> colId;

    @FXML
    private TableColumn<CustomerDTO, String> colTitle;

    @FXML
    private TableColumn<CustomerDTO, String> colName;

    @FXML
    private TableColumn<CustomerDTO, String> colAddress;

    @FXML
    private TableColumn<CustomerDTO, String> colCity;

    @FXML
    private TableColumn<CustomerDTO, String> colProvince;

    @FXML
    private TableColumn<CustomerDTO, String> colPostalCode;

    private int cartSelectedRowForRemove = -1;
    private String deleteCustomerId = null;
    public static String selectedCustomer;
    private final ObservableList<CustomerDTO> obList = FXCollections.observableArrayList();
    private final CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BoTypes.CUSTOMER);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        try {
            setCustomersToTable(customerBO.getAllCustomers());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        tblCustomer.setItems(obList);

        tblCustomer.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;
        });

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            deleteCustomerId = newValue.getId();
            selectedCustomer = newValue.getId();

            //-----------------------------------
            txtId.setText(newValue.getId());
            txtAddress.setText(newValue.getAddress());
            txtTitle.setText(newValue.getTitle());
            txtCity.setText(newValue.getCity());
            txtName.setText(newValue.getName());
            txtPostalCode.setText(newValue.getPostalCode());
            txtProvince.setText(newValue.getProvince());
        });
    }

    @FXML
    void addCustomerOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        CustomerDTO c1 = new CustomerDTO(
                txtId.getText(),txtTitle.getText(),txtName.getText(),txtAddress.getText(),
                txtCity.getText(),txtProvince.getText(),txtPostalCode.getText()
        );

        obList.add(c1);
        if(customerBO.addCustomer(c1))
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
        else
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
    }

    @FXML
    void deleteCustomerOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (cartSelectedRowForRemove==-1){
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        }else{
            deleteCustomerFromTable(deleteCustomerId);
            obList.remove(cartSelectedRowForRemove);
            tblCustomer.refresh();
        }
    }

    private void setCustomersToTable(ArrayList<CustomerDTO> customerDTOS) {
        customerDTOS.forEach(e->{
            obList.add(
                    new CustomerDTO(e.getId(),e.getTitle(),e.getName(),e.getAddress(),e.getCity(),e.getProvince(),e.getPostalCode()));
        });
        tblCustomer.setItems(obList);
    }

    private void deleteCustomerFromTable(String customerId) throws SQLException, ClassNotFoundException {
        if (customerBO.deleteCustomer(customerId)){
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    public int selectCustomerOnAction(ActionEvent event) throws IOException {
        if (cartSelectedRowForRemove==-1){
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
            return -1;
        }else{

            Stage primaryStage = (Stage) goToNextBtn.getScene().getWindow();
            primaryStage.close();

            Parent root = FXMLLoader.load(getClass().getResource("../views/PlaceCustomerOrderForm.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            return cartSelectedRowForRemove;
        }
    }

    public void goBackOnAction(ActionEvent event) throws IOException {
        Stage primaryStage = (Stage) goBackBtn.getScene().getWindow();
        primaryStage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../views/LoginPanel.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void goToOrderDetailsOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/OrderHistoryView.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void updateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        CustomerDTO c1= new CustomerDTO(txtId.getText(),txtTitle.getText(),txtName.getText(),txtAddress.getText(),
                txtCity.getText(),txtProvince.getText(),txtPostalCode.getText()
        );

        obList.remove(cartSelectedRowForRemove);
        obList.add((cartSelectedRowForRemove),c1);
        if (customerBO.updateCustomer(c1))
            new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
        else
            new Alert(Alert.AlertType.WARNING,"Try Again").show();

        tblCustomer.refresh();
    }
}
