package tr.com.agem.alfa.form;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public class TagForm extends BaseForm {

	private static final long serialVersionUID = 3887521280929916142L;

	@NotEmpty
	@Length(max = 100)
	private String name;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private Date plannedMigrationDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public Date getPlannedMigrationDate() {
		return plannedMigrationDate;
	}

	public void setPlannedMigrationDate(Date plannedMigrationDate) {
		this.plannedMigrationDate = plannedMigrationDate;
	}

}
