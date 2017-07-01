package org.sunhp.rcampus.vo;

import org.sunhp.rcampus.bean.Course;

/**
 * Created by yufeng.fyf on 17/4/29.
 */
public class SimpleCourse {
    private Long courseId;
    private String courseName;
    private Double progress;
    private Integer order;
    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public SimpleCourse(){}
    public SimpleCourse(Course course,Double progress){
        this.courseId = course.getCourseId();
        this.courseName = course.getCourseName();
        this.progress = progress;
        this.order = course.getCourseOrder();
    }
    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }
}
