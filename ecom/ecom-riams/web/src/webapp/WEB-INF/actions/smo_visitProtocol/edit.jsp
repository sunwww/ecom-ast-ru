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
    <msh:form action="entityParentSaveGoSubclassView-smo_visitProtocol.do" 
    defaultField="dateRegistration" guid="b55hb-b971-441e-9a90-5155c07" >
        <msh:hidden property="id"/>
        <msh:hidden property="saveType"/>
        <msh:hidden property="username"/>
        <msh:hidden property="date"/>
        <msh:hidden property="time"/>
        <msh:hidden property="printDate"/>
        <msh:hidden property="printTime"/>
        <msh:hidden property="medCase"/>
        <msh:hidden property="specialist"/>

            <msh:ifFormTypeIsView formName="smo_visitProtocolForm">
            	<msh:hidden property="record"/>
            </msh:ifFormTypeIsView>
            <msh:panel colsWidth="1%,1%,1%,1%,1%,1%,65%">
                <msh:row>
                    <msh:textField label="Дата" property="dateRegistration" fieldColSpan="1" guid="b58ehb-b971-441e-9a90-58019c07" />
                    <msh:textField label="Время" property="timeRegistration" fieldColSpan="1"  guid="b3hb-b971-441e-9a90-8019c07" />
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
                                      size="100" rows="25" fieldColSpan="8"  guid="b6ehb-b971-441e-9a90-519c07" />
                    
                </msh:row>

                <msh:ifFormTypeIsNotView formName="smo_visitProtocolForm">
                <msh:row>
                    <td colspan="3" align="right">
                        <input type="button" value="Шаблон" onClick="showtmpTemplateProtocol()"/>
                        <input type="button" id="changeSizeEpicrisisButton" value="Увеличить" onclick="changeSizeEpicrisis()">
                    </td>
                    <tags:keyWord name="record" service="KeyWordService" methodService="getDecryption"/>
                </msh:row>
                </msh:ifFormTypeIsNotView>
                <msh:row>
                	<msh:textArea property="journalText" fieldColSpan="8" rows="2" size="100" label="Принятые меры (для журнала)"/>
                </msh:row>
                <msh:ifFormTypeIsView formName="smo_visitProtocolForm">
                
                <msh:row>
                	<msh:textField property="date" label="Дата создания" viewOnlyField="true"/>
                	<msh:textField property="time" label="Время" viewOnlyField="true"/>
                	<msh:textField property="username" label="Пользователь" viewOnlyField="true"/>
                </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редак."/>
          	<msh:label property="editUsername" label="Пользователь" guid="2258d5ca-cde5-46e9-a1cc-3ffc278353fe" />
        </msh:row>
                        <msh:row>
                	<msh:textField property="printDate" label="Дата печати" viewOnlyField="true"/>
                	<msh:textField property="printTime" label="Время" viewOnlyField="true"/>
                </msh:row>
                </msh:ifFormTypeIsView>
                <msh:ifFormTypeIsCreate formName="smo_visitProtocolForm">
                <msh:row>
                    <td><input type="button" 
                    onclick="this.form.action='entityParentSaveGoSubclassView-smo_draftProtocol.do';this.form.submit();"
                    value="Сохранить как черновик"/></td>
                
                </msh:row>
                </msh:ifFormTypeIsCreate>
                <msh:row>
	                <msh:submitCancelButtonsRow colSpan="3" />
                </msh:row>
            </msh:panel>
    </msh:form>
	<msh:ifFormTypeIsNotView formName="smo_visitProtocolForm">
    	<tags:templateProtocol idSmo="smo_visitProtocolForm.medCase" version="Visit" name="tmp" property="record" voc="protocolVisitByPatient"/>
    </msh:ifFormTypeIsNotView>
</tiles:put>

