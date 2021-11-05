package bo.custom.impl;

import bo.custom.OrderHistoryBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailDAO;
import dto.CustomerDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderHistoryBOImpl implements OrderHistoryBO {

    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDAO);
    private final OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILDAO);
    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMERDAO);

    @Override
    public ArrayList<OrderDTO> getAllOrders() throws SQLException, ClassNotFoundException {
        return orderDAO.getAll("<->");
    }

    @Override
    public ArrayList<OrderDetailDTO> getAllItems(String orderId) throws SQLException, ClassNotFoundException {
        return orderDetailDAO.getAll(orderId);
    }

    @Override
    public CustomerDTO searchCustomer(String customerId) throws SQLException, ClassNotFoundException {
        Customer c = customerDAO.search(customerId);
        return new CustomerDTO(c.getCustId(),c.getCustTitle(),c.getCustName(),c.getCustAddress(),c.getCustCity(),c.getCustProvince(),c.getCustPostalCode());
    }
}
