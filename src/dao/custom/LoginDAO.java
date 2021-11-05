package dao.custom;

import dto.UserDetailDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LoginDAO extends SuperDAO{
    ArrayList<UserDetailDTO> getAllUsers(String accountType) throws SQLException, ClassNotFoundException;
}
