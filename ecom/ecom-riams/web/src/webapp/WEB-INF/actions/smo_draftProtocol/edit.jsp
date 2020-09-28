 <%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>



<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="style" type="string">
        <style type="text/css">            
            .protocols {
				left:0px;  width:99%; 
				top:0px;  height:55em;
			}
			#epicriPanel {
			width:100%;
			}
			.record {
			width:100%;
			}
        </style>

    </tiles:put>

<tiles:put name='body' type='string'>
	<msh:ifFormTypeIsNotView formName="smo_draftProtocolForm">
    	<tags:templateProtocol idSmo="smo_draftProtocolForm.medCase" version="Visit" name="tmp" property="record" voc="protocolVisitByPatient"/>
    </msh:ifFormTypeIsNotView>
    <msh:form action="entityParentSaveGoSubclassView-smo_draftProtocol.do" 
    defaultField="dateRegistration" >
        <msh:hidden property="id"/>
        <msh:hidden property="saveType"/>
        <msh:hidden property="username"/>
        <msh:hidden property="date"/>
        <msh:hidden property="time"/>
        <msh:hidden property="printDate"/>
        <msh:hidden property="printTime"/>
        <msh:hidden property="medCase"/>
        <msh:hidden property="specialist"/>

            <msh:ifFormTypeIsView formName="smo_draftProtocolForm">
            	<msh:hidden property="record"/>
            </msh:ifFormTypeIsView>
            <msh:panel colsWidth="1%,1%,1%,1%,1%,1%,65%">
                <msh:row>
                    <msh:textField label="Дата" property="dateRegistration" fieldColSpan="1" />
                    <msh:textField label="Время" property="timeRegistration" fieldColSpan="1"  />
                </msh:row >
                	<msh:row>
                		<msh:autoComplete property="type" fieldColSpan="3" label="Тип протокола" horizontalFill="true"
                		vocName="vocTypeProtocol"/>
	                </msh:row>
                	<msh:row>
                		<msh:autoComplete property="mode" fieldColSpan="3" label="Режим" horizontalFill="true"
                		vocName="vocProtocolMode"/>
	                </msh:row>
                	<msh:row>
                		<msh:autoComplete property="state" fieldColSpan="3" label="Состояние больного" horizontalFill="true"
                		vocName="vocPhoneMessageState"/>
	                </msh:row>
                <msh:row>
                    <msh:textArea property="record" label="Текст:"
                                      size="100" rows="25" fieldColSpan="8"  />
                    
                </msh:row>
                <msh:ifFormTypeIsNotView formName="smo_draftProtocolForm">
                <msh:row>
                    <td colspan="3" align="right">
                        <input type="button" value="Шаблон" onClick="showtmpTemplateProtocol()"/>
                        <input type="button" id="changeSizeEpicrisisButton" value="Увеличить" onclick="changeSizeEpicrisis()">
                    </td>
                    <tags:keyWord name="record" service="KeyWordService" methodService="getDecryption"/>
                </msh:row>
                </msh:ifFormTypeIsNotView>
                <msh:ifFormTypeIsView formName="smo_draftProtocolForm">
                
                <msh:row>
                	<msh:textField property="date" label="Дата создания" viewOnlyField="true"/>
                	<msh:textField property="time" label="Время" viewOnlyField="true"/>
                	<msh:textField property="username" label="Пользователь" viewOnlyField="true"/>
                </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редак."/>
          	<msh:label property="editUsername" label="Пользователь" />
        </msh:row>
                </msh:ifFormTypeIsView>
                <msh:ifFormTypeIsNotView formName="smo_draftProtocolForm">
                <msh:row>
                	
                    <td><input type="button" 
                    onclick="this.form.action='entityParentSaveGoSubclassView-smo_visitProtocol.do';this.form.submit();"
                    value="Сохранить как оригинал"/></td>
                
                </msh:row>
                <msh:row>
	                <msh:submitCancelButtonsRow colSpan="3" />
                </msh:row>
                </msh:ifFormTypeIsNotView>
            </msh:panel>
    </msh:form>
</tiles:put>

<tiles:put name='side' type='string'>
    <msh:sideMenu>
    <tags:template_new_diary name="newTemp" roles="/Policy/Diary/Template/Create" field="record" title="Создание шаблона"></tags:template_new_diary>

        <msh:ifFormTypeIsView formName="smo_draftProtocolForm">
            <msh:sideLink roles="/Policy/Mis/MedCase/Protocol/Edit" key="ALT+2" params="id" action="/entityParentEdit-smo_draftProtocol"
                          name="Редактировать"/>
        
        </msh:ifFormTypeIsView>

        <msh:ifFormTypeAreViewOrEdit formName="smo_draftProtocolForm">
            <msh:sideLink roles="/Policy/Mis/MedCase/Protocol/Delete" key='ALT+DEL' params="id"
                          action="/entityParentDeleteGoSubclassView-smo_draftProtocol" name="Удалить"
                          confirm="Вы действительно хотите удалить?"/>
         </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>

</tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient" beginForm="smo_draftProtocolForm" />
    </tiles:put>
    

    <tiles:put name='javascript' type='string'>
    	<msh:ifFormTypeIsNotView formName="smo_draftProtocolForm">
    	<script type="text/javascript">
    	var isChangeSizeEpicrisis=1 ;
		function changeSizeEpicrisis() {
			if (isChangeSizeEpicrisis==1) {
				Element.addClassName($('record'), "protocols") ;
				if ($('changeSizeEpicrisisButton')) $('changeSizeEpicrisisButton').value='Уменьшить' ;
				isChangeSizeEpicrisis=0 ;
			} else {
				Element.removeClassName($('record'), "protocols") ;
				if ($('changeSizeEpicrisisButton')) $('changeSizeEpicrisisButton').value='Увеличить' ;
				isChangeSizeEpicrisis=1;
			}
		}
		eventutil.addEventListener($('record'), "dblclick", 
	  		  	function() {
					changeSizeEpicrisis() ;
	  		  	}) ;
    	</script>
    	</msh:ifFormTypeIsNotView>
    	<msh:ifFormTypeIsCreate formName="smo_draftProtocolForm">
    		<script type="text/javascript">
    			if (confirm("Вы хотите создать дневник на основе шаблона?")) {
    				showtmpTemplateProtocol() ;
    			} else {
    				if ($('dateRegistration').value!="") setFocusOnField('record') ;

    			}
    		</script>
    	</msh:ifFormTypeIsCreate>

    	<msh:ifFormTypeAreViewOrEdit formName="smo_draftProtocolForm"><msh:ifFormTypeIsNotView formName="smo_draftProtocolForm">
    		<script type="text/javascript">

    		TemplateProtocolService.isCanEditProtocol($('username').value,
    			{
                    callback: function(aString) {
                    	//alert(aString) ;
                        if (+aString>0) {} else {
                         	alert('У Вас стоит ограничение на редактрование данного протокола!');
                         	window.location.href= "entityParentView-smo_draftProtocol.do?id=${param.id}";
                         }
                     }
                 });
    		</script>
    	</msh:ifFormTypeIsNotView>
    	
    	</msh:ifFormTypeAreViewOrEdit>
    
    </tiles:put>
</tiles:insert>
