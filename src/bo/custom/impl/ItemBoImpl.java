package bo.custom.impl;

import bo.custom.ItemBO;
import dao.DAOFactory;
import dao.custom.ItemDAO;
import dto.ItemDTO;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBoImpl implements ItemBO {
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEMDAO);

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> itemDTOS = new ArrayList<>();
        ArrayList<Item> itemDAOAll = itemDAO.getAll("<->");
        for (Item i:itemDAOAll) {
            itemDTOS.add(new ItemDTO(i.getItemCode(),i.getDescription(),i.getPackSize(),i.getQtyOnHand(),i.getUnitProfit(),i.getUnitPrice()));
        }
        return itemDTOS;
    }

    @Override
    public boolean addItem(ItemDTO i) throws SQLException, ClassNotFoundException {
        return itemDAO.add(new Item(i.getCode(),i.getDescription(),i.getPackageSize(),i.getQtyOnHand(),i.getUnitProfit(),i.getUnitPrice()));
    }

    @Override
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(code);
    }

    @Override
    public boolean updateItem(ItemDTO i) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(i.getCode(),i.getDescription(),i.getPackageSize(),i.getQtyOnHand(),i.getUnitProfit(),i.getUnitPrice()));
    }
}
