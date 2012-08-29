<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient" beginForm="mis_patientForm" title="Импорт внешнего документа"/>
    </tiles:put>


    <tiles:put name='body' type='string'>
        <msh:form action="externalDocument-preView.do"  defaultField="typeName" fileTransferSupports="true">
            <msh:hidden property="saveType"/>
      <msh:hidden property="tmpFile" />
      <msh:hidden property="objectId" />

            <msh:panel>
                <msh:row>
                	<msh:autoComplete property="type" label="Тип документа" vocName="vocExternalDocumentType" horizontalFill="true" fieldColSpan="3"/>
                </msh:row>
                <msh:row>
                    <td>Файл *.jpg</td>
                    <td colspan="1">
                        <input type="file" name="file" id="file" size="50" value="" onchange="getImage()">
                		<input type="button" name="cancel_import" value="Отмена"  onclick="cancelImport()" />
                    </td>
                </msh:row>                	
            </msh:panel>
            <iframe scrolling="auto"  name="fileinfo" id="fileinfo" style="border: 0px" width="100%" height="500px" title="Предварительный просмотр"></iframe>
        </msh:form>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>

        </msh:sideMenu>

    </tiles:put>

    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
        	$('objectId').value='${param.id}' ;
        	$('mainForm').action = 'javascript:getListRoles()' ;
        	function cancelImport() {
        		window.location = "riams_config.do";
        	}
        	function getImage() {
	        	$('mainForm').action ='externalDocument-viewImage.do' ;
	        	$('mainForm').target="fileinfo" ;
	        	$('mainForm').enctype ='multipart/form-data' ;
	        	$('tmpFile').value='1' ;
	        	$('mainForm').submit() ;
	        }
        	function getListRoles() {
	        	$('mainForm').action ='externalDocument-viewImage.do' ;
	        	$('mainForm').target="fileinfo" ;
	        	$('mainForm').enctype ='multipart/form-data' ;
	        	$('tmpFile').value='0' ;
	        	$('mainForm').submit() ;
        	}
        	
        	function getRunImport() {
        		getListRoles() ;
        		return ;
        		var list_iframe = document.getElementsByTagName("iframe") ;
        		var iframe = list_iframe[0] ;
        		if ($('file').value!="") {
	        		var ids = iframe.contentDocument.defaultView.importRole() ;
	        		if (ids) {
	        			$('mainForm').action='externalDocument-viewImage.do?';
	    	        	$('mainForm').enctype ='multipart/form-data' ;
	    	        	$('tmpFile').value='0' ;
	        			$('mainForm').target='fileinfo' ;
	        			$('mainForm').submit() ;
	        		} 
        		} else {
        			alert('Выберите файл для импорта!!!') ;
        		}
        		
        	}
           // Element.addClassName($('mainMenuService'), "selected");
        </script>
    </tiles:put>

</tiles:insert>