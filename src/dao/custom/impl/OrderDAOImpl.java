package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDAO;
import dto.OrderDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public String getOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT orderId FROM `Orders` ORDER BY orderId DESC LIMIT 1");
        if (rst.next()){
            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<=9){
                return "O-00"+tempId;
            }else if(tempId<99){
                return "O-0"+tempId;
            }else{
                return "O-"+tempId;
            }

        }else{
            return "O-001";
        }
    }

    @Override
    public ArrayList<OrderDTO> getAll(String s) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `Orders` ORDER BY orderId DESC");
        ArrayList<OrderDTO> ord = new ArrayList<>();
        while (rst.next()) {
            ord.add(new OrderDTO(rst.getString(1),rst.getString(2),rst.getString(3)));
        }
        return ord;
    }

    @Override
    public boolean add(OrderDTO odt) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO `Orders` VALUES(?,?,?)",odt.getOrderId(),odt.getCustomerId(),odt.getOrderDate());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException(".............");
    }

    @Override
    public boolean update(OrderDTO orderDTO) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException(".............");
    }

    @Override
    public OrderDTO search(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException(".............");
    }
}
