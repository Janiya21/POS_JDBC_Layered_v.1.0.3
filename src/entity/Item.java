package entity;

import java.text.DecimalFormat;

public class Item {
    private String itemCode;
    private String description;
    private String  packSize;
    private int qtyOnHand;
    private Double unitProfit;
    private Double unitPrice;

    public Item(String itemCode, String description, String packSize, int qtyOnHand, Double unitProfit, Double unitPrice) {
        this.itemCode = itemCode;
        this.description = description;
        this.packSize = packSize;
        this.qtyOnHand = qtyOnHand;
        this.unitProfit = unitProfit;
        this.unitPrice = unitPrice;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackSize() {
        return packSize;
    }

    public void setPackSize(String packSize) {
        this.packSize = packSize;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public Double getUnitProfit() {
        return unitProfit;
    }

    public void setUnitProfit(Double unitProfit) {
        this.unitProfit = unitProfit;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
