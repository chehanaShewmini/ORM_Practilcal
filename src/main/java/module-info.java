module lk.ijse{
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;
    requires static lombok;
    requires javafx.graphics;
    requires jbcrypt;

    // Hibernate & JavaFX reflection access
    opens lk.ijse.Entity to org.hibernate.orm.core, javafx.base;
    opens lk.ijse.tdm to javafx.base;

    // FXML controllers
    opens lk.ijse.controller to javafx.fxml;

    // Exports
    exports lk.ijse;
    exports lk.ijse.controller;
    exports lk.ijse.DTO;
    exports lk.ijse.tdm;
    exports lk.ijse.Entity;
}