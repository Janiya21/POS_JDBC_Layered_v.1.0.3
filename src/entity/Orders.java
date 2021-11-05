package entity;

import java.util.Date;

public class Orders {
    private String orderId;
    private String cId;
    private Date date;

    public Orders(String orderId, String cId, Date date) {
        this.orderId = orderId;
        this.cId = cId;
        this.date = date;
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
}
