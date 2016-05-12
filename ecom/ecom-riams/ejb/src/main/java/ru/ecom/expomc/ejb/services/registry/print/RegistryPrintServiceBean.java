package ru.ecom.expomc.ejb.services.registry.print;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import javax.annotation.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import ru.ecom.ejb.services.file.IJbossGetFileLocalService;
import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.util.QueryIteratorUtil;
import ru.ecom.expomc.ejb.domain.format.Format;
import ru.ecom.expomc.ejb.domain.impdoc.ImportTime;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcKl;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcLpu;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcSk;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcTariff;
import ru.ecom.expomc.ejb.domain.registry.LpuFond;
import ru.ecom.expomc.ejb.domain.registry.RegistryEntry;
import ru.ecom.expomc.ejb.services.exportservice.ExportServiceBean;
import ru.ecom.expomc.ejb.services.exportservice.IBeforeSaveInterceptor;
import ru.ecom.expomc.ejb.services.registry.CreateRegistryForm;
import ru.ecom.expomc.ejb.services.timeservice.IImportTimeService;
import ru.ecom.report.excel.ReportEngine;
import ru.ecom.report.replace.BshValueGetter;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;

/**
 * Печать реестра
 */
@Stateless
@Remote(IRegistryPrintService.class)
public class RegistryPrintServiceBean implements IRegistryPrintService {

	private ReportEngine theReportEngine = new ReportEngine();

	final String JBOSS_SERVER_DATA_DIR = System
			.getProperty("jboss.server.data.dir");

	private Collection<String> makeInsuranceCompanyList(long aTimeId,
			long aTimeDsId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select insuranceCompany from RegistryEntry");
		sb.append(" where time=").append(aTimeId);
		if (aTimeDsId != 0) {
			sb.append(" or time=").append(aTimeDsId);
		}
		sb.append(" group by insuranceCompany");
		LinkedList<String> ret = new LinkedList<String>();
		//System.out.println("query=" + sb);
		List<String> list = theManager.createQuery(sb.toString())
				.getResultList();
		for (String row : list) {
			ret.add(row);
			//System.out.println(row);
		}
		return ret;
	}

	public void printReestr(long aMonitorId, long aFileId,
			CreateRegistryForm aForm) throws RegistryPrintException {
		IMonitor monitor = null;
		try {
			// Сделать список кодов страховых компаний
			Collection<String> companies = makeInsuranceCompanyList(aForm
					.getTimeImport(), aForm.getTimeImportDs());

			monitor = theMonitorService.startMonitor(aMonitorId,
					"Печать реестра", 4);
			String suffix = aForm.getExportType()==CreateRegistryForm.EXPORT_EXCEl ? "excel" : "dbf" ;
			File file = theJbossGetFileLocalService.createFile(aFileId,
					two(aForm.getCompany())+"_"+suffix+".zip");

			File dir = new File("/tmp/" + System.currentTimeMillis());
			dir.mkdirs();
			
			printByImportTime(monitor, dir, aForm.getTimeImport(), aForm,
					aForm.getCompany(), new File(JBOSS_SERVER_DATA_DIR
							+ "/xlstemplate/"+aForm.getTemplate()));
			
			sun.tools.jar.Main m = new sun.tools.jar.Main(System.out, System.err, "jar");
			String[] cmd = {"cfM",file.getAbsolutePath(),"-C",dir.getAbsolutePath(),"."} ;
		    if(!m.run(cmd)) throw new IllegalArgumentException("Ошибка создания архива "+file.getAbsoluteFile()+" из каталога "+dir.getAbsolutePath()) ;
			
			monitor.finish(aFileId + "");
		} catch (Exception e) {
			if (monitor != null)
				monitor.error("Ошибка:" + e.getMessage(), e);
			throw new RegistryPrintException("Ошибка", e);
		}
	}

