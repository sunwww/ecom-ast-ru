package ru.ecom.mis.ejb.service.diary;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.jboss.annotation.security.SecurityDomain;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import ru.ecom.diary.ejb.domain.protocol.parameter.FormInputProtocol;
import ru.ecom.diary.ejb.domain.protocol.parameter.ParameterByForm;
import ru.ecom.diary.ejb.form.protocol.parameter.ParameterForm;
import ru.ecom.diary.ejb.service.protocol.ParameterPage;
import ru.ecom.diary.ejb.service.protocol.ParameterType;
import ru.ecom.diary.ejb.service.protocol.field.*;
import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.poly.ejb.domain.protocol.RoughDraft;
import ru.nuzmsh.util.StringUtil;

import javax.annotation.EJB;
import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
/**
 * Сервис для работы с параметрами
 * @author stkacheva
 *
 */
@Stateless
@Local(IParameterService.class)
@Remote(IParameterService.class)
@SecurityDomain("other")
public class ParameterServiceBean implements IParameterService{
	
	public String checkOrCreateCode(String aCode, String aId) {
		String retCode ;
		if (aCode==null||aCode.equals("")) { //Генерируем код автоматически
			List<Object> ll = theManager.createNativeQuery("select max(id) from parameter").getResultList() ;
			Long maxId = ConvertSql.parseLong(!ll.isEmpty()?ll.get(0):Long.valueOf(0));
			if (maxId==null) maxId=Long.valueOf(0) ;
			maxId++;
			while (true) {
				List<Object> l = theManager.createNativeQuery("select id from parameter where code='code_"+maxId+"'").getResultList();
				if (l.isEmpty()) {
					retCode = "code_"+maxId;
					break;
				} else {
					maxId++;
				}
			}
		} else { //Проверяем указанный код на уникальность
			Long id = Long.valueOf(theManager.createNativeQuery("select count(*) from parameter where code='"+aCode+"'" +(aId!=null&&!aId.equals("")?" and id!="+aId:"") ).getSingleResult().toString());
			if (id>0) {
				retCode=new Random().nextInt()+aCode;
			} else {
				retCode = aCode;
			}
		}
		
		return retCode;
	}
	
	public Long createProtocolDrForCreateParam(Long aSmoId, Long aTemplate) {
		MedCase smo = theManager.find(MedCase.class, aSmoId) ;
		RoughDraft draft = new RoughDraft() ;
		draft.setMedCase(smo) ;
		theManager.persist(draft) ;
		List<ParameterByForm> listParam = theManager.createNativeQuery("from ParameterByForm where template_id="+aTemplate).getResultList() ;
		//TODO ошибка доделать
		//if (listParam.isEmpty()) {}
		for (ParameterByForm pf:listParam) {
			FormInputProtocol pfnew = new FormInputProtocol() ;
			pfnew.setParameter(pf.getParameter()) ;
			pfnew.setPosition(pf.getPosition()) ;
			pfnew.setDocProtocol(draft) ;
			theManager.persist(pfnew) ;
		}
		
		return draft.getId() ;
	}
	public List<ParameterType> loadParameterType()  {
		List<ParameterType> list = new LinkedList<>()  ;
			loadFile(theFileDiaryParameter ,list);
		return list ;
	}
	
	public ParameterPage loadParameter(ParameterForm aParameterForm, Long aId, ActionErrors aErrors) {
		try {
			return loadParameterFromFile(theFileDiaryParameter,aId, aParameterForm, aErrors);
		}catch (Exception e) {
		}

		return null ;
	}
	public ParameterPage loadParameter(ParameterForm aParameterForm,Long aId)  {
		return  loadParameter(aParameterForm,aId,null) ;
		//StringBuilder ret = new StringBuilder() ;
	}
	
