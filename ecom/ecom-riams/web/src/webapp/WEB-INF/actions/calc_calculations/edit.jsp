<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom"%>



<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	
	<tiles:put name="title" type="string">
<ecom:titleTrail mainMenu="calc_calculator" beginForm="calc_calculationsForm"/>
</tiles:put>

	<tiles:put name="style" type="string">
	 <style>
           .border {
               display: inline-block;
               padding: 3px;
               border: 1px solid rgba(255,0,0,0);
               transition: 3s;
               border-color: rgb(59,92,105) ;
               position: relative;
			   padding-top: 0.4em;
			   min-height: 0em;
            }
            
            
   .colortext {
    background-color: red; /* Цвет фона */
    color: #ffe; /* Цвет текста */
    width: 97%;
   }
   
        </style>
	</tiles:put>
	
	<tiles:put name="body" type="string">
		<msh:form action="/entitySaveGoView-calc_calculations.do"
			defaultField="name">
			
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="calculator" />

 
 
 <div class="information"> </div>
<div id="copyMe">
			<msh:panel>
<msh:row>
	<msh:autoComplete  property="type" label="Тип" vocName="vocTypeCalc"
		size="100" fieldColSpan="3" />
</msh:row>
<msh:row>
	<msh:textField property="value" label="Значение" horizontalFill="true" fieldColSpan="3" />
</msh:row>
<msh:row>
<msh:textField property="comment" label="Комментарий" horizontalFill="true" fieldColSpan="3" />
</msh:row>
<msh:row>
<msh:textField property="orderus" label="Порядок" viewOnlyField="true" fieldColSpan="3" />
</msh:row>
<msh:row>
	<msh:textField property="note" label="Примечание" horizontalFill="true" fieldColSpan="3" />
</msh:row>
</msh:panel>
</div>	

