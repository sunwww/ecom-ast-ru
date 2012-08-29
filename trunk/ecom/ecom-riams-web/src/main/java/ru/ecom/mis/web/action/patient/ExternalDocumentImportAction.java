package ru.ecom.mis.web.action.patient;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.spi.IIORegistry;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.jaas.web.action.policy.ExportPolicyForm;
import ru.ecom.mis.ejb.service.patient.IPatientService;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

import com.sun.imageio.plugins.jpeg.JPEGImageWriter;
import com.sun.media.imageioimpl.plugins.tiff.TIFFImageReaderSpi;
import com.sun.media.imageioimpl.plugins.tiff.TIFFImageWriterSpi;

public class ExternalDocumentImportAction extends BaseAction{

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm,
			HttpServletRequest aRequest, HttpServletResponse aResponse)
			throws Exception {
		// TODO Auto-generated method stub
		BufferedImage image = null ;
    	try {
    		/*
    		System.out.println("ReaderMIMETypes") ;
        	printlist(ImageIO.getReaderMIMETypes()) ;
        	System.out.println("ReaderFormatNames") ;
        	printlist(ImageIO.getReaderFormatNames()) ;
        	System.out.println("WriterMIMETypes") ;
        	printlist(ImageIO.getWriterMIMETypes()) ;
        	System.out.println("WriterFormatNames") ;
        	printlist(ImageIO.getWriterFormatNames()) ;
        	*/
    		String path = aRequest.getParameter("url_image_tmp") ;
    		String path1 = aRequest.getParameter("url_image_tmp_comp") ;
    		
    		IPatientService service = Injection.find(aRequest).getService(IPatientService.class);
    		
        	IIORegistry registry = IIORegistry.getDefaultInstance() ;
        	registry.registerServiceProvider(new TIFFImageWriterSpi()) ;
        	registry.registerServiceProvider(new TIFFImageReaderSpi()) ;
    		boolean isCompress = false ;
    		boolean isRecord = false ;
    		boolean isDeleteTmp = false ;
        	String url_image="" ;
        	Date dat = new Date() ;
        	String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
        	String dirmain = service.getImageDir() ;
        	String diradd = "" ;
    		url_image = dat.getTime()+"-"+username ;
    		Long objectId=null;
    		Long objectType = null ;
    		File tmp = null ;
    		float mcomp = 1f ;
    		//System.out.println(1) ;
    		
    		//System.out.println("path="+path) ;
    		if (path!=null && !path.equals("")) {
	    		//System.out.println(2) ;
	    		//mcomp = 0.25f ;
	    		tmp = new File(dirmain+path) ;
	    		image = ImageIO.read(tmp) ;
	    		isDeleteTmp = true ;
	    		isRecord = true ;
	    		objectId= ConvertSql.parseLong(aRequest.getParameter("objectId")) ;
	    		objectType = ConvertSql.parseLong(aRequest.getParameter("objectType")) ;
        		Calendar cal = Calendar.getInstance() ;
        		cal.setTime(dat) ;
        		int year = cal.get(Calendar.YEAR) ;
        		int month = cal.get(Calendar.MONTH)+1 ;
        		int day = cal.get(Calendar.DAY_OF_MONTH);
        		IWebQueryService wqservice = Injection.find(aRequest).getService(IWebQueryService.class) ;
        		String typeString = "unknown" ;
        		String sql = "select id,code from VocExternalDocumentType where id='"+objectType+"'" ;
        		Collection<WebQueryResult> list1 = wqservice.executeNativeSql(sql.toString(),1) ;
        		if (list1.size()>0) {
        			WebQueryResult wqr = list1.iterator().next() ;
        			if (wqr.get2()!=null && !wqr.get2().equals("")) typeString=String.valueOf(wqr.get2()) ;
        		}
        		diradd = "orig/"+typeString+"/"+year+"/"+month+"/"+day ;
        		isCompress = false ;
        		System.out.println(3) ;
	    	} else if (aForm!=null) {
	    		ExportPolicyForm form = aForm!=null?(ExportPolicyForm)aForm:null ;
	    		String contentType= form.getFile().getContentType() ;
            	image = ImageIO.read(form.getFile().getInputStream()) ;
            	objectId = form.getObjectId() ;
            	objectType = form.getType() ;
            	aRequest.setAttribute("objectId", objectId) ;
            	aRequest.setAttribute("objectType", objectType) ;
            	if (contentType.toLowerCase().equals("image/tiff")) {
            		//
            		
            	}
             	url_image = ""+ dat.getTime() ;
            	diradd = "tmp/"+username+"" ;
            	isCompress=true;
            	mcomp = 0.25f ;
	    	} else {
	    		return aMapping.findForward("success") ;
	    	}
    		//System.out.println(4) ;
	    		//System.out.println("file="+form.getFile().getContentType()+" "+form.getFile().getInputStream()) ;
	    		try {
	            	

	    			//image = Scalr.resize(image, image.getWidth()/2,image.getHeight()/2) ;
	            	File file = new File(dirmain+diradd) ;
            		if (!file.exists()) {
            			file.mkdirs() ;
            		}
            		 
            		//save(image, dirmain+"proba/");
            		diradd=diradd+"/"+url_image ;
            		//ImageIO.write(image, "jpg", new File(dirmain+diradd+".jpg")) ;
            		//ImageIO.write(image, "jpg", new File(dirmain+diradd+".jpg")) ;
	            	//ImageIO.write(image, "TIFF", new File(dirmain+diradd+".tif")) ;
	            	if (isRecord) {
	            		tmp.renameTo(new File(dirmain+diradd+".jpg"));
	            	} else {
	            		save(image,mcomp,dirmain+diradd+".jpg") ;
	            	}
	            	//image = ImageIO.read(new File(dirmain+diradd+".jpg"));
	            	String newName = dirmain+diradd+"-comp.jpg" ;
	            	if (isCompress) {
	            		float comp = service.getImageCompress()*mcomp ;
	            		System.out.println("compress...") ;
	            		//image = Scalr.resize(image, image.getWidth()/2,image.getHeight()/2) ;
		            	save(image,comp,newName) ;
	            	} else {
	            		System.out.println(newName) ;
	            		System.out.println(path1) ;
	            		new File(dirmain+path1).renameTo(new File(newName));
	            	}
	            	if(isRecord) {
	            		service.insertExternalDocumentByObject("Patient", objectId, objectType
	            				, diradd+"-comp.jpg", diradd+".jpg", "", username) ;
	            		aRequest.setAttribute("result_image", "Обработано и записано") ;
		            	aRequest.setAttribute("url_image", diradd+"-comp.jpg") ;
	            	} else {
		            	aRequest.setAttribute("url_image", diradd+".jpg") ;
		            	aRequest.setAttribute("url_image_comp", diradd+"-comp.jpg") ;
	            	}
	            	
	            	
	            	
	            }catch(Exception e) {
	            	//System.out.println(e.fillInStackTrace());
	            	aRequest.setAttribute("result_image", "Ошибка при сохранении") ;
	            } 
	            
	    	
	    	
	    	
	    	
    	} catch(Exception e) {
    		System.out.println(e);
    		image = null ;
    		aRequest.setAttribute("result_image", "Ошибка при обработке") ;
    	}
    	
    	return aMapping.findForward("success") ;
    }
	public void save(BufferedImage aImage, Float aCompress, String aFileName) throws IOException  {
		if (aCompress.equals(1f)) {
			ImageIO.write(aImage, "jpg", new File(aFileName)) ;
		} else {
			FileImageOutputStream output = null ;
			try {
			Iterator<ImageWriter> iter =ImageIO.getImageWritersByFormatName("jpg") ;
			if (iter.hasNext()) {
				
				
				ImageWriter writer = iter.next() ;
				ImageWriteParam iwp = writer.getDefaultWriteParam();
				iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT) ;
				iwp.setCompressionQuality(aCompress) ;
				printlist(iwp.getCompressionTypes()) ;
				//iwp.setCompressionType("BI_RGB") ;
				
				File outFile = new File(aFileName) ;
				output = new FileImageOutputStream(outFile) ;
				writer.setOutput(output) ;
				IIOImage iioimage = new IIOImage(aImage,null,null) ;
				writer.write(null,iioimage,iwp) ;
				
			}
			}catch (IOException e) {
				// TODO: handle exception
			}finally {
	            //image.();
	        	if (output!=null) output.close() ;
	        }
		}
	}
	
    public void save1(BufferedImage bufferedImage,String aPath) {
    	System.out.println(new  SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS").format(new Date())+" Started");
    		GraphicsEnvironment ge = GraphicsEnvironment
    		        .getLocalGraphicsEnvironment();
    		GraphicsDevice[] gs = ge.getScreenDevices();
    		Rectangle[] rectangles = new Rectangle[gs.length];
    		for (int j = 0; j < gs.length; j++) {
    		    GraphicsDevice gd = gs[j];
    		    GraphicsConfiguration[] gc = gd.getConfigurations();
    		    for (int i = 0; i < gc.length; i++) {
    		        rectangles[j] = gc[i].getBounds();
    		    }
    		}
    		 
    		try {
    		    for (int j=0; j<rectangles.length; j++) {
    		         //= new Robot()
    		            //    .createScreenCapture(rectangles[j]);
    		        ImageIO.write(bufferedImage, "jpg", new File("test"+j+".jpg"));
    		        ImageIO.write(bufferedImage, "jpg", new File("test"+j+".jpg"));
    		        List<BufferedImage> list = new ArrayList<BufferedImage>();
    		        list.add(bufferedImage);
    		        IIOImage iioi = new IIOImage(bufferedImage.getRaster(), list, null);
    		        JPEGImageWriteParam jpegiwp = new JPEGImageWriteParam(Locale.ENGLISH);
    		        jpegiwp.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
    		        jpegiwp.setCompressionQuality(1.0f);
    		        Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpg");
    		        while (iter.hasNext()) {
    		            JPEGImageWriter jpegiw = (JPEGImageWriter)iter.next();
    		            FileOutputStream fos = new FileOutputStream(new File(aPath+"testiioi"+j+".jpg"));
    		            jpegiw.setOutput(ImageIO.createImageOutputStream(fos));
    		            jpegiw.write((IIOMetadata)null, iioi, jpegiwp);
    		            fos.flush();
    		            fos.close();
    		        }
    		    }

    		} catch (IOException e) {
    		    e.printStackTrace();
    		}
    		System.out.println(new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS").format(new Date())+" Stopped");
    }	
	
	private void printlist(String[] aList) {
		for(String l:aList) {
			System.out.println("    "+l) ;
		}
	}
	

}
