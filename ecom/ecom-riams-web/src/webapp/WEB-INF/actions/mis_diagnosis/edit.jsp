<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='body' type='string'>
        <msh:form action="entitySaveGoView-mis_diagnosis.do" defaultField="id">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:hidden property="patient"/>
            <msh:panel>
                <msh:ifFormTypeIsNotView formName="mis_diagnosisForm">
                 <msh:sectionTitle>Пациент</msh:sectionTitle>
                 <msh:sectionContent>
                    <msh:row>
                        <msh:label property="patient" label="Номер" size="10"/>
                        <msh:label property="patient.info" label="Фамилия" horizontalFill="true" size="50"/>                       
                     </msh:row>
                  </msh:sectionContent>
                  <msh:sectionTitle>Установлен</msh:sectionTitle>
                  <msh:sectionContent>
                    <msh:row>
                        <msh:textArea property="name" label="Диагноз" horizontalFill="true" size="50"/>
                    </msh:row>
                    <msh:row>
                        <msh:textField property="date" label="Дата"/>
                        <msh:textField property="worker.info" label="Кем" horizontalFill="true" size="30"/>
                    </msh:row>
                    <msh:row>
                       <msh:textField property="diagnosisPrior.name" label="Вместо диагноза" horizontalFill="true" size="50"/>
                    </msh:row>
                  </msh:sectionContent>
                  <msh:sectionTitle>Уточнен</msh:sectionTitle>
                  <msh:sectionContent>
                    <msh:row>                  
                      <msh:textField property="accurationDate" label="Дата уточнения"/>
                      <msh:textField property="idc10" label="МКБ10" size="10" />
                     </msh:row>   
                    <msh:row>                    
                      <msh:autoComplete property="idc10" label="Название" vocName="VocIdc10" horizontalFill="true" size="50"/>                                      
                     </msh:row>  
                  </msh:sectionContent>                 
    
                        <msh:textField property="disableDate" label="Дата отмены"/>
                    </msh:row>
                    <msh:row>
                        <msh:textField property="diagnosisNext.name" label="Заменен диагнозом" horizontalFill="true" size="50"/>
                    </msh:row>
                    <msh:row>
 
                    </msh:row>
                </msh:ifFormTypeIsNotView>  
            </msh:panel>

        </msh:form>

        <msh:ifFormTypeIsView formName="mis_diagnosisForm">
            <msh:section>
                <msh:sectionTitle>Образование</msh:sectionTitle>
                <msh:sectionContent>
                    <ecom:parentEntityListAll formName="mis_educationForm" attribute="educ"/>
                    <msh:table name="educ" action="entityParentView-mis_education.do" idField="id">
                        <msh:tableColumn columnName="Учреждения" property="nameInstitut"/>
                        <msh:tableColumn columnName="Специальность" property="nameSpec"/>
                        <msh:tableColumn columnName="Образование" property="nameEduc"/>
                        <msh:tableColumn columnName="Дата окончания" property="dateFinish"/>
                    </msh:table>
                </msh:sectionContent>
            </msh:section>

            <msh:section>
                <msh:sectionTitle>Аттестация</msh:sectionTitle>
                <msh:sectionContent>
                    <ecom:parentEntityListAll formName="mis_specializationForm" attribute="spec"/>
                    <msh:table name="spec" action="entityParentView-mis_specialization.do" idField="id">
                        <msh:tableColumn columnName="Наименоваие организации" property="nameInstitut"/>
                        <msh:tableColumn columnName="Специализация" property="nameSpec"/>
                        <msh:tableColumn columnName="Дата получения сертификата" property="dateGetCertif"/>
                    </msh:table> 
                </msh:sectionContent>
            </msh:section>

            <msh:section>
                <msh:sectionTitle>Должности</msh:sectionTitle>
                <msh:sectionContent>
                    <ecom:parentEntityListAll formName="mis_workBookForm" attribute="text"/>
                    <msh:table name="text" action="entityParentView-mis_workBook.do" idField="id">
                        <msh:tableColumn columnName="Должность" property="namePost"/>
                        <msh:tableColumn columnName="Совместительство" property="nameCombo"/>
                        <msh:tableColumn columnName="Дата приёма" property="dateOn"/>
                        <msh:tableColumn columnName="Дата увольнения" property="dateOff"/>
                    </msh:table>
                </msh:sectionContent>
            </msh:section>
        </msh:ifFormTypeIsView>
        <tags:addressTag/>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:ifFormTypeIsView formName="mis_diagnosisForm">
                <msh:sideLink key="ALT+1" params="id" action="/entityParentList-mis_diagnosis" name="⇧ Список сотрудников"/>
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-mis_diagnosis" name="Изменить"/>
            </msh:ifFormTypeIsView>
            <msh:ifFormTypeIsView formName="mis_diagnosisForm">
                <msh:sideLink key="ALT+3" params="id" action="/entityParentPrepareCreate-mis_education" name="Добавить образование" title="Добавить образование"/>
                <msh:sideLink key="ALT+4" params="id" action="/entityParentPrepareCreate-mis_specialization" name="Добавить аттестат" title="Добавить аттестат"/>
                <msh:sideLink key="ALT+5" params="id" action="/entityParentPrepareCreate-mis_workBook" name="Добавить должность" title="Добавить должность"/>
            </msh:ifFormTypeIsView>
            <hr/>
            <msh:ifFormTypeAreViewOrEdit formName="mis_diagnosisForm">
                <msh:sideLink key='ALT+DEL' params="id" action="/entityDelete-mis_diagnosis" name="Удалить" confirm="Удалить сотрудника?"/>
            </msh:ifFormTypeAreViewOrEdit>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name="javascript" type="string">
        <msh:ifFormTypeIsView formName="mis_diagnosisForm">
            <script type="text/javascript">
                $('buttonShowAddress').style.display = 'none';
            </script>
        </msh:ifFormTypeIsView>
    </tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Lpu" beginForm="mis_diagnosisForm" />
    </tiles:put>


</tiles:insert>