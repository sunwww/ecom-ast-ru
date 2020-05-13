<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
  <tags:templateProtocol  property="text" name="Temp" voc="protocolTicketByPatient"/>
    <msh:form action="entitySaveGoView-temp_protocol" defaultField="title">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="medService" />
      <msh:panel>
        <msh:row>
          <msh:textField property="title" label="Заголовок шаблона" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
                <msh:ifFormTypeIsNotView formName="temp_protocolForm">
	                <msh:row>
	                	<td colspan="1">
	                	<b>Горячие клавиши для поля "ТЕКСТ ПРОТОКОЛА"</b>
	                	</td>
	                	<td colspan=3>
	                		<i>ESC</i> завершение редактирования поля, переход на следующее поле<br>
	                		<i>CTRL+ENTER</i> сохранение протокола<br>
	                		<i>SHIFT+ESC</i> отмена
	                	</td>
	                </msh:row>
                </msh:ifFormTypeIsNotView>
                <msh:row>
          <msh:textArea property="text" label="Текст протокола" horizontalFill="true" fieldColSpan="3" />
        <msh:ifFormTypeIsNotView formName="temp_protocolForm">
          <!--begin-->
                    <td colspan="2">
                        <input type="button" value="Шаблон" onClick="showTempTemplateProtocol()"/>
                    </td>
          <!--end-->
        </msh:ifFormTypeIsNotView>          
        </msh:row>
        <msh:ifFormTypeIsNotView formName="temp_protocolForm">
        <msh:row>
          <ecom:oneToManyOneAutocomplete label="Категории шаблона" vocName="vocTemplateCategory" property="categories" colSpan="4" />
        </msh:row>
        </msh:ifFormTypeIsNotView>
        <msh:row>
          <ecom:oneToManyOneAutocomplete  viewAction="entityView-secgroup.do" label="Довер. группы" vocName="secGroup" property="secGroups" colSpan="4" />
        </msh:row>
        <msh:row>
        <msh:checkBox property="disableEdit" label="Запрет на ручное изменение дневника"/>
          <msh:textField property="username" label="Пользователь" viewOnlyField="true" />
          <msh:textField property="date" label="Дата создания" viewOnlyField="true" />
        </msh:row>
        <msh:ifFormTypeIsNotView formName="temp_protocolForm">
          <!--begin-->
          <tags:keyWord name="text" service="KeyWordService" methodService="getDecryption" />
          <!--end-->
        </msh:ifFormTypeIsNotView>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="temp_protocolForm">
      <msh:section title="Категории">
        <ecom:webQuery name="categories" nativeSql="select c.id,c.name,c.comments from TemplateProtocol_TemplateCategory pc left join TemplateCategory c on c.id=pc.categories_id where pc.protocols_id='${param.id}' group by c.id,c.name,c.comments order by c.name" />
        <msh:table name="categories" action="entityParentView-temp_category.do" idField="1" noDataMessage="Нет данных">
          <msh:tableColumn property="2" columnName="Название" />
          <msh:tableColumn property="3" columnName="Комментарии" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>    
     <msh:ifFormTypeIsView formName="temp_protocolForm">
      <msh:section title="Печатные формы шаблона">
        <ecom:webQuery name="protocolPrints" nativeSql="select ud.id ,ud.name,ud.fileName from userDocument ud where ud.template='${param.id}' order by ud.name" />
        <msh:table name="protocolPrints" action="entityParentView-temp_protocolPrint.do" idField="1" noDataMessage="Нет данных">
          <msh:tableColumn property="2" columnName="Название шаблона для печати" />
          <msh:tableColumn property="3" columnName="Название файла" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>    
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink key="ALT+N" action="/entityPrepareCreate-temp_protocol" name="Шаблон протокола" roles="/Policy/Diary/Template/Create" />
      <msh:sideLink key="ALT+N" action="/entityParentPrepareCreate-temp_protocolPrint" params="id" name="Шаблон для печати" roles="/Policy/Diary/Template/Create" />
    </msh:sideMenu>
    <msh:sideMenu title="Шаблон протокола">
      <msh:sideLink key="ALT+1" action="/js-temp_protocol-listTemplate" name="⇧ Список шаблонов протоколов" roles="/Policy/Diary/Template/View" />
      <msh:ifFormTypeIsView formName="temp_protocolForm">
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-temp_protocol" name="Изменить" roles="/Policy/Diary/Template/Edit" />
        <msh:sideLink key="ALT+3" params="id" action="/diary_templateParamsEdit" name="Параметры" roles="/Policy/Diary/Template/Edit"/>
        <msh:sideLink key="ALT+3" action="/javascript:showParametersToPrint()" name="Показать параметры для печати" roles="/Policy/Diary/Template/View"/>
      </msh:ifFormTypeIsView>
      <hr />
      <msh:ifFormTypeIsView formName="temp_protocolForm">
        <msh:sideLink key="ALT+DEL" params="id" action="/entityDelete-temp_protocol" name="Удалить" confirm="Удалить шаблон протокола?" roles="/Policy/Diary/Template/Delete" />
      </msh:ifFormTypeIsView>
    </msh:sideMenu>
    <tags:template_menu currentAction="protocols"/>
    
  </tiles:put>
  <tiles:put name="javascript" type="string">
    <script type="text/javascript">
        $('disableEdit').disabled=true;
    </script>
    <msh:ifFormTypeIsView formName="temp_protocolForm">
    <script type='text/javascript' src='./dwr/interface/TemplateProtocolService.js'></script>
      <script type="text/javascript">
      //            $('buttonShowAddress').style.display = 'none';
      function showParametersToPrint() {
      TemplateProtocolService.getTemplateParametersList($('id').value,{
    	  callback: function (a) {
    		  if (a!=null&&a!=''){
    			  var rows = a.split("#");
    			  a='';
    			  for (var i=0;i<rows.length;i++) {
    				  a+=rows[i]+'\n';
    			  }
    			  
    			  alert (a);  
    		  } else {
    			  alert (a);
    		  }
    		  
    	  }
      });
      }
      
      </script>
    </msh:ifFormTypeIsView>
    	<msh:ifFormTypeIsNotView formName="temp_protocolForm">
 			<script type="text/javascript">
			    eventutil.addEventListener($('text'), "keydown", 
			  	function(aEvent) {
			  		if(aEvent.ctrlKey && eventutil.isKey(aEvent, eventutil.VK_ENTER)){
	            		$('submitButton').click() ;
				  	} else if(aEvent.shiftKey && eventutil.isKey(aEvent, eventutil.VK_ESCAPE)) {
				            $('cancelButton').click() ;
        
			            } else if(eventutil.isKey(aEvent, eventutil.VK_ESCAPE)) {
							$('text').blur() ;
				            try {
				                $('submitButton').focus() ;
				                $('submitButton').select() ;
				                return false ;
				            } catch (e) {}										 
			            }
			            
				  	}) ;    		
  			</script>   	
  			
    	
    	</msh:ifFormTypeIsNotView>
    	<msh:ifFormTypeAreViewOrEdit formName="temp_protocolForm"><msh:ifFormTypeIsNotView formName="temp_protocolForm">
    		<script type="text/javascript">
    		TemplateProtocolService.getUsername(
    			{
                    callback: function(aString) {
                    	if ($('username').value=="") {}
                        if($('username').value != aString){
                         	alert('Вы можете редактировать только созданный Вами шаблон');
                         	window.location.href= "entityView-temp_protocol.do?id=${param.id}";
                         }
                     }
                 }
    		);
    		</script>
    	</msh:ifFormTypeIsNotView>
    	
    	</msh:ifFormTypeAreViewOrEdit>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Template" beginForm="temp_protocolForm" />
  </tiles:put>
    <tiles:put name='style' type='string'>
        <msh:ifFormTypeIsView formName="temp_protocolForm">
	    	<style type='text/css'>
	    		.text {
	    			white-space: pre;
	    			font-weight: normal;
	    		}
	    		
	    	</style>
    	</msh:ifFormTypeIsView>
    </tiles:put>
</tiles:insert>

