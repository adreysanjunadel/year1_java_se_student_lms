package model;

public class AdminSession {
    private String ano;
    private String firstName;
    private String lastName;

    private static AdminSession instance;

    private AdminSession() {
        
    }

    public static AdminSession getInstance() {
        if (instance == null) {
            instance = new AdminSession();
        }
        return instance;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
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
