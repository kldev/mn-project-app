package mn.portal.data.entity;


import java.sql.Date;

public class UserAccountEntity {
    private long id;
    private String email;
    private boolean active;
    private boolean deleted;
    private Date createdAt;
    private String googleUserId;
    private String openIdUserId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getGoogleUserId() {
        return googleUserId;
    }

    public void setGoogleUserId(String googleUserId) {
        this.googleUserId = googleUserId;
    }

    public String getOpenIdUserId() {
        return openIdUserId;
    }

    public void setOpenIdUserId(String openIdUserId) {
        this.openIdUserId = openIdUserId;
    }
}
