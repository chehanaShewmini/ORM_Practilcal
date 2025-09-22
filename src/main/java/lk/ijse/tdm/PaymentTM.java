package lk.ijse.tdm;


import lombok.*;

import java.sql.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class PaymentTM {
    private String paymentId;
    private String paymentDate;
    private double amount;
    private String paymentMethod;
    private String status;
    private String studentId;
}
