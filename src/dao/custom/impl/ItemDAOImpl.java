package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ItemDAO;
import db.DBConnection;
import dto.ItemDTO;
import entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public List<String> getAllItemIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getDbConnection().getConnection().prepareStatement("SELECT * FROM Item").executeQuery();
        List<String> ids= new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    @Override
    public boolean add(Item i) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Item VALUES(?,?,?,?,?,?)",i.getItemCode(),i.getDescription(),i.getPackSize(),i.getQtyOnHand(),
        i.getUnitProfit(),i.getUnitPrice());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Item WHERE itemCode=?",s);
    }

    @Override
    public boolean update(Item i) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Item SET description=?, packSize=?, qtyOnHand=?, unitProfit=?, unitPrice=? WHERE ItemCode=?",
                i.getDescription(),i.getPackSize(),i.getQtyOnHand(),i.getUnitProfit(),i.getUnitPrice(),i.getItemCode());
    }

    @Override
    public Item search(String s) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item WHERE itemCode=?", s);
        if (rst.next()) {
            return new Item(
                    rst.getString(1), rst.getString(2), rst.getString(3),
                    rst.getInt(4), rst.getDouble(5), rst.getDouble(6)
            );
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<Item> getAll(String s) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item");
        ArrayList<Item> itemDTOS = new ArrayList<>();
        while (rst.next()) {
            itemDTOS.add(new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getDouble(5),
                    rst.getDouble(6)
            ));
        }
        return itemDTOS;
    }

    @Override
    public boolean updateQty(String itemCode, int qty) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE ITEM SET qtyOnHand=(qtyOnHand-?) WHERE itemCode=?",qty,itemCode);
    }
}
