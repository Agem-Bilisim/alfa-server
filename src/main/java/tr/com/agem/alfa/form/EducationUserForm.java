package tr.com.agem.alfa.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EducationUserForm {

	@JsonProperty("user_id")
	@NotEmpty
	private int userId;

	@NotEmpty
	private String email;

	@JsonProperty("full_name")
	private String fullName;
	
	private String yetki;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getfullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getYetki() {
		return yetki;
	}

	public void setYetki(String yetki) {
		this.yetki = yetki; 
	}
}