	private static String two(String aStr) {
		StringBuilder sb = new StringBuilder(aStr) ;
		while(sb.length()<2) {
			sb.insert(0, '0') ;
		}
		return sb.toString() ;
	}
	private static String two(int aInt) {
		return (aInt < 10 ? "0" : "") + aInt;
//		StringBuilder sb = new StringBuilder(aInt) ;
//		if (aInt < 10) {sb.insert(0, '0') ;}
//		return sb.toString() ;
	}
	private void printByImportTime(IMonitor aMonitor, File aDir,
			long aImportTime, CreateRegistryForm aForm,
			String companyId, File aTemplateFle) throws ParseException,
			RegistryPrintException, Exception {
		// Печать счетов (Excel)
		// 
		
			String companyName = theTimeService.getName(OmcSk.class, companyId, new java.sql.Date(new Date().getTime())) ;
			
			if(aForm.getExportType()==CreateRegistryForm.EXPORT_EXCEl) {
				// Работающие
				aMonitor.advice(1) ;
				aMonitor.setText(companyName+" : Работающие") ;
				printByGroup(aDir, "registryEntry.byGroupWorking"
						, aForm.getBillDate()
						, aForm.getBillWorking(), aForm.getRegWorking()
						, aImportTime, companyId, companyName, aTemplateFle
						, "Работающие");
				
				// Пенсионеры
				aMonitor.advice(1) ;
				aMonitor.setText(companyName+" : Пенсионеры") ;
				printByGroup(aDir, "registryEntry.byGroupPens"
						, aForm.getBillDate()
						, aForm.getBillPens(), aForm.getRegPens()
						, aImportTime, companyId, companyName, aTemplateFle
						, "Пенсионеры") ;
	
				
				
				// Прочие неработающие
				aMonitor.advice(1) ;
				aMonitor.setText(companyName+" : Прочие неработающие") ;
				printByGroup(aDir, "registryEntry.byGroupOther"
						, aForm.getBillDate()
						, aForm.getBillOther(), aForm.getRegOther()
						, aImportTime, companyId, companyName, aTemplateFle
						, "Прочие неработающие");
				
				
				if(!StringUtil.isNullOrEmpty(aForm.getRegChild())) {
					// Дети
					aMonitor.advice(1) ;
					aMonitor.setText(companyName+" : Дети") ;
					printByGroup(aDir, "registryEntry.byGroupChilds"
							, aForm.getBillDate()
							, aForm.getBillChild(), aForm.getRegChild()
							, aImportTime, companyId, companyName, aTemplateFle
							, "Дети");
				} else {
					// Дети (55)
					aMonitor.advice(1) ;
					aMonitor.setText(companyName+" : Дети (55)") ;
					printByGroup(aDir, "registryEntry.byGroupChilds55"
							, aForm.getBillDate()
							, aForm.getBillChild55(), aForm.getChild55()
							, aImportTime, companyId, companyName, aTemplateFle
							, "Дети (55)");
					
					// Подростки (50)
					aMonitor.advice(1) ;
					aMonitor.setText(companyName+" : Подростки (50)") ;
					printByGroup(aDir, "registryEntry.byGroupChilds50"
							, aForm.getBillDate()
							, aForm.getBillChild50(), aForm.getChild50()
							, aImportTime, companyId, companyName, aTemplateFle
							, "Подростки (50)");
				}
				
				
			} else {
				aMonitor.advice(1);
				aMonitor.setText(companyName+" : Работающие : DBF") ;
				printByCompany(aDir, aForm.getBillDate(), aForm.getBillWorking(), aImportTime, companyId, aForm.getFormat()) ;
	
				aMonitor.advice(1);
				aMonitor.setText(companyName+" : Пенсионеры : DBF") ;
				printByCompany(aDir, aForm.getBillDate(), aForm.getBillPens(), aImportTime, companyId, aForm.getFormat()) ;
	
				aMonitor.advice(1);
				aMonitor.setText(companyName+" : Дети : DBF") ;
				printByCompany(aDir, aForm.getBillDate(), aForm.getBillChild(), aImportTime, companyId, aForm.getFormat()) ;
	
				aMonitor.advice(1);
				aMonitor.setText(companyName+" : Прочие неработающие : DBF") ;
				printByCompany(aDir, aForm.getBillDate(), aForm.getBillOther(), aImportTime, companyId, aForm.getFormat()) ;
			}
	}

//	public void _printReestr(long aMonitorId, long aFileId,
//			CreateRegistryForm aForm) throws ParseException,
//			RegistryPrintException {
//
//		switch (aForm.getGroup()) {
//		case CreateRegistryForm.GROUP_WORKING:
//			printByGroup(aMonitorId, aFileId, "registryEntry.byGroupWorking",
//					aForm.getBillDate(), aForm.getBillNumber(), aForm
//							.getRegWorking(), aForm.getTimeImport(), aForm
//							.getCompany());
//			break;
//		case CreateRegistryForm.GROUP_PENS:
//			printByGroup(aMonitorId, aFileId, "registryEntry.byGroupPens",
//					aForm.getBillDate(), aForm.getBillNumber(), aForm
//							.getRegPens(), aForm.getTimeImport(), aForm
//							.getCompany());
//			break;
//		case CreateRegistryForm.GROUP_OTHER:
//			printByGroup(aMonitorId, aFileId, "registryEntry.byGroupOther",
//					aForm.getBillDate(), aForm.getBillNumber(), aForm
//							.getRegOther(), aForm.getTimeImport(), aForm
//							.getCompany());
//			break;
//		case CreateRegistryForm.GROUP_CHILD:
//			printByGroup(aMonitorId, aFileId, "registryEntry.byGroupChilds",
//					aForm.getBillDate(), aForm.getBillNumber(), aForm
//							.getRegChild(), aForm.getTimeImport(), aForm
//							.getCompany());
//			break;
//		case CreateRegistryForm.GROUP_ALL:
//			printByCompany(aMonitorId, aFileId, aForm.getBillDate(), aForm
//					.getBillNumber(), aForm.getTimeImport(),
//					aForm.getCompany(), aForm);
//			break;
//
//		default:
//			throw new IllegalStateException("Не реализовано");
//		}
//
//	}

