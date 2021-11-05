package bo.custom.impl;

import bo.custom.OrderCheckOutBO;
import dao.DAOFactory;
import dao.custom.*;
import db.DBConnection;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import entity.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderCheckOutBOImpl implements OrderCheckOutBO {

    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDAO);
    private final OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILDAO);
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEMDAO);

    public OrderCheckOutBOImpl() {

    }

    @Override
    public boolean addItemToOrder(OrderDTO odt) throws SQLException, ClassNotFoundException {

        Connection con= DBConnection.getDbConnection().getConnection();
        con.setAutoCommit(false);

        boolean added = orderDAO.add(odt);

        if (added){
            if (saveToOrderDetail(odt.getOrderDetailDTOS())){
                con.commit();
                return true;
            }else{
                con.rollback();
                return false;
            }
        }else{
            con.rollback();
            return false;
        }
    }
    public boolean saveToOrderDetail(ArrayList<OrderDetailDTO> ord) throws SQLException, ClassNotFoundException {
        for (OrderDetailDTO temp : ord) {
            boolean ok = orderDetailDAO.add(temp);
            if (ok) {
                if(!updateQty(temp.getItemCode(), temp.getOrderQty())){
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }
    public boolean updateQty(String itemCode, int qty) throws SQLException, ClassNotFoundException {
        return itemDAO.updateQty(itemCode,qty);
    }

    @Override
    public List<String> getAllItemIds() throws SQLException, ClassNotFoundException {
        return itemDAO.getAllItemIds();
    }

    @Override
    public String getOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.getOrderId();
    }

    @Override
    public ItemDTO searchItem(String itemCode) throws SQLException, ClassNotFoundException {
        Item i = itemDAO.search(itemCode);
        return new ItemDTO(i.getItemCode(),i.getDescription(),i.getPackSize(),i.getQtyOnHand(),i.getUnitProfit(),i.getUnitPrice());
    }
}
