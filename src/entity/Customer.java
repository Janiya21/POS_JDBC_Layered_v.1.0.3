package entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "customer")
public class Customer{
    @Id
    private String custId;
    private String custTitle;
    private String custName;
    private String custAddress;
    private String custCity;
    private String custProvince;
    private String custPostalCode;

    @OneToMany(mappedBy = "orders")
    private List<Orders> orderList = new ArrayList<>();

    public Customer(String custId, String custTitle, String custName, String custAddress, String custCity, String custProvince, String custPostalCode) {
        this.custId = custId;
        this.custTitle = custTitle;
        this.custName = custName;
        this.custAddress = custAddress;
        this.custCity = custCity;
        this.custProvince = custProvince;
        this.custPostalCode = custPostalCode;
    }

    public Customer(String custId, String custTitle, String custName, String custAddress, String custCity, String custProvince, String custPostalCode, List<Orders> orderList) {
        this.custId = custId;
        this.custTitle = custTitle;
        this.custName = custName;
        this.custAddress = custAddress;
        this.custCity = custCity;
        this.custProvince = custProvince;
        this.custPostalCode = custPostalCode;
        this.orderList = orderList;
    }

    public Customer() {

    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustTitle() {
        return custTitle;
    }

    public void setCustTitle(String custTitle) {
        this.custTitle = custTitle;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustCity() {
        return custCity;
    }

    public void setCustCity(String custCity) {
        this.custCity = custCity;
    }

    public String getCustProvince() {
        return custProvince;
    }

    public void setCustProvince(String custProvince) {
        this.custProvince = custProvince;
    }

    public String getCustPostalCode() {
        return custPostalCode;
    }

    public void setCustPostalCode(String custPostalCode) {
        this.custPostalCode = custPostalCode;
    }

    public List<Orders> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Orders> orderList) {
        this.orderList = orderList;
    }
}
