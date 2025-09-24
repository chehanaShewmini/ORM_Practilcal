package lk.ijse.tdm;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class LessonsTM {
    private String  lessonId;
    private String lessonDate;
    private String startTime;
    private String endTime;
    private String status;
    private String  studentId;
    private String courseId;
    private String instructorId;

}
