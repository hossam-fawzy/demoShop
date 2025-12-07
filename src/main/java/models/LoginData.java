package models;

public class LoginData {

    private String testCase;
    private String email;
    private String password;
    private String expectedResult;

    public String getTestCase() {
        return testCase;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    @Override
    public String toString() {
        return "LoginData{" +
                "testCase='" + testCase + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", expectedResult='" + expectedResult + '\'' +
                '}';
    }
}
