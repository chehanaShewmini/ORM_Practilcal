package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lk.ijse.BO.BOFactory;
import lk.ijse.BO.custom.UserBO;
import lk.ijse.DTO.UserDTO;

public class userController {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtName;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBo(BOFactory.BoType.User);

    @FXML
    void btnDeleteAction(ActionEvent event) {

    }

    @FXML
    void btnResetAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws Exception {
        String name = txtName.getText();
        String address = txtAddress.getText();


        UserDTO userDTO = new UserDTO();
        userDTO.setAddress(address);
        userDTO.setName(name);



        boolean response  = userBO.save(userDTO);
        System.out.println(response);

    }

    @FXML
    void btnUpdateAction(ActionEvent event) {

    }

}