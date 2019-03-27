<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="preg_robsonClassForm" guid="0908a638-fd02-4b94-978b-18ab86829e08">
            <msh:sideMenu title="Классификация Робсона" guid="bc6ceef3-4709-47d9-ba37-d68540cffc61">
                <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_robsonClass" name="Изменить" roles="/Policy/Mis/Pregnancy/ChildBirth/Edit" guid="a8d1a1fa-aa31-408a-b1f6-6b9ba1ff18e8" />
                <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-preg_robsonClass" name="Удалить" roles="/Policy/Mis/Pregnancy/ChildBirth/Delete" guid="91460b8b-80a7-46b3-bc95-a53cd320f687" />
            </msh:sideMenu>
        </msh:ifFormTypeIsView>
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:form action="/entityParentSaveGoView-preg_robsonClass.do" defaultField="durationPregnancy" guid="93666922-7bed-42a7-be5e-b2d52e41d39b">
            <msh:hidden property="id" guid="2821496c-bc8e-4cbe-ba14-ac9a7f019ead" />
            <msh:hidden property="medCase" guid="2104232f-62fa-4f0b-84de-7ec4b5f306b3" />
            <msh:hidden property="saveType" guid="3ec5c007-f4b1-443c-83b0-b6d93f55c6f2" />
            <msh:hidden property="robsonType" />
            <msh:hidden property="robsonSub" />
            <msh:panel guid="0a4989f1-a793-45e4-905f-4ac4f46d7815">
                <div id="classRobsonsDiv"></div>
                <div id="subRobsonsDiv"></div>
                <msh:ifFormTypeAreViewOrEdit formName="preg_robsonClassForm">
                    <msh:row>
                        <msh:separator label="Дополнительная информация" colSpan="4"/>
                    </msh:row>
                    <msh:row>
                        <msh:label property="createDate" label="Дата создания"/>
                        <msh:label property="createTime" label="время"/>
                    </msh:row>
                    <msh:row>
                        <msh:label property="createUsername" label="пользователь"/>
                    </msh:row>
                    <msh:row>
                        <msh:label property="editDate" label="Дата редактирования"/>
                        <msh:label property="editTime" label="время"/>
                    </msh:row>
                    <msh:row>
                        <msh:label property="editUsername" label="пользователь"/>
                    </msh:row>
                </msh:ifFormTypeAreViewOrEdit>
                <msh:submitCancelButtonsRow colSpan="3"  guid="bd5bf27d-bcd4-4779-9b5d-1de22f1ddc68" functionSubmit="save();"/>
            </msh:panel>
        </msh:form>
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Patient" beginForm="preg_robsonClassForm" guid="d16befe8-59da-47d9-9c54-ee0d13e97be2" />
    </tiles:put>
    <script type="text/javascript" src="./dwr/interface/PregnancyService.js" >/**/</script>
    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            <msh:ifFormTypeIsCreate formName="preg_robsonClassForm">
            //Если уже есть и мы переходим с СЛО, запустить просмотр существующей
            function loadExisting() {
                PregnancyService.getIfRobbsonClassOrMisbirthAlreadyExists($('medCase').value,true,{
                    callback: function(aResult) {
                        if (aResult!='')
                            window.location.href='entityParentEdit-preg_robsonClass.do?id='+aResult;
                    }
                });
            }
            loadExisting();
            </msh:ifFormTypeIsCreate>
            var voc='VocRobsonClass';
            function loadYesNoRobson() {
                var txt="";
                VocService.getAllValueByVocs(voc,{
                    callback: function(aResult) {
                        var vocRes=JSON.parse(aResult).vocs[0];
                        txt+="<table border='1px'><tbody>";
                        for (var ind1 = 0; ind1 < vocRes.values.length; ind1++) {
                            var vocVal = vocRes.values[ind1];
                            txt+="<tr><td><label  id='" + voc + vocVal.id + "'>"+vocVal.name+"</label></td>";
                            txt+="<td><select id='yesNo" + vocVal.id + "'";
                            <msh:ifFormTypeIsView formName="preg_robsonClassForm" guid="07462ced-904f-4485-895c-0107f05b5d8d">
                            txt+=" disabled='true' ";
                            </msh:ifFormTypeIsView>
                            txt+=" onchange='loadSubs();' ";
                               txt+= "><option>Нет</option>" +
                                "<option>Да</option>" +
                                "</select><td></tr>";
                        }
                        txt+="</tbody></table>";
                        document.getElementById('classRobsonsDiv').innerHTML+=txt;
                        <msh:ifFormTypeAreViewOrEdit formName="preg_robsonClassForm" guid="07462ced-904f-4485-895c-0107f05b5d8d">
                        document.getElementById('yesNo'+$('robsonType').value).selectedIndex=1;
                        </msh:ifFormTypeAreViewOrEdit>
                        loadSubs();
                    }
                });
            }
            window.onload=function() { loadYesNoRobson(); }
            function save() {
                var id=checkYesNoGetIndex();
                if (id!=-1) {
                    $('robsonType').value=id;
                    document.forms["mainForm"].submit();
                }
                else showToastMessage('Обязательно должен быть выбран ОДИН из подпунктов!',null,true);
            }
            //коряво, но такие странные требования
            function checkYesNoGetIndex() {
                var selects = document.getElementsByTagName('select');
                var count=0;
                var id=-1;
                for (var i = 0; i < selects.length; i++) {
                    if (selects[i].selectedIndex==1 && selects[i].id.indexOf('yesNo')!=-1) {
                        id=selects[i].id.replace('yesNo',''); count++;
                    }
                }
                if (count!=1) {
                    if ($('submitButton')) $('submitButton').disabled = false;
                    return -1;
                }
                else return id;
            }
            //загрузка подгрупп в зависимости от группы, обнуление текущей
            function loadSubs() {
                var id=checkYesNoGetIndex();
                $('robsonSub').value='';
                var txt="";
                PregnancyService.getRobsonSub(id,{
                    callback: function(aResult) {
                        if (aResult!=null && aResult!='[]') {
                            var res=JSON.parse(aResult);
                            txt+="<table><tbody>";
                            txt+="<tr><td><label><b>Выберите:</b></label><td><td><select id='sub'";
                            <msh:ifFormTypeIsView formName="preg_robsonClassForm" guid="07462ced-904f-4485-895c-0107f05b5d8d">
                            txt+=" disabled='true' ";
                            </msh:ifFormTypeIsView>
                            txt+=" onchange='changeSub();' style=\"background-color:#fcffa7\">";
                            for (var ind1 = 0; ind1 < res.length; ind1++) {
                                var val = res[ind1];
                                txt+= "<option id='option"+val.id+"'>"+val.name+"</option>";
                            }
                            txt+="</select><td></tr></tbody></table>";
                        }
                        document.getElementById('subRobsonsDiv').innerHTML=txt;
                        <msh:ifFormTypeAreViewOrEdit formName="preg_robsonClassForm" guid="07462ced-904f-4485-895c-0107f05b5d8d">
                        if ($('robsonSub').value) selectItemById($('robsonSub').value);
                        </msh:ifFormTypeAreViewOrEdit>
                        changeSub();
                    }
                });
            }
            //установка значения подкатегории при выборе
            function changeSub() {
                var selects = document.getElementsByTagName('option');
                for (var i = 0; i < selects.length; i++) {
                    if (selects[i].selected && selects[i].id.indexOf('option')!=-1)
                        $('robsonSub').value=selects[i].id.replace('option','');
                }
            }
            //установка значения подкатегории при загрузке
            function selectItemById(elmnt, numId){
                for(var i=0; i < elmnt.options.length; i++)
                {
                    if(elmnt.options[i].id === 'option'+numId) {
                        elmnt.selectedIndex = i;
                        break;
                    }
                }
            }
        </script>
    </tiles:put>
</tiles:insert>