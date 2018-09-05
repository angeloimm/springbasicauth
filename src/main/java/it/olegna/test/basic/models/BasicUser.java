package it.olegna.test.basic.models;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.userdetails.UserDetails;

@DynamicUpdate
@Entity
@Table(name = "BASIC_USER")
public class BasicUser extends AbstractModel implements UserDetails {

	private static final long serialVersionUID = -319286812757498485L;
	private String username;
	private String password;
	private boolean accountExpired;
	private boolean accountLocked;
	private boolean credentialsExpired;
	private boolean enabled;
	private Collection<UserRole> authorities;

	@Column(name = "USERNAME")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "PASSWORD")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "ACCOUNT_EXPIRED")
	public boolean isAccountExpired() {
		return accountExpired;
	}

	public void setAccountExpired(boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	@Column(name = "ACCOUNT_LOCKED")
	public boolean isAccountLocked() {
		return accountLocked;
	}

	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	@Column(name = "CREDENTIALS_EXPIRED")
	public boolean isCredentialsExpired() {
		return credentialsExpired;
	}

	public void setCredentialsExpired(boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	@Column(name = "ENABLED")
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ROLE_USER", joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"))
	@OrderBy
	public Collection<UserRole> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<UserRole> authorities) {
		this.authorities = authorities;
	}

	@Override
	@Transient
	public boolean isAccountNonExpired() {

		return !isAccountExpired();
	}

	@Override
	@Transient
	public boolean isAccountNonLocked() {

		return !isAccountLocked();
	}

	@Override
	@Transient
	public boolean isCredentialsNonExpired() {

		return !isCredentialsExpired();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (accountExpired ? 1231 : 1237);
		result = prime * result + (accountLocked ? 1231 : 1237);
		result = prime * result + ((authorities == null) ? 0 : authorities.hashCode());
		result = prime * result + (credentialsExpired ? 1231 : 1237);
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicUser other = (BasicUser) obj;
		if (accountExpired != other.accountExpired)
			return false;
		if (accountLocked != other.accountLocked)
			return false;
		if (authorities == null) {
			if (other.authorities != null)
				return false;
		} else if (!authorities.equals(other.authorities))
			return false;
		if (credentialsExpired != other.credentialsExpired)
			return false;
		if (enabled != other.enabled)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}
