<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom"%>
<%@ taglib prefix="title" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">


    <tiles:put name="side" type="string">
        <msh:sideMenu>
            <msh:sideLink
                          roles="/Policy/Mis/Directory/Department/DeleteRecord" key="ALT+D"
                          action="/javascript:deleteRecord('.do')"
                          name="Удалить" />
        </msh:sideMenu>
    </tiles:put>


    <tiles:put name="style" type="string">
        <style>
            .number {
                display: inline-block;
                padding: 3px;
                border: 1px solid;
                transition: 3s;
                border-color: rgb(59,92,105) ;
                position: relative;
                min-height: 0em;
                margin-top: 10px;
                margin-left: 20%;
            }
            .left{
                margin-top: 10px;
                margin-left: 20%;
            }
        </style>
    </tiles:put>
    <tiles:put name="body" type="string">
        <% String Id = request.getParameter("id");
            request.setAttribute("Id", Id);%>


        <msh:ifInRole roles="/Policy/Mis/Directory/Department/EditRecord">
        <msh:panel>
            <div class="content">
                <msh:row>
                    <msh:row>
                        <msh:autoComplete vocName="vocBuildingShort" property="building" label="Корпус" fieldColSpan="4" size="60" />
                    </msh:row>
                    <msh:row>
                        <msh:autoComplete vocName="vocBuildingLevelShort" property="buildingLevel" label="Этаж" fieldColSpan="4" size="60" />
                    </msh:row>
                    <msh:row>
                        <msh:autoComplete vocName="vocDepartmet" property="department" label="Отделение" fieldColSpan="4" size="60" />
                    </msh:row>
                    <msh:row>
                        <msh:autoComplete vocName="vocPersonShort" property="person" label="Персона" fieldColSpan="4" size="60" />
                    </msh:row>
                    <msh:row>
                        <msh:textField property="comment" label="Наименование" horizontalFill="true" />
                    </msh:row>
                    <msh:row>
                        <msh:textField property="internalNumber" label="Внутренний номер" horizontalFill="true"/>
                    </msh:row>
                </msh:row>
            </div>
        </msh:panel>
        <div class="numbers">
            <div class="left">
                <td><input id="addnumber" type="submit" value="Добавить еще номер" /></td>
                <td><input id="save" type="submit" value="Сохранить" /></td>
                <td><input id="cancel" type="submit" value="Отмена" onclick="goBack()"/></td>
            </div>
        </div>
        </msh:ifInRole>
    </tiles:put>


    <tiles:put name="javascript" type="string">
        <script type="text/javascript" src="./dwr/interface/DirectoryService.js"></script>
        <script language="javascript" type="text/javascript">

            var EntryId=${Id};
            var DepartmentId=0;
            var Json="";

            function setAutocomplete(id, idvalue, idnamevalue) {
                document.getElementById(id).value = idvalue;
                document.getElementById(id+"Name").value = idnamevalue;
            }

            DirectoryService.getEntryEdit(${Id}, {
                callback : function(aResult) {
                    var obj = jQuery.parseJSON(aResult);
                    //alert(obj.building);
                    setAutocomplete('building',obj.buildingId,obj.building);
                    setAutocomplete('buildingLevel',obj.buildingLevelId,obj.buildingLevel);
                    setAutocomplete('department',obj.depId,obj.department);
                    DepartmentId = obj.departmentId;

                    if(obj.personId!=null && obj.personId!="" && obj.personId!="null" ){
                    setAutocomplete('person',obj.personId,obj.person);
                    }
                    document.getElementById('comment').value = obj.comment;
                    document.getElementById('internalNumber').value = obj.insidenumber;
                    var count = obj.numbers.length;
                    console.log("D:"+DepartmentId+" E:"+EntryId);
                    var indx=0;
                    for (var i = 1; i < (count + 1); i++) {
                        var t = i - 1;
                        var telnumber = obj.numbers[t].telnumber;
                        var typeId = obj.numbers[t].typeId;
                        var typeName = obj.numbers[t].typeName;
                        var telephonenumberId = obj.numbers[t].telephonenumberId;
                        //alert(telephonenumberId);
                        autocompliteCreateString(j);
                        setAutocomplete('typeNumber'+(j-1),typeId,typeName);
                        document.getElementById('comment'+(j-1)).value = telnumber;
                        //document.getElementById('telephonenumberId'+(j-1)).value = telephonenumberId;
                        if(indx>0) Json+=',';
                        Json+='{\"TelNumberIdDel\":\"'+telephonenumberId+'\"}';
                        indx++
                       }
                }
            });

            var jq = jQuery.noConflict();

            var j=1;
            function autocompliteCreateString(i) {
                var str = '<div class="number" id="number_' + i + '"> <input onclick="deleteBlock(jq(this).parent().attr(\'id\'))" id="delnumber_' + i + '" type="submit" class="del" value="Удалить"/> <br>';
                str += '<td title="Тип номера (typeNumber)" class="label">' +
                    '<label id="typeNumberLabel' + i + '" for="typeNumberName' + i + '">Тип номера:</label></td>' +
                    '<td colspan="4" class="typeNumber"><div>' +
                    '<input size="1" name="typeNumber" value="" id="typeNumber' + i + '" type="hidden">' +
                    '<input title="vocTypeNumber" name="typeNumber' + i + 'Name" value="" id="typeNumber' + i + 'Name" size="60" class="autocomplete ' +
                    'maxHorizontalSize" autocomplete="off" type="text">' +
                    '<div id="typeNumber' + i + 'Div"><span></span></div></div></td>';

                str+='<tr>';
                str +='<td colspan="1" title="Внутренний&nbsp;номер (internalNumber)" class="label"><label id="internalNumberLabel" for="internalNumber">Номер:</label></td>';
                str +='<td colspan="1" class="comment">' +
                    '<input title="НаименованиеNoneField" class=" horizontalFill" id="comment'+i+'" name="comment" size="10" value="" autocomplete="off" type="text">' +
                    '</td>' +
                    '<input id="telephonenumberId'+i+'" type="text" value="" hidden /> </tr>';

                str += '</div>';
                jq(str).appendTo('.numbers');
                var t = new msh_autocomplete.Autocomplete();
                t.setUrl('simpleVocAutocomplete/vocTypeNumber');
                t.setIdFieldId('typeNumber' + i);
                t.setNameFieldId('typeNumber' + i + 'Name');
                t.setDivId('typeNumber' + i + 'Div');
                t.build();
                j++;
            }

            jq('#addnumber').click(function () {
                autocompliteCreateString(j);
            });

            function deleteBlock(id) {
                document.querySelector('#'+id).remove();
                j--;
            }

            jq('#save').click(function () {
                var JSON="{";

                var buildingId = document.querySelector('#building').value;
                var buildingLevelId = document.querySelector('#buildingLevel').value;
                var misLpuId = document.querySelector('#department').value;
                var personId = document.querySelector('#person').value;
                var comment = document.querySelector('#comment').value;
                var internalNumber = document.querySelector('#internalNumber').value;

                JSON+='\"departmentId\":\"'+DepartmentId+'\",';
                if(buildingId!=""){JSON+='\"buildingId\":\"'+buildingId+'\",';}
                if(buildingLevelId!=""){JSON+='\"buildingLevelId\":\"'+buildingLevelId+'\",';}
                if(misLpuId!=""){JSON+='\"misLpuId\":\"'+misLpuId+'\",';}
                JSON+='\"entryId\":\"'+EntryId+'\",';
                if(personId!=""){JSON+='\"personId\":\"'+personId+'\",';}
                if(comment!=""){ JSON+='\"comment\":\"'+comment+'\",';}
                if(internalNumber!=""){ JSON+='\"internalNumber\":\"'+internalNumber+'\",';}

                JSON+='\"delId\":['+Json+'],';
                JSON+='\"numbers\":[';
                tick=0;
                jq.each(jq('.number'), function() {
                    var theId= getId(jq(this).attr('id'));
                    //console.log( getId(jq(this).attr('id')));
                    var str="";
                    var TypeNumber = document.querySelector('#typeNumber'+theId).value;
                    var Number = document.querySelector('#comment'+theId).value;
                    if(TypeNumber!='' ||Number!=''){
                        if(tick>0)JSON+=',';
                        JSON+='{\"typeNumber\": \"'+TypeNumber+'\",';
                        JSON+='\"Number\": \"'+Number+'\"}';
                        tick++;
                    }
                });
                JSON+=']}';
                console.log(JSON);
                DirectoryService.updateEntryEdit(JSON, {
                    callback : function(aResult) {
                        goBack();
                    }
                });
            });

            function getId(Id) {
                var intIdArr = Id.split('_');
                var intId = parseInt(intIdArr[1]);
                return intId;
            }

            <msh:ifNotInRole roles="/Policy/Mis/Directory/Department/EditRecord">
            goBack();
            </msh:ifNotInRole>
            function goBack() // возврат на родительскую страницу.
            {
                location.href = "js-riams-phoneTest.do";
            }

            function deleteRecord() {
                DirectoryService.deleteEnryRecord(${Id}, {
                    callback : function(aResult) {
                        goBack();
                    }
                });
            }
        </script>
    </tiles:put>
</tiles:insert>
