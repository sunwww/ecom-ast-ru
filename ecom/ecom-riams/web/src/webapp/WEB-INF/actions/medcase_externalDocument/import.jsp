<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient" beginForm="smo_visitForm" title="Импорт внешнего документа"/>
    </tiles:put>


    <tiles:put name='body' type='string'>
        <msh:form action="medcaseExternalDocument-import.do"  defaultField="typeName" fileTransferSupports="true">
            <msh:hidden property="saveType"/>
      <msh:hidden property="tmpFile" />
      <msh:hidden property="objectId" />
      <msh:hidden property='parentType' />
            <msh:panel>
                <msh:row>
                	<msh:autoComplete property="type" label="Тип документа" vocName="vocExternalDocumentTypeByParent" parentId="MEDCASE" horizontalFill="true" fieldColSpan="3"/>
                </msh:row>
                <msh:row>
                    <td>Документ *.pdf</td>
                    <td colspan="1">
                        <input type="file" name="file" id="file" size="50" value="" onchange="uploadDocument()">
                		<input type="button" name="cancel_import" value="Отмена"  onclick="cancelImport()" />
                    </td>
                </msh:row>                	
            </msh:panel>
          
        </msh:form>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>

        </msh:sideMenu>

    </tiles:put>

    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
        	$('parentType').value = 'MedCase';
			$('objectId').value='${param.id}' ;
        	$('mainForm').action = 'javascript:getListRoles()' ;
        	function cancelImport() {
        		window.location = "entityParentView-smo_visit.do?id='${param.id}'";
        	}
        	
        	function uploadDocument() {
        		alert ('Документ загружен успешно');
	        	$('mainForm').action ='medcaseExternalDocument-import.do?id='+'${param.id}' ;
	        	$('mainForm').enctype ='multipart/form-data' ;
	        	$('tmpFile').value='0' ;
	        	$('mainForm').submit() ;
        	}
        	
        </script>
    </tiles:put>

</tiles:insert>