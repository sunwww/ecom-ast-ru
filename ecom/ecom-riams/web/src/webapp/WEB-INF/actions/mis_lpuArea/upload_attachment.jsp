<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Journals" title="Журнал прикрепленного населения"/>
  </tiles:put>

  <tiles:put name="body" type="string">
  <%
    /* String typeRead =ActionUtil.updateParameter("PatientAttachment","typeRead","1", request) ;
	String typeView=ActionUtil.updateParameter("PatientAttachment","typeView","1", request) ;
	String typeAge=ActionUtil.updateParameter("PatientAttachment","typeAge","3", request) ;
	String typeAttachment=ActionUtil.updateParameter("PatientAttachment","typeAttachment","3", request) ;
	String typeChange=ActionUtil.updateParameter("PatientAttachment","typeChange","1", request) ;
	String typeDefect=ActionUtil.updateParameter("PatientAttachment","typeDefect","3", request) ;
	String typeCompany=ActionUtil.updateParameter("PatientAttachment","typeCompany","3", request) ;
	String typeAreaCheck=ActionUtil.updateParameter("PatientAttachment","typeAreaCheck","3", request) ;
	String typeWork=ActionUtil.updateParameter("PatientAttachment","typeWork","1", request) ;
	String typeDivide=ActionUtil.updateParameter("PatientAttachemnt","typeDivide","1",request) ; */
	//String typeXmlFormat = ActionUtil.updateParameter("PatientAttachment", "typeXmlFormat", "2", request);

  %>
  
    <msh:form action="/mis_attachmentUpload.do" defaultField="lpuName" disableFormDataConfirm="true" method="POST" fileTransferSupports="true">
    <msh:panel>
      
       </msh:panel>
       <msh:panel colsWidth="fondTable">
         <msh:row>
      <msh:autoComplete property="importFormat" vocName="patientAttachmentFormats" fieldColSpan="50" size="50"/>
      </msh:row>
     
              
       </msh:panel>
       <msh:panel colsWidth="systemTable">
      <msh:row>
        <td colspan="11">
             <html:file property="attachmentFile" />
        </td>
       </msh:row>
       <msh:row>
       	<msh:hidden property="filename" />
       	<td colspan="4">
       		Файл <span id='aView'></span>
       	</td>
       </msh:row>
      <msh:row>
           <td colspan="11">
            <input type="submit" value="Импортировать" />
           
          </td>
      </msh:row>
       
      <script type="text/javascript" src="./dwr/interface/AttachmentService.js"></script>
      <script type="text/javascript">
      //checkFieldUpdate('typeXmlFormat', '${typeXmlFormat}',1);
      $('aView').innerHTML=$('filename').value ;
     
    	var text="";
    function syncUpload () {
    	$('syncText').innerHTML='Идет синхронизация...';
    	AttachmentService.syncUpload('ru.ecom.mis.ejb.domain.patient.PatientAttachedImport',{
    		callback: function (aResult) {
    			$('syncText').innerHTML=aResult;
    		}
    	});
    }
      
      </script>
    </msh:panel>
     
    </msh:form>
    
   
<%
      String importResult = (String)request.getAttribute("impResult");
      if (importResult!=null&&importResult.startsWith("Файл")) {
    	%>
    	
    	<msh:row>
          <td colspan="11">
            <input type="button" value="Синхронизировать" onclick='syncUpload()' />
          </td>
         
      </msh:row>  
    	  <%
      
      }
      
    String date = (String)request.getParameter("period") ;
    String date1 = (String)request.getParameter("periodTo") ;
    String sqlAdd = (String)request.getAttribute("sqlAdd");
    String exportDefects = (String)request.getAttribute("exportDefects");
   
     %>
   
     
    <script type="text/javascript">
   
   </script> 
    <msh:row>
    <td>
          <span id='syncText'>${impResult}</span>
          </td>
          </msh:row>
  </tiles:put>
</tiles:insert>