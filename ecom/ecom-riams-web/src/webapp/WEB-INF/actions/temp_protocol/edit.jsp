<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
  <tags:templateProtocol  property="text" name="Temp"/>
    <msh:form action="entitySaveGoView-temp_protocol" defaultField="title" guid="34dc6e2d-dfa9-41a2-b8e1-3a0bdbb24d36">
      <msh:hidden property="id" guid="b13af088-2be6-4450-8c05-aaa9971111bf" />
      <msh:hidden property="saveType" guid="4f251d39-a1c1-46cc-b8a8-6c641aadad7d" />
      <msh:hidden property="medService" guid="ea9befb6-d884-4f43-b90e-f45eb4a310f4" />
      <msh:panel guid="4b44ca53-2bd6-4bdf-9e54-0ea38d8fb582">
        <msh:row guid="d40cb3bf-d3e7-4544-a6ca-9db18a786f47">
          <msh:textField property="title" label="Заголовок шаблона" horizontalFill="true" guid="83e6d295-6ea7-4010-8845-c1f694f8fc2d" fieldColSpan="3" />
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
                <msh:row guid="432f3c8f-a13a-477b-86ac-c2eaaff4fa0e">
          <msh:textArea property="text" label="Текст протокола" horizontalFill="true" guid="d60c66de-1e5f-42b9-b7cb-fb34356ee8bf" fieldColSpan="3" />
        <msh:ifFormTypeIsNotView formName="temp_protocolForm" guid="22ea86e5-018a-4d6e-9795-806789c09700">
          <!--begin-->
                    <td colspan="2">
                        <input type="button" value="Шаблон" onClick="showTempTemplateProtocol()"/>
                    </td>
          <!--end-->
        </msh:ifFormTypeIsNotView>          
        </msh:row>
        <msh:ifFormTypeIsNotView formName="temp_protocolForm">
        <msh:row guid="b9051979-4115-40c0-8d63-4fce097d9a72">
          <ecom:oneToManyOneAutocomplete label="Категории шаблона" vocName="vocTemplateCategory" property="categories" colSpan="4" guid="5a17c3bb-c5f2-4053-98e2-4cbbfd7fccce" />
        </msh:row>
        </msh:ifFormTypeIsNotView>
        <msh:row guid="fdcf0100-ab1c-4900-b7d6-cb08c77924b0">
          <msh:textField property="username" label="Пользователь" viewOnlyField="true" guid="b3fd6145-7072-4065-accc-73fc37fb20ac" />
          <msh:textField property="date" label="Дата создания" viewOnlyField="true" guid="7162d626-b2a7-4928-ab70-adb244c07d5d" />
        </msh:row>
        <msh:ifFormTypeIsNotView formName="temp_protocolForm" guid="22ea86e5-018a-4d6e-9795-806789c09700">
          <!--begin-->
          <tags:keyWord name="text" service="KeyWordService" methodService="getDecryption" />
          <!--end-->
        </msh:ifFormTypeIsNotView>
        <msh:submitCancelButtonsRow colSpan="4" guid="4a71ff37-bc99-4d73-a6a4-80c15f1c29e8" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="temp_protocolForm" guid="770fc32b-aee3-426b-9aba-6f6af9de6c9d">
      <msh:section title="Категории" guid="712b744d-be86-4dc1-9d3a-0ab52eb1bed9">
        <ecom:webQuery name="categories" nativeSql="select c.id,c.name,c.comments from TemplateProtocol_TemplateCategory pc left join TemplateCategory c on c.id=pc.categories_id where pc.protocols_id='${param.id}' group by c.id,c.name,c.comments order by c.name" />
        <msh:table name="categories" action="entityParentView-temp_category.do" idField="1" noDataMessage="Нет данных" guid="123c019a-f668-4454-af88-4897d27728ab">
          <msh:tableColumn property="2" columnName="Название" guid="4d4c6566-75c9-4ef5-931c-723e88d4efbb" />
          <msh:tableColumn property="3" columnName="Комментарии" guid="f6c2e5ba-4045-4dbe-b8fb-bb5d8386e9c4" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>    
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="5db0db09-9993-44cb-8477-a3fee5037b42" title="Шаблон протокола">
      <msh:sideLink key="ALT+1" action="/js-temp_protocol-listTemplate" name="⇧ Список шаблонов протоколов" guid="aa1d3bc4-7c77-483b-b355-0a50f799ba47" roles="/Policy/Diary/Template/View" />
      <msh:ifFormTypeIsView formName="temp_protocolForm" guid="dd63e5e4-f81c-43f2-b50a-f12b1d8e026b">
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-temp_protocol" name="Изменить" roles="/Policy/Diary/Template/Edit" guid="05503c33-989a-45dc-ab6f-8d1be735e97e" />
      </msh:ifFormTypeIsView>
      <hr />
      <msh:ifFormTypeIsView formName="temp_protocolForm" guid="458c4701-2a7c-495e-ad4c-c3326ff8c2bb">
        <msh:sideLink key="ALT+DEL" params="id" action="/entityDelete-temp_protocol" name="Удалить" confirm="Удалить шаблон протокола?" guid="bc31c499-00cd-4cf8-94f0-fcdf1c9915ff" roles="/Policy/Diary/Template/Delete" />
      </msh:ifFormTypeIsView>
    </msh:sideMenu>
    <tags:template_menu currentAction="protocols"/>
  </tiles:put>
  <tiles:put name="javascript" type="string">
    <msh:ifFormTypeIsView formName="temp_protocolForm" guid="4a81e464-1352-415f-9286-596451caf264">
      <script type="text/javascript">//            $('buttonShowAddress').style.display = 'none';</script>
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
    <ecom:titleTrail mainMenu="Template" beginForm="temp_protocolForm" guid="4399c99f-8801-4a73-b168-c25c23f8b0ba" />
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

