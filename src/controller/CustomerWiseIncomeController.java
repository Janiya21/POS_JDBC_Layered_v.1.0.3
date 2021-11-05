package controller;

import dao.DAOFactory;
import dao.custom.QueryDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class CustomerWiseIncomeController implements Initializable {

    @FXML
    public Label lblDiscount;

    @FXML
    public Label lblAbsProfit;

    @FXML
    private TableView<CustomDTO> tblProfitShow;

    @FXML
    private Label lblProfit;

    @FXML
    private TableColumn<?, ?> colDiscount;

    @FXML
    private TableColumn<?, ?> colCustomerName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotalProfit;

    @FXML
    private BarChart<?, ?> movableChart;

    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERYDAO);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadData();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void loadData() throws SQLException, ClassNotFoundException {
        tblProfitShow.getItems().clear();

        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotalProfit.setCellValueFactory(new PropertyValueFactory<>("profit"));

        setCustomersToTable(queryDAO.getCUSTOMERProfit());

        setMonthlyOrderDetail();
    }

    ObservableList<CustomDTO> obsList = FXCollections.observableArrayList();

    private void setCustomersToTable(ArrayList<CustomDTO> customDTOS) {
        if(customDTOS !=null){
            customDTOS.forEach(e->{
                obsList.add(
                        new CustomDTO(e.getCustomerId(),e.getQty(),e.getDiscount(),
                                Double.parseDouble(String.format("%.2f",e.getProfit()))));
            });
            tblProfitShow.setItems(obsList);
        }
    }

    private void setMonthlyOrderDetail(){
        XYChart.Series set = new XYChart.Series<>();
        double totalDiscount = 0;
        double totalProfit=0;

        for (CustomDTO customDTO :obsList) {
            totalDiscount+= customDTO.getDiscount();
            totalProfit+= customDTO.getProfit();

            set.getData().add(new XYChart.Data(customDTO.getCustomerId(), customDTO.getProfit()));
        }
        double absoluteProfit=totalProfit-totalDiscount;

        lblProfit.setText(String.valueOf(Double.parseDouble(String.format("%.2f",totalProfit))));
        lblDiscount.setText(String.valueOf(Double.parseDouble(String.format("%.2f",totalDiscount))));
        lblAbsProfit.setText(String.valueOf(Double.parseDouble(String.format("%.2f",absoluteProfit))));

        movableChart.getData().addAll(set);
    }
}
