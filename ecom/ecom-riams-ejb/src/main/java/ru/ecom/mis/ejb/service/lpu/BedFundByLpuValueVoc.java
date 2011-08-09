package ru.ecom.mis.ejb.service.lpu;

import java.sql.Date;
import java.text.ParseException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import ru.ecom.ejb.services.voc.helper.AllValueContext;
import ru.ecom.ejb.services.voc.helper.IAllValue;
import ru.ecom.mis.ejb.domain.lpu.BedFund;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.util.voc.VocValue;

public class BedFundByLpuValueVoc  implements IAllValue {
	
	public void destroy() {
		
	}

	
	@SuppressWarnings("unchecked")
	public Collection<VocValue> listAll(AllValueContext aContext) {
		LinkedList<VocValue> ret = new LinkedList<VocValue>() ;
		StringBuilder sql = new StringBuilder();

		if (aContext.getVocAdditional()==null || aContext.getVocAdditional()!=null && aContext.getVocAdditional().getParentId()==null) {
			//System.out.println("parent lpuAndDate="+aContext.getVocAdditional().getParentId()) ;
			sql.append("from BedFund");
			
			
			////????????????????????????
			List<BedFund> funds = null ;
			try{
				 funds = (List<BedFund>) aContext.getEntityManager()
					.createQuery(sql.toString())
					.getResultList() ;
			} catch (Exception e) {
				System.out.println("Error sql:="+e.getMessage()) ;
				return ret ;
			}
			System.out.println("size="+funds.size()) ;
			for(BedFund fund : funds) {
				 try {
					 add(ret, fund) ;
				 } catch (IllegalStateException e) {
					 System.out.println(e.getMessage()) ;
				 }
			}
			////////////////?????????????????????/
		}
		
		
		if (aContext.getVocAdditional()!=null && aContext.getVocAdditional().getParentId()!=null) {
			String parent =aContext.getVocAdditional().getParentId() ;
			int ind1 = parent.indexOf(":");
			int ind2 = parent.indexOf(":",ind1+1);
			if (ind1+1==ind2) return ret ;
			//int ind3 = parent.indexOf(":",ind2) ;
			System.out.println("ind1="+ind1);
			System.out.println("ind2="+ind2);
			//System.out.println("ind3="+ind3);
			System.out.println("parent lpuAndDate="+parent) ;
			Long lpu = Long.valueOf(parent.substring(0,ind1)) ;
			System.out.println("parent lpu="+lpu) ;
			Long serviceStream = Long.valueOf(parent.substring(ind1+1,ind2)) ;
			System.out.println("parent serviceStream="+serviceStream) ;
			String datest = parent.substring(ind2+1) ;
			System.out.println("parent date="+datest) ;
			
			Date date;
			//String date ;
			try {
				date = DateFormat.parseSqlDate(datest) ;
				/*
				date = parent.substring(parent.indexOf(":")+1);
				
				int ind1 = date.indexOf(".") ;
				int ind2=  date.lastIndexOf(".") ;
				date = new StringBuilder().append(date.substring(ind2+1))
					.append("-")
					.append(date.substring(ind1+1,ind2))
					.append("-")
					.append(date.substring(0,ind1)).toString() ;
				System.out.println("parent convert date="+date) ;*/
				
			} catch (Exception e1) {
				return ret ;
			}
			//Long lpu = Long.valueOf(parent.substring(0,ind1)) ;
			//Long serviceStream = Long.valueOf(parent.substring(ind1+1,ind2)) ;
			/*StringBuilder sql = new StringBuilder() ;
				sql.append("from BedFund where lpu=:lpu and cast('")
				.append(date)
				.append("' as date) between cast('")
				.append(date)
				.append("' as date) and isnull(dateFinish, cast('")
				.append(date)
				.append("' as date) ) ") ;
				*/
			sql.append("from BedFund where lpu_id=:lpu and serviceStream_id=:serviceStream and :date between dateStart and isnull(dateFinish,:date)");
			//sql.append("from BedFund where lpu_id=:lpu");
			System.out.println(sql.toString()) ;
			List<BedFund> funds = null ;
			try{
				 funds = (List<BedFund>) aContext.getEntityManager()
					.createQuery(sql.toString())
					.setParameter("date",date)
					.setParameter("serviceStream",serviceStream)
					.setParameter("lpu", lpu) 
					.getResultList() ;
			} catch (Exception e) {
				System.out.println("Error sql:="+e.getMessage()) ;
				return ret ;
			}
			System.out.println("size="+funds.size()) ;
			for(BedFund fund : funds) {
				 try {
					 add(ret, fund) ;
				 } catch (IllegalStateException e) {
					 System.out.println(e.getMessage()) ;
				 }
			}
		}
		return ret;
	}
	
	private static void add(List<VocValue> aValues, BedFund aBedFund) {
		StringBuilder name = new StringBuilder() ;
		
		name.append(aBedFund.getBedTypeName()) ;
		if (aBedFund.getBedSubType()!=null) name.append(" (").append(aBedFund.getBedSubType().getName()).append(")") ; 
		aValues.add(new VocValue(String.valueOf(aBedFund.getId()), name.toString())) ;
	}
}