	private void printByCompany(File aDir,
			String aBillDate, String aBillNumber, long aTimeImport,
			String aCompanyCode, long aFormat) throws Exception {
		
		Query query = theManager.createNamedQuery("registryEntry.byCompany");
		query.setParameter("time", aTimeImport);
		query.setParameter("company", aCompanyCode);
		query.setParameter("bill", aBillNumber);
		System.out.println("RegistryPrintService: time="+aTimeImport+", company="+aCompanyCode+", bill="+aBillNumber);
		List<RegistryEntry> entries = query.getResultList();
		if(entries.isEmpty()) {
			System.err.println("RegistryPrintService: Нет данных time="+aTimeImport+", company="+aCompanyCode+", bill="+aBillNumber);
			return ;
		}
		
		int i = 0;
		for(RegistryEntry entry: entries) {
			i++;
			entry.setSerialNumber(String.valueOf(i));
			//theManager.persist(entry) ;
			//theManager.flush();
			//theManager.clear();
		}
		LpuFond firstEntry = entries.iterator().next();		//zav
		Date dischargeDate = firstEntry.getDischargeDate();
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(dischargeDate);
//        int month = cal.get(cal.MONTH)+1;			//zav
		String dbfFileName = dbfFileName(aCompanyCode, aBillNumber, dischargeDate); //zav 
//		File file = new File(aDir, two(aCompanyCode) + two(aBillNumber) + (month<10?"0":"") + month + ".dbf" ); // FIXME //zav
		File file = new File(aDir, dbfFileName + ".dbf" ); //zav

			Query query2 = theManager.createNamedQuery("registryEntry.byCompany");
			query2.setParameter("time", aTimeImport);
			query2.setParameter("company", aCompanyCode);
			query2.setParameter("bill", aBillNumber);
			Iterator iterator = QueryIteratorUtil.iterate(query2);

			Format format = theManager.find(Format.class, aFormat) ;
			ExportServiceBean.export(format, iterator, file,
					null, new Long(entries.size()),
					new BeforeSaveInterceptor());

	}

	private static class BeforeSaveInterceptor implements
			IBeforeSaveInterceptor {
		public void beforeSave(Object aEntity) {
			id++;
			RegistryEntry entry = (RegistryEntry) aEntity;
			entry.setSerialNumber(id + "");
		}

		private long id = 0;
	}

