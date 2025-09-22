package lk.ijse.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class LessonManagePageController {

    @FXML
    private Button btnAddLesson;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnClearLesson;

    @FXML
    private Button btnDeleteLesson;

    @FXML
    private Button btnUpdateLesson;

    @FXML
    private ComboBox<?> cmbCourseId;

    @FXML
    private ComboBox<?> cmbInstructorId;

    @FXML
    private ComboBox<?> cmbStatus;

    @FXML
    private ComboBox<?> cmbStudentId;

    @FXML
    private TableColumn<?, ?> colCourseId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colEndTime;

    @FXML
    private TableColumn<?, ?> colInstructorId;

    @FXML
    private TableColumn<?, ?> colLessonId;

    @FXML
    private TableColumn<?, ?> colStartTime;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colStudentId;

    @FXML
    private DatePicker dpLessonDate;

    @FXML
    private TableView<?> tblLessons;

    @FXML
    private TextField txtEndTime;

    @FXML
    private TextField txtLessonId;

    @FXML
    private TextField txtStartTime;

}
