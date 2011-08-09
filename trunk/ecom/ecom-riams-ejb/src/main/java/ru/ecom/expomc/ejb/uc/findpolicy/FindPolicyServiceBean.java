package ru.ecom.expomc.ejb.uc.findpolicy;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.ecom.expomc.ejb.domain.externalbase.ExternalPersonInfo;
import ru.ecom.expomc.ejb.domain.message.Message;
import ru.ecom.expomc.ejb.domain.registry.RegistryEntry;
import ru.ecom.expomc.ejb.services.externalperson.IExternalPersonInfoService;
import ru.ecom.expomc.ejb.uc.snils.IOmcSnilsService;
import ru.ecom.expomc.ejb.uc.snils.OmcSnils;
import ru.nuzmsh.util.StringUtil;

@Stateless
@Remote(IFindPolicyService.class)
public class FindPolicyServiceBean implements IFindPolicyService {

	
	public void restore() throws IOException {
		LineNumberReader in = new LineNumberReader(new FileReader("policyReplaced.txt")) ;
		String line ;
		while ( (line=in.readLine())!=null) {
			//System.out.println(line);
			String p[] = line.split(",") ;
			int type = Integer.parseInt(p[0]) ;
			String url = p[1] ;
			long policyId = Long.parseLong(p[2]) ;
			List<RegistryEntry> list = theManager
				.createQuery("from RegistryEntry where urlEdit=:url")
				.setParameter("url",url).getResultList() ;
			for ( RegistryEntry e : list) {
				copyPolicyInfoToRegistryEntry(type, policyId, e);
				theManager.persist(e) ;
			}
		}
		in.close() ;
	}
	public Collection<PolicyRow> findPolicy(String aLastname, String aFirstname, String aMiddlename, Date aBirthDate, String aSnils, long aMessageId) {
		LinkedList<PolicyRow> ret = new LinkedList<PolicyRow>() ;
		add("СНИЛС: ФИО и год рождения", theSnilsService.findSnils(aLastname, aFirstname, aMiddlename, aBirthDate), ret, aMessageId) ;
		//add("СНИЛС: ФИО", theSnilsService.findSnilsByFio(aLastname, aFirstname, aMiddlename), ret) ;
		adde("ПОЛИСЫ: ФИО", theExternalService.findPersonInfos(aLastname, aFirstname, aMiddlename), ret, aMessageId) ;
		return ret;
	}

	private void copyPolicyInfoToRegistryEntry(int type, long policyId, RegistryEntry entry) {
		if(type==1) {
			OmcSnils snils = theManager.find(OmcSnils.class, policyId) ;
			entry.setSPolis(snils.getPolicySeries());
			entry.setNPolis(snils.getPolicyNumber()) ;
			entry.setInsuranceCompany(snils.getInsuranceCompany()) ;
			if(!StringUtil.isNullOrEmpty(snils.getSnils())) {
				entry.setPatientSnils(snils.getSnils()) ;
			}
		} else {
			ExternalPersonInfo info = theManager.find(ExternalPersonInfo.class, policyId) ;
			entry.setNPolis(info.getPolicyNumber()) ;
			entry.setSPolis(info.getPolicySeries()) ;
			entry.setInsuranceCompany(info.getInsuranceCompany()) ;
			entry.setWorksCode(info.getOrgCodeNew()) ;
			entry.setWorksOldCode(info.getOrgCodeOld()) ;
		}
	}
	/**
	 * 
	 * @param aParameters messageid,1-snils||2-person,policiId
	 */
	public long  acceptPolicy(String aParameters) {
		String[] pars = aParameters.split(",") ;
		long messageId = Long.parseLong(pars[0]) ;
		long policyId = Long.parseLong(pars[2]) ;
		int type = Integer.parseInt(pars[1] );
		Message message = theManager.find(Message.class, messageId) ;  
		RegistryEntry entry = theManager.find(RegistryEntry.class, message.getDataId()) ;
		copyPolicyInfoToRegistryEntry(type, policyId, entry);
		print(type, entry.getUrlEdit(), policyId) ;
		long checkId = getNextMessageId(message) ;
		theManager.remove(message) ;
		return checkId ;
	}
	
	
	public long getNextMessageId(long aMessageId) {
		return getNextMessageId(theManager.find(Message.class, aMessageId)) ;
	}
	
	private long getNextMessageId(Message aMessage) {
		
		List<Message> messages = theManager.createQuery("from Message where id>:id and importTime_id=:time and check=:check order by id")
		.setParameter("id", aMessage.getId())
		.setParameter("time", aMessage.getImportTime())
		.setParameter("check", aMessage.getCheck())
		.setMaxResults(2).getResultList();
		return messages.iterator().next().getId() ;
	}
	
	private void print(int aType, String aUrlEdit, long aPolicyId) {
		try {
			PrintWriter out = new PrintWriter(new FileWriter("policyReplaced.txt",true)) ;
			out.println(aType+","+aUrlEdit+","+aPolicyId) ;
			out.close() ;
		} catch (Exception e) {
			throw new RuntimeException("Ошибка записи файла",e) ;
		}
	}
	
	private static void adde(String aFindedType, Collection<Map<String, Object>> aSource, List<PolicyRow> aDest, long aMessageId) {
		//
		for(Map<String,Object> map:aSource) {
			PolicyRow row = new PolicyRow(2, (Long)map.get(IExternalPersonInfoService.ID), aMessageId) ;
			row.setFindedType(aFindedType) ;
			row.setLastname((String)map.get(IExternalPersonInfoService.LASTNAME)) ;
			row.setFirstname((String)map.get(IExternalPersonInfoService.FIRSTNAME)) ;
			row.setMiddlename((String)map.get(IExternalPersonInfoService.MIDDLENAME)) ;
			row.setBirthDate((Date)map.get(IExternalPersonInfoService.BIRTHDATE)) ;
			row.setInsuranceCompany((String)map.get(IExternalPersonInfoService.INSURANCE_COMPANY)) ;
			row.setPolicyNumber((String)map.get(IExternalPersonInfoService.POLICY_NUMBER)) ;
			row.setPolicySeries((String)map.get(IExternalPersonInfoService.POLICY_SERIES)) ;
			aDest.add(row) ;
		}
		
	}
	private static void add(String aFindedType, Collection<OmcSnils> aSource, List<PolicyRow> aDest, long aMessageId) {
		//
		for(OmcSnils s:aSource) {
			PolicyRow row = new PolicyRow(1, s.getId(), aMessageId) ;
			row.setFindedType(aFindedType) ;
			row.setLastname(s.getLastname1()) ;
			row.setFirstname(s.getFirstname1()) ;
			row.setMiddlename(s.getMiddlename1()) ;
			row.setBirthYear(s.getBirthYear()) ;
			row.setSnils(s.getSnils()) ;
			row.setInsuranceCompany(s.getInsuranceCompany()) ;
			row.setPolicyNumber(s.getPolicyNumber()) ;
			row.setPolicySeries(s.getPolicySeries()) ;
			aDest.add(row) ;
		}
	}
	private @EJB IOmcSnilsService theSnilsService ;
	private @EJB IExternalPersonInfoService theExternalService ;
    private @PersistenceContext EntityManager theManager;
}
