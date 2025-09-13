package lk.ijse.BO;

import lk.ijse.BO.Impl.UserBOImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory() {

    }

    public static BOFactory getBoFactory() {
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }
    public enum BoType{
        User, Student, Payment, Course, Student_Course,Login

    }
    public SuperBO getBo(BoType boType){
        switch (boType){

            case User:
                return new UserBOImpl();

            default:
                return null;

        }
    }
}
