package lk.ijse.DAO.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.DAO.custom.CourseDAO;
import lk.ijse.Entity.Course;
import lk.ijse.Entity.Lessons;
import lk.ijse.Entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseDAOImpl implements CourseDAO {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    public String getNextId() {
        Session session = factoryConfiguration.getSession();
        String lastId = (String) session.createQuery(
                        "SELECT c.courseId FROM Course c ORDER BY c.courseId DESC")
                .setMaxResults(1)
                .uniqueResult();

        if (lastId != null) {
            int num = Integer.parseInt(lastId.substring(1));
            num++;
            return String.format("C%03d", num);
        } else {
            return "C001";
        }
    }

    public List<Course> getAll()throws SQLException{
        Session session = factoryConfiguration.getSession();
        try {
            Query<Course> query = session.createQuery("from Course ",Course.class);
            List<Course> courseList = query.list();
            return courseList;
        }finally {
            session.close();
        }
    }

    public String getLastId(){
        Session session = factoryConfiguration.getSession();
        try {
            Query<String> query = session.createQuery(
                    "SELECT c.courseId FROM Course c ORDER BY c.courseId DESC " ,
                    String.class
            ).setMaxResults(1);
            List<String> idList = query.list();
            if (idList.isEmpty()) {
                return null;
            }
            return idList.get(1);

        }finally {
            session.close();
        }
    }

    public boolean save(Course course)throws SQLException{
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(course);
            transaction.commit();
            return true;
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }


    public boolean update(Course course)throws SQLException{
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(course);
            transaction.commit();
            return true;
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String id)throws SQLException{
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            Student student = session.get(Student.class, id);
            if (student != null) {
                session.remove(student);
                transaction.commit();
                return true;
            }
            return false;
        }catch (Exception e){
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }finally {
            session.close();
        }
    }

    public List<String> getAllIds()throws SQLException{
        Session session = factoryConfiguration.getSession();
        try {
            Query<String> query = session.createQuery("SELECT c.courseId FROM Course c", String.class);
            return query.list();
        } finally {
            session.close();
        }

    }

    public Optional<Course> findById(String id)throws SQLException{
        Session session = factoryConfiguration.getSession();

        try {
            Course course = session.get(Course.class, id);
            return Optional.ofNullable(course);
        }finally {
            session.close();
        }
    }

    public List<Course> search(String search)throws SQLException{
        String searchText="%"+search+"%";


        try(Session session = factoryConfiguration.getSession()) {
            Query<Course> query = session.createQuery("FROM Course c " +
                            "WHERE c.courseId LIKE :search OR " +
                            "c.courseName LIKE  :search OR " +
                            " c.duration LIKE  :search OR" +
                            " c.fee LIKE  :search OR" +
                            " c.description LIKE  :search " ,
                    Course.class
            );
            query.setParameter("search", searchText);

            return query.list();
        }
    }

    public boolean saveNewCourse(Course course)throws SQLException{
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(course);
            transaction.commit();
            return true;
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }
}