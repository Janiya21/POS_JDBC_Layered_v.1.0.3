package dao.custom.impl;

import dao.custom.QueryDAO;
import db.DBConnection;
import dto.CustomDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public ArrayList<CustomDTO> getDailyProfit(String date) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getDbConnection().getConnection().prepareStatement("SELECT item.itemCode, item.description," +
                " SUM(OrderDetail.orderQty), item.unitProfit, OrderDetail.discount\n" +
                "FROM item JOIN OrderDetail ON item.itemCode=orderDetail.itemCode JOIN orders ON orderDetail.orderId=orders.orderId\n" +
                "WHERE orderDate=? GROUP BY item.itemCode;");
        stm.setObject(1, date);

        ResultSet rst = stm.executeQuery();

        ArrayList<CustomDTO> customDTOS = new ArrayList<>();
        while (rst.next()) {
            customDTOS.add(new CustomDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4),
                    rst.getDouble(5),
                    (rst.getInt(3)*rst.getDouble(4))-rst.getDouble(5)
            ));
        }
        return customDTOS;
    }

    @Override
    public ArrayList<CustomDTO> getMonthlyProfit(String month) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getDbConnection().getConnection().prepareStatement("SELECT item.itemCode, item.description," +
                " SUM(OrderDetail.orderQty), item.unitProfit, OrderDetail.discount\n" +
                "FROM item JOIN OrderDetail ON item.itemCode=orderDetail.itemCode JOIN orders ON orderDetail.orderId=orders.orderId\n" +
                "WHERE orderDate BETWEEN '2021-"+month+"-1' AND '2021-"+month+"-31' GROUP BY item.itemCode;");

        ResultSet rst = stm.executeQuery();

        ArrayList<CustomDTO> customDTOS = new ArrayList<>();
        while (rst.next()) {
            customDTOS.add(new CustomDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4),
                    rst.getDouble(5),
                    (rst.getInt(3)*rst.getDouble(4))-rst.getDouble(5)
            ));
        }
        return customDTOS;
    }

    @Override
    public ArrayList<CustomDTO> getCUSTOMERProfit() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getDbConnection().getConnection().prepareStatement("SELECT DISTINCT orders.cId, OrderDetail.orderQty," +
                "OrderDetail.discount, orderDetail.orderedItemTotal\n" +
                "FROM item JOIN OrderDetail ON item.itemCode=orderDetail.itemCode JOIN orders ON orderDetail.orderId=orders.orderId\n" +
                "GROUP BY orders.cId;");

        ResultSet rst = stm.executeQuery();

        ArrayList<CustomDTO> customDTOS = new ArrayList<>();
        while (rst.next()) {
            customDTOS.add(new CustomDTO(
                    rst.getString(1),
                    rst.getInt(2),
                    rst.getDouble(3),
                    rst.getDouble(4)
            ));
        }
        return customDTOS;
    }
}
