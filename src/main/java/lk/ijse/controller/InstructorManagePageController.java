package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.BO.BOFactory;
import lk.ijse.BO.BOTypes;
import lk.ijse.BO.custom.InstructorBO;
import lk.ijse.DTO.InstructorDTO;
import lk.ijse.tdm.InstructorTM;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class InstructorManagePageController implements Initializable {

    private final InstructorBO instructorBO =
            (InstructorBO) BOFactory.getInstance().getBo(BOTypes.INSTRUCTOR);

    public AnchorPane ancInstructorPage;

    public Label lblInstructorId;
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtEmail;
    public TextField txtPhone;
    public ComboBox<String> cmbSpecialization;
    public TextField txtAvailabilitySchedule;

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    public TextField txtSearch;

    public TableView<InstructorTM> tblInstructors;
    public TableColumn<InstructorTM, String> colInstructorId;
    public TableColumn<InstructorTM, String> colFirstName;
    public TableColumn<InstructorTM, String> colLastName;
    public TableColumn<InstructorTM, String> colEmail;
    public TableColumn<InstructorTM, String> colPhone;
    public TableColumn<InstructorTM, String> colSpecialization;
    public TableColumn<InstructorTM, String> colAvailabilitySchedule;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactories();
        try {
            loadAllInstructors();
            loadNextId();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Initialization error: " + e.getMessage());
        }
    }

    private void setCellValueFactories() {
        colInstructorId.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colSpecialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        colAvailabilitySchedule.setCellValueFactory(new PropertyValueFactory<>("availability_schedule"));

        cmbSpecialization.setItems(FXCollections.observableArrayList(
                "Light Vehicle", "Motorcycle", "Heavy Vehicle",
                "Defensive Driving", "Special Needs Training",
                "Commercial License Training", "Theory Instructor"
        ));
    }

    private void loadAllInstructors() throws Exception {
        tblInstructors.setItems(FXCollections.observableArrayList(
                instructorBO.getAll().stream().map(dto -> new InstructorTM(
                        dto.getInstructorId(),
                        dto.getFirstName(),
                        dto.getLastName(),
                        dto.getEmail(),
                        dto.getPhone(),
                        dto.getSpecialization(),
                        dto.getAvailability_schedule()
                )).toList()
        ));
    }

    private void loadNextId() throws Exception {
        String nextId = instructorBO.getNextId();
        lblInstructorId.setText(nextId);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        if (!validateInput()) return;

        try {
            boolean isSaved = instructorBO.save(InstructorDTO.builder()
                    .instructorId(lblInstructorId.getText())
                    .firstName(txtFirstName.getText())
                    .lastName(txtLastName.getText())
                    .email(txtEmail.getText())
                    .phone(txtPhone.getText())
                    .specialization(cmbSpecialization.getValue())
                    .availability_schedule(txtAvailabilitySchedule.getText())
                    .build());

            if (isSaved) {
                showAlert(Alert.AlertType.INFORMATION, "Instructor saved successfully!");
                loadAllInstructors();
                resetForm();
                loadNextId();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to save instructor!");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error saving instructor: " + e.getMessage());
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        if (!validateInput()) return;

        try {
            boolean isUpdated = instructorBO.update(InstructorDTO.builder()
                    .instructorId(lblInstructorId.getText())
                    .firstName(txtFirstName.getText())
                    .lastName(txtLastName.getText())
                    .email(txtEmail.getText())
                    .phone(txtPhone.getText())
                    .specialization(cmbSpecialization.getValue())
                    .availability_schedule(txtAvailabilitySchedule.getText())
                    .build());

            if (isUpdated) {
                showAlert(Alert.AlertType.INFORMATION, "Instructor updated successfully!");
                loadAllInstructors();
                resetForm();
                loadNextId();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to update instructor!");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error updating instructor: " + e.getMessage());
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = lblInstructorId.getText();
        if (id.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please select an instructor to delete!");
            return;
        }

        try {
            boolean isDeleted = instructorBO.delete(id);
            if (isDeleted) {
                showAlert(Alert.AlertType.INFORMATION, "Instructor deleted successfully!");
                loadAllInstructors();
                resetForm();
                loadNextId();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to delete instructor!");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error deleting instructor: " + e.getMessage());
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetForm();
        try {
            loadNextId();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error generating ID: " + e.getMessage());
        }
    }

    public void OnClickedTable(MouseEvent mouseEvent) {
        InstructorTM selected = tblInstructors.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lblInstructorId.setText(selected.getInstructorId());
            txtFirstName.setText(selected.getFirstName());
            txtLastName.setText(selected.getLastName());
            txtEmail.setText(selected.getEmail());
            txtPhone.setText(selected.getPhone());
            cmbSpecialization.setValue(selected.getSpecialization());
            txtAvailabilitySchedule.setText(selected.getAvailability_schedule());
        }
    }

    private void resetForm() {
        txtFirstName.clear();
        txtLastName.clear();
        txtEmail.clear();
        txtPhone.clear();
        cmbSpecialization.getSelectionModel().clearSelection();
        txtAvailabilitySchedule.clear();
        tblInstructors.getSelectionModel().clearSelection();
    }

    private boolean validateInput() {
        if (txtFirstName.getText().isEmpty() || txtLastName.getText().isEmpty()
                || txtEmail.getText().isEmpty() || txtPhone.getText().isEmpty()
                || cmbSpecialization.getSelectionModel().isEmpty() || txtAvailabilitySchedule.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please fill all fields!");
            return false;
        }

        if (!Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$", txtEmail.getText())) {
            showAlert(Alert.AlertType.ERROR, "Invalid email format!");
            return false;
        }

        if (!Pattern.matches("^(0\\d{9})$", txtPhone.getText())) {
            showAlert(Alert.AlertType.ERROR, "Invalid phone number! Must be 10 digits starting with 0.");
            return false;
        }

        return true;
    }

    public void search(KeyEvent keyEvent) {
        String search = txtSearch.getText();
        if (search.isEmpty()) {
            try {
                loadAllInstructors();
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Failed to search! : " + e.getMessage());
            }
        } else {
            try {
                ArrayList<InstructorDTO> instructorList = (ArrayList<InstructorDTO>) instructorBO.search(search);
                tblInstructors.setItems(FXCollections.observableArrayList(
                        instructorList.stream()
                                .map(dto -> new InstructorTM(
                                        dto.getInstructorId(),
                                        dto.getFirstName(),
                                        dto.getLastName(),
                                        dto.getEmail(),
                                        dto.getPhone(),
                                        dto.getSpecialization(),
                                        dto.getAvailability_schedule()
                                )).toList()
                ));
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Failed to search! : " + e.getMessage());
            }
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        new Alert(type, message).show();
    }

    private void navigateTo(String path) {
        try {
            ancInstructorPage.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));
            anchorPane.prefWidthProperty().bind(ancInstructorPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancInstructorPage.heightProperty());
            ancInstructorPage.getChildren().add(anchorPane);
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Page not found!");
        }
    }
}
