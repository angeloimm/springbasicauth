package it.olegna.test.basic.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;

@DynamicUpdate
@Entity
@Table(name = "BASIC_USER_ROLE")
public class UserRole extends AbstractModel implements GrantedAuthority {

	private static final long serialVersionUID = 947587826854724373L;
	private String name;
	@Transient
	private static final String ROLE_SUFFIX = "ROLE_";
	@Column(name = "NAME", nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	@Transient
	public String getAuthority() {
		StringBuilder sb = new StringBuilder(getName());
		if( sb.indexOf(ROLE_SUFFIX) == -1 )
		{
			sb.insert(0, ROLE_SUFFIX);
		}
		return sb.toString();
	}	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		UserRole other = (UserRole) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
