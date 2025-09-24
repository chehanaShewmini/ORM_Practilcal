package lk.ijse.DAO.custom;

import lk.ijse.DAO.CrudDAO;
import lk.ijse.DTO.PaymentDTO;
import lk.ijse.Entity.Lessons;
import lk.ijse.Entity.Payment;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PaymentDAO extends CrudDAO<Payment> {
    public List<Payment> getByStudentId(String studentId) throws SQLException;
    public boolean savePayment(PaymentDTO payment) throws SQLException;
    public  List<PaymentDTO> getPaymentByStudent(String studentId) throws SQLException;
}