package lk.ijse.BO.custom;

import lk.ijse.BO.SuperBO;
import lk.ijse.DTO.UserDTO;

public interface UserBO extends SuperBO {
    boolean save(UserDTO userDTO) throws Exception;
}
