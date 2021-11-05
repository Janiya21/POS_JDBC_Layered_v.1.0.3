package dao;

import dao.custom.SuperDAO;
import dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private  DAOFactory() {
    }

    public static DAOFactory getDaoFactory(){
        if (daoFactory==null) {
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }

    public enum DAOTypes{
        CUSTOMERDAO, ITEMDAO, LOGINDAO, ORDERDAO, ORDERDETAILDAO, QUERYDAO
    }

    public SuperDAO getDAO(DAOTypes types){
        switch (types){
            case ITEMDAO:
                return new ItemDAOImpl();
            case LOGINDAO:
                return new LoginDAOImpl();
            case ORDERDAO:
                return new OrderDAOImpl();
            case QUERYDAO:
                return new QueryDAOImpl();
            case CUSTOMERDAO:
                return new CustomerDAOImpl();
            case ORDERDETAILDAO:
                return new OrderDetailDAOImpl();
            default:
                return null;
        }
    }
}