<input id="btn" type="button" value="Отмена" onclick="goBack()"/>
		<input id="btn" type="button" value="Перейти к следующему элементу" onclick="copyElements()"/>
	 	<input type="button" value="Предпросмотр" onclick="preview()"/>
		<input id="bt" type="button" value="Сохранить все" onclick="Create()"/>  
		</msh:form>
		
		

	</tiles:put>

 <tiles:put name="javascript" type="string">
 <script type="text/javascript" src="./dwr/interface/CalculateService.js"></script>
 <script type="text/javascript">
 
 

		var global = 1;
		var information = document.querySelector('.information');
		var calculatorId = $('calculator');
		var ord = document.querySelector('#orderusReadOnly.viewOnly');
		ord.value = global;

		function preview() {
			var i = 1;
			information.innerHTML = '<br> <font> Финальная формула будет выглядеть: <b>';
			for (i; i < global; i++) {
				var SpanElement = document.querySelector('#content')
						.querySelector('#id' + i);
				var Value = SpanElement.querySelector('#value.horizontalFill');

				information.innerHTML += '<b>' + Value.value + '</b>';
			}
			information.innerHTML += '</font></b><br>';
		}
		function isEmpty(field) {
			if (field.value != "") {
				return false;
			}
			return true;
		}
		function copyElements() {

			var value = document.querySelector('#value.horizontalFill');
			var comment = document.querySelector('#comment.horizontalFill');

			if (!isEmpty(value) && !isEmpty(comment)) {

				information.innerHTML = '';
				var clonedNode = document.getElementById("copyMe").cloneNode(
						true); // где копируем

				var content = document.querySelector('#content');
				var span = document.createElement('span');
				span.className = "border";
				span.id = "id" + global;
				content.appendChild(span);
				span.appendChild(clonedNode);
				var butn = document.createElement('div');
				butn.innerHTML = '<button type="button"  class="btn0" id="id'
						+ global
						+ '" onclick="delthis(this.id)">Удалить</button>';
				butn.innerHTML += '<button type="button" class="btn1" id="id'
						+ global + '" onclick="DoDown(this.id)">↑</button>'; //up
				butn.innerHTML += '<button type="button" class="btn2" id="id'
						+ global + '" onclick="DoUp(this.id)">↓</button>'; //down
				span.appendChild(butn);

				global++;

				var clear = document.querySelector('#value.horizontalFill');
				clear.value = "";
				clear = document.querySelector('#comment.horizontalFill');
				clear.value = "";
                clear = document.querySelector('#note.horizontalFill');
                clear.value = "";
				ord.value = global;
				preview();
			} else {
				information.innerHTML = 'надо<font color="red"><b>Заполните все поля!</b></font>';
			}
		}

		function DoDown(id) {
			var intIdArr = id.split('id');
			var intId = parseInt(intIdArr[1]);
			var UpSpanElement = document.querySelector('#content')
					.querySelector('#' + id);

			var UpValue = UpSpanElement.querySelector('#value.horizontalFill');
			var UpComment = UpSpanElement
					.querySelector('#comment.horizontalFill');
			var UpOrderus = UpSpanElement
					.querySelector('#orderusReadOnly.viewOnly');
			var UpType = UpSpanElement.querySelector('#type');
			var UpTypeName = UpSpanElement
					.querySelector('#typeName.autocomplete.maxHorizontalSize');

			var DownId = intId - 1;
			if (DownId > 0) {

				var DownSpanElement = document.querySelector('#content')
						.querySelector('#id' + DownId);

				var DownValue = DownSpanElement
						.querySelector('#value.horizontalFill');
				var DownComment = DownSpanElement
						.querySelector('#comment.horizontalFill');
				var DownOrderus = DownSpanElement
						.querySelector('#orderusReadOnly.viewOnly');
				var DownType = DownSpanElement.querySelector('#type');
				var DownTypeName = DownSpanElement
						.querySelector('#typeName.autocomplete.maxHorizontalSize');

				var t = DownValue.value;
				DownValue.value = UpValue.value;
				UpValue.value = t;

				t = DownComment.value;
				DownComment.value = UpComment.value;
				UpComment.value = t;

				t = DownType.value;
				DownType.value = UpType.value;
				UpType.value = t;

				t = DownTypeName.value;
				DownTypeName.value = UpTypeName.value;
				UpTypeName.value = t;
			}
		}
		function DoUp(id) {

			var intIdArr = id.split('id');
			var intId = parseInt(intIdArr[1]);
			var UpSpanElement = document.querySelector('#content')
					.querySelector('#' + id);

			var UpValue = UpSpanElement.querySelector('#value.horizontalFill');
			var UpComment = UpSpanElement
					.querySelector('#comment.horizontalFill');
			var UpOrderus = UpSpanElement
					.querySelector('#orderusReadOnly.viewOnly');
			var UpType = UpSpanElement.querySelector('#type');
			var UpTypeName = UpSpanElement
					.querySelector('#typeName.autocomplete.maxHorizontalSize');

			var DownId = intId + 1;
			if (DownId < global) {

				var DownSpanElement = document.querySelector('#content')
						.querySelector('#id' + DownId);

				var DownValue = DownSpanElement
						.querySelector('#value.horizontalFill');
				var DownComment = DownSpanElement
						.querySelector('#comment.horizontalFill');
				var DownOrderus = DownSpanElement
						.querySelector('#orderusReadOnly.viewOnly');
				var DownType = DownSpanElement.querySelector('#type');
				var DownTypeName = DownSpanElement
						.querySelector('#typeName.autocomplete.maxHorizontalSize');

				var t = DownValue.value;
				DownValue.value = UpValue.value;
				UpValue.value = t;

				t = DownComment.value;
				DownComment.value = UpComment.value;
				UpComment.value = t;

				t = DownType.value;
				DownType.value = UpType.value;
				UpType.value = t;

				t = DownTypeName.value;
				DownTypeName.value = UpTypeName.value;
				UpTypeName.value = t;
			}
		}
		function delthis(id) {
			var intIdArr = id.split('id');
			var intId = parseInt(intIdArr[1]);
			var del = document.querySelector('#' + id);
			del.remove();

			for ( var i = intId; i < global; i++) {
				var id2 = (i + 1);
				if (id2 < global) {
					var span = document.querySelector('#id' + id2);
					var orderus = span.querySelector('#copyMe').querySelector(
							'#orderusReadOnly.viewOnly');
					var btn = document.querySelector('#id' + id2 + '.btn0');
					orderus.value = i;
					btn.id = "id" + i;
					btn = document.querySelector('#id' + id2 + '.btn1');
					btn.id = "id" + i;
					btn = document.querySelector('#id' + id2 + '.btn2');
					btn.id = "id" + i;
					span.id = "id" + i;
				}
			}
			global--;
			var ord = document.querySelector('#orderusReadOnly.viewOnly');
			ord.value = global;
		}

		function goBack() // возврат на родительскую страницу.
		{
			location.href = "entityParentView-calc_calculator.do?id="
					+ calculatorId.value;
		}

		function Create() {

			var i = 1;
			var calculate = new Array(); //struct-like!
			for (i; i < global; i++) {
				var span = document.querySelector('#id' + i + '.border')
						.querySelector('#copyMe');

				/*var copyMe = span.querySelector('#copyMe');
				var horizontalFilltype = copyMe.querySelector('#type');
				var horizontalFillvalue = copyMe.querySelector('#value.horizontalFill');
				var horizontalFillcomment = copyMe.querySelector('#comment.horizontalFill');
				var horizontalFillorderus = copyMe.querySelector('#orderusReadOnly.viewOnly');
				calculate[i] = {
				 type: horizontalFilltype.value,
				    value: horizontalFillvalue.value,
				    comment:horizontalFillcomment.value,
				    orderus: horizontalFillorderus.value
				};*/

				calculate[i] = {
					type : span.querySelector('#type').value,
					value : span.querySelector('#value.horizontalFill').value,
					comment : span.querySelector('#comment.horizontalFill').value,
					orderus : span.querySelector('#orderusReadOnly.viewOnly').value,
                    note : span.querySelector('#note.horizontalFill').value
				};
			}

			var Json = "{\"calculatorId\":\"" + calculatorId.value
					+ "\",\"params\":" + JSON.stringify(calculate) + "}";
			CalculateService.SetCalculatorSettings(Json, {
				callback : function(aResult) {
				}
			});
			goBack();
		}
	</script>
</tiles:put>
</tiles:insert>