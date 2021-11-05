package dao.custom.impl;

import dao.custom.LoginDAO;
import db.DBConnection;
import dto.UserDetailDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginDAOImpl implements LoginDAO {
    @Override
    public ArrayList<UserDetailDTO> getAllUsers(String accountType) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getDbConnection().getConnection().prepareStatement("SELECT * FROM Users WHERE AccountType='"+accountType+"'");
        ResultSet rst = stm.executeQuery();
        ArrayList<UserDetailDTO> users = new ArrayList<>();
        while (rst.next()) {
            users.add(new UserDetailDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            ));
        }
        return users;
    }
}
