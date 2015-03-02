package org.rjung.alfred.objects;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.Date;

@Entity
public class Visit implements Printable {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "name")
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
	public int print(Graphics g, PageFormat pf, int page)
			throws PrinterException {
		if (page > 0) {
			return NO_SUCH_PAGE;
		}

		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(pf.getImageableX(), pf.getImageableY());

		g.drawString(name, 100, 100);
		g.drawString(company, 100, 100);

		return PAGE_EXISTS;
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
