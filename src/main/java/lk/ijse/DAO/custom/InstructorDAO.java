package lk.ijse.DAO.custom;

import lk.ijse.DAO.CrudDAO;
import lk.ijse.Entity.Course;
import lk.ijse.Entity.Instructor;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface InstructorDAO extends CrudDAO<Instructor> {
    public List<Course> getCoursesByInstructor(String instructorId) throws SQLException;
    Instructor get(String instructorId) throws Exception;
}