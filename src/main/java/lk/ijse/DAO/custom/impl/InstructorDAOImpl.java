package lk.ijse.DAO.custom.impl;

import lk.ijse.DAO.custom.InstructorDAO;
import lk.ijse.Entity.Course;
import lk.ijse.Entity.Instructor;
import lk.ijse.config.FactoryConfiguration;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class InstructorDAOImpl implements InstructorDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    // ================= ID HANDLING =================

    @Override
    public String getLastId() throws SQLException {
        try (Session session = factoryConfiguration.getSession()) {
            Query<String> query = session.createQuery(
                    "SELECT i.instructorId FROM Instructor i ORDER BY i.instructorId DESC",
                    String.class
            ).setMaxResults(1);

            return query.uniqueResultOptional().orElse(null);
        }
    }

    @Override
    public String getNextId() throws SQLException {
        String lastId = getLastId();

        if (lastId != null) {
            int num = Integer.parseInt(lastId.substring(1)); // remove "I"
            num++;
            return String.format("I%03d", num);
        } else {
            return "I001";
        }
    }

    // ================= CRUD =================

    @Override
    public List<Instructor> getAll() throws SQLException {
        try (Session session = factoryConfiguration.getSession()) {
            return session.createQuery("FROM Instructor", Instructor.class).list();
        }
    }

    @Override
    public boolean save(Instructor instructor) throws SQLException {
        Transaction tx = null;
        try (Session session = factoryConfiguration.getSession()) {
            tx = session.beginTransaction();
            session.persist(instructor);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Instructor instructor) throws SQLException {
        Transaction tx = null;
        try (Session session = factoryConfiguration.getSession()) {
            tx = session.beginTransaction();
            session.merge(instructor);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String id) throws SQLException {
        Transaction tx = null;
        try (Session session = factoryConfiguration.getSession()) {
            tx = session.beginTransaction();
            Instructor instructor = session.get(Instructor.class, id);
            if (instructor != null) {
                session.remove(instructor);
                tx.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<Instructor> findById(String id) throws SQLException {
        try (Session session = factoryConfiguration.getSession()) {
            Instructor instructor = session.get(Instructor.class, id);
            return Optional.ofNullable(instructor);
        }
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        try (Session session = factoryConfiguration.getSession()) {
            return session.createQuery("SELECT i.instructorId FROM Instructor i", String.class).list();
        }
    }

    // ================= SEARCH =================

    @Override
    public List<Instructor> search(String search) throws SQLException {
        String searchText = "%" + search + "%";
        try (Session session = factoryConfiguration.getSession()) {
            Query<Instructor> query = session.createQuery(
                    "FROM Instructor i " +
                            "WHERE i.instructorId LIKE :search OR " +
                            "i.firstName LIKE :search OR " +
                            "i.lastName LIKE :search OR " +
                            "i.email LIKE :search OR " +
                            "i.phone LIKE :search OR " +
                            "i.specialization LIKE :search OR " +
                            "i.availability_schedule LIKE :search",
                    Instructor.class
            );
            query.setParameter("search", searchText);
            return query.list();
        }
    }

    // ================= RELATIONSHIPS =================

    @Override
    public List<Course> getCoursesByInstructor(String instructorId) throws SQLException {
        Transaction tx = null;
        try (Session session = factoryConfiguration.getSession()) {
            tx = session.beginTransaction();
            List<Course> courses = session.createQuery(
                    "FROM Course c WHERE c.instructor.instructorId = :instructorId",
                    Course.class
            ).setParameter("instructorId", instructorId).list();

            // Ensure lazy collections are initialized
            for (Course c : courses) {
                Hibernate.initialize(c.getStudents());
            }

            tx.commit();
            return courses;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public Instructor get(String instructorId) throws Exception {
        try (Session session = factoryConfiguration.getSession()) {
            Transaction tx = session.beginTransaction();
            Instructor instructor = session.get(Instructor.class, instructorId);
            tx.commit();
            return instructor;
        }
    }
}
