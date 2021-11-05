package controller;

import bo.BOFactory;
import bo.custom.OrderCheckOutBO;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import views.tm.CartTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

public class PlaceCustomerOrderFormController implements Initializable {

    @FXML
    public JFXTextField txtPackageSize;

    @FXML
    public TextField txtDiscount;

    @FXML
    public Button goBackBtn;

    public AnchorPane paymentPane;

    @FXML
    public JFXRadioButton radCash;

    @FXML
    public JFXRadioButton radVisa;

    @FXML
    public TextArea txtPaymentDesc;

    @FXML
    public Label lblFulAmount;

    @FXML
    public Label lblFullDiscount;

    @FXML
    public Label lblTotalPayment;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private TextField txtQty;

    @FXML
    private TableView<CartTm> tblCart;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colQTY;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colDiscount;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private Label txtTtl;

    @FXML
    private JFXComboBox<String> cmbItemIds;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtQtyOnHand;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    private Label lblCustomerId;

    @FXML
    private Button goToPaymentBtn;

    private int cartSelectedRowForRemove;
    private final ObservableList<CartTm> obList= FXCollections.observableArrayList();
    private final OrderCheckOutBO odc = (OrderCheckOutBO) BOFactory.getBoFactory().getBO(BOFactory.BoTypes.ORDER_CHECKOUT);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        TranslateTransition translate = new TranslateTransition(Duration.seconds(0.2), paymentPane);
        translate.setToX(350);
        translate.play();

        //----------------------------------------------------------

        lblCustomerId.setText(CustomerRootController.selectedCustomer);

        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        loadDateAndTime();

        try {
            loadItemIds();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        cmbItemIds.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setItemData(newValue);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });

        tblCart.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;
        });

        goToPaymentBtn.setDisable(true);

        txtTtl.textProperty().addListener(((observable, oldValue, newValue) -> {
            System.out.println(newValue + " " + oldValue);
            goToPaymentBtn.setDisable(newValue.equals("0.0 /="));
        }));
    }

    @FXML
    void addToCartOnAction(ActionEvent event) {
        String description = txtDescription.getText();
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qtyForCustomer = Integer.parseInt(txtQty.getText());
        double fullDiscount = Double.parseDouble(String.format("%.2f",qtyForCustomer * (unitPrice * (Double.parseDouble(txtDiscount.getText())/100.00))));
        System.out.println(fullDiscount);
        double total = (qtyForCustomer * unitPrice) - fullDiscount;

        if (qtyOnHand<qtyForCustomer){
            new Alert(Alert.AlertType.WARNING,"Invalid QTY").show();
            return;
        }

        CartTm tm = new CartTm(
                cmbItemIds.getValue(),
                description,
                qtyForCustomer,
                unitPrice,
                fullDiscount,
                total
        );

        int rowNumber=isExists(tm);

        if (rowNumber==-1){
            obList.add(tm);
        }else{
            CartTm temp = obList.get(rowNumber);
            CartTm newTm = new CartTm(
                    temp.getCode(),
                    temp.getDescription(),
                    temp.getQty()+qtyForCustomer,
                    unitPrice,
                    fullDiscount,
                    total+temp.getTotal()
            );

            obList.remove(rowNumber);
            obList.add(newTm);
        }
        tblCart.setItems(obList);
        calculateCost();
    }

    @FXML
    void clearItemOnAction(ActionEvent event) {
        if (cartSelectedRowForRemove==-1){
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        }else{
            obList.remove(cartSelectedRowForRemove);
            calculateCost();
            tblCart.refresh();
        }
    }

    @FXML
    void placeOrderOnAction(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        if(displayModalPopup("Confirm Order","Confirm the Order to create the Bill ?")){
            setOrderId();
            try {
                ArrayList<OrderDetailDTO> odt=new ArrayList<>();
                for (CartTm c: obList) {
                    assert false;
                    odt.add(new OrderDetailDTO(c.getCode(),lblOrderId.getText(),c.getDiscount(),c.getQty(),c.getTotal()));
                }

                OrderDTO orderDTO = new OrderDTO(lblOrderId.getText(), lblCustomerId.getText(), lblDate.getText(), odt);
                saveOrder(orderDTO);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private void loadItemIds() throws SQLException, ClassNotFoundException {
        cmbItemIds.getItems().addAll(odc.getAllItemIds());
    }

    private void setOrderId() throws SQLException, ClassNotFoundException {
        lblOrderId.setText(odc.getOrderId());
    }

    private void saveOrder(OrderDTO odt) throws SQLException, ClassNotFoundException {
        if(odc.addItemToOrder(odt)){
            new Alert(Alert.AlertType.INFORMATION,"Order Added Done").show();
        }else{
            new Alert(Alert.AlertType.ERROR,"Error..").show();
        }
    }

    private int isExists(CartTm tm){
        for (int i = 0; i < obList.size(); i++) {
            if (tm.getCode().equals(obList.get(i).getCode())){
                return i;
            }
        }
        return -1;
    }

    void calculateCost(){
        double ttl=0;
        for (CartTm tm:obList
        ) {
            ttl+=tm.getTotal();
        }
        txtTtl.setText(ttl+" /=");
    }

    private void setItemData(String itemCode) throws SQLException, ClassNotFoundException {
        ItemDTO i1 = odc.searchItem(itemCode);
        if (i1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtDescription.setText(i1.getDescription());
            txtPackageSize.setText(i1.getPackageSize());
            txtQtyOnHand.setText(String.valueOf(i1.getQtyOnHand()));
            txtUnitPrice.setText(String.valueOf(i1.getUnitPrice()));
        }
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() +
                            " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void goBackOnAction(ActionEvent event) throws IOException {
        Stage primaryStage = (Stage) goBackBtn.getScene().getWindow();
        primaryStage.close();

        Parent root = FXMLLoader.load(getClass().getResource("../views/CustomerRoot.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public boolean displayModalPopup(String title, String context) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(context);

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public void goToPaymentOnAction(ActionEvent event) {

        double allAmount=0;
        double allDiscount=0;

        for (CartTm c: obList) {
            allAmount+=(c.getQty()*c.getUnitPrice());
            allDiscount+=(c.getDiscount());
        }

        lblTotalPayment.setText(String.valueOf(txtTtl.getText()));
        lblFullDiscount.setText(String.valueOf(-allDiscount));
        lblFulAmount.setText(String.valueOf(allAmount));

        TranslateTransition translate = new TranslateTransition(Duration.seconds(1.5), paymentPane);
        translate.setByX(-348);
        translate.play();
    }

    public void goRightOnAction(ActionEvent event) {
        TranslateTransition translate = new TranslateTransition(Duration.seconds(1.5), paymentPane);
        translate.setToX(340);
        translate.play();
    }
}
