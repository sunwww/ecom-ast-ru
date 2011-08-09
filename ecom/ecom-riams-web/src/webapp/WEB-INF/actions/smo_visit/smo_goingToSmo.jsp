<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Poly" property="worker">Переход к случаю медицинского обслуживания (СМО) по его номеру</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
    	<tags:visit_finds currentAction="smo_goingToSmo"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
            <msh:form action="/smo_goingToSmo.do" defaultField="number" method="GET">
                <msh:panel>
                    <msh:row>
                    <msh:hidden property="id"/>
                        <msh:textField property="number" label="Номер СМО" size="30"/>
                        <td>        
							<input type="button" title="Поиск [CTRL+ENTER]" onclick="this.value=&quot;Поиск...&quot;;  this.form.action=&quot;entitySubclassView-mis_medCase.do&quot; ;this.form.id.value=this.form.number.value ; this.form.target=&quot;&quot; ; this.form.submit(); return true ;" value="Перейти" class="default" id="submitButton" autocomplete="off">
							<input type="button" value="Отменить" title="Отменить изменения [SHIFT+ESC]" onclick="this.disabled=true; window.history.back()" id="cancelButton">
						</td>
                    </msh:row>
                </msh:panel>
            </msh:form>
            
    </tiles:put>
    <tiles:put name="javascript" type="string">
    	<script type="text/javascript">
    		document.forms[0].action = "javascript:goingToSmo()" ;
    		function goingToSmo() {
    			document.forms[0].action="entitySubclassView-mis_medCase.do" ;
    			document.forms[0].id.value=document.forms[0].number.value ;
    			if (document.forms[0].id.value!="") {
    				document.forms[0].submit() ;
    			}
    		}
    	</script>
    </tiles:put>

</tiles:insert>