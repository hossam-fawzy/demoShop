package api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * UserRequest - Request model for User API operations
 * 
 * This POJO (Plain Old Java Object) represents the request body
 * when creating or updating a user via API.
 * 
 * BEST PRACTICES:
 * - Immutable builder pattern for flexible object creation
 * - Jackson annotations for JSON serialization/deserialization
 * - Proper encapsulation with getters/setters
 * 
 * @author QA Team
 * @version 1.0
 */
public class UserRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("job")
    private String job;

    @JsonProperty("email")
    private String email;

    @JsonProperty("id")
    private Integer id;

    // ═══════════════════════════════════════════════════════════════════════════
    // CONSTRUCTORS
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Default constructor (required for Jackson deserialization)
     */
    public UserRequest() {
    }

    /**
     * All-args constructor
     */
    public UserRequest(String name, String job, String email, Integer id) {
        this.name = name;
        this.job = job;
        this.email = email;
        this.id = id;
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // BUILDER PATTERN
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Creates a new builder instance
     * @return UserRequestBuilder
     */
    public static UserRequestBuilder builder() {
        return new UserRequestBuilder();
    }

    /**
     * Builder class for UserRequest
     */
    public static class UserRequestBuilder {
        private String name;
        private String job;
        private String email;
        private Integer id;

        public UserRequestBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserRequestBuilder job(String job) {
            this.job = job;
            return this;
        }

        public UserRequestBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserRequestBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public UserRequest build() {
            return new UserRequest(name, job, email, id);
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // GETTERS AND SETTERS
    // ═══════════════════════════════════════════════════════════════════════════

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // UTILITY METHODS
    // ═══════════════════════════════════════════════════════════════════════════

    @Override
    public String toString() {
        return "UserRequest{" +
                "name='" + name + '\'' +
                ", job='" + job + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                '}';
    }
}
