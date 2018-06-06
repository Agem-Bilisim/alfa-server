package tr.com.agem.alfa.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import tr.com.agem.alfa.form.BiosForm;
import tr.com.agem.alfa.form.CpuForm;
import tr.com.agem.alfa.form.DiskForm;
import tr.com.agem.alfa.form.EducationForm;
import tr.com.agem.alfa.form.GpuForm;
import tr.com.agem.alfa.form.LdapIntegrationForm;
import tr.com.agem.alfa.form.MemoryForm;
import tr.com.agem.alfa.form.NetworkInterfaceForm;
import tr.com.agem.alfa.form.PackageForm;
import tr.com.agem.alfa.form.PeripheralDeviceForm;
import tr.com.agem.alfa.form.ProblemForm;
import tr.com.agem.alfa.form.ProblemReferenceForm;
import tr.com.agem.alfa.form.ProcessForm;
import tr.com.agem.alfa.form.SurveyForm;
import tr.com.agem.alfa.form.SysRoleForm;
import tr.com.agem.alfa.form.SysUserForm;
import tr.com.agem.alfa.form.TagForm;
import tr.com.agem.alfa.model.Bios;
import tr.com.agem.alfa.model.Cpu;
import tr.com.agem.alfa.model.Disk;
import tr.com.agem.alfa.model.Education;
import tr.com.agem.alfa.model.Gpu;
import tr.com.agem.alfa.model.InstalledPackage;
import tr.com.agem.alfa.model.LdapIntegration;
import tr.com.agem.alfa.model.Memory;
import tr.com.agem.alfa.model.NetworkInterface;
import tr.com.agem.alfa.model.PeripheralDevice;
import tr.com.agem.alfa.model.Problem;
import tr.com.agem.alfa.model.ProblemReference;
import tr.com.agem.alfa.model.RunningProcess;
import tr.com.agem.alfa.model.Survey;
import tr.com.agem.alfa.model.SysRole;
import tr.com.agem.alfa.model.SysUser;
import tr.com.agem.alfa.model.Tag;

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

	Cpu toCpuEntity(CpuForm form);

	Gpu toGpuEntity(GpuForm form);

	Disk toDiskEntity(DiskForm form);

	Memory toMemoryEntity(MemoryForm form);

	Bios toBiosEntity(BiosForm form);

	NetworkInterface toNetworkInterfaceEntity(NetworkInterfaceForm form);

	Education toEducationEntity(EducationForm form);

	EducationForm toEducationForm(Education education);

	Tag toTagEntity(TagForm tagForm);

	PeripheralDevice toPeripheralDeviceEntity(PeripheralDeviceForm form);

}
