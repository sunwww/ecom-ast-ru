<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <msh:form action="formCustomizeSave" defaultField="label" editRoles="/Policy/Stac/CustomizeMode/Edit" createRoles="/Policy/Stac/CustomizeMode/Edit">
    <msh:hidden property="saveType" />
    <msh:hidden property="required" />
    <msh:panel colsWidth='10%,10%,10%'>
      <msh:row>
        <msh:separator colSpan="4" label="Идентификатор поля" />
      </msh:row>
      <msh:row>
        <msh:label label="Форма" property="formName" />
        <msh:label label="Поле" property="elementName" />
      </msh:row>
      <msh:row>
        <msh:label label="Название поля" property="origLabel" fieldColSpan="3" horizontalFill="true" />
      </msh:row>
      <msh:row>
        <msh:label label="nextUrl" property="nextUrl" fieldColSpan="3" horizontalFill="true" />
      </msh:row>
      <msh:row>
        <msh:separator colSpan="4" label="Изменить данные формы" />
      </msh:row>
      <msh:row>
        <msh:textField label="Название" property="newLabel" fieldColSpan="3" horizontalFill="true" />
      </msh:row>
      <msh:row>
        <msh:autoComplete label="Показывать" property="visible" />
      </msh:row>
      <msh:row>
        <msh:textField label="Значение по-умолчанию" property="default" fieldColSpan="3" horizontalFill="true" />
      </msh:row>
    </msh:panel>
    <table>
      <msh:submitCancelButtonsRow colSpan="1" labelSave="Сохранить" labelCreate="" />
    </table>
  </msh:form>

