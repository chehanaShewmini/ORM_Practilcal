package lk.ijse.BO.custom;

import lk.ijse.BO.SuperBO;
import lk.ijse.DTO.CourseDTO;
import lk.ijse.Entity.Course;
import lk.ijse.Entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface CourseBO extends SuperBO {
    public String getNextId() throws SQLException;
    public List<CourseDTO> getAll()throws SQLException;
    public String getLastId() throws SQLException;
    public boolean save(CourseDTO courseDTO)throws SQLException;
    public boolean update(CourseDTO courseDTO)throws SQLException;
    public boolean delete(String id)throws SQLException;
    public List<String> getAllIds()throws SQLException;
    public Optional<CourseDTO> findById(String id)throws SQLException;
    public List<CourseDTO> search(String search)throws SQLException;
    public boolean saveNewCourse(CourseDTO courseDTO)throws SQLException;
}
