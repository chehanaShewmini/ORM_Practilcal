package lk.ijse.DAO.custom;

import lk.ijse.DAO.CrudDAO;
import lk.ijse.DTO.CourseDTO;
import lk.ijse.Entity.Course;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CourseDAO extends CrudDAO<Course> {
    public int getEnrollmentCount(String courseId) throws Exception;
    public boolean enrollStudent(String courseId, String studentId) throws Exception;
    public Course get(String id) throws Exception;
    public CourseDTO getCourseByName(String courseName) throws Exception;

}