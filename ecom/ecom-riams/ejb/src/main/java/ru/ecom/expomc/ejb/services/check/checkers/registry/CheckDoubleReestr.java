package ru.ecom.expomc.ejb.services.check.checkers.registry;

/**
 * Проверить на наличие дублей в реестре
 */

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import ru.ecom.expomc.ejb.domain.registry.RegistryEntry;
import ru.ecom.expomc.ejb.services.check.CheckException;
import ru.ecom.expomc.ejb.services.check.CheckResult;
import ru.ecom.expomc.ejb.services.check.ICheck;
import ru.ecom.expomc.ejb.services.check.ICheckContext;
import ru.ecom.expomc.ejb.services.check.checkers.BadPropertyUtil;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Проверить на наличие дублей в реестре")
public class CheckDoubleReestr implements ICheck {
	@PersistenceUnit
	EntityManagerFactory emf;
    @PersistenceContext() 
    EntityManager em;

	    public CheckResult check(ICheckContext aContext) throws CheckException 
	       {
	    	RegistryEntry entry=(RegistryEntry) aContext.getEntry();
	        Long  fillTime= entry.getTime() ;
	    	String  sPolis= entry.getSPolis() ;
	        String  NPolis= entry.getNPolis() ;
	        String  depType= entry.getDepType() ;
	        Date  dischargeDate= entry.getDischargeDate();
	        Date  admissionDate= entry.getAdmissionDate() ;
	        String clause = " where \"time\" = \'"+ fillTime + "\' and \"SPolis\" = \'" + sPolis; 
	        clause=clause + "\' and \"NPolis\" = \'" + NPolis; 
	        clause=clause + "\' and \"depType\" = \'" + depType;
	        clause = clause + "\' and \"dischargeDate\" = \'" + convertDate(dischargeDate); 
	        clause=clause + "\' and \"admissionDate\" = \'" + convertDate(admissionDate) + "\'";
	        String queryString = "from RegistryEntry " + clause;
	        //EntityManagerFactory emf=javax.persistence.Persistence.createEntityManagerFactory();
	        //EntityManager em = emf.createEntityManager();
			System.out.println("emf="+emf+",manager="+em+", query="+queryString);
	        List<RegistryEntry> list= em.createQuery(queryString).getResultList();
	        Boolean res=true;
	        if (list!=null) {res=list.size()>1?true:false;}
	        CheckResult result = CheckResult.createAccepted(res);
	    	//em.close() ;
	        return result;
	       }

		public Collection<String> getBadProperties() {
			// TODO Auto-generated method stub
			return BadPropertyUtil.create("time","SPolis","NPolis","depType","dischargeDate","admissionDate");
		}
		public int convertDate(Date aDate){
			Calendar cal = java.util.Calendar.getInstance() ;
			cal.setTime(aDate) ;
			long time=cal.getTimeInMillis();
			int days = Math.round(time / (1000 * 60 * 60 * 24)+47028);
			return days;
		}

}

//public class CheckDoubleReestr extends AbstractRegistryAcceptedCheck {
//
//	    public boolean isAccepted(RegistryEntry aEntry, ICheckLog aLog) 
//	       {
//	        Long  fillTime= aEntry.getTime() ;
//	    	String  sPolis= aEntry.getSPolis() ;
//	        String  NPolis= aEntry.getNPolis() ;
//	        String  depType= aEntry.getDepType() ;
//	        Date  dischargeDate= aEntry.getDischargeDate();
//	        Date  admissionDate= aEntry.getAdmissionDate() ;
//	        String clause = " where \"time\" = \'"+ fillTime + "\' and \"SPolis\" = \'" + sPolis; 
//	        clause=clause + "\' and \"NPolis\" = \'" + NPolis; 
//	        //clause=clause + "\' and \"depType\" = \'" + depType;
//	        clause = clause + "\' and \"dischargeDate\" = \'" + convertDate(dischargeDate); 
//	        clause=clause + "\' and \"admissionDate\" = \'" + convertDate(admissionDate) + "\'";
//	        String queryString = "from RegistryEntry " + clause;
//			System.out.println(queryString);
//			EntityManager manager=null;
//	        List<RegistryEntry> list= manager.createQuery(queryString).getResultList();
//	        manager.close();
//	        return list!=null?list.size()>1:false;
//	       }
//
//		public Collection<String> getBadProperties() {
//			// TODO Auto-generated method stub
//			return BadPropertyUtil.create("time","SPolis","NPolis","depType","dischargeDate","admissionDate");
//		}
//		public int convertDate(Date aDate){
//			Calendar cal = java.util.Calendar.getInstance() ;
//			cal.setTime(aDate) ;
//			long time=cal.getTimeInMillis();
//			int days = Math.round(time / (1000 * 60 * 60 * 24)+47028);
//			return days;
//		}
//	 }

