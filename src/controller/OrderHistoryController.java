package controller;

import bo.BOFactory;
import bo.custom.OrderHistoryBO;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import dto.CustomerDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrderHistoryController implements Initializable {
    @FXML
    private TableView<OrderDTO> orderIdTable;

    @FXML
    private TableColumn<OrderDTO, ?> colOrderId;

    @FXML
    private TableView<OrderDetailDTO> tblItem;

    @FXML
    private TableColumn<OrderDetailDTO, ?> colItemCode;
    @FXML
    private TableColumn<OrderDetailDTO, ?> colQty;

    @FXML
    private TableColumn<OrderDetailDTO, ?> colDiscount;

    @FXML
    private TableColumn<OrderDetailDTO, ?> colTot;

    @FXML
    private JFXTextField txtCustomerId;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private JFXTextField txtOrderedDate;

    @FXML
    private JFXTextField txtCustomerAddress;

    @FXML
    private JFXTextField txtPostalCode;

    @FXML
    private Label lblTotal;

    @FXML
    private Button goBackBtn;

    private final ObservableList<OrderDTO> obsListOrderDTO = FXCollections.observableArrayList();
    private final ObservableList<OrderDetailDTO> obsListItem = FXCollections.observableArrayList();

    OrderHistoryBO ohb = (OrderHistoryBO) BOFactory.getBoFactory().getBO(BOFactory.BoTypes.ORDER_HISTORY);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        try {
            setOrdersToTable(ohb.getAllOrders());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        orderIdTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                loadCustomersToFields(newValue);
                tblItem.getItems().clear();
                loadItemsToTable(ohb.getAllItems(newValue.getOrderId()));
                calculateTotal();
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    @FXML
    void goBackOnAction(ActionEvent event) throws IOException {
        Stage primaryStage = (Stage) goBackBtn.getScene().getWindow();
        primaryStage.close();
    }

    private void setOrdersToTable(ArrayList<OrderDTO> ord) {
        ord.forEach(e->{
            obsListOrderDTO.add(new OrderDTO(e.getOrderId(),e.getCustomerId(),e.getOrderDate()));
        });
        orderIdTable.setItems(obsListOrderDTO);
    }

    private void loadCustomersToFields(OrderDTO orderDTO) throws SQLException, ClassNotFoundException {
        CustomerDTO customerDTO = ohb.searchCustomer(orderDTO.getCustomerId());
        txtCustomerId.setText(customerDTO.getId());
        txtCustomerAddress.setText(customerDTO.getAddress());
        txtCustomerName.setText(customerDTO.getName());
        txtOrderedDate.setText(orderDTO.getOrderDate());
        txtPostalCode.setText(customerDTO.getPostalCode());
    }

    private void loadItemsToTable(ArrayList<OrderDetailDTO> dto) throws SQLException, ClassNotFoundException {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTot.setCellValueFactory(new PropertyValueFactory<>("total"));

        //obsListItem.add(new OrderDetailDTO(dto.getItemCode(),dto.getDiscount(),dto.getOrderQty(),dto.getTotal()));
        dto.forEach(e->{
            obsListItem.add(new OrderDetailDTO(e.getItemCode(),e.getDiscount(),e.getOrderQty(),e.getTotal()));
        });
        tblItem.setItems(obsListItem);
    }

    private void calculateTotal(){
        double total=0.00;
        for (OrderDetailDTO item:obsListItem) {
            total+=item.getTotal();
        }
        lblTotal.setText(String.valueOf(total)+ " /=");
    }
}