	private void printByGroup(File aDir, String aQuery, String aBillDateStr,
			String aBillNumber, String aRegNumber, long aTime, String aCompany
			, String aCompanyName, File aTemplateFile, String aGroupName)
			throws ParseException, RegistryPrintException {
		// IMonitor monitor = null;

		// entry.setBillDate(d) ;

		ImportTime importTime = theManager.find(ImportTime.class, aTime);
		Query query = theManager.createNamedQuery(aQuery);
		query.setParameter("time", aTime);
		query.setParameter("company", aCompany);
		List<RegistryEntry> entries = query.getResultList();
		// monitor = theMonitorService.startMonitor(aMonitorId, "Печать
		// реестра", entries.size());

		EntityManager manager = theFactory.createEntityManager();
		Query query2 = manager.createNamedQuery(aQuery);
		query2.setParameter("time", aTime);
		query2.setParameter("company", aCompany);
		List<RegistryEntry> entries2 = query2.getResultList();

		java.sql.Date d = new java.sql.Date(DateFormat.parseDate(aBillDateStr)
				.getTime());
		for (RegistryEntry entry2 : entries2) {
			entry2.setRegistryNumber(aRegNumber);
			entry2.setBillDate(d);
			entry2.setBillNumber(aBillNumber);
			manager.persist(entry2);
			// manager.flush() ;
			// manager.clear() ;
		}
		manager.close();
		try {
			// File file = theJbossGetFileLocalService.createFile(aFileId,
			// "lpufond.xls");
			File file = new File(aDir, two(aCompany) + "_" + two(aBillNumber) + "_"
					+ two(aRegNumber) + ".xls"); // FIXME

			RegistryPrintHolder holder = createPrintHolder(importTime
					.getActualDateFrom(), importTime.getActualDateTo(),
					entries, aRegNumber, DateFormat.parseDate(aBillDateStr),
					aBillNumber);
			holder.setWorkingName(aGroupName) ;
			holder.setCompanyName(aCompanyName) ;
			BshValueGetter bshGetter = new BshValueGetter();
			bshGetter.set("h", holder);
			if(!holder.getRenders().isEmpty()) {
				theReportEngine.make(aTemplateFile, file, bshGetter, 0);
			}
			// monitor.finish(aFileId + "");
		} catch (Exception e) {
			// monitor.error("Ошибка печати реестра: " + e, e);
			throw new RegistryPrintException("Ошибка печати реестра", e);
		}

	}

	public void printReestrXls(String aFilename, long aPeriodId)
			throws RegistryPrintException {
		//
		// List<RegistryEntry> entries = theManager.createQuery("from
		// RegistryEntry").getResultList();
		// RegPeriod period = theManager.find(RegPeriod.class, aPeriodId) ;
		// RegistryPrintHolder holder = createPrintHolder(period, entries, );
		//
		//
		// try {
		// BshValueGetter bshGetter = new BshValueGetter();
		// bshGetter.set("h", holder);
		// theReportEngine.make(
		// new File(JBOSS_SERVER_DATA_DIR+"/xlstemplate/registry.xls")
		// ,new File(aFilename), bshGetter, 0);
		// } catch (Exception e) {
		// throw new RegistryPrintException("Ошибка экспорта в excel",
		// CopyException.copyException(e));
		// }
		//
	}

