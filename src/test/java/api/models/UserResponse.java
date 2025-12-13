package api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * UserResponse - Response model for User API operations
 * 
 * This POJO represents the response body received from
 * user-related API endpoints (create, update, get user).
 * 
 * BEST PRACTICES:
 * - Jackson annotations for JSON deserialization
 * - Matches the structure of typical REST API responses
 * - Proper encapsulation with getters/setters
 * 
 * EXAMPLE RESPONSE:
 * {
 *   "id": "123",
 *   "name": "John Doe",
 *   "job": "QA Engineer",
 *   "createdAt": "2025-12-12T15:57:07.000Z",
 *   "updatedAt": "2025-12-12T15:57:07.000Z"
 * }
 * 
 * @author QA Team
 * @version 1.0
 */
public class UserResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("job")
    private String job;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;

    @JsonProperty("email")
    private String email;

    // ═══════════════════════════════════════════════════════════════════════════
    // CONSTRUCTORS
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Default constructor (required for Jackson deserialization)
     */
    public UserResponse() {
    }

    /**
     * All-args constructor
     */
    public UserResponse(String id, String name, String job, String createdAt, String updatedAt, String email) {
        this.id = id;
        this.name = name;
        this.job = job;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.email = email;
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // GETTERS AND SETTERS
    // ═══════════════════════════════════════════════════════════════════════════

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // UTILITY METHODS
    // ═══════════════════════════════════════════════════════════════════════════

    @Override
    public String toString() {
        return "UserResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", job='" + job + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
