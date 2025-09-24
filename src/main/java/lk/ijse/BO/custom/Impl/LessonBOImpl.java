package lk.ijse.BO.custom.Impl;

import lk.ijse.BO.custom.LessonBO;
import lk.ijse.BO.exceptionHandling.DuplicateException;
import lk.ijse.BO.exceptionHandling.NotFoundException;
import lk.ijse.BO.utils.EntityDTOConverter;
import lk.ijse.DAO.DAOFactory;
import lk.ijse.DAO.DAOTypes;
import lk.ijse.DAO.custom.LessonDAO;
import lk.ijse.DTO.LessonsDTO;
import lk.ijse.Entity.Lessons;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LessonBOImpl implements LessonBO {
    private final LessonDAO lessonDAO = DAOFactory.getInstance().getDAO(DAOTypes.LESSONS);
    private final EntityDTOConverter converter = new EntityDTOConverter();

    @Override
    public String getNextId() throws SQLException {
        return lessonDAO.getNextId();
    }

    @Override
    public List<LessonsDTO> getAll() throws SQLException {
        List<Lessons> lessons = lessonDAO.getAll();
        List<LessonsDTO> lessonsDTOS = new ArrayList<>();
        for (Lessons lesson : lessons) {
            lessonsDTOS.add(converter.getLessonsDTO(lesson));
        }
        return lessonsDTOS;
    }

    @Override
    public String getLastId() throws SQLException {
        return lessonDAO.getLastId();
    }

    @Override
    public boolean save(LessonsDTO lessonsDTO) throws SQLException {
        Optional<Lessons> lessons = lessonDAO.findById(lessonsDTO.getLessonId());
        if (lessons.isPresent()) {
            throw new DuplicateException("lesson already exists");
        }
        return lessonDAO.save(converter.getLessons(lessonsDTO));
    }

    @Override
    public boolean update(LessonsDTO lessonsDTO) throws SQLException {
        Optional<Lessons> lessons = lessonDAO.findById(lessonsDTO.getLessonId());
        if (lessons.isEmpty()){
            throw new NotFoundException("lesson not found");
        }
        return lessonDAO.update(converter.getLessons(lessonsDTO));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        Optional<Lessons> lessons = lessonDAO.findById(id);
        if (lessons.isEmpty()){
            throw new NotFoundException("lesson not found");
        }
        return lessonDAO.delete(id);
    }

    @Override
    public Optional<LessonsDTO> findById(String id) throws SQLException {
        Optional<Lessons> lessons = lessonDAO.findById(id);
        if (lessons.isPresent()){
            return Optional.of(converter.getLessonsDTO(lessons.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<LessonsDTO> search(String search) throws SQLException {
        ArrayList<Lessons> lessons = (ArrayList<Lessons>) lessonDAO.search(search);
        List<LessonsDTO> lessonsDTOS = new ArrayList<>();
        for (Lessons lesson : lessons) {
            lessonsDTOS.add(converter.getLessonsDTO(lesson));
        }
        return lessonsDTOS;
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return lessonDAO.getAllIds();
    }

    @Override
    public boolean scheduleLesson(LessonsDTO lessonsDTO) throws SQLException {
        return lessonDAO.save(converter.getLessons(lessonsDTO));
    }

    @Override
    public boolean rescheduleLesson(String lessonId, LessonsDTO dto) throws SQLException {

        Lessons lesson = (Lessons) lessonDAO.search(lessonId);
        if (lesson == null) {
            throw new SQLException("Lesson not found with ID: " + lessonId);
        }


        lesson.setLessonDate(dto.getLessonDate());
        lesson.setStartTime(dto.getStartTime());
        lesson.setEndTime(dto.getEndTime());


        return lessonDAO.update(lesson);
    }

}