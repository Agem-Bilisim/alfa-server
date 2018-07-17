package tr.com.agem.alfa.lms;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public class LmsUser implements Serializable {

	private static final long serialVersionUID = -4022211279515304116L;

	private String durumu;
	private String sure;
	@JsonProperty("user_id")
	private Long userId;
	private String email;
	@JsonProperty("full_name")
	private String fullName;
	@JsonProperty("sinav_durumu")
	private String sinavDurumu;
	@JsonProperty("sinav_puani")
	private String sinavPuani;
	private Long id;
	@JsonProperty("urun_adi")
	private String urunAdi;

	public String getDurumu() {
		return durumu;
	}

	public void setDurumu(String durumu) {
		this.durumu = durumu;
	}

	public String getSure() {
		return sure;
	}

	public void setSure(String sure) {
		this.sure = sure;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public void setSinavPuani(String sinavPuani) {
		this.sinavPuani = sinavPuani;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrunAdi() {
		return urunAdi;
	}

	public void setUrunAdi(String urunAdi) {
		this.urunAdi = urunAdi;
	}

}
