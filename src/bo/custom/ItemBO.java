package bo.custom;

import bo.SuperBO;
import dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;

    boolean addItem(ItemDTO i1) throws SQLException, ClassNotFoundException;

    boolean deleteItem(String code) throws SQLException, ClassNotFoundException;

    boolean updateItem(ItemDTO i1) throws SQLException, ClassNotFoundException;
}