	public String getActionByDocument(Long aId,
			String aDocument) throws IOException {
	//	LOG.info(new StringBuilder().append("Loading ").append(theFileDocumentParameter).append(" ...").toString());
		try (InputStream in =getInputStream(theFileDocumentParameter)) {
		//	LOG.info(new StringBuilder().append("		file=").append(in).toString());
			Document doc = new SAXBuilder().build(in);
			Element parConfigElement = doc.getRootElement();
			for (Object o : parConfigElement.getChildren()) {
				Element parElement = (Element) o;
				if("parameter".equals(parElement.getName())) {
					Long key = Long.valueOf(parElement.getAttributeValue("id"));
					if (key==null) {
						throw new IllegalArgumentException("Нет атрибута id");
					}
					if (key.equals(aId)) return parElement.getAttributeValue("action") ;
				} else {
					LOG.warn("Нет поддержки элемента "+parElement.getName());
				}
			}
		}catch(Exception e) {
			LOG.error(e.getMessage());
		}
	//	LOG.info("Done.") ;
		
		
		return null ;
	}
	private ParameterPage loadParameterFromFile(String aResourceString,Long aId,
			ParameterForm aParameterForm, ActionErrors aErrors) throws IOException {
     //   LOG.info(new StringBuilder().append("Loading ").append(aResourceString).append(" ...").toString());

        try (InputStream in = getInputStream(aResourceString)){
               	Document doc = new SAXBuilder().build(in);
                Element parConfigElement = doc.getRootElement();
                for (Object o : parConfigElement.getChildren()) {
                    Element parElement = (Element) o;
                    if("parameter".equals(parElement.getName())) {
                    	Long key = Long.valueOf(parElement.getAttributeValue("id"));
                	    if (key==null) {
                	    	throw new IllegalArgumentException("Нет атрибута id");
                	    }
                	    if (key.equals(aId)) return loadParameter(parElement, aParameterForm, aErrors) ;
                    //} else if("vocFile".equals(vocElement.getName())) {
                        //loadFile(aHash, vocElement.getTextTrim());
                    } else {
                        LOG.warn("Нет поддержки элемента "+parElement.getName());
                    }
                }
            }catch(Exception e) {
            	LOG.error(e.getMessage(),e);
            } 
		return null ;
	}
	private void loadFile( String aResourceString,List<ParameterType> aList) {

        try (InputStream in = getInputStream(aResourceString)){
            Document doc = new SAXBuilder().build(in);
            Element parConfigElement = doc.getRootElement();
            for (Object o : parConfigElement.getChildren()) {
            	Element parElement = (Element) o;
                if("parameter".equals(parElement.getName())) {
                	aList.add(put(parElement));
                } else {
                	LOG.warn("Нет поддержки элемента "+parElement.getName());
                }
            }
        } catch(Exception e) {
            	LOG.error(e.getMessage());
        }

    }
	private ParameterPage loadParameter(Element aElement,ParameterForm aParameterForm, ActionErrors aErrors) {
		StringBuilder data = new StringBuilder() ;
		StringBuilder javaContext = new StringBuilder() ;
		
		Element fields = aElement.getChild("fields")  ;
		for (Object o :fields.getChildren()) {
			Element field = (Element) o ;
			ParameterPage par = getParameterDataBuilder(field, aParameterForm, aErrors) ;
			data.append(par.getFormData().toString()) ;
			javaContext.append(par.getJavaContext().toString()) ;
		}
		ParameterPage page = new ParameterPage();
		page.setFormData(data);
		page.setJavaContext(javaContext) ;
		return page;
	}
	
