<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="preg_childBirthForm" guid="0908a638-fd02-4b94-978b-18ab86829e08">
      <msh:sideMenu title="Роды" guid="bc6ceef3-4709-47d9-ba37-d68540cffc61">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_childBirth" name="Изменить" roles="/Policy/Mis/Pregnancy/ChildBirth/Edit" guid="a8d1a1fa-aa31-408a-b1f6-6b9ba1ff18e8" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-preg_childBirth" name="Удалить" roles="/Policy/Mis/Pregnancy/ChildBirth/Delete" guid="91460b8b-80a7-46b3-bc95-a53cd320f687" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="3f5cf55a-2ae6-4367-b9b9-1ce75e0938c4">
        <msh:sideLink params="id" action="/entityParentPrepareCreate-preg_newBorn" name="Данные о новорожденном" title="Показать данные о новорожденном" roles="/Policy/Mis/NewBorn/View" guid="49eb7931-f37b-46fd-8102-ac1b8af96472" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Течение родов
    	  -->
    <msh:form action="/entityParentSaveGoView-preg_childBirth.do" defaultField="pangsStartDate" guid="93666922-7bed-42a7-be5e-b2d52e41d39b">
      <msh:hidden property="id" guid="2821496c-bc8e-4cbe-ba14-ac9a7f019ead" />
      <msh:hidden property="medCase" guid="2104232f-62fa-4f0b-84de-7ec4b5f306b3" />
      <msh:hidden property="saveType" guid="3ec5c007-f4b1-443c-83b0-b6d93f55c6f2" />
      <msh:panel guid="0a4989f1-a793-45e4-905f-4ac4f46d7815">
        <msh:row guid="4bbea36b-255f-441f-8617-35cb54eaf9d0">
          <td width="1px" />
          <td width="1px" />
        </msh:row>
        <msh:row><msh:separator label="Начало родовой деятельности" colSpan="4"/>
        </msh:row>
        <msh:row guid="ab9ced52-ecf7-48b9-8afe-938249840fab">
          <msh:textField property="pangsStartDate" label="Дата" guid="eec0820e-6990-40f8-aa50-f48b8034d7f3" />
          <msh:textField property="pangsStartTime" label="Время" guid="360cc928-1178-46ee-b5c0-5710ff873e3c" />
        </msh:row>
        <msh:row>
        	<msh:separator label="Отхождение вод" colSpan="4"/>
        </msh:row>
        <msh:row guid="84d7a910-53ad-48b2-8582-294aef3497e6">
          <msh:autoComplete property="watersPrematurity" vocName="vocBirthWatesPrematurity" label="Преждевременность отхождения вод" fieldColSpan="3" horizontalFill="true" guid="821b866e-afa4-41d7-bba0-bbc81ab340bf" />
        </msh:row>
        <msh:row guid="1e548b4d-8b56-42e2-9b51-c116784c92a3">
          <msh:textField property="watersDate" label="Дата отхождения вод" guid="34fc13d6-0618-42ac-96e8-d8717302f0bd" />
          <msh:textField property="watersTime" label="Время" guid="ffcc4f9d-05fc-4b65-8def-78adc1c0b908" />
        </msh:row>
        <msh:row>
          <msh:textField property="downWatersAmount" label="Количество нижних вод (мл)" guid="0ec80893-b680-4ec5-838d-38f79bba181b" />
          <msh:textField property="upperWatersAmount" label="Количество верхних вод (мл)" guid="4bca3368-2341-4f2a-aaab-c82b137349fb" />
        </msh:row>
        <msh:row>
          <msh:textField property="downWatersQuality" label="Качество нижних вод" guid="864f219e-5bbe-4377-9c5a-7d52da0e4456" />
          <msh:textField property="upperWatersQuality" label="Качество верхних вод" guid="173aa935-23ed-49d3-a1dc-d6eed8dbd63a" />
        </msh:row>
        <msh:row><msh:separator label="Начало потужной деятельности" colSpan="4"/>
        </msh:row>
        <msh:row>
          <msh:textField property="travailStartDate" label="Дата начала потуг" guid="10d048cc-6dd3-4e6c-8ef2-3b580a02ec7e" />
          <msh:textField property="travailStartTime" label="Время начала потуг" guid="683fa920-9a2c-4f8a-a223-4e59c6f55f85" />
        </msh:row>
        <msh:separator label="Общая продолжительность родов" colSpan="9"/>
        <msh:row guid="0ab970a1-9adb-4534-9d5e-c749136557f8">
          <msh:textField property="period1Duration" label="1 периода (час)" guid="1fc59e7b-1423-4249-9bec-e7131f2221e0" />
          <msh:textField property="period2Duration" label="2 периода (час)" guid="b8f27f6b-bed2-43a0-b0a3-e3eed5f58544" />
        </msh:row>
        <msh:row guid="11eec04b-5366-44fa-a8d8-620c1cab1ff0">
          <msh:textField property="period3Duration" label="3 периода (мин)" guid="79931dee-fefe-480e-b976-f39afd0d003c" />
        </msh:row>
        <msh:row guid="557af74e-7c16-4f10-b51f-43c4125722db">
          <msh:textField property="birthFinishDate" label="Дата окончания родов" guid="4ca102af-a7eb-4b61-95f3-8eba8ae25ac6" />
          <msh:textField property="birthFinishTime" label="Время" guid="bd37fba9-cb02-4966-9301-1a03d62e551e" />
        </msh:row>
        <msh:separator label="Плацента и оболочки" colSpan="9" guid="32379f0e-c12d-43ea-b5b7-8e29e524c0ff" />
        <msh:row guid="a4dbd316-9945-4038-a7ca-914f7642160e">
          <msh:autoComplete label="Послед выделился" property="placentaSeparation" vocName="vocPlacentaSeparation" fieldColSpan="3" horizontalFill="true" guid="8ce53500-60ec-409e-8fd9-3d6f822fd1a6" />
        </msh:row>
        <msh:row guid="c07fa99b-206e-4dad-b1a7-c112173bb4cd">
          <msh:autoComplete fieldColSpan="3" vocName="vocPlacentaIntegrity" property="placentaIntegrity" label="Детское место" guid="bcac0c74-7fb5-49f8-beaa-917a584a812e" horizontalFill="true" />
        </msh:row>
        <msh:row guid="d1181fd6-a4cf-469c-ae0a-fc0f7a9fab42">
          <msh:autoComplete property="membranesIntegrity" label="Целостность оболочек" vocName="vocFetalMembranesIntegrity" fieldColSpan="3" horizontalFill="true" guid="308af9c9-080f-486f-af92-d2be2d0b6e0d" />
        </msh:row>
        <msh:row guid="7330bf51-0d0c-415b-a0fc-e852a175fc6c">
          <msh:autoComplete fieldColSpan="3" vocName="vocMembranesBreakPlace" property="membranesBreakPlace" label="Место разрыва оболочек" guid="12caed25-3ab8-42c6-afba-c9d75bd72034" horizontalFill="true" />
        </msh:row>
        
        <msh:row guid="79ce3dc8-b631-4014-b22e-a83841d34c04">
          <msh:textField property="placentaWeight" label="Вес плаценты (гр.)" guid="0b31bed6-d06e-48c0-aaac-b16e54959b53" />
          <msh:textField property="placentaSize" label="Размеры плаценты" guid="d245643c-f2ba-4e98-a764-0a3c51792866" />
        </msh:row>
        <msh:row guid="3167dc30-1547-4a75-ac6d-193f5384a202">
          <msh:textField property="placentaFeatures" label="Особенности плаценты" guid="f817f39f-82a1-4dca-b2e8-136f15069658" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
          <msh:checkBox property="placentaHistologyOrder" label="Направление плаценты на гистологию" guid="bfc88e8a-d54c-48f9-87e9-6740779e3287" fieldColSpan="3"/>
        </msh:row>

        <msh:row guid="ec1d91f5-4ec4-42e2-bdcb-b3eba2032d22">
          <msh:textField property="hemorrhageVolume" label="Объем кровопотери (мл)" guid="e613e17e-0fad-4dcb-bca5-0261d03cd28c" />
        </msh:row>
        <msh:row guid="21aa5b55-367d-404d-b8c5-03ce63e32329">
          <msh:autoComplete property="placentaInspector" label="Кто исследовал плаценту" vocName="workFunction" fieldColSpan="3" horizontalFill="true" guid="9429d5cb-cf05-42e1-8df8-ec5545f3a198" />
        </msh:row>
        <msh:separator label="Послеродовый период" colSpan="9" guid="b9a073cb-0a22-498c-91d1-e2597bed1f94" />
        <msh:row guid="594aee9a-c209-4c5e-bdab-3aa13dc01dd5">
          <msh:checkBox property="wrongPostNatalPeriod" fieldColSpan="3" label="Неправильный постнатальный период" guid="204f9e1a-b1db-4c4f-a40c-74cae949d39f" />
        </msh:row>
        <msh:row>
          <msh:checkBox property="mammillaChaps" label="Трещины сосков" guid="f9da3a84-c732-4cf2-8d1e-7b378610f55b" />
        </msh:row>
        <msh:row guid="52e685cd-dae1-41fd-93bd-5d520431e53e">
          <msh:textField property="perineumEdema" label="Отек промежности" fieldColSpan="3" horizontalFill="true" guid="fa5ec656-b3a6-4ae4-9799-5103a470142b" />
        </msh:row>
        <msh:row guid="91b10857-d8da-4392-b901-05f8b6e55459">
          <msh:textField property="haemorrhoids" label="Геморрой" fieldColSpan="3" horizontalFill="true" guid="31c97d61-4401-4e1c-997b-addebf8b6b26" />
        </msh:row>
        <msh:row guid="2c6ca870-43ed-4858-a408-6cd1d57c30bd">
          <msh:textField property="postNatalDisease" label="Послеродовая болезнь" fieldColSpan="3" horizontalFill="true" guid="0bbbe146-5eec-46f9-aa9b-e89c2ce29d34" />
        </msh:row>
        <msh:row guid="12838637-2ecb-4b5f-bbca-aa8889069c31">
          <msh:textField property="notPostNatalDisease" label="Болезнь, не зависящая от родов" fieldColSpan="3" horizontalFill="true" guid="ed38468f-bca6-4e74-b4a7-5fa5e3ee8bfe" />
        </msh:row>
        <msh:row guid="b0a5f06f-bc47-4dd7-a8f1-8f08ac4d2b1c">
          <msh:autoComplete property="feverWithoutDiagnosis" label="Повышение температуры без диагноза" vocName="vocFeverFeature" fieldColSpan="3" horizontalFill="true" guid="086e9d43-17c4-4f8d-beb3-17c73b5236e6" />
        </msh:row>
        <msh:row guid="da3f817e-9db7-46f0-bd1c-dedfb2ec8bf5">
          <msh:textArea property="treatmentFeatures" label="Особенности лечения" fieldColSpan="3" horizontalFill="true" guid="aa1e9f8e-0326-4d38-935d-986eb39e9d93" />
        </msh:row>
        <msh:row guid="584f8a80-e54f-4d5a-bda4-d334e79be843">
          <msh:textField property="dischangeMotherCondition" label="Состояние матери при выписке" fieldColSpan="3" horizontalFill="true" guid="e8b99bd4-b1ce-4db3-9fd6-71dba21a86d8" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="3" guid="bd5bf27d-bcd4-4779-9b5d-1de22f1ddc68" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="preg_childBirthForm" guid="07462ced-904f-4485-895c-0107f05b5d8d">
      <msh:ifInRole roles="/Policy/Mis/NewBorn/View" guid="187f5083-94a7-42fd-a428-7f9d4720bfd1">
        <ecom:parentEntityListAll attribute="newBorns" formName="preg_newBornForm" guid="35b71f42-e1fc-40f2-93e5-0908ea385878" />
        <msh:tableNotEmpty name="newBorns" guid="bd28e321-5e07-4e52-95dc-9851c96a0007">
          <msh:section title="Данные о новорожденных" guid="7aee16b5-d063-4868-8891-313de24ca013">
            <msh:table name="newBorns" action="entityParentView-preg_newBorn.do" idField="id" guid="e5ff27a4-e8ae-44ef-a20f-7a6f71f42f3a">
              <msh:tableColumn columnName="Дата рождения" property="birthDate" guid="724d2c83-221e-4a44-9144-5346fa8fefd2" />
              <msh:tableColumn columnName="Время рождения" property="birthTime" guid="17991748-77de-4b16-8c94-740bbfa10e7a" />
            </msh:table>
          </msh:section>
        </msh:tableNotEmpty>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_childBirthForm" guid="d16befe8-59da-47d9-9c54-ee0d13e97be2" />
  </tiles:put>
</tiles:insert>

