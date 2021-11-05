package dto;

public class OrderDetailDTO {
    private String itemCode;
    private String orderId;
    private double discount;
    private int orderQty;
    private double total;

    public OrderDetailDTO() {

    }

    public OrderDetailDTO(String itemCode, double discount, int orderQty, double total) {
        this.itemCode = itemCode;
        this.discount = discount;
        this.orderQty = orderQty;
        this.total = total;
    }

    public OrderDetailDTO(String itemCode, String orderId, double discount, int orderQty, double total) {
        this.itemCode = itemCode;
        this.orderId = orderId;
        this.discount = discount;
        this.orderQty = orderQty;
        this.total = total;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "itemCode='" + itemCode + '\'' +
                ", orderId='" + orderId + '\'' +
                ", discount=" + discount +
                ", orderQty=" + orderQty +
                ", total=" + total +
                '}';
    }
}
