package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.medcase.DepartmentMedCase;
import ru.ecom.mis.ejb.domain.medcase.voc.VocDiagnosisRegistrationType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPriorityDiagnosis;
import ru.ecom.mis.ejb.form.medcase.hospital.DepartmentMedCaseForm;
import ru.nuzmsh.util.format.DateFormat;

import javax.persistence.EntityManager;
import java.util.List;

public class DepartmentSaveInterceptor implements IFormInterceptor {

	public static void setDiagnosis(EntityManager aManager, Long aMedCase, String aListDiags, String aRegistrationType, String aPriority) {
		setDiagnosis(aManager, aMedCase, aListDiags, aRegistrationType, aPriority, null, null);
	}

	public static void setDiagnosis(EntityManager aManager, Long aMedCase, String aListDiags, String aRegistrationType, String aPriority, Long aIllnesPrimary) {
		setDiagnosis(aManager, aMedCase, aListDiags, aRegistrationType, aPriority, aIllnesPrimary, null);
	}

    public static void setDiagnosis(EntityManager aManager, Long aMedCase, String aListDiags, String aRegistrationType, String aPriority, Long aIllnesPrimary, String aMkbAdc) {
		if (aListDiags != null && !aListDiags.equals("")) {
			VocDiagnosisRegistrationType vocDRT = (VocDiagnosisRegistrationType) getVocByCode(aManager, "VocDiagnosisRegistrationType", aRegistrationType);
			VocPriorityDiagnosis vocPrior = (VocPriorityDiagnosis) getVocByCode(aManager, "VocPriorityDiagnosis", aPriority);
			StringBuilder sql = new StringBuilder();
			sql.append("select id from Diagnosis where medCase_id=").append(aMedCase)
					.append(" and registrationType_id='").append(vocDRT.getId())
					.append("' and priority_id='").append(vocPrior.getCode()).append("' order by id");
			List<Object> list = aManager.createNativeQuery(sql.toString()).getResultList();
			String[] otherServs = aListDiags.split("#@#");
			if (otherServs.length > 0) {
				for (int i = 0; i < otherServs.length; i++) {
					String[] serv = otherServs[i].split("@#@");
					if (serv[0] == null || serv[0].equals("") || serv[0].equals("0")) {
						if (list.size() > i)
							aManager.createNativeQuery("delete from Diagnosis where id=" + list.get(i)).executeUpdate();
					} else {
						if (list.size() > i) {
							aManager.createNativeQuery("update Diagnosis set name=:name,idc10_id=:idc10,illnesPrimary_id=" + ((aIllnesPrimary == null || aIllnesPrimary.intValue() == 0) ? "null" : "'" + aIllnesPrimary + "'") + ",mkbAdc=:mkbAdc " +
									" where id=" + list.get(i))
									.setParameter("name", serv.length > 2 ? serv[2] : "")
									.setParameter("idc10", Long.valueOf(serv[0]))
									.setParameter("mkbAdc", aMkbAdc)
									.executeUpdate();
						} else {
							aManager.createNativeQuery("insert into Diagnosis (name,idc10_id,medCase_id,priority_id,registrationType_id,illnesPrimary_id,mkbAdc) " +
									" values (:name,'" + serv[0] + "','" + aMedCase + "','" + vocPrior.getId() + "','" + vocDRT.getId() + "',"
									+ (aIllnesPrimary == null || aIllnesPrimary.intValue() == 0 ? "null" : "'" + aIllnesPrimary + "'") + ",:mkbAdc)")
									.setParameter("name", serv.length > 2 ? serv[2] : "")
									.setParameter("mkbAdc", aMkbAdc)
									.executeUpdate();
						}
					}
				}
				for (int i = otherServs.length; i < list.size(); i++) {
					aManager.createNativeQuery("delete from Diagnosis where id=" + list.get(i)).executeUpdate();
				}
			}
		}
    }

	public static Object getVocByCode(EntityManager aManager, String aTable, String aCode) {
		List list = aManager.createQuery("from " + aTable + " where code=:code").setParameter("code", aCode).getResultList();
		return list.isEmpty() ? null : list.get(0);
    }

	private static boolean isEmpty(Long aLong) {
		return (aLong == null) || (aLong == 0);
	}

