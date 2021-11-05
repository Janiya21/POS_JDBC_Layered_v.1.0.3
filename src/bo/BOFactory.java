package bo;

import bo.custom.impl.CustomerBOImpl;
import bo.custom.impl.ItemBoImpl;
import bo.custom.impl.OrderCheckOutBOImpl;
import bo.custom.impl.OrderHistoryBOImpl;

public class BOFactory {

    private static BOFactory boFactory;

    public BOFactory() {
    }

    public static BOFactory getBoFactory(){
        if (boFactory==null) {
            boFactory=new BOFactory();
        }
        return boFactory;
    }

    public enum BoTypes{
        CUSTOMER,ITEM,ORDER_CHECKOUT,ORDER_HISTORY
    }

    public SuperBO getBO(BoTypes boTypes){
        switch (boTypes){
            case ITEM:
                return new ItemBoImpl();
            case CUSTOMER:
                return new CustomerBOImpl();
            case ORDER_HISTORY:
                return new OrderHistoryBOImpl();
            case ORDER_CHECKOUT:
                return new OrderCheckOutBOImpl();
            default:
                return null;
        }
    }
}
