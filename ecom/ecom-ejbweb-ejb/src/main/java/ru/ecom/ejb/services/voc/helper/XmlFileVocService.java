package ru.ecom.ejb.services.voc.helper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import ru.ecom.ejb.services.voc.IVocContextService;
import ru.ecom.ejb.services.voc.IVocServiceManagement;
import ru.ecom.ejb.services.voc.VocContext;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocServiceException;
import ru.nuzmsh.util.voc.VocValue;

public class XmlFileVocService implements IVocContextService, IVocServiceManagement { 
    private final static Logger LOG = Logger.getLogger(XmlFileVocService.class) ;
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled() ;

	public void destroy() {
		// TODO Auto-generated method stub
		
	}
    public XmlFileVocService(String aFilename) {
    	theFileDiaryParameter = aFilename;
    }


	
	public List<VocValue> loadParameterType(VocContext aContext)  {
		List<VocValue> list = new LinkedList<VocValue>()  ;
		try {
			loadFile(theFileDiaryParameter ,list,aContext);
		}catch (Exception e) {
		}

		return list ;
	}
	private void loadFile( String aResourceString,List<VocValue> aList,VocContext aContext) throws IOException  {
        //LOG.info(new StringBuilder().append("Loading ").append(aResourceString).append(" ...").toString());
        InputStream in = null;
        try {
        	in = getInputStream(aResourceString) ;
                //LOG.info(new StringBuilder().append("		file=").append(in).toString());
               	Document doc = new SAXBuilder().build(in);
                Element parConfigElement = doc.getRootElement();
                for (Object o : parConfigElement.getChildren()) {
                    Element parElement = (Element) o;
                    if("parameter".equals(parElement.getName())) {
                    	VocValue vocValue = put(parElement,aContext) ;
                    	if (vocValue!=null) aList.add(vocValue);
                    //} else if("vocFile".equals(vocElement.getName())) {
                        //loadFile(aHash, vocElement.getTextTrim());
                    } else {
                        LOG.warn("Нет поддержки элемента "+parElement.getName());
                    }
                }
            }catch(Exception e) {
            	System.out.println(e.getMessage());
            } 
            finally {
                in.close();
            }
        LOG.info("Done.") ;

    }
	 private VocValue put(Element aElement,VocContext aContext) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
		String key = aElement.getAttributeValue("id");
	    if (StringUtil.isNullOrEmpty(key)) {
	    	throw new IllegalArgumentException("Нет атрибута id");
	    }
	    String name = aElement.getAttributeValue("name");
	    String roles = aElement.getAttributeValue("roles");
	    if (StringUtil.isNullOrEmpty(roles)||aContext.getSessionContext().isCallerInRole(roles)) { 
	    if (StringUtil.isNullOrEmpty(key)) {
	    	throw new IllegalArgumentException("Нет атрибута name");
	    }
	    String type = aElement.getAttributeValue("type");
	    /*LOG.info(new StringBuilder().append(" Parameter ").append(key)
	        		.append(" (name = ").append(name).append("")
	        		.append(" type= ").append(type).append(")")
	        		.toString());*/
	    StringBuilder vocname = new StringBuilder() ;
		vocname.append(name).append(" (").append(type).append(")") ;
		VocValue voc = new VocValue(key, name.toString()) ;
	    
	    return voc ;
	    } else {
	    	return null ;
	    }
	 }
	private InputStream getInputStream(String aResourceString) throws FileNotFoundException {
		//return theEcomConfig.getInputStream(aResourceString, "diary.dir.prefix",true);
    	return theEcomConfig.getInputStream(aResourceString, "",true);
    }
	EjbEcomConfig theEcomConfig = EjbEcomConfig.getInstance(); 
	final String theFileDiaryParameter ;




	    public String getNameById(String aId, String aVocName, VocAdditional aAdditional, VocContext aContext) throws VocServiceException {
	        String ret = null;
	        if (aId != null) {
	            for (VocValue value : listAll(aAdditional, aContext)) {
	                if (aId.equals(value.getId())) {
	                    ret = value.getName();
	                }
	            }
	        }
	        return ret;
	    }

	    public Collection<VocValue> findVocValueByQuery(String aVocName, String aQuery, int aCount, VocAdditional aAdditional, VocContext aContext) throws VocServiceException {
	        String query = aQuery.toUpperCase();
	        String findedId = null;
	        if (!StringUtil.isNullOrEmpty(aQuery)) {
	            for (VocValue value : listAll(aAdditional, aContext)) {
	                String name = value.getName();
	                String id = value.getId();
	                if (name != null) {
	                    if (name.toUpperCase().startsWith(query)) {
	                        findedId = id;
	                        break;
	                    }
	                }
	                if (findedId == null) {
	                    if (id != null) {
	                        if (id.toUpperCase().startsWith(query)) {
	                            findedId = id;
	                            break;
	                        }
	                    }
	                }
	                if (name != null && name.toUpperCase().indexOf(query) > -1) {
	                    findedId = id;
	                    break;
	                }
	                if (id != null && id.toUpperCase().indexOf(query) > -1) {
	                    findedId = id;
	                    break;
	                }
	            }
	        }
	        return findVocValueNext(aVocName, findedId, aCount, aAdditional, aContext);
	    }

	    public Collection<VocValue> findVocValuePrevious(String aVocName, String aId, int aCount, VocAdditional aAdditional, VocContext aContext) throws VocServiceException {
	        LinkedList<VocValue> ret = new LinkedList<VocValue>();
	        boolean finded = StringUtil.isNullOrEmpty(aId);
	        LinkedList<VocValue> reverted = new LinkedList<VocValue>();
	        for (VocValue value : listAll(aAdditional, aContext)) {
	            reverted.add(0, value);
	        }
	        for (VocValue value : reverted) {
	            //if (CAN_TRACE) LOG.trace("valueProperty = " + value);
	            if (!finded && value.getId().equals(aId)) {
	                finded = true;
	            }
	            if (finded) {
	                ret.add(0,value);
	                if (ret.size() > aCount) break;
	            }
	        }
	        return ret;
	    }

	    public Collection<VocValue> findVocValueNext(String aVocName, String aId, int aCount, VocAdditional aAdditional, VocContext aContext) {
	        LinkedList<VocValue> ret = new LinkedList<VocValue>();
	        boolean finded = StringUtil.isNullOrEmpty(aId);
	        for (VocValue value : listAll(aAdditional, aContext)) {
	            //if (CAN_TRACE) LOG.trace("valueProperty = " + value);
	            if (!finded && value.getId().equals(aId)) {
	                finded = true;
	            }
	            if (finded) {
	                ret.add(value);
	                if (ret.size() > aCount) break;
	            }
	        }
	        return ret;
	    }

	    private Collection<VocValue> listAll(VocAdditional aAdditional, VocContext aContext ) {
	        //AllValueContext ctx = new AllValueContext(
	        //		aAdditional, aContext.getEntityManager(), aContext.getSessionContext());
			//LinkedList<VocValue> ret = new LinkedList<VocValue>() ;
			
			List<VocValue> list = loadParameterType(aContext) ;

			return list;
	    }    


}
