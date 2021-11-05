package controller;

import com.jfoenix.controls.JFXComboBox;
import dao.DAOFactory;
import dao.custom.QueryDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import dto.CustomDTO;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MonthlyIncomeViewController implements Initializable {

    @FXML
    public Label lblDiscount;

    @FXML
    public Label lblAbsProfit;

    @FXML
    public JFXComboBox<String> cmbMonthView;

    @FXML
    private TableView<CustomDTO> tblProfitShow;

    @FXML
    private Label lblProfit;

    @FXML
    private TableColumn<?, ?> colDiscount;

    @FXML
    private TableColumn<?, ?> colDesc;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colUnitProfit;

    @FXML
    private TableColumn<?, ?> colTotalProfit;

    @FXML
    private BarChart<?, ?> movableChart;

    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERYDAO);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbMonthView.getItems().addAll("01","02","03","04","05","06","07","08","09","10","11","12");
    }

    @FXML
    void picTheMonthOnDateAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        tblProfitShow.getItems().clear();

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitProfit.setCellValueFactory(new PropertyValueFactory<>("unitProfit"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotalProfit.setCellValueFactory(new PropertyValueFactory<>("profit"));

        System.out.println(cmbMonthView.getValue());

        setCustomersToTable(queryDAO.getMonthlyProfit(String.valueOf(cmbMonthView.getValue())));

        setMonthlyOrderDetail();
    }

    ObservableList<CustomDTO> obsList = FXCollections.observableArrayList();

    private void setCustomersToTable(ArrayList<CustomDTO> customDTOS) {
        customDTOS.forEach(e->{
            obsList.add(
                    new CustomDTO(e.getItemCode(),e.getDesc(),e.getQty(),e.getUnitProfit(),e.getDiscount(),
                            Double.parseDouble(String.format("%.2f",e.getProfit()))));
        });
        tblProfitShow.setItems(obsList);
    }

    private void setMonthlyOrderDetail(){
        XYChart.Series set = new XYChart.Series<>();
        double totalDiscount = 0;
        double totalProfit=0;

        for (CustomDTO customDTO :obsList) {
            totalDiscount+= customDTO.getDiscount();
            totalProfit+= customDTO.getProfit();

            set.getData().add(new XYChart.Data(customDTO.getDesc(), customDTO.getQty()));
        }
        double absoluteProfit=totalProfit-totalDiscount;

        lblProfit.setText(String.valueOf(Double.parseDouble(String.format("%.2f",totalProfit))));
        lblDiscount.setText(String.valueOf(Double.parseDouble(String.format("%.2f",totalDiscount))));
        lblAbsProfit.setText(String.valueOf(Double.parseDouble(String.format("%.2f",absoluteProfit))));

        movableChart.getData().addAll(set);
    }
}
