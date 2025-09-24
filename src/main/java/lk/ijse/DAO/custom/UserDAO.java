package lk.ijse.DAO.custom;

import lk.ijse.DAO.CrudDAO;
import lk.ijse.DTO.UserDTO;
import lk.ijse.Entity.Student;
import lk.ijse.Entity.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserDAO extends CrudDAO<User> {
    public User getUserByName(String userName) throws SQLException;

}