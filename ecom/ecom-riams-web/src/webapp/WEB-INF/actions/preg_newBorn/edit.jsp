<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="preg_newBornForm" guid="c8ee4cc0-2ebd-4772-8d66-f2891f3c6f04">
      <msh:sideMenu title="Новорожденный" guid="bef784df-2afa-4cbb-8052-3b0ea730b81c">
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-preg_newBorn" name="Изменить" roles="/Policy/Mis/NewBorn/Edit" guid="920412b7-c092-4ef5-bec9-d0e235b8f8c4" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-preg_newBorn" name="Удалить" roles="/Policy/Mis/NewBorn/Delete" guid="dc3b73ce-bf8d-4651-baf0-718b8779e8da" />
      </msh:sideMenu>
      <msh:sideMenu title="Показать">
    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/View" name="Отделения по новорожденному" 
    	params="id"  action='/entityParentList-stac_newBornSlo'  key='Alt+6' title='Лечение в отделениях по новорожденному' 
    	styleId="stac_newBornSlo"
    	/>      
      </msh:sideMenu>
      <msh:sideMenu title="Печать" guid="d942ffee-6e9f-4317-9fa7-8dc776d47e90">
        <msh:sideLink params="id" action="/print-newbornhistory.do?s=PrintNewBornHistoryService&amp;m=printNewBornHistory" name="Историю развития новорожденного" guid="439e8484-527f-4f10-aa0f-4073e6431478" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Новорожденный
    	  -->
    <msh:form action="/entityParentSaveGoView-preg_newBorn.do" defaultField="lastname" guid="88a846fc-510d-4ffd-b68a-f4157d2ccdba">
      <msh:hidden property="id" guid="227e3745-e945-40cb-891e-ba073b77e5c6" />
      <msh:hidden property="medCase" guid="e078491f-f05c-48ab-b515-69cca8aff105" />
      <msh:hidden property="childBirth" guid="54270d91-95d2-4ef9-b466-2a7f5c640871" />
      <msh:hidden property="saveType" guid="fe3095ba-8a17-4e20-a90a-e725d5d8c7c1" />
      <msh:hidden property="patient" guid="d1dbbb8c-6fd4-4303-87e3-eefed35dae3f" />
      <msh:panel guid="6698b1a2-f6ce-4e70-85a3-0c3689e690da" colsWidth="7%,7%,4%">
        <msh:ifFormTypeIsNotView formName="preg_newBornForm" guid="0cc493cc-488d-4b55-b90f-68a5ca3e4fd5">
          <msh:row guid="9625d712-b37d-4426-8072-de2ccf878aa4">
            <msh:textField property="lastname" label="Фамилия" guid="39c48320-804b-4b00-8ca2-5d85cd0f7ee5" horizontalFill="true" />
          	<msh:autoComplete property="child" vocName="vocChild" label="Ребенок" horizontalFill="true" size="25"/>
          </msh:row>
          <msh:row guid="9851b07d-f4b5-45e5-a028-68f0bba966ce">
            <msh:textField property="middlename" label="Отчество" guid="ab852d1b-5231-4c29-9faa-62f7101b3c26" horizontalFill="true" />
          </msh:row>
        </msh:ifFormTypeIsNotView>
        <msh:ifFormTypeIsView formName="preg_newBornForm" guid="2211c6d9-2251-4059-b91c-780e6bf32bad">
          <msh:row guid="90569cad-0d3e-4dbc-93d2-79a86a59eefe">
            <msh:autoComplete property="patient" vocName="patient" label="ФИО" guid="c7d24f51-85cd-4714-83bf-84c071df9efd" fieldColSpan="3" horizontalFill="true" viewOnlyField="true" viewAction="entityView-mis_patient.do" />
          </msh:row>
        </msh:ifFormTypeIsView>
        <msh:row guid="712f1fdd-f61f-4f8f-8f82-3a16fa870225">
          <msh:autoComplete property="sex" vocName="vocSex" label="Пол" guid="171b9903-3e96-4d7b-9c5e-f374cf8ea696" horizontalFill="true" />
        </msh:row>
        <msh:row guid="7ed1ae1c-b636-481a-b020-abf75a0d68d5">
          <msh:textField property="birthDate" label="Дата рождения" guid="d72aef25-6193-4b14-8311-e6841db6162b" />
          <msh:textField property="birthTime" label="Время" guid="8cfac7a8-4610-46ee-a906-3f057a87a426" />
        </msh:row>
        <msh:row guid="ffcd5ba0-5927-43df-8de8-78486cb9da6f">
          <msh:textField property="birthWeight" label="Вес при рождении (гр.)" guid="d9c42906-074e-4514-a87e-103a475c55f4" />
          <msh:textField property="birthHeight" label="Рост при рождении (см)" guid="8dd9c5cc-7b1d-4f60-91b2-717d1f2c1e6f" />
        </msh:row>
        <msh:row guid="b8af5514-e54a-4e01-9ca9-c2d245657a21">
          <msh:textField property="headCircle" label="Окружность головы" guid="0314588d-60e4-4800-b094-ed7dc3f6268c" />
          <msh:textField property="shouldersCircle" label="Окружность плеч" guid="3cd23d3e-00a4-4053-a814-5a34c81d5231" />
        </msh:row>
        <msh:row guid="43669dbf-aba5-48b7-8979-20b12d5e196b">
          <msh:textField property="livingPart" label="Вышел (часть тела)" guid="ab67aa43-7f24-4e7b-ae1a-e96df32e29c8" />
          <msh:checkBox property="liveBorn" label="Родился живым" guid="e7209c6c-2030-4787-9c53-6a471191e4ec" />
        </msh:row>
        <msh:row guid="59f403df-942b-49f4-b67c-46baf688b3a6">
          <msh:autoComplete property="maturity" vocName="vocNewBornMaturity" label="Зрелость" horizontalFill="true" fieldColSpan="3" guid="f6d7e46c-adc3-4225-810a-8fccc2c1a137" />
        </msh:row>
        <msh:row guid="cca33c1f-0258-4790-8982-c0f578fec4e6">
          <msh:textField property="birthState" label="Состояние при рождении" horizontalFill="true" fieldColSpan="3" guid="50f9ebae-0c85-496b-97c5-376576f7f4b4" />
        </msh:row>
        <msh:row guid="c7ee4578-8d70-4735-a152-76b44c70644b">
          <msh:textField property="setPart" label="Вставленная часть тела" guid="af5727c6-6da3-486e-8dd0-892c32d938f7" />
          <msh:textField property="setView" label="Вид вставки" guid="21a1677c-35b9-49b6-8727-0b69c861b5a4" />
        </msh:row>
        <msh:row guid="42bc7a86-2a89-4d8d-83b7-bc1b7bccc8d4">
          <msh:separator label="Размеры" colSpan="4" guid="15f30f1f-6e2a-4b33-85e3-0573a197aaeb" />
        </msh:row>
        <msh:row guid="36205540-4dea-4a97-93f6-e209f157572b">
          <msh:textField property="directSize" label="Прямой (см)" guid="385466bb-bb72-4ba9-89c6-0563a29951b6" />
          <msh:textField property="directCircleSize" label="Прямой окружности (см)" guid="a36b3501-800c-4029-8235-df2dda57fe7b" />
        </msh:row>
        <msh:row guid="5aa4c7b2-656e-4075-9233-3b1d8f0c71fa">
          <msh:textField property="shortSkewSize" label="Малый косой" guid="76786701-5dae-4faa-9270-93de2b0e094c" />
          <msh:textField property="shortSkewCircleSize" label="Малой косой окружности (см)" guid="c9e85931-5b26-4c7a-b9e5-03259f8b18ab" />
        </msh:row>
        <msh:row guid="64db250f-ccd3-4515-b6ca-e0f0f982b477">
          <msh:textField property="longSkewSize" label="Большой косой (см)" guid="4302c2c5-b4e6-48f8-a23e-fdf24b3b3bf4" />
          <msh:textField property="longSkewCircleSize" label="Большой косой окружности (см)" guid="9da31023-db29-4d2b-9568-64b37a28bc4b" />
        </msh:row>
        <msh:row guid="8f77885a-1879-4418-b9fe-47374eb08f85">
          <msh:textField property="shortTransversalSize" label="Малый поперечный (см)" guid="4707bb1c-4225-4242-84e3-3d6e6e074a55" />
          <msh:textField property="longTransversalSize" label="Большой поперечный (см)" guid="10c5e255-c48b-4ebb-a797-1de276df8b8f" />
        </msh:row>
        <msh:row guid="5f38a220-3e41-408b-bb66-231156b77f68">
          <msh:separator label="Пуповина" colSpan="4" guid="921f6c76-69ed-4032-9adc-d780405df2d8" />
        </msh:row>
        <msh:row guid="bb717265-975a-4084-8cb2-a8785c800583">
          <msh:textField property="cordLength" label="Длина пуповины (см)" guid="f6171ecf-bc9a-42c5-b458-ac7833911ed2" />
          <msh:textField property="loopCordAmount" label="Кол-во обвитий пуповиной" guid="b72af3bf-7d76-45d8-b6fe-0f3c3a4be620" />
        </msh:row>
        <msh:row guid="e7fd00f3-b4c5-4bf6-ada2-5603a53a1186">
          <msh:autoComplete property="cordAttaching" horizontalFill="true" vocName="vocNewBornCordAttaching" label="Прикрепление пуповины" guid="9e8a6644-c476-4308-b5cf-fef98328c1bb" />
          <msh:textField property="hemorrhageVolume" label="Кровопотеря (мл)" guid="ef03770a-3e38-4866-99f7-db2c2762f4f3" />
        </msh:row>
        <msh:row guid="bd84b98f-8c49-48ac-bf83-670c46c7c85a">
          <msh:textField property="loopCordPart" label="Часть тела, обвитая пуповиной" fieldColSpan="3" horizontalFill="true" guid="26888759-b093-4c23-9bcd-8b2da9a4aedb" />
        </msh:row>
        <msh:row guid="c3c7839e-e37d-4979-b2ea-23937b555c8d">
          <msh:separator label="Дополнительно" colSpan="4" guid="5507691a-5fdf-4588-be0f-83c6605419c5" />
        </msh:row>
        <msh:row guid="62deaeba-2fda-43fc-97b7-89c0ae2a5f42">
          <msh:textField property="oesophagusPermeability" label="Проходимость пищевода" guid="3cdd3867-1684-4184-9050-41ee7b2a7219" horizontalFill="true" />
          <msh:checkBox property="anus" label="Наличие ануса" guid="efeb3f63-f958-4f8f-919b-f68df6ab20df" />
        </msh:row>
        <msh:row guid="8392365c-d4d2-4df5-9b69-f8b273165275">
          <msh:textField property="malformations" label="Пороки развития" fieldColSpan="3" horizontalFill="true" guid="495d5ee7-30fa-478f-8e04-53e3f8d2e9d1" />
        </msh:row>
        <msh:row guid="0ba5c980-775f-4a75-8f09-7bfe5fc46f68">
          <msh:autoComplete property="childDeliverer" label="Кто принимал ребенка" vocName="workFunction" fieldColSpan="3" horizontalFill="true" guid="c2a66ae9-2bf4-4e42-a879-f641e5ae95d0" />
        </msh:row>
        <msh:row guid="70ed7bfa-e2a6-46a9-b78d-bc041b32fa36">
          <msh:textArea property="mothersHarmfulEffect" label="Вредное влияние матери" rows="2" fieldColSpan="3" guid="9db73a80-87e6-413e-8393-3f0edb9a76e4" />
        </msh:row>
        <msh:row guid="8beb7730-4f41-43ee-9adf-ddae331545f1">
          <msh:textArea property="fathersHarmfulEffect" label="Вредное влияние отца" rows="2" fieldColSpan="3" guid="8016c3e0-94dd-49d6-8285-5383412d1da3" />
        </msh:row>
        <msh:row guid="2be7215c-2830-421d-9500-1bc3c7a5f9d5">
          <msh:textField property="createDate" label="Дата создания" viewOnlyField="true" guid="309593a6-8300-4ea9-be02-be2d0ef8cd1b" />
          <msh:textField property="createTime" label="Время" viewOnlyField="true" guid="fac5b2da-e4ff-4370-a1fd-9f5fabf69ac2" />
        </msh:row>
        <msh:row guid="ca06aabe-2a85-4477-81b5-9590545b3b87">
          <msh:textField property="username" label="Пользователь" viewOnlyField="true" guid="e6946c8a-08e3-4c5e-bc45-500c5fa53b01" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="3" guid="15e2342c-9373-4dec-b306-0bc21c1b603c" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_newBornForm" guid="59d4465e-092d-4353-98ff-af27d0a2c482" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
  <msh:ifFormTypeIsCreate formName="preg_newBornForm">
  	<script type="text/javascript">
  		$('liveBorn').checked=true ;
  	</script>
  </msh:ifFormTypeIsCreate>
  </tiles:put>
</tiles:insert>