	private RegistryPrintHolder createPrintHolder(java.sql.Date aDateFrom,
			java.sql.Date aDateTo, List<RegistryEntry> aEntries,
			String aRegistryNumber, Date aBillDate, String aBillNumber) {

		RegistryPrintHolder ret = new RegistryPrintHolder();
		if (aEntries != null && aEntries.size() == 0) {
			return ret;
		}
		RegistryEntry firstEntry = aEntries.iterator().next();
		//zav
		String lpuCode = firstEntry.getKodLpu(); 
		String qStr="from OmcLpu where voc_code = \'" + lpuCode + "\'"; 
		Query rs = theManager.createQuery(qStr); 
		List<OmcLpu> omcLpu = rs.getResultList();
		String lpuName = omcLpu.get(0)!=null?omcLpu.get(0).getName():"";
		String dbfFileName = dbfFileName(firstEntry.getInsuranceCompany(), aBillNumber, firstEntry.getDischargeDate()); //zav 
		//zav		
		ret.setCount(aEntries.size());
		ret.setBillDate(aBillDate);
		ret.setBillNumber(aBillNumber);
//		ret.setDbfFilename(two(firstEntry.getInsuranceCompany())+two(aBillNumber)+"02"); // FIXME
		ret.setDbfFilename(dbfFileName); //zav
		ret.setDbfSizeBytes(0); // todo
		ret.setLpuName(lpuName);
		ret.setLpuCategoryName(theTimeService.getName(OmcKl.class, firstEntry
				.getLevel(), firstEntry.getActualDate())); // todo
		ret.setPeriodFrom(aDateFrom);
		ret.setPeriodTo(aDateTo);
		ret.setRegistryNumber(aRegistryNumber);
		ret.setWorkingName("10".equals(firstEntry.getSgroup()) ? "Работающие"
				: "Неработающие");

		// по услугам
		TreeMap<String, RegistryPrintRenderHolder> aRenders = new TreeMap<String, RegistryPrintRenderHolder>();
		// по районам
		TreeMap<String, RegistryPrintGroupHolder> byRegions = new TreeMap<String, RegistryPrintGroupHolder>();

		for (RegistryEntry entry : aEntries) {
			add(aRenders, entry);
			addByRegion(byRegions, entry);
			ret.addSumm(entry.getCasePrice());

			// entry.setRegistryNumber(aRegistryNumber) ;
			// java.sql.Date d = new
			// java.sql.Date(aBillDate!=null?aBillDate.getTime():new
			// Date().getTime()) ;
			// entry.setBillDate(d) ;
			// entry.setBillNumber(aBillNumber) ;
			// theManager.persist(entry);
			// theManager.flush() ;
			// theManager.clear() ;
		}

		int i = 0;
		for (RegistryPrintRenderHolder renderHolder : aRenders.values()) {
			renderHolder.setSerialNumber(++i);
			ret.getRenders().add(renderHolder);
		}

		for (RegistryPrintGroupHolder group : byRegions.values()) {
			ret.getRegionGroups().add(group);
		}

		return ret;
	}

	private void addByRegion(
			TreeMap<String, RegistryPrintGroupHolder> byRegions,
			RegistryEntry aEntry) {
		String region = aEntry.getRegion();
		if (StringUtil.isNullOrEmpty(region)) {
			region = "";
		}
		RegistryPrintGroupHolder g = byRegions.get(region);
		if (g == null) {
			g = new RegistryPrintGroupHolder();
			byRegions.put(region, g);
		}
		g.setName(region);
		g.addSumm(aEntry.getCasePrice());
		g.addCount(1);
		// ;
	}

	private void add(TreeMap<String, RegistryPrintRenderHolder> aRenders,
			RegistryEntry aEntry) {

		String key = aEntry.getRender() + "-" + aEntry.getLevel();
		RegistryPrintRenderHolder rh = aRenders.get(key);
		if (rh == null) {
			rh = new RegistryPrintRenderHolder();
			aRenders.put(key, rh);
			rh.setRenderCode(aEntry.getRender());
			rh.setRenderName(theTimeService.getName(OmcTariff.class, aEntry
					.getRender(), aEntry.getActualDate()));
			rh.setTariff(aEntry.getTariff()!=null?aEntry.getTariff():aEntry.getCasePrice()); //zav			
		}


		//zav
		//BigDecimal zero = new BigDecimal(1);
		int realBD = aEntry.getBedDays();
		BigDecimal calcBD = aEntry.getCalcBedDays();
        //if (calcBD.compareTo(zero)==-1) {calcBD=realBD;};
		//calcBD=realBD;
		//zav

		//rh.setBedDaysCalc(aEntry.getCalcBedDays()); // todo from MY_FIELD //zav
		rh.addBedDaysCalc(calcBD); //zav
		rh.addBedDaysReal(realBD);
		rh.setLevel(aEntry.getLevel());
		rh.addPrice(aEntry.getCasePrice());
		rh.addRenderCount(1);
		rh.setSerialNumber(-1);
		//rh.setTariff(aEntry.getTariff()); // todo FROM MY_FIELD //zav
	}
	
	private String dbfFileName(String aCompany, String aBillNumber, Date aDate) { //zav
		String name = new String();
        Calendar cal = Calendar.getInstance();
        cal.setTime(aDate);
        int month = cal.get(cal.MONTH)+1;
        name = aCompany + two(aBillNumber) + two(month);
		return name;
	}

	private @PersistenceContext
	EntityManager theManager;

	private @EJB
	IImportTimeService theTimeService;

	private @EJB
	IJbossGetFileLocalService theJbossGetFileLocalService;

	private @EJB
	ILocalMonitorService theMonitorService;

	private @PersistenceUnit
	EntityManagerFactory theFactory;

}
