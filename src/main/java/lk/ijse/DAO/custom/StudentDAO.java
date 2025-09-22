package lk.ijse.DAO.custom;

import lk.ijse.DAO.CrudDAO;
import lk.ijse.Entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface StudentDAO extends CrudDAO<Student> {
    public String getStudentIdByContact(String phone) throws SQLException;
    public String getStudentFirstNameById(String studentId) throws SQLException;
    public String getStudentLastNameById(String studentId) throws SQLException;
}