	private ParameterPage getParameterDataBuilder(Element aField,ParameterForm aParameterForm, ActionErrors aErrors) {
		ParameterPage page = new ParameterPage() ;
		StringBuilder data = new StringBuilder() ;
		StringBuilder javaScript = new StringBuilder() ;
		String type = aField.getAttributeValue("type")  ;
		String property = aField.getAttributeValue("property");
		String value ; 
		if (StringUtil.isNullOrEmpty(type)) {
			throw new IllegalArgumentException("Нет атрибута type в field");
		}
		if (StringUtil.isNullOrEmpty(property)) {
			value="" ;
		} else {
			try {
				value = BeanUtils.getProperty(aParameterForm, property);
				LOG.info("property="+property+" value="+value);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				value="";
			}
		}
		
		// поле Row Сделано
		if (type.equals("row")) {
			String styleId = aField.getAttributeValue("styleId")  ;
			boolean openIs = toBoolean(aField.getAttributeValue("open")) ;
			data.append(RowField.getField(openIs, styleId)) ;
			page.setFormData(data);
			page.setJavaContext(javaScript);
			return page ;
		}
		String label = aField.getAttributeValue("label") ;
		// поле separator Сделано
		if (type.equals("separator")) {
			String colSpan = aField.getAttributeValue("colSpan")  ;
			data.append(SeparatorField.getField(label,colSpan)) ;
			page.setFormData(data);
			page.setJavaContext(javaScript);
			return page ;
		}
		
		ErrorByField errorsByField = new ErrorByField(aErrors,property);
		StringBuilder errors = errorsByField.createError() ; 
		boolean hideLabel = toBoolean(aField.getAttributeValue("hideLabel"));
		boolean required = toBoolean(aField.getAttributeValue("required")) ;
		boolean horizontalFill = toBoolean(aField.getAttributeValue("horizontalFill")) ;
		String fieldColSpan = aField.getAttributeValue("fieldColSpan") ;
		String labelColSpan = aField.getAttributeValue("labelColSpan") ;
		
		String size = aField.getAttributeValue("size") ;
		String propertyNext="name" ;
		// поле TextField
		if (type.equals("textField")) {
			
			//Label
			data.append(LabelField.getField(aParameterForm.isViewOnly(), required, property, label, labelColSpan, hideLabel,errors));
			
			//Data
			data.append(TextField.getField(aParameterForm,property, label, value, fieldColSpan, size
					, horizontalFill, required, aParameterForm.isViewOnly(),errors ));
			javaScript.append(TextField.getJavaScript(aParameterForm.isViewOnly(),property,propertyNext));
			page.setFormData(data);
			page.setJavaContext(javaScript);
			return page ;
		}
		// поле AutoComplete TODO несделано
		if (type.equals("autoComplete")) {
			data.append(LabelField.getField(aParameterForm.isViewOnly(), required, property, label, labelColSpan, hideLabel,errors));
			String vocName =  aField.getAttributeValue("vocName") ;
			String parentId =  aField.getAttributeValue("parentId") ;
			
			data.append(AutoCompleteField.getField(vocName,property, label, value,parentId, fieldColSpan, size
					, horizontalFill, required, aParameterForm.isViewOnly(),errors));
			javaScript.append(AutoCompleteField.getJavaScript(aParameterForm.isViewOnly(),vocName, property,propertyNext,label)) ;
			page.setFormData(data);
			page.setJavaContext(javaScript);
			return page ;
		}
		if (type.equals("separator")) {
			//<field type='separator' label='Границы допустимых значений' colSpan='4'/>
			
			page.setFormData(data);
			page.setJavaContext(javaScript);
			return page ;
		}
		throw new IllegalArgumentException(new StringBuilder().append("Нет обработчика для типа параметра "+type).toString());
	}
	

