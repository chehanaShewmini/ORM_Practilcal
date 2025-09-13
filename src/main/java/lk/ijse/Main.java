package lk.ijse;

import lk.ijse.Entity.User;
import lk.ijse.config.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
Session session = FactoryConfiguration.getInstance().getSession();
Transaction ysx = session.beginTransaction();

    }
}