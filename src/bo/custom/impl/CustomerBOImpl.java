package bo.custom.impl;

import bo.custom.CustomerBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dto.CustomerDTO;
import entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMERDAO);

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();
        ArrayList<Customer> all = customerDAO.getAll("<->");
        for (Customer c : all) {
            customerDTOS.add(new CustomerDTO(c.getCustId(),c.getCustTitle(),c.getCustName(),c.getCustAddress(),c.getCustCity(),
                    c.getCustProvince(),c.getCustPostalCode()));
        }
        return customerDTOS;
    }

    @Override
    public boolean addCustomer(CustomerDTO c1) throws SQLException, ClassNotFoundException {
        return customerDAO.add(new Customer(c1.getId(),c1.getTitle(),c1.getName(),c1.getAddress(),c1.getCity(),c1.getProvince(),c1.getPostalCode()));
    }

    @Override
    public boolean deleteCustomer(String customerId) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(customerId);
    }

    @Override
    public boolean updateCustomer(CustomerDTO c1) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(c1.getId(),c1.getTitle(),c1.getName(),c1.getAddress(),c1.getCity(),c1.getProvince(),c1.getPostalCode()));
    }
}
