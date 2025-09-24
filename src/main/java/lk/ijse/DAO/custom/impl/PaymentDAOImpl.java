package lk.ijse.DAO.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.DAO.custom.PaymentDAO;
import lk.ijse.DTO.PaymentDTO;
import lk.ijse.Entity.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentDAOImpl implements PaymentDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();
    private static final List<PaymentDTO> paymentList = new ArrayList<>();

    @Override
    public String getNextId() throws SQLException {
        Session session = factoryConfiguration.getSession();
        try {
            String lastId = (String) session.createQuery(
                            "SELECT p.paymentId FROM Payment p ORDER BY p.paymentId DESC")
                    .setMaxResults(1)
                    .uniqueResult();

            if (lastId != null) {
                int num = Integer.parseInt(lastId.substring(1)); // remove 'P' prefix
                num++;
                return String.format("P%03d", num);
            } else {
                return "P001";
            }
        } finally {
            session.close();
        }
    }

    @Override
    public List<Payment> getAll() throws SQLException {
        Session session = factoryConfiguration.getSession();
        try {
            Query<Payment> query = session.createQuery("FROM Payment", Payment.class);
            return query.list();
        } finally {
            session.close();
        }
    }

    @Override
    public String getLastId() throws SQLException {
        Session session = factoryConfiguration.getSession();
        try {
            Query<String> query = session.createQuery(
                    "SELECT p.paymentId FROM Payment p ORDER BY p.paymentId DESC",
                    String.class
            ).setMaxResults(1);

            List<String> idList = query.list();
            if (idList.isEmpty()) {
                return null;
            }
            return idList.get(0); // ✅ fixed (instead of getFirst())
        } finally {
            session.close();
        }
    }

    @Override
    public boolean save(Payment payment) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            payment.setPaymentId(getNextId());
            session.persist(payment);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Payment payment) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(payment);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(String id) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Payment payment = session.get(Payment.class, id);
            if (payment != null) {
                session.remove(payment);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<Payment> findById(String id) throws SQLException {
        Session session = factoryConfiguration.getSession();
        try {
            Payment payment = session.get(Payment.class, id);
            return Optional.ofNullable(payment);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Payment> search(String search) throws SQLException {
        String searchText = "%" + search + "%";
        try (Session session = factoryConfiguration.getSession()) {
            Query<Payment> query = session.createQuery(
                    "FROM Payment p " +
                            "WHERE p.paymentId LIKE :search OR " +
                            "p.paymentDate LIKE :search OR " +
                            "p.amount LIKE :search OR " +
                            "p.paymentMethod LIKE :search OR " +
                            "p.status LIKE :search",
                    Payment.class
            );
            query.setParameter("search", searchText);
            return query.list();
        }
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        Session session = factoryConfiguration.getSession();
        try {
            Query<String> query = session.createQuery("SELECT p.paymentId FROM Payment p", String.class);
            return query.list();
        } finally {
            session.close();
        }
    }

    public List<Payment> getByStudentId(String studentId) throws SQLException {
        Session session = factoryConfiguration.getSession();
        try {
            Query<Payment> query = session.createQuery(
                    "FROM Payment p WHERE p.student.studentId = :studentId", Payment.class);
            query.setParameter("studentId", studentId);
            return query.list();
        } finally {
            session.close();
        }
    }

    @Override
    public boolean savePayment(PaymentDTO payment) throws SQLException {
        return paymentList.add(payment);
    }

    @Override
    public List<PaymentDTO> getPaymentByStudent(String studentId) throws SQLException {
        List<PaymentDTO> result = new ArrayList<>();
        for (PaymentDTO paymentDTO : paymentList) {
            if (paymentDTO.getStudentId().equals(studentId)) {
                result.add(paymentDTO);
            }
        }
        return result;
    }
}
