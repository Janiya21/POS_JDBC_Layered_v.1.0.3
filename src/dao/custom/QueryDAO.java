package dao.custom;

import dto.CustomDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO{

    ArrayList<CustomDTO> getDailyProfit(String date) throws SQLException, ClassNotFoundException;

    ArrayList<CustomDTO> getMonthlyProfit(String month) throws SQLException, ClassNotFoundException;

    ArrayList<CustomDTO> getCUSTOMERProfit() throws SQLException, ClassNotFoundException;
}