	 private ParameterType put(Element aElement) {
		String key = aElement.getAttributeValue("id");
	    if (StringUtil.isNullOrEmpty(key)) {
	    	throw new IllegalArgumentException("Нет атрибута id");
	    }
	    String name = aElement.getAttributeValue("name");
	    if (StringUtil.isNullOrEmpty(key)) {
	    	throw new IllegalArgumentException("Нет атрибута name");
	    }
	    String type = aElement.getAttributeValue("type");
	    LOG.info(new StringBuilder().append(" Parameter ").append(key)
	        		.append(" (name = ").append(name)
	        		.append(" type= ").append(type).append(")")
	        		.toString());
	    ParameterType param = new ParameterType() ;
	    param.setId(Long.valueOf(key)) ;
	    param.setName(name);
	    param.setType(type) ;
	    return param ;
	        
	        /*
	        if ("AllValueHelper".equals(type)) {
	            Element iAllValueElement = aElement.getChild("IAllValue");
	            if (iAllValueElement == null) throw new IllegalStateException("Нет элемента IAllValue у справочника " + key);
	            String clazz = iAllValueElement.getTextTrim();
	            if (StringUtil.isNullOrEmpty(clazz)) throw new IllegalStateException("Пустой элемент IAllValue");
	            IAllValue iAllValueObject = (IAllValue) theClassLoaderHelper.loadClass(clazz).newInstance() ;
	            AllValueHelper allValueHelper = new AllValueHelper(iAllValueObject);
	            put(aHash, key, allValueHelper);
	        } else if("AllValueHelperEntityVoc".equals(type)) {
	            Element vocValueElement = aElement.getChild("vocValue") ;
	            if(vocValueElement==null) throw new IllegalStateException("Нет элемента vocValue у "+key) ;

	            AllValueHelper allValueHelper = new AllValueHelper(new EntityVocAllValue(
	                    vocValueElement.getAttributeValue("entity")
	                    , vocValueElement.getAttributeValue("id")
	                    , vocValueElement.getAttributeValue("names")
	                    , vocValueElement.getAttributeValue("sortBy")
	            ));
	            put(aHash, key, allValueHelper) ;
	        } else if("IVocService".equals(type)) {

//	            Element iVocServiceElement = aElement.getChild("IVocService");
//	            if(iVocServiceElement==null) throw new IllegalStateException("Нет элемента IVocService") ;
	            String className = aElement.getAttributeValue("class") ;
	            if(StringUtil.isNullOrEmpty(className)) throw new IllegalStateException("Нет аттрибута class в справочнике "+key) ;
	            Class clazz = theClassLoaderHelper.loadClass(className) ;
	            IVocContextService service = (IVocContextService) clazz.newInstance() ;
	            if(service instanceof IVocConfigXmlService) {
	                IVocConfigXmlService iVocConfigXmlService = (IVocConfigXmlService) service ;
	                try {
	                    iVocConfigXmlService.config(aElement);
	                } catch (Exception e) {
	                    throw new IllegalStateException("Ошибка инициализации справочника "+key+": "+e.getMessage(), e) ;
	                }
	            }
	            put(aHash, key, service) ;
	        } else if("EntityVocService".equals(type)) {
	            Element elm = aElement.getChild("EntityVocService") ;
	            String className = elm.getAttributeValue("entity") ;
	            String[] names = getAsArray(elm.getAttributeValue("names")) ;
	            String[] queried = getAsArray(elm.getAttributeValue("queried")) ;
	            String parent = elm.getAttributeValue("parent") ;
	            if(StringUtil.isNullOrEmpty(parent)) {
	                parent = elm.getAttributeValue("parentProperty") ;
	            }
	            String queryAppend = elm.getAttributeValue("queryAppend") ;
	            String queryConvertType = elm.getAttributeValue("queryConvertType") ;
	            EntityVocService service = new EntityVocService(className, names, queried,parent, queryAppend,queryConvertType);
	            put(aHash, key, service) ;
	        } else {
	            throw new IllegalStateException("Не поддерживается тип " + type);
	        }
	        */
	    }
	
	private InputStream getInputStream(String aResourceString) throws FileNotFoundException {
		//return theEcomConfig.getInputStream(aResourceString, "diary.dir.prefix",true);
    	return theEcomConfig.getInputStream(aResourceString, "",true);
    }
	private Boolean toBoolean(String aString) {
		Boolean ret; 
		if (!StringUtil.isNullOrEmpty(aString) && (aString.equals("1") || aString.equalsIgnoreCase("true"))){
			ret= true;
		} else {
			ret=false;
		}
		return ret ;
	}
	
	EjbEcomConfig theEcomConfig = EjbEcomConfig.getInstance(); 
	private static final Logger LOG = Logger.getLogger(ParameterServiceBean.class) ;
	String theFileDiaryParameter = "/META-INF/diary/parameter-config.xml" ;
	String theFileDocumentParameter = "/META-INF/diary/document-polic-config.xml" ;
	@EJB ILocalEntityFormService theEntityFormService ;
    @PersistenceContext EntityManager theManager ;
    @Resource SessionContext theContext ;

}
