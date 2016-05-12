<%--
    Свойства документа


    Changes:
    IKO 070228 *** Поддержка экспорта
    IKO 070308 *** Поддержка импорта
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">


    <tiles:put name='body' type='string'>
        <msh:form action="entitySaveGoView-exp_importdocument.do" defaultField="entityClassNameName">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>

            <msh:panel>
                <msh:row>
                    <msh:autoComplete property="entityClassName" label="Класс" horizontalFill="true"
                                      vocName="entities"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="keyName" label="Ключ" size='30'/>
                </msh:row>

                <msh:row>
                    <msh:textArea size="80" property="comment" label="Комментарий" horizontalFill="true"/>
                </msh:row>

                <msh:submitCancelButtonsRow colSpan="4"/>
            </msh:panel>
        </msh:form>

        <msh:ifFormTypeIsView formName="exp_importdocumentForm">
           <msh:section title="Форматы импорта (DBF)" >
			<ecom:webQuery name="dbfFormat" nativeSql="
			select id, comment, actualDateFrom, actualDateTo, disabled 
			from efformat
			where document_id = ${param.id} and formatprovider='0'
			" />
				<msh:table name="dbfFormat" action="entityParentView-exp_format.do" idField="1">
                    <msh:tableColumn columnName="Комментарий" property="2"/>
                    <msh:tableColumn columnName="Дата действия с" property="3"/>
                    <msh:tableColumn columnName="Дата действия по" property="4"/>
                    <msh:tableColumn columnName="Отключен" property="5"/>
                    </msh:table>
			</msh:section>
           
            
          
                
                
          

            <%-- IKO 070228 +++ --%>
            <!-- 070308 -->
            <msh:ifInRole roles="/Policy/Exp/ImportFormat/View">
            <msh:section title="Форматы импорта (XML)">
                <ecom:parentEntityListAll formName="exp_importformatForm" attribute="exportFormats"/>
                <msh:table name="exportFormats" action="entityParentView-exp_importformat.do" idField="id">
                    <msh:tableColumn columnName="Комментарий" property="comment"/>
                    <msh:tableColumn columnName="Дата действия с" property="actualDateFrom"/>
                    <msh:tableColumn columnName="Дата действия по" property="actualDateTo"/>
                    <msh:tableColumn columnName="Отключен" property="disabled"/>
                </msh:table>
            </msh:section>
            </msh:ifInRole>
            <!-- 070228 -->

            <msh:ifInRole roles="/Policy/Exp/ExportFormat/View">

            <msh:section title="Форматы экспорта (XML)">
                <ecom:parentEntityListAll formName="exp_exportformatForm" attribute="exportFormats"/>
                <msh:table name="exportFormats" action="entityParentView-exp_exportformat.do" idField="id">
                    <msh:tableColumn columnName="Комментарий" property="comment"/>
                    <msh:tableColumn columnName="Дата действия с" property="actualDateFrom"/>
                    <msh:tableColumn columnName="Дата действия по" property="actualDateTo"/>
                    <msh:tableColumn columnName="Отключен" property="disabled"/>
                </msh:table>
            </msh:section>
               
            </msh:ifInRole>

            <%-- IKO 070228 === --%>

            <msh:section title="Импортированные данные">
                <ecom:parentEntityListAll formName="exp_importtimeForm" attribute="imports"/>
                <msh:table selection="multy" name="imports" action="entityParentView-exp_importtime.do" idField="id">
                	<msh:tableNotEmpty name="imports">
	                	<tbody>
	                		<tr>
	                			<th class='linkButtons' colspan="6">
	                				<msh:toolbar>
	                					<input type='button' value='Объединить данные' onclick="javascript:appendData()" />
	                				</msh:toolbar>
	                			</th>
	                		</tr>
	                	</tbody>
                	</msh:tableNotEmpty>
                	
                    <msh:tableColumn columnName="Комментарий" property="comment"/>
                    <msh:tableColumn columnName="Файл" property="originalFilename"/>
                    <msh:tableColumn columnName="Размер" property="sizeInBytes"/>
                    <msh:tableColumn columnName="Дата импорта" property="importDate"/>
                    <msh:tableColumn columnName="Дата актуальности с" property="actualDateFrom"/>
                </msh:table>
            </msh:section>
        </msh:ifFormTypeIsView>

    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:ifFormTypeIsView formName="exp_importdocumentForm">

                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-exp_importdocument" name="Изменить"/>
                <%-- IKO 070301 ***
                <msh:sideLink key='ALT+3' params="id" action="/entityParentPrepareCreate-exp_format" name="Создать новый формат" />
                <msh:sideLink key="ALT+4" params="id" action="/entityParentList-exp_importtime" name="Данные"/>
                <msh:sideLink key="ALT+5" params="id" action="/entityParentList-exp_check" name="Проверки"/>
                --%>

                <msh:sideLink key='ALT+3' params="id" action="/entityParentPrepareCreate-exp_format" name="Создать новый формат импорта (DBF)" />
                <msh:ifInRole roles="/Policy/Exp/ImportFormat/Create">
                <msh:sideLink key='ALT+4' params="id" action="/entityParentPrepareCreate-exp_importformat" name="Создать новый формат импорта (XML)" />
                </msh:ifInRole>
                <msh:ifInRole roles="/Policy/Exp/ExportFormat/Create">
                <msh:sideLink key='ALT+5' params="id" action="/entityParentPrepareCreate-exp_exportformat" name="Создать новый формат экспорта (XML)" />
                </msh:ifInRole>
                <msh:sideLink key="ALT+6" params="id" action="/entityParentList-exp_importtime" name="Данные"/>
                <msh:sideLink key="ALT+7" params="id" action="/entityParentList-exp_check" name="Проверки"/>

                <msh:sideLink params="id" action="/exp_exportEdit" name="Экспорт (DBF)" />
                
                <%-- IKO 070301 === --%>

                <msh:sideLink params="id" action="/entityDelete-exp_importdocument"
                              confirm="Удалить документ со всеми данными, форматами и проверками?" name="Удалить"/>

                <msh:sideLink roles="/Policy/Exp/Document/Delete" params="id" action="/exp_importDocumentDeleteAllValues" name="Очистить данные" confirm="Удалить все данные по этому документу?"/>


            </msh:ifFormTypeIsView>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Document" beginForm="exp_importdocumentForm"/>
    </tiles:put>

    <tiles:put name="javascript" type="string">
        <msh:javascriptSrc src='./dwr/interface/CreateKeyForClassNameService.js'/>

        <script type="text/javascript">
        	
       	     function appendData() {
		            var ids = theTableArrow.getInsertedIdsAsParams("id") ;
		            if (ids) {
		                window.location = 'exp_importdataAppend.do?' + ids;
		            } else {
		                alert("Нет выделенных данных для объединения");
		            }
	        }
        	
        	
            <msh:ifFormTypeIsNotView formName="exp_importdocumentForm">
            function updateComment() {
                if ($('comment').value == null || $('comment').value == "") {
                    $('comment').value = $('entityClassNameName').value;
                }
                if ($('entityClassName').value != "" && ($('keyName').value == null || $('keyName').value == "")) {
                    CreateKeyForClassNameService.createKey($('entityClassName').value, {
                        callback: function(aKey) {
                            $('keyName').value = aKey;
                        }
                    });

                }
            }
            eventutil.addEventListener($('entityClassNameName'), "blur", updateComment);
            </msh:ifFormTypeIsNotView>
        </script>
    </tiles:put>


</tiles:insert>