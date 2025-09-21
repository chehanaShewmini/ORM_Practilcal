package lk.ijse.DAO.impl;

import lk.ijse.DAO.custom.StudentDAO;
import lk.ijse.Entity.Student;

import java.sql.SQLException;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    @Override
    public boolean save(Student entity) throws Exception {
        return false;
    }

    @Override
    public boolean update(Student entity) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String ID) throws Exception {
        return false;
    }

    @Override
    public List<Student> getAll() throws SQLException, ClassNotFoundException {
        return List.of();
    }

    @Override
    public Student searchByID(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public List<String> getIds() {
        return List.of();
    }
}
