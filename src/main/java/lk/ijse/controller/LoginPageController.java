package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class LoginPageController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private CheckBox chkShowPassword;

    @FXML
    private ComboBox<String> cmbRole;

    @FXML
    private Button btnLogin;

    @FXML
    private Hyperlink lnkChangePassword;

    @FXML
    private TextField txtShowPassword; // For showing password when checkbox is checked

    @FXML
    public void initialize() {
        // Initialize roles (Admin, Receptionist, etc.)
        cmbRole.getItems().addAll("Admin", "Receptionist");

        // Bind show password functionality
        txtShowPassword = new TextField();
        txtShowPassword.setManaged(false);
        txtShowPassword.setVisible(false);

        txtShowPassword.managedProperty().bind(chkShowPassword.selectedProperty());
        txtShowPassword.visibleProperty().bind(chkShowPassword.selectedProperty());
        txtPassword.managedProperty().bind(chkShowPassword.selectedProperty().not());
        txtPassword.visibleProperty().bind(chkShowPassword.selectedProperty().not());

        txtShowPassword.textProperty().bindBidirectional(txtPassword.textProperty());
    }

    @FXML
    void onLogin(ActionEvent event) {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText();
        String role = cmbRole.getValue();

        if(username.isEmpty() || password.isEmpty() || role == null) {
            showAlert(Alert.AlertType.WARNING, "Please fill all fields!");
            return;
        }

        // TODO: Add database authentication here
        // Example pseudo-code:
        // boolean success = UserModel.authenticate(username, password, role);
        boolean success = fakeAuthenticate(username, password, role); // remove after DB is ready

        if(success) {
            showAlert(Alert.AlertType.INFORMATION, "Login successful! Role: " + role);
            // TODO: Navigate to MenuPage
            // Example:
            // Navigation.navigateToMenuPage(btnLogin);
        } else {
            showAlert(Alert.AlertType.ERROR, "Invalid credentials!");
        }
    }

    @FXML
    void onChangePassword(ActionEvent event) {
        // TODO: Open change password page
        showAlert(Alert.AlertType.INFORMATION, "Change Password clicked!");
    }

    // Helper method to show alerts
    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Temporary fake authentication for testing
    private boolean fakeAuthenticate(String username, String password, String role) {
        // Replace with actual DB logic
        return username.equals("admin") && password.equals("123") && role.equals("Admin");
    }
}
