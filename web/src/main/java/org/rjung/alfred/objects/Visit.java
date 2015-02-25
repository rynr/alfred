package org.rjung.alfred.objects;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.CreatedDate;

@Entity
public class Visit {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    @NotNull
    @Size(min = 3, max = 128)
    private String name;

    @Column(name = "email")
    @NotNull
    @Email
    private String email;

    @Column(name = "company")
    @Size(min = 3, max = 128)
    private String company;

    @Column(updatable = false, name = "created_at", nullable = false)
    private Date createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Date getCreatedAt() {
        return createdAt == null ? null : new Date(createdAt.getTime());
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt == null ? null : new Date(
                createdAt.getTime());
    }

    @PrePersist
    protected void beforeCreate() {
        if (createdAt == null)
            createdAt = new Date(System.currentTimeMillis());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Visit other = (Visit) obj;
        return new EqualsBuilder().append(this.id, other.id)
                .append(this.name, other.name).append(this.email, other.email)
                .append(this.company, other.company)
                .append(this.createdAt, other.createdAt).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(name).append(email)
                .append(company).append(createdAt).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("name", name)
                .append("email", email).append("company", company)
                .append("createdAt", createdAt).toString();
    }
}
