package entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name = "orderDetail")
public class OrderDetail {
    @Id
    private String orderId;
    private String itemCode;
    private int qty;
    private double discount;
    private double itemTotal;

    @ManyToOne
    private Orders orders;

    @ManyToOne
    private Item item;

    public OrderDetail(String orderId, String itemCode, int qty, double discount, double itemTotal) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.qty = qty;
        this.discount = discount;
        this.itemTotal = itemTotal;
    }

    public OrderDetail(String orderId, String itemCode, int qty, double discount, double itemTotal, Orders orders, Item item) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.qty = qty;
        this.discount = discount;
        this.itemTotal = itemTotal;
        this.orders = orders;
        this.item = item;
    }

    public OrderDetail() {

    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(double itemTotal) {
        this.itemTotal = itemTotal;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
