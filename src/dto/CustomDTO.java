package dto;

public class CustomDTO{
    private String itemCode;
    private String desc;
    private String customerId;
    private int qty;
    private double unitProfit;
    private double discount;
    private double profit;

    public CustomDTO(String customerId, int qty, double discount, double profit) {
        this.customerId = customerId;
        this.qty = qty;
        this.discount = discount;
        this.profit = profit;
    }

    public CustomDTO(String itemCode, String desc, int qty, double unitProfit, double discount, double profit) {
        this.itemCode = itemCode;
        this.desc = desc;
        this.qty = qty;
        this.unitProfit = unitProfit;
        this.discount = discount;
        this.profit = profit;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitProfit() {
        return unitProfit;
    }

    public void setUnitProfit(double unitProfit) {
        this.unitProfit = unitProfit;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
