package lk.ijse.BO.Impl;

import lk.ijse.BO.custom.UserBO;
import lk.ijse.DAO.DAOFactory;
import lk.ijse.DAO.custom.UserDAO;
import lk.ijse.DTO.UserDTO;
import lk.ijse.Entity.User;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DaoType.User);
    @Override
    public boolean save(UserDTO user) throws Exception {
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setAddress(user.getAddress());

        return userDAO.save(newUser);
        // return userDAO.save(new User(user.getId(),user.getName(),user.getAddress()));
    }
}