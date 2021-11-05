package dto;

import java.util.Objects;

public class ItemDTO {
    private String code;
    private String description;
    private String packageSize;
    private int qtyOnHand;
    private double unitProfit;
    private double unitPrice;

    public ItemDTO(){}

    public ItemDTO(String code, String description, String packageSize, int qtyOnHand, double unitProfit, double unitPrice) {
        this.code = code;
        this.description = description;
        this.packageSize = packageSize;
        this.qtyOnHand = qtyOnHand;
        this.unitProfit = unitProfit;
        this.unitPrice = unitPrice;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackageSize() {
        return packageSize;
    }

    public void setPackageSize(String packageSize) {
        this.packageSize = packageSize;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getUnitProfit() {
        return unitProfit;
    }

    public void setUnitProfit(double unitProfit) {
        this.unitProfit = unitProfit;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "Item{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", packageSize='" + packageSize + '\'' +
                ", qtyOnHand=" + qtyOnHand +
                ", unitProfit=" + unitProfit +
                ", unitPrice=" + unitPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDTO itemDTO = (ItemDTO) o;
        return qtyOnHand == itemDTO.qtyOnHand &&
                Double.compare(itemDTO.unitProfit, unitProfit) == 0 &&
                Double.compare(itemDTO.unitPrice, unitPrice) == 0 &&
                Objects.equals(code, itemDTO.code) &&
                Objects.equals(description, itemDTO.description) &&
                Objects.equals(packageSize, itemDTO.packageSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, description, packageSize, qtyOnHand, unitProfit, unitPrice);
    }
}
