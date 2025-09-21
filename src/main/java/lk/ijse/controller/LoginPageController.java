package lk.ijse.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginPageController {

    @FXML
    private Button btnLogin;

    @FXML
    private CheckBox chkShowPassword;

    @FXML
    private ComboBox<?> cmbRole;

    @FXML
    private Hyperlink lnkChangePassword;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

}
