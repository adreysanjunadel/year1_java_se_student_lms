package model;

public class StudentSession {
    private static StudentSession instance;
    
    private String sno;
    private String firstName;
    private String lastName;

    
    private StudentSession() {
        // Private constructor to prevent instantiation from outside the class
    }

    public static StudentSession getInstance() {
        if (instance == null) {
            instance = new StudentSession();
        }
        return instance;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
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
}