	public static boolean isDiagnosisAllowed(Long clinicalMkb, Long department, Long aPatient, Long serviceStream, Long diagnosisRegistrationType, Long diagnosisPriority, EntityManager manager) {
		if (clinicalMkb == null || clinicalMkb.equals(0L)) return true;
		if (diagnosisRegistrationType == null) {
			diagnosisRegistrationType = Long.valueOf(manager.createNativeQuery("select id from vocdiagnosisregistrationtype where code='4'").getResultList().get(0).toString());  //4
		}

		if (diagnosisPriority == null) {
			diagnosisPriority = Long.valueOf(manager.createNativeQuery("select id from vocprioritydiagnosis where code='1'").getResultList().get(0).toString()); //1
		}
		VocIdc10 mkb = manager.find(VocIdc10.class, clinicalMkb);
		boolean ret = true;
		boolean isPermitted = false;
		String sql = "select ni.id as f1, case when ldr.permissionrule='1' then '1' else '0' end as f2" +
				" ,case when '" + mkb.getCode() + "' between ni.fromidc10code and ni.toidc10code then '1' else '0' end as f3" +
				" from lpudiagnosisrule ldr" +
				" left join lpucontractnosologygroup lcng on lcng.lpudiagnosisrule = ldr.id" +
				" left join contractnosologygroup cng on cng.id=lcng.nosologygroup" +
				" left join nosologyinterval ni on ni.nosologygroup_id=cng.id" +
				" where ldr.department = " + department +
				" and ldr.id is not null" +
				" and ((ldr.diagnosisregistrationtype is null or ldr.diagnosisregistrationtype=0) or ldr.diagnosisregistrationtype=" + diagnosisRegistrationType + ")";

		if (aPatient != null)
			sql = sql + " and (ldr.sex is null or ldr.sex='0' or ldr.sex=(select p.sex_id from patient p where p.id='" + aPatient + "'))";
		sql = sql + " and ((ldr.diagnosispriority is null or ldr.diagnosispriority=0) or ldr.diagnosispriority=" + diagnosisPriority + ")" +
				" and ((ldr.servicestream is null or ldr.servicestream=0) or ldr.servicestream=" + serviceStream + ")";

		List<Object[]> o = manager.createNativeQuery(sql).getResultList();
		if (o == null || o.isEmpty()) {
			return true;
		}
		boolean first = true;
		for (Object[] oo : o) {
			if (first) {
				isPermitted = oo[1].toString().equals("1");
				first = false;
				ret = !isPermitted;
			}
			boolean isEnter = oo[2].toString().equals("1");
			if (isPermitted) {
				if (isEnter) {
					ret = true;
					break;
				}
			} else {
				if (isEnter) {
					ret = false;
					break;
				}
			}

		}
		return ret;
	}

	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		DepartmentMedCaseForm form = (DepartmentMedCaseForm) aForm;
		EntityManager manager = aContext.getEntityManager();
		DepartmentMedCase medCase = (DepartmentMedCase) aEntity;
		String dateFinish = "null";
		if (medCase.getDateFinish() != null) {
			dateFinish = "to_date('" + DateFormat.formatToDate(medCase.getDateFinish()) + "','dd.mm.yyyy')";
		}
		String timeFinish = "null";
		if (medCase.getDischargeTime() != null) {
			timeFinish = "'" + DateFormat.formatToTime(medCase.getDischargeTime()) + "'";
		}

		if (!isDiagnosisAllowed(form.getClinicalMkb(), form.getDepartment(), form.getPatient(), form.getServiceStream(), null, null, manager)) {
			throw new IllegalStateException("Данный диагноз запрещен в отделении!");
		}
		if (medCase.getParent() != null)
			medCase.setServiceStream(medCase.getParent().getServiceStream());// всегда поток обслуживания = потоку родителя.
		manager.createNativeQuery("update MedCase set dateFinish=" + dateFinish + ", dischargeTime=" + timeFinish + " where parent_id=:parent and DTYPE='DepartmentMedCase' and (dateFinish is not null or (transferDate is null and dateFinish is null))")
				.setParameter("parent", form.getId())
				.executeUpdate();
	}
}