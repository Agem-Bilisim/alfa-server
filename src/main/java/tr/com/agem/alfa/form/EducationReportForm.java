package tr.com.agem.alfa.form;

import tr.com.agem.alfa.model.enums.*;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EducationReportForm extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 

	@NotEmpty
	private Long id;
	
	private int durumu;
	private String sure;
	private int userId;
	private String userEmail;
	private String userFullname;
	private String sinavDurumu;
	private String sinavPuani;
	private int educationId;
	private String educationUrunAdi;
	
	public Long getID() {
		return id;
	}	
	public void setID(int id) {
		this.id = (long)id;
	}
	
	public int getDurumu() {
		return durumu;
	}	
	public void setDurumu(String durumu) {
		
		EducationStatus status = EducationStatus.getTypeFromLabel(durumu);
		this.durumu = status.getId();
	}
	
	public String getSure() {
		return sure;
	}
	public void setSure(String sure) {
		this.sure = sure;
	}
	
	public int getUserID() {
		return userId;
	}
	public void setUserID(int userId) {
		this.userId = userId;
	}
	
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public String getUserFullname() {
		return userFullname;
	}
	public void setUserFullname(String userFullname) {
		this.userFullname = userFullname;
	}
	
	public String getSinavDurumu() {
		return sinavDurumu;
	}
	public void setSinavDurumu(String sinavDurumu) {
		this.sinavDurumu = sinavDurumu;
	}
	
	public String getSinavPuani() {
		return sinavPuani;
	}
	public void setSinavPuani(int sinavPuani) {
		String puan = "";
		puan+=sinavPuani;
		this.sinavPuani = puan;
	}
	
	public int getEducationID() {
		return educationId;
	}
	public void setEducationID(int educationId) {
		this.educationId = educationId;
	}
	
	public String getEducationUrunAdi() {
		return educationUrunAdi;
	}
	public void setEducationUrunAdi(String educationUrunAdi) {
		this.educationUrunAdi = educationUrunAdi;
	}
	
}
