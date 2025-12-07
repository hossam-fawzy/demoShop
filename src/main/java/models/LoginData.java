package models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data model for login test data.
 * Follows best practices with proper encapsulation, Jackson annotations, and builder pattern.
 */
public class LoginData {

    @JsonProperty("testCase")
    private String testCase;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("expectedResult")
    private String expectedResult;

    // Default constructor for Jackson
    public LoginData() {
    }

    // Constructor with all fields
    public LoginData(String testCase, String email, String password, String expectedResult) {
        this.testCase = testCase;
        this.email = email;
        this.password = password;
        this.expectedResult = expectedResult;
    }

    // Getters
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

    // Setters (for Jackson deserialization and flexibility)
    public void setTestCase(String testCase) {
        this.testCase = testCase;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    // Helper methods
    public boolean isSuccessExpected() {
        return "success".equalsIgnoreCase(expectedResult);
    }

    public boolean isFailureExpected() {
        return "failure".equalsIgnoreCase(expectedResult);
    }

    public boolean hasEmail() {
        return email != null && !email.trim().isEmpty();
    }

    public boolean hasPassword() {
        return password != null && !password.trim().isEmpty();
    }

    @Override
    public String toString() {
        return "LoginData{" +
                "testCase='" + testCase + '\'' +
                ", email='" + (email != null && !email.isEmpty() ? email : "<empty>") + '\'' +
                ", password='" + (password != null && !password.isEmpty() ? "***" : "<empty>") + '\'' +
                ", expectedResult='" + expectedResult + '\'' +
                '}';
    }

    // Builder pattern for fluent test data creation
    public static class Builder {
        private String testCase;
        private String email;
        private String password;
        private String expectedResult;

        public Builder testCase(String testCase) {
            this.testCase = testCase;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder expectedResult(String expectedResult) {
            this.expectedResult = expectedResult;
            return this;
        }

        public LoginData build() {
            return new LoginData(testCase, email, password, expectedResult);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
