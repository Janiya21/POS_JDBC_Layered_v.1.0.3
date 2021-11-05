package bo.custom;

import bo.SuperBO;
import dto.ItemDTO;
import dto.OrderDTO;
import entity.Item;

import java.sql.SQLException;
import java.util.List;

public interface OrderCheckOutBO extends SuperBO {
    public boolean addItemToOrder(OrderDTO odt) throws SQLException, ClassNotFoundException;

    List<String> getAllItemIds() throws SQLException, ClassNotFoundException;

    String getOrderId() throws SQLException, ClassNotFoundException;

    ItemDTO searchItem(String itemCode) throws SQLException, ClassNotFoundException;
}
