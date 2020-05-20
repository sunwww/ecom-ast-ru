package ru.ecom.mis.web.dwr.claim;

import org.jdom.IllegalDataException;
import org.json.JSONObject;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * 
 * @author vtsybulin
 * 01.2016
 * Сервис для работы с заявками в тех. поддержку
 *
 */
public class ClaimServiceJs {
	public static String sendToUserConfirm (String aId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		return ""+service.executeUpdateNativeSql("update claim set completeconfirmed = null where id="+aId);
	}
	public static String setComment (String aId, String comment, HttpServletRequest aRequest) throws NamingException {
		
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		return ""+service.executeUpdateNativeSql("update claim set executorcomment = '"+comment+"' where id="+aId);
	}
	public static String setStatusClaim (String aStatus, String aId,String aDate, String aTime, String aUsername, String aComment, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		if (aStatus==null) return "Не указан статус";
		if (!aStatus.equalsIgnoreCase("FINISH") && (aUsername==null || aUsername.equals(""))) {
			aUsername = LoginInfo.find(aRequest.getSession(true)).getUsername();
		}
			
		if (aDate!=null&&!aDate.equals("")) {
			aDate = "to_date('"+aDate+"', 'dd.MM.yyyy')";
		} else {
			aDate = "current_date";
		}
		if (aTime!=null&&!aTime.equals("")) {
			aTime = "cast('"+aTime+"' as time)";
		} else {
			aTime = "current_time";
		}
		StringBuilder sql = new StringBuilder();
		sql.append("update claim set ").append(aStatus)
				.append("Date = ").append(aDate)
				.append(", ").append(aStatus).append("Time =").append(aTime);

		if (aComment!=null&&!aComment.equals(""))
			sql.append(", executorComment='").append(aComment).append("'");

		if (aUsername!=null&&!aUsername.equals(""))
			sql.append(", ").append(aStatus).append("Username='").append(aUsername).append("'");
		else if (aStatus.equalsIgnoreCase("FINISH"))
			sql.append(", finishUsername = startWorkUsername, canceldate=null, cancelusername=null");

		if (!aStatus.equalsIgnoreCase("FREEZE")) {
			sql.append(", freezeDate = null, freezeusername = null");
		}
		if (aStatus.equalsIgnoreCase("FINISH")) {
			sql.append(",startworkusername= case when startworkusername is null then '").append(aUsername).append("' else startworkusername end")
					.append(",startworkdate= case when startworkdate is null then ").append(aDate).append(" else startworkdate end")
					.append(",startworktime= case when startworktime is null then ").append(aTime).append(" else startworktime end");
		}
		sql.append(" where id="+aId);

		return aStatus+" : "+ service.executeUpdateNativeSql(sql.toString());
		
	}

	public static String setViewed (String aId, HttpServletRequest aRequest) throws NamingException {
		return setStatusClaim("View", aId, null, null, null, null, aRequest);
	}
	
	public static String setStartClaim(String aId, String aDate, String aTime, String aExecutorLogin, HttpServletRequest aRequest) throws NamingException {
		if (aExecutorLogin==null||aExecutorLogin.equals("")) {
			throw new IllegalDataException ("Не указан исполнитель");
		}
		
		if (aDate!=null&&!aDate.equals("")) {
			aDate = "to_date('"+aDate+"', 'dd.MM.yyyy')";
		} else {
			aDate = "current_date";
		}
		if (aTime!=null&&!aTime.equals("")) {
			aTime = "cast('"+aTime+"' as time)";
		} else {
			aTime = "current_time";
		}
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		
		String sql = "update Claim set startWorkDate = "+aDate+", startWorkTime = "+aTime+", startworkUsername = '"+aExecutorLogin+"' where id="+aId;
		//System.out.println("===== "+sql);
		service.executeUpdateNativeSql(sql);
		return aId;
	}

	/**
	 * Получить текст и id типа заявки soft для скриншотов #77.
	 * @param aRequest HttpServletRequest
	 */
	public static String getSoftType (HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		Collection<WebQueryResult> list = service.executeNativeSql("select id,name from vocclaimtype where code='SOFT'");
		JSONObject res = new JSONObject();
		if (!list.isEmpty()) {
			WebQueryResult w = list.iterator().next() ;
			res.put("id", w.get1())
					.put("name", w.get2());
		}
		return res.toString();
	}


	/**
	 * Сохранить скриншота ошибки #77.
	 * Переименование папки для единообразия всех настроек
	 * Один метод сохранения файла на сервер (по полному пути из настроек), который возвращает его относительный путь
	 * @param aRequest HttpServletRequest
	 * @return String путь
	 */
	public String postRequestWithErrorScrean(String file,String filename,HttpServletRequest aRequest) throws IOException {
		String base64Image = file.split(",")[1];
		BufferedImage image ;
		byte[] imageByte;

		BASE64Decoder decoder = new BASE64Decoder();
		imageByte = decoder.decodeBuffer(base64Image);
		ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
		image = ImageIO.read(bis);
		bis.close();
		EjbEcomConfig cnf = EjbEcomConfig.getInstance();
		String fileway=cnf.get("tomcat.screenshot.dir","/opt/tomcat/webapps/screenshotdir/");
		String retVal=fileway;
		fileway=fileway.equals("")? fileway:fileway+"/";
		File outputfile = new File(fileway+filename);
		ImageIO.write(image, "png", outputfile);
        //в конце должна быть папка /имя_конечной_папки/
        if (!retVal.startsWith("/")) retVal="/"+retVal;
        if (retVal.endsWith("/")) retVal=retVal.substring(0,retVal.length()-1);
		return retVal.substring(retVal.lastIndexOf('/'))+"/"+filename;
	}
}