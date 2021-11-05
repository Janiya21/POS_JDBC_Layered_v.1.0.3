package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderHistoryBO extends SuperBO {
    ArrayList<OrderDTO> getAllOrders() throws SQLException, ClassNotFoundException;

    ArrayList<OrderDetailDTO> getAllItems(String orderId) throws SQLException, ClassNotFoundException;

    CustomerDTO searchCustomer(String customerId) throws SQLException, ClassNotFoundException;
}
