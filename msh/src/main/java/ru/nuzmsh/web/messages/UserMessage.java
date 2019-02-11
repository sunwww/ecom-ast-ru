package ru.nuzmsh.web.messages;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class UserMessage {

    private static final String ATTRIBUTE_ID = UserMessage.class+".USER_MESSAGE" ;

    public static void addMessage(ServletRequest aRequest, Long aId, String aTitle, String aMessage) {
    	addMessage(aRequest,aId ,aTitle,aMessage,null) ;
    }
    public static void addMessage(ServletRequest aRequest,Long aId, String aTitle, String aMessage, String aUrl) {
    	UserMessage us = new UserMessage() ;
    	us.setMessage(aMessage) ;
        us.setTitle(aTitle) ;
        us.setUrl(aUrl) ;
        us.setId(aId) ;
        Date date = new Date() ;
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm") ;
        us.theInfo = format.format(date) ;
        List<UserMessage> list = findInRequest(aRequest) ;
        if (list==null) {
        	list = new ArrayList<UserMessage>() ;
        }
        list.add(us) ;
        setInRequest(aRequest, list) ;
    }
    public static void setInRequest(ServletRequest aRequest, List<UserMessage> aList) {
        aRequest.setAttribute(ATTRIBUTE_ID, aList);
        if(aRequest instanceof HttpServletRequest) {
            HttpSession session = ((HttpServletRequest) aRequest).getSession() ;
            if(session!=null) {
                session.setAttribute(ATTRIBUTE_ID, aList);
            }
        }
    	
    }
    public static List<UserMessage> findInRequest(ServletRequest aRequest) {

    	List<UserMessage> message = (List<UserMessage>) aRequest.getAttribute(ATTRIBUTE_ID) ;
        if(message==null && aRequest instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) aRequest ;
            HttpSession session = request.getSession() ;
            if(session!=null) {
                message = (List<UserMessage>) session.getAttribute(ATTRIBUTE_ID);
                //session.removeAttribute(ATTRIBUTE_ID);
            }
        }
        return message ;
    }

    public String getMessage() {
        return theMessage ;
    }
    public String getTitle() {
    	return theTitle ;
    }
    public String getUrl() {
    	return theUrl ;
    }
    
    public String getInfo() {
    	return theInfo ;
    }
    public Long getId() {
    	return theId ;
    }

    public static void removeFromSession(ServletRequest aRequest,Long aId) {
    	List<UserMessage> list = findInRequest(aRequest) ;
        if(list!=null && list.size()>0) {
        	for (int i=0;i<list.size();i++) {
        		UserMessage mes = list.get(i) ;
        		if (aId!=null && mes.getId()!=null && mes.getId().equals(aId)) {
        			list.remove(i) ;
        		}
        	}
        	setInRequest(aRequest, list) ;
        }
        
    }
    public void setMessage(String aMessage) {
    	theMessage = aMessage ;
    }
    public void setId(Long aId) {
    	theId = aId ;
    }
    public void setUrl(String aUrl) {
    	theUrl = aUrl ;
    }
    public void setTitle(String aTitle) {
    	theTitle = aTitle ;
    }
    public void setInfo(String aInfo) {
    	theInfo = aInfo ;
    }
    private Long theId ;
    private String theMessage ;
    private String theUrl ;
    private String theTitle ;
    private String theInfo ;
    //private final Exception theException ;

}
