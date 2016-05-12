<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title title="Импорт списка ролей"/>
    </tiles:put>


    <tiles:put name='body' type='string'>
        <msh:form action="serviceRole-importEdit.do"  defaultField="clear" fileTransferSupports="true">
            <msh:hidden property="saveType"/>

            <msh:panel>
                <msh:row>
                	<msh:checkBox property="clear" label="Удалять существующие политики?"/>
                </msh:row>
                <msh:row>
                    <td>Файл *.xml</td>
                    <td colspan="1">
                        <input type="file" name="file" id="file" size="50" value="" onchange="getListRoles()">
                    </td>
                </msh:row>
                	<msh:row>
                	<td colspan="4" align="center">
                		<input type="button" name="cancel_import" value="Отмена"  onclick="cancelImport()" />
                		<input type="button" name="run_import" value="Импорт выбранных политик"  onclick="getRunImport()" />
                	</td>
                	</msh:row>
                	
            </msh:panel>
            <iframe scrolling="auto"  name="fileinfo" id="fileinfo" style="border: 0px" width="100%" height="300px" marginheight="100%" title="Список ролей для импорта"></iframe>
        </msh:form>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>

        </msh:sideMenu>
        <tags:menuJaas currentAction="roles"/>

    </tiles:put>

    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
        	
        	$('mainForm').action = 'javascript:getListRoles()' ;
        	function cancelImport() {
        		window.location = "riams_config.do";
        	}
        	function getListRoles() {
	        	$('mainForm').action ='serviceRole-importEditList.do' ;
	        	$('mainForm').target="fileinfo" ;
	        	$('mainForm').enctype ='multipart/form-data' ;
	        	$('mainForm').submit() ;
        	}
        	
        	function getRunImport() {
        		var list_iframe = document.getElementsByTagName("iframe") ;
        		var iframe = list_iframe[0] ;
        		if ($('file').value!="") {
	        		var ids = iframe.contentDocument.defaultView.importRole() ;
	        		if (ids) {
	        			$('mainForm').action='serviceRole-import.do?' +ids;
	        			$('mainForm').target='' ;
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