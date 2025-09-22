//package lk.ijse.controller;
//
//import javafx.collections.FXCollections;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.input.KeyEvent;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.AnchorPane;
//import lk.ijse.BO.BOFactory;
//import lk.ijse.BO.BOTypes;
//import lk.ijse.BO.custom.CourseBO;
//import lk.ijse.DTO.CourseDTO;
//import lk.ijse.tdm.CourseTM;
//
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.ResourceBundle;
//
//public class CourseManagePageController implements Initializable {
//
////    private final CourseBO courseBO = (CourseBO) BOFactory.getInstance().getBO(BOTypes.COURSE);
//
//    @FXML
//    private AnchorPane ancCoursePage;
//
//    @FXML
//    private Label lblCourseId;
//
//    @FXML
//    private ComboBox<String> cmbCourseName;
//
//    @FXML
//    private TextField txtDuration;
//
//    @FXML
//    private TextField txtFee;
//
//    @FXML
//    private TextArea txtDescription;
//
//    @FXML
//    private TextField txtInstructorId;
//
//    @FXML
//    private Button btnAdd;
//
//    @FXML
//    private Button btnUpdate;
//
//    @FXML
//    private Button btnDelete;
//
//    @FXML
//    private Button btnClear;
//
//    @FXML
//    private TextField txtSearch;
//
//    @FXML
//    private TableView<CourseTM> tblCourses;
//
//    @FXML
//    private TableColumn<CourseTM, String> colCourseId;
//
//    @FXML
//    private TableColumn<CourseTM, String> colCourseName;
//
//    @FXML
//    private TableColumn<CourseTM, String> colDuration;
//
//    @FXML
//    private TableColumn<CourseTM, Double> colFee;
//
//    @FXML
//    private TableColumn<CourseTM, String> colDescription;
//
//    @FXML
//    private TableColumn<CourseTM, String> colInstructorId;
//
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        setCellValueFactories();
//        try {
//            loadAllCourses();
//            loadNextId();
//        } catch (Exception e) {
//            showAlert(Alert.AlertType.ERROR, "Initialization Error : " + e.getMessage());
//        }
//    }
//
//    private void setCellValueFactories() {
//        colCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
//        colCourseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
//        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
//        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
//        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
//        colInstructorId.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
//
//        cmbCourseName.setItems(FXCollections.observableArrayList(
//                "Manual Car Driving",
//                "Automatic Car Driving",
//                "Motorcycle / Scooter Driving",
//                "Three-Wheeler Driving",
//                "Heavy Vehicle Driving (Truck / Bus)"
//        ));
//    }
//
//    private void loadAllCourses() throws Exception {
//        tblCourses.setItems(FXCollections.observableArrayList(
//                courseBO.getAll().stream().map(courseDTO -> new CourseTM(
//                        courseDTO.getCourseId(),
//                        courseDTO.getCourseName(),
//                        courseDTO.getDuration(),
//                        courseDTO.getFee(),
//                        courseDTO.getDescription(),
//                        courseDTO.getInstructorId()
//                )).toList()
//        ));
//    }
//
//    private void loadNextId() throws Exception {
//        String nextId = courseBO.getNextId();
//        lblCourseId.setText(nextId);
//    }
//
//    @FXML
//    void btnAddOnAction(ActionEvent event) {
//        try {
//            boolean isSaved = courseBO.save(CourseDTO.builder()
//                    .courseId(lblCourseId.getText())
//                    .courseName(cmbCourseName.getValue())
//                    .duration(txtDuration.getText())
//                    .fee(Double.parseDouble(txtFee.getText()))
//                    .description(txtDescription.getText())
//                    .instructorId(txtInstructorId.getText())
//                    .build());
//
//            if (isSaved) {
//                showAlert(Alert.AlertType.INFORMATION, "Course saved successfully!");
//                loadAllCourses();
//                resetForm();
//                loadNextId();
//            } else {
//                showAlert(Alert.AlertType.ERROR, "Error saving course");
//            }
//
//        } catch (Exception e) {
//            showAlert(Alert.AlertType.ERROR, "Error saving course: " + e.getMessage());
//        }
//    }
//
//    @FXML
//    void btnUpdateOnAction(ActionEvent event) {
//        try {
//            boolean isUpdated = courseBO.update(CourseDTO.builder()
//                    .courseId(lblCourseId.getText())
//                    .courseName(cmbCourseName.getValue())
//                    .duration(txtDuration.getText())
//                    .fee(Double.parseDouble(txtFee.getText()))
//                    .description(txtDescription.getText())
//                    .instructorId(txtInstructorId.getText())
//                    .build());
//
//            if (isUpdated) {
//                showAlert(Alert.AlertType.INFORMATION, "Course updated successfully!");
//                loadAllCourses();
//                resetForm();
//                loadNextId();
//            } else {
//                showAlert(Alert.AlertType.ERROR, "Error updating course");
//            }
//        } catch (Exception e) {
//            showAlert(Alert.AlertType.ERROR, "Error updating course: " + e.getMessage());
//        }
//    }
//
//    @FXML
//    void btnDeleteOnAction(ActionEvent event) {
//        String id = lblCourseId.getText();
//        if (id.isEmpty()) {
//            showAlert(Alert.AlertType.WARNING, "Please select a course to delete!");
//            return;
//        }
//
//        try {
//            boolean isDeleted = courseBO.delete(id);
//            if (isDeleted) {
//                showAlert(Alert.AlertType.INFORMATION, "Course deleted successfully!");
//                loadAllCourses();
//                resetForm();
//                loadNextId();
//            } else {
//                showAlert(Alert.AlertType.ERROR, "Error deleting course");
//            }
//        } catch (Exception e) {
//            showAlert(Alert.AlertType.ERROR, "Error deleting course: " + e.getMessage());
//        }
//    }
//
//    @FXML
//    void btnClearOnAction(ActionEvent event) {
//        resetForm();
//        try {
//            loadNextId();
//        } catch (Exception e) {
//            showAlert(Alert.AlertType.ERROR, "Error generating ID: " + e.getMessage());
//        }
//    }
//
//    @FXML
//    void OnClickedTable(MouseEvent event) {
//        CourseTM selectedItem = tblCourses.getSelectionModel().getSelectedItem();
//        if (selectedItem != null) {
//            lblCourseId.setText(selectedItem.getCourseId());
//            cmbCourseName.setValue(selectedItem.getCourseName());
//            txtDuration.setText(selectedItem.getDuration());
//            txtFee.setText(String.valueOf(selectedItem.getFee()));
//            txtDescription.setText(selectedItem.getDescription());
//            txtInstructorId.setText(selectedItem.getInstructorId());
//        }
//    }
//
//    @FXML
//    void search(KeyEvent event) {
//        String search = txtSearch.getText();
//        if (search.isEmpty()) {
//            try {
//                loadAllCourses();
//            } catch (Exception e) {
//                showAlert(Alert.AlertType.ERROR, "Failed to search: " + e.getMessage());
//            }
//        } else {
//            try {
//                ArrayList<CourseDTO> courseList = (ArrayList<CourseDTO>) courseBO.search(search);
//                tblCourses.setItems(FXCollections.observableArrayList(
//                        courseList.stream()
//                                .map(courseDTO -> new CourseTM(
//                                        courseDTO.getCourseId(),
//                                        courseDTO.getCourseName(),
//                                        courseDTO.getDuration(),
//                                        courseDTO.getFee(),
//                                        courseDTO.getDescription(),
//                                        courseDTO.getInstructorId()
//                                )).toList()
//                ));
//            } catch (Exception e) {
//                showAlert(Alert.AlertType.ERROR, "Failed to search: " + e.getMessage());
//            }
//        }
//    }
//
//    private void resetForm() {
//        cmbCourseName.getSelectionModel().clearSelection();
//        txtDuration.clear();
//        txtFee.clear();
//        txtDescription.clear();
//        txtInstructorId.clear();
//        tblCourses.getSelectionModel().clearSelection();
//    }
//
//    private void showAlert(Alert.AlertType type, String message) {
//        new Alert(type, message).show();
//    }
//}
