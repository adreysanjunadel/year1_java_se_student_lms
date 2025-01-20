package model;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class TeacherSession {

    private String tno;
    private String firstName;
    private String lastName;
    private Date date;

    private static TeacherSession instance;

    private TeacherSession() {

    }

    public static TeacherSession getInstance() {
        if (instance == null) {
            instance = new TeacherSession();
        }
        return instance;
    }

    public String getTno() {
        return tno;
    }

    public void setTno(String tno) {
        this.tno = tno;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
