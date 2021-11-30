package entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "orders")
public class Orders {
    @Id
    private String orderId;
    private String cId;
    private Date date;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "orders")
    private List<OrderDetail> orderList = new ArrayList<>();

    public Orders(String orderId, String cId, Date date) {
        this.orderId = orderId;
        this.cId = cId;
        this.date = date;
    }

    public Orders(String orderId, String cId, Date date, Customer customer) {
        this.orderId = orderId;
        this.cId = cId;
        this.date = date;
        this.customer = customer;
    }

    public Orders(String orderId, String cId, Date date, Customer customer, List<OrderDetail> orderList) {
        this.orderId = orderId;
        this.cId = cId;
        this.date = date;
        this.customer = customer;
        this.orderList = orderList;
    }

    public Orders() {

    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderDetail> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderDetail> orderList) {
        this.orderList = orderList;
    }
}
