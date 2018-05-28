package tr.com.agem.alfa.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import tr.com.agem.alfa.form.LdapIntegrationForm;
import tr.com.agem.alfa.form.PackageForm;
import tr.com.agem.alfa.form.ProblemForm;
import tr.com.agem.alfa.form.ProblemReferenceForm;
import tr.com.agem.alfa.form.ProcessForm;
import tr.com.agem.alfa.form.SurveyForm;
import tr.com.agem.alfa.form.SysRoleForm;
import tr.com.agem.alfa.form.SysUserForm;
import tr.com.agem.alfa.model.InstalledPackage;
import tr.com.agem.alfa.model.LdapIntegration;
import tr.com.agem.alfa.model.Problem;
import tr.com.agem.alfa.model.ProblemReference;
import tr.com.agem.alfa.model.RunningProcess;
import tr.com.agem.alfa.model.Survey;
import tr.com.agem.alfa.model.SysRole;
import tr.com.agem.alfa.model.SysUser;

/**
 * Mapper class to easily map/convert entity and form (DTO) instances to one
 * another. (NO need to implement this interface since it's auto-generated
 * during compile-time.)<br/>
 * <br/>
 * See {@link https://github.com/mapstruct/mapstruct-examples} for usage.
 * 
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface SysMapper {
	SysUserForm toSysUserForm(SysUser entity);

	SysUser toSysUserEntity(SysUserForm form);

	SysRoleForm toSysRoleForm(SysRole entity);

	SysRole toSysRoleEntity(SysRoleForm form);

	PackageForm toPackageForm(InstalledPackage entity);

	InstalledPackage toPackageEntity(PackageForm form);

	ProcessForm toProcessForm(RunningProcess entity);

	RunningProcess toProcessEntity(ProcessForm form);

	Problem toProblemEntity(ProblemForm form);

	ProblemForm toProblemForm(Problem entity);

	ProblemReference toProblemReferenceEntity(ProblemReferenceForm form);

	ProblemReferenceForm toProblemReferenceForm(ProblemReference entity);

	SurveyForm toSurveyForm(Survey entity);

	Survey toSurveyEntity(SurveyForm form);

	LdapIntegrationForm toIntegrationForm(LdapIntegration entity);

	LdapIntegration toIntegrationEntity(LdapIntegrationForm form);
}