<tiles:put name='side' type='string'>
    <msh:sideMenu>
    <tags:template_new_diary name="newTemp" roles="/Policy/Diary/Template/Create" field="record" title="Создание шаблона"></tags:template_new_diary>

        <msh:ifFormTypeIsView formName="smo_visitProtocolForm">
            <msh:sideLink roles="/Policy/Mis/MedCase/Protocol/Edit" key="ALT+2" params="id" action="/entityParentEdit-smo_visitProtocol"
                          name="Редактировать"/>
        
        </msh:ifFormTypeIsView>

        <msh:ifFormTypeAreViewOrEdit formName="smo_visitProtocolForm">
            <msh:sideLink roles="/Policy/Mis/MedCase/Protocol/Delete" key='ALT+DEL' params="id"
                          action="/entityParentDeleteGoSubclassView-smo_visitProtocol" name="Удалить"
                          confirm="Вы действительно хотите удалить?"/>
         </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
    
    <msh:ifFormTypeIsView formName="smo_visitProtocolForm">
    <msh:sideMenu title="Печать" >
    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/PrintProtocol" 
    	name="Печать дневника"   
    	action='/javascript:printProtocol(".do")' title='Печать дневника' />
    
    </msh:sideMenu>
    </msh:ifFormTypeIsView>
</tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient" beginForm="smo_visitProtocolForm" guid="444ehb-b971-441e-9a90-5194a8019c07" />
    </tiles:put>
    

    <tiles:put name='javascript' type='string'>
    
    <msh:ifFormTypeIsNotView formName="smo_visitProtocolForm">
    <msh:ifNotInRole roles="/Policy/Mis/MedCase/Protocol/NoCheckTime">
    <script type="text/javascript">
    setTimeout(checktime,600000) ;
    function checktime() {
    	if (confirm('Вы хотите сохранить дневник?')) {
    		document.forms[1].action='entityParentSaveGoEdit-smo_visitProtocol.do' ;
    		document.forms[1].submit() ;
    	}
    }
    
    </script>
    </msh:ifNotInRole>
    <msh:ifFormTypeAreViewOrEdit formName="smo_visitProtocolForm">
    <msh:ifFormTypeIsNotView formName="smo_visitProtocolForm">
    <script type="text/javascript">
    $('record').focus() ;
    $('record').selectionStart=$('record').value.length ;
    
    </script>
    </msh:ifFormTypeIsNotView>
    </msh:ifFormTypeAreViewOrEdit>
    </msh:ifFormTypeIsNotView>
    <script type="text/javascript">
    function printProtocol() {
    	HospitalMedCaseService.getPrefixByProtocol(${param.id},
    		{
        callback: function(prefix) {
        	if (prefix==null) prefix="" ;
        	window.location.href="print-protocol"+prefix+".do?m=printProtocol&s=HospitalPrintService&id=${param.id}" ;
            
         }
     }
    ) ;
    }
    
    </script>
    	<msh:ifFormTypeIsNotView formName="smo_visitProtocolForm">
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
    	<msh:ifFormTypeIsCreate formName="smo_visitProtocolForm">
    		<script type="text/javascript">
    			if ($('record').value=="" && confirm("Вы хотите создать дневник на основе шаблона?")) {
    				showtmpTemplateProtocol() ;
    			} else {
    				if ($('dateRegistration').value!="") setFocusOnField('record') ;

    			}
    		</script>
    	</msh:ifFormTypeIsCreate>
    	
    	<msh:ifFormTypeAreViewOrEdit formName="smo_visitProtocolForm"><msh:ifFormTypeIsNotView formName="smo_visitProtocolForm">
    		<script type="text/javascript">

    		TemplateProtocolService.isCanEditProtocol($('id').value,$('username').value,
    			{
                    callback: function(aString) {
                    	//alert(aString) ;
                        if (+aString>0) {} else {
                         	alert('У Вас стоит ограничение на редактрование данного протокола!');
                         	window.location.href= "entityParentView-smo_visitProtocol.do?id=${param.id}";
                         }
                     }
                 });
    		</script>
    	</msh:ifFormTypeIsNotView>
    	
    	</msh:ifFormTypeAreViewOrEdit>
    	<msh:ifFormTypeIsNotView formName="smo_visitProtocolForm">
    		<script type="text/javascript">
    		TemplateProtocolService.getDtypeMedCase($('medCase').value,{
    			callback: function(aDtype) {
                	//alert(aString) ;
                    if (aDtype!=null && aDtype=="HospitalMedCase") {
                    	$('stateName').className="autocomplete horizontalFill required";
                    	$('typeName').className="autocomplete horizontalFill required";
                        $('journalText').className="required maxHorizontalSize";
                    } else if (aDtype!=null && aDtype=="DepartmentMedCase") {
                    	$('typeName').className="autocomplete horizontalFill required";
                    	$('stateName').className="autocomplete horizontalFill required";
                    }
                 }
    		})
    		</script>
    	</msh:ifFormTypeIsNotView>
    </tiles:put>
</tiles:insert>