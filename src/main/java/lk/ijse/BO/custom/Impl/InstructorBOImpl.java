package lk.ijse.BO.custom.Impl;

import lk.ijse.BO.custom.CourseBO;
import lk.ijse.BO.custom.InstructorBO;
import lk.ijse.BO.exceptionHandling.DuplicateException;
import lk.ijse.BO.exceptionHandling.NotFoundException;
import lk.ijse.BO.utils.EntityDTOConverter;
import lk.ijse.DAO.DAOFactory;
import lk.ijse.DAO.DAOTypes;
import lk.ijse.DAO.custom.CourseDAO;
import lk.ijse.DAO.custom.InstructorDAO;
import lk.ijse.DTO.CourseDTO;
import lk.ijse.DTO.InstructorDTO;
import lk.ijse.Entity.Course;
import lk.ijse.Entity.Instructor;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InstructorBOImpl implements InstructorBO {
    private final InstructorDAO instructorDAO = DAOFactory.getInstance().getDAO(DAOTypes.INSTRUCTOR);
    private final CourseDAO courseDAO = DAOFactory.getInstance().getDAO(DAOTypes.COURSE);
    private final EntityDTOConverter converter = new EntityDTOConverter();
    @Override
    public String getNextId() throws SQLException {
        return instructorDAO.getNextId();
    }

    @Override
    public List<InstructorDTO> getAll() throws SQLException {
        List<Instructor> instructors = instructorDAO.getAll();
        List<InstructorDTO> instructorDTOS = new ArrayList<>();
        for (Instructor instructor : instructors) {
            instructorDTOS.add(converter.getInstructorDTO(instructor));
        }
        return instructorDTOS;
    }

    @Override
    public String getLastId() throws SQLException {
        return instructorDAO.getLastId();
    }

    @Override
    public boolean save(InstructorDTO instructorDTO) throws SQLException {
        Optional<Instructor> instructor = instructorDAO.findById(instructorDTO.getInstructorId());
        if (instructor.isPresent()) {
            throw new DuplicateException("instructor already exists");
        }
        return instructorDAO.save(converter.getInstructor(instructorDTO));
    }

    @Override
    public boolean update(InstructorDTO instructorDTO) throws SQLException {
        Optional<Instructor> instructor = instructorDAO.findById(instructorDTO.getInstructorId());
        if(instructor.isEmpty()){
            throw new NotFoundException("instructor not found");
        }

        return instructorDAO.update(converter.getInstructor(instructorDTO));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        Optional<Instructor> instructor = instructorDAO.findById(id);
        if(instructor.isEmpty()){
            throw new NotFoundException("instructor not found");
        }
        return instructorDAO.delete(id);
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return instructorDAO.getAllIds();
    }

    @Override
    public Optional<InstructorDTO> findById(String id) throws SQLException {
        Optional<Instructor> instructor = instructorDAO.findById(id);
        if (instructor.isPresent()) {
            return Optional.of(converter.getInstructorDTO(instructor.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<InstructorDTO> search(String search) throws SQLException {
        ArrayList<Instructor> instructors = (ArrayList<Instructor>) instructorDAO.search(search);
        List<InstructorDTO> instructorDTOS = new ArrayList<>();
        for (Instructor instructor : instructors) {
            instructorDTOS.add(converter.getInstructorDTO(instructor));
        }
        return instructorDTOS;
    }

    public boolean assignCourse(String instructorId, String courseId) throws Exception {
        Course course = courseDAO.get(courseId);
        if (course != null) {
            Instructor instructor = instructorDAO.get(instructorId);
            if (instructor == null) {
                throw new RuntimeException("Instructor not found with ID: " + instructorId);
            }

            course.setInstructor(instructor);
            return courseDAO.update(course);
        }
        return false;
    }


    @Override
    public List<CourseDTO> getCoursesByInstructor(String instructorId) throws SQLException {
        List<Course> courses = instructorDAO.getCoursesByInstructor(instructorId);
        return courses.stream().map(converter::getCourseDTO).collect(Collectors.toList());
    }

    @Override
    public int getEnrollmentCount(String courseId) throws Exception {
        return courseDAO.getEnrollmentCount(courseId);
    }

}