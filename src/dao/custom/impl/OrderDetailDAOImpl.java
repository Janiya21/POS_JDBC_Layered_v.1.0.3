package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDetailDAO;
import dto.OrderDetailDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDAOImpl implements OrderDetailDAO {

    @Override
    public boolean add(OrderDetailDTO temp) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO `OrderDetail` VALUES(?,?,?,?,?)",temp.getOrderId(),temp.getItemCode(), temp.getOrderQty(),
                temp.getDiscount(),temp.getTotal());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException(".............");
    }

    @Override
    public boolean update(OrderDetailDTO orderDetailDTO) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException(".............");
    }

    @Override
    public OrderDetailDTO search(String s) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `OrderDetail` WHERE OrderId=?", s);
        OrderDetailDTO dto=null;
        while (rst.next()) {
            dto = new OrderDetailDTO(rst.getString(2),rst.getInt(4),rst.getInt(3),rst.getDouble(5));
        }
        return dto;
    }

    @Override
    public ArrayList<OrderDetailDTO> getAll(String s) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `OrderDetail` WHERE OrderId=?", s);
        ArrayList<OrderDetailDTO> dto = new ArrayList<>();
        while (rst.next()) {
            dto.add(new OrderDetailDTO(rst.getString(2),rst.getInt(4),rst.getInt(3),rst.getDouble(5)));
        }
        return dto;
    }
}
