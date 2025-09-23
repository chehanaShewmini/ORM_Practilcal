package lk.ijse.DAO.custom;

import lk.ijse.DAO.CrudDAO;
import lk.ijse.Entity.Course;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.Optional;

public interface CourseDAO extends CrudDAO<Course> {
    public int getEnrollmentCount(String courseId) throws Exception;
    public boolean enrollStudent(String courseId, String studentId) throws Exception;
}