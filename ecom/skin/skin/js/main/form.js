
var theDefaultTimeOut ;
var theDefaultTimeOutCnt=4 ;
var theDefaultFieldName ;
var theDefaultEvt ;
var theDefaultTimeOutCountMsg =10000;  //интервал в 10 сек

/*Преобразуем данные html формы в объект json*/
function getFormDataAsJson(form){
    var unindexed_array = form.serializeArray();
    var indexed_array = {};
    jQuery.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });
    return indexed_array;
}

function mshHideBlockByElement(element) {
/*	var d = element.parentNode.children[1];
	var hided = d.style.display==='none';
	d.style.display =  hided ? 'block' : 'none';
	element.style.backgroundColor= hided ? '#ddd' : '#f65ce9' ;*/
}

function mshSaveTableToExcelById(aId) {
	mshPrintTextToExcelTable(document.getElementById(aId).outerHTML);
}
function mshSaveNextTableToExcel(aButton) {
	var nodes = aButton.parentNode.childNodes;
	var ret=0;
	for (var i=0;i<nodes.length;i++) {
		if(""+nodes[i]=='[object HTMLTableElement]') {
			ret=1;
			var html = nodes[i].outerHTML;
			mshPrintTextToExcelTable(html);
		} 
	}
	if (ret===0) {alert('Не смог найти таблицу :(');}
	
}
function mshPrintTextToExcelTable (html) {
	window.location.href='data:application/vnd.ms-excel,'+'\uFEFF'+encodeURIComponent(html);
}
function adjustMessage(text,aDiv) {
	var MessageObj = document.getElementById(aDiv);
	var isThisMozilla = true ;
	if (isThisMozilla) {var event=theDefaultEvt;}
	var scroll = getScrollXY() ;
	MessageObj.innerHTML = text;
	if (aDiv=="divInstantMessage") {
		MessageObj.style.top = (scroll[1]+50)+'px' ;
		MessageObj.style.left = '100px';
		MessageObj.style.visibility = "visible";
	}
}
function getScrollXY() {
  var scrOfX = 0, scrOfY = 0;
  if( typeof( window.pageYOffset ) == 'number' ) {
    scrOfY = window.pageYOffset;
    scrOfX = window.pageXOffset;
  } else if( document.body && ( document.body.scrollLeft || document.body.scrollTop ) ) {
    scrOfY = document.body.scrollTop;
    scrOfX = document.body.scrollLeft;
  } else if( document.documentElement && ( document.documentElement.scrollLeft || document.documentElement.scrollTop ) ) {
     scrOfY = document.documentElement.scrollTop;
    scrOfX = document.documentElement.scrollLeft;
  }
  return [ scrOfX, scrOfY ];
}
function getDefinition(term,evt,aDiv){
	if (aDiv==null||aDiv=="") aDiv="divInstantMessage" ;
	
	var MessageObj=document.getElementById(aDiv);
	MessageObj.innerHTML = "Загрузка..." ;
	var param = term.split("?") ;
	var query = param[1] ;
        mshaDoRequest(param[0], query, function () {onResponse1(aDiv)});
    //theDefaultEvt = evt ;
	
	
	return false;
}
function goToPageNewWindow(aPage,aId,aTableCell){
	goToPage(aPage,aId,aTableCell+"##NEW_WINDOW##");
}

function goToPage(aPage,aId,aTableCell) {
	//if (aTableCell==null) aTableCell="" ;
    if (aPage.indexOf('javascript:')!=-1) {
    	
    	var func = aPage.split("javascript:")[1] ;
    	if (func.indexOf('void(0)')>-1) {
    		return ;
    	} else {
	    	if (func.indexOf('(')==-1) {
	    		func = func + '(';
	    	} else if (!func.lastIndexOf(',')) {
	    		func = func + "," ;
	    	} 
	    	event(func+"'"+aId+"','"+aTableCell+"')") ;
	    	//alert(func+"'"+aId+"'"+")");
    	}
    } else {
    	var delim = aPage.indexOf('?')==-1 ? '?' : '&' ;
    	var url = aPage+delim+'id='+aId+'&tmp='+Math.random() ;
    	if (aTableCell==null) {
            window.location = url ;
    	} else {
    		if (aTableCell.indexOf('##NEW_WINDOW##')>-1) {
    			url+=aTableCell.replace("##NEW_WINDOW##","");
    			window.open(url);
			} else {
                getDefinition(url+aTableCell+"&short=Short") ;
			}

    	}
    }
}
function onResponse1(aDiv) {
	
    var aResponse = theMshaHttpRequest;
    if (theMshaHttpRequest.readyState == 4) {
        //alert(theMshaHttpRequest.status) ;
        var MessageObj = document.getElementById(aDiv);
        theIsSearching = false;
        if (theMshaHttpRequest.status == 200) {
            
            //MessageObj.innerHTML = ;
            
            adjustMessage(aResponse.responseText,aDiv);
        } else {
        	adjustMessage(aResponse.status + " " + aResponse.statusText+" "+aResponse.responseText,aDiv);
        }
    } else {

    }
}

function hideMessage(){
	var MessageObj=document.getElementById('divInstantMessage');
	MessageObj.style.visibility="hidden";
	MessageObj.innerHTML = "" ;
}
var funcemergencymessage = {
		func: function() {
			clearTimeout(theDefaultTimeOut) ;
			VocService.getUnreadMessages('1',10,false,{
		        callback: function(aName) {
		        	
		        	viewEmergencyUserMessage(aName) ;
		        	
		        }
		    } ) ;
		}
}
function showToastMessage(aMessage,aJson,aAutoClose,aError, aMs) {
    if (aMs==null || typeof aMs==='undefined')
        aMs=10000;
	if (aJson) {
        jQuery.toast(aJson);
	} else {
		jQuery.toast({
			text:aMessage
			,hideAfter:aAutoClose!=null && aAutoClose ? aMs : aAutoClose
			,icon:true===aError ? "error" : "info"

		});
	}
}
function viewEmergencyUserMessage(aJsonId) {
	var fldJson = JSON.parse(aJsonId) ;
	var cnt = fldJson.params.length ;
	if (cnt>0) {
	    for (var ind=0;ind<cnt;ind++) {
	    	var param = fldJson.params[ind] ;
	    	jQuery.toast({
                position: 'bottom-center'
				,heading:"Cрочное сообщение: "+param.messageTitle
				,text:param.messageText+(param.messageUrl?"\n<a href='"+param.messageUrl+"' target='_blank'>Перейти</a>":"")
				,hideAfter: false
                ,bgColor: '#ff0000'
				,icon:"warning"
				,beforeShow: function () {checkEmergencyMessage(param.id);}
				,stack:cnt
			});
	    }
	}
}
function checkEmergencyMessage(aId) {
    VocService.checkEmergencyMessages(aId,'', {
        callback: function(aName) {
            --theDefaultTimeOutCnt ;
            if (theDefaultTimeOutCnt>0) theDefaultTimeOut = setTimeout(funcemergencymessage.func,180000) ;
        }}
    ) ;
}
function hideUserMessage(aId) {
	VocService.hiddenMessage(aId, {
        callback: function(aName) {
        	if ($("userMessagePop"+aId)) $("userMessagePop"+aId).style.display="none";
        }
    } ) ;

}

function checkClaimMessage (aId, aStatus) {
	var creatorComment ='';
	if (+aStatus==0) {
		creatorComment = prompt('Введите примечание - что именно не сделано');		
	}

		VocService.checkClaimMessage(aId, aStatus, creatorComment, {
			callback: function (aResult) {
				if ($('claimMessageContainer'+aId)) { $('claimMessageContainer'+aId).style.display='none'; }
			}
		});

}
function checkAllMessages(aName) {
	var msg = document.getElementsByClassName(aName);
	for (var i=0; i<msg.length;i++){
		var id = msg[i].id.substring(aName.length);
		checkUserMessage(id);

	}
	
}
function checkUserMessage(aId) {
	VocService.checkMessage(aId, {
        callback: function(aName) {
        	if ($("userMessageContainer"+aId)) $("userMessageContainer"+aId).style.display="none";
        },
        errorHandler:function(message) {
        	alert("Ошибка при обработке сообщения!") ;
        },
        warningHandler:function(message) {
        	alert("Ошибка при обработке сообщения!") ;
        }
    } ) ;
}
function getCheckedRadio(aFrm,aFieldName) {
	var radioGrp = aFrm[aFieldName];
	
	if (radioGrp) {
		if (radioGrp.length) {
			for(i=0; i < radioGrp.length; i++){
			  if (radioGrp[i].checked == true){
			    return radioGrp[i].value;
			  }
			}
		} else {
			if (radioGrp.checked == true){
				return radioGrp.value;
			}
		}
	}
	return "" ;
}
function getCheckedCheckBox(aFrm,aFieldName,aRazd) {
	var checkBoxGrp = aFrm.elements[aFieldName];
	var checkValue = ""
		if (checkBoxGrp) {
			if (checkBoxGrp.length) {
				for(i=0; i < checkBoxGrp.length; i++){
					if (checkBoxGrp[i].checked == true){
						checkValue = checkValue+aRazd+checkBoxGrp[i].value;
					}
				}
			} else {
				if (checkBoxGrp.checked == true){
					checkValue = checkValue+aRazd+checkBoxGrp.value;
				}
			}
			
		} 
	if (checkValue!="") {
		checkValue=checkValue.substring(aRazd.length) ;
	}
	return checkValue ;
}
function setCheckedAllRadio(aFrm,aFieldName,aCheck) {
	var radioGrp = aFrm[aFieldName];
	if (radioGrp) {
		if (radioGrp.length) {
			radioGrp[0].checked = aCheck;
		} else {
			radioGrp.checked = aCheck;
		}
	}
}

function setCheckedAllCheckBox(aFrm,aFieldName,aCheck) {
	var checkBoxGrp = aFrm.elements[aFieldName];
	if (checkBoxGrp) {
		if (checkBoxGrp.length) {
			for(i=0; i < checkBoxGrp.length; i++){
				  checkBoxGrp[i].checked = aCheck;
				}
		} else {
			checkBoxGrp.checked = aCheck;
		}
	}
}
function setFocusOnField(aFieldName) {
	theDefaultFieldName = aFieldName ;
	eventutil.addObserveListener(window, 'load', _zetFocusOnField) ;
}

function _zetFocusOnField() {
	try {
	   $(theDefaultFieldName).focus() ;
	} catch (e) {}
	try {
	   $(theDefaultFieldName).select() ;
	} catch (e) {}

}
function getCurrentDate() {
	var dt = new Date() ;
	return format2day(dt.getDate())+"."+format2day(dt.getMonth()+1)+"."+dt.getFullYear() ;
}
//toDate - в дату из строки, dd mm yyyy разделённы символом sym
//например, dd.mm.yyyy sym - это .
function toDate(dateStr,sym) {
    var parts = dateStr.split(sym);
    return new Date(parts[2], parts[1] - 1, parts[0])
}

/**
 * Получить дату ранее или позднее полученной или текущей
 * строка с датой или текущая
 * sym символ-разделитель в строке
 * razn разница, которую вычеть/прибавить
 * @return Boolean true - если есть
 */
function getDateAfterOrBeforeCurrent(dateStr,sym,razn) {
    var tomorrow = typeof dateStr=='undefined'? new Date() : toDate(dateStr,sym);
    if (typeof razn=='undefined') razn=1;
    tomorrow.setDate(tomorrow.getDate() + razn);
    var dd = tomorrow.getDate();
    var mm = tomorrow.getMonth() + 1;
    var yyyy = tomorrow.getFullYear();
    if (dd < 10) {
        dd = '0' + dd;
    }
    if (mm < 10) {
        mm = '0' + mm;
    }
    return dd + '.' + mm + '.' + yyyy;
}
function format2day(aCnt) {
	if (aCnt>9) {
		return aCnt ;
	} else {
		return "0"+aCnt ;
	}
}
function checkTime(i) {
    if (i<10) i="0" + i;
    return i;
}
Date.prototype.yyyymmdd = function() {
    var mm = this.getMonth() + 1; // getMonth() is zero-based
    var dd = this.getDate();

    return [this.getFullYear() + "-" ,
        (mm>9 ? '' : '0')+ mm + "-",
        (dd>9 ? '' : '0') + dd
    ].join('');
};
function capture() {
    var img = {
        image_pro: null
    };
    html2canvas(document.body).then(function(canvas) { cp(canvas); });
    var cp = function(canvas) {
        img = canvas.toDataURL("image/png");
        var now = new Date();
        var fileName=now.yyyymmdd()+"_"+checkTime(now.getHours())+"."+checkTime(now.getMinutes())+"."+checkTime(now.getSeconds())
            +checkTime(now.getMilliseconds())+"_" + document.getElementById("current_username_li").innerHTML+".png";
        showToastMessage('Вы будете перенаправлены на создание заявки, к которой будет прикреплён скриншот окна. Ожидайте.',null,false);
        ClaimService.postRequestWithErrorScrean(img,fileName,{
            callback: function (res) {
                if (res!=null) {
                    if (document.getElementsByClassName("errorMessage")[0] != null)
                        window.location = "entityPrepareCreate-mis_claim.do?img=" + res + "&description=в скриншоте" + "&hist=" + document.referrer;
                    else
                        window.location = "entityPrepareCreate-mis_claim.do?img=" + res + "&description=в скриншоте" + "&hist=" + document.referrer;
                }
            }
        });
    }
}
function capture500() {
    showException();
    var img = {
        image_pro: null
    };
    html2canvas(document.body).then(function(canvas) { cp(canvas); });
    var cp = function(canvas) {
        img = canvas.toDataURL("image/png");
        var now = new Date();
        var fileName=now.yyyymmdd()+"_"+checkTime(now.getHours())+"."+checkTime(now.getMinutes())+"."+checkTime(now.getSeconds())
            +checkTime(now.getMilliseconds())+"_" + document.getElementById("current_username_li").innerHTML+".png";
        var text=document.getElementsByClassName('errorMessage')[0].innerHTML.replace(/<a.*a>/,''); //document.getElementsByClassName("errorMessage")[0].innerText не работает в 17й мозилле
        showToastMessage('Вы будете перенаправлены на создание заявки, к которой будет прикреплён скриншот ошибки. Ожидайте.',null,false);
        ClaimService.postRequestWithErrorScrean(img,fileName,{
            callback: function (res) {
                if (res!=null) {
                    if (document.getElementsByClassName("errorMessage")[0] != null)
                        window.location = "entityPrepareCreate-mis_claim.do?img=" + res + "&description=" + text + "&hist=" + document.referrer;
                    else
                        window.location = "entityPrepareCreate-mis_claim.do?img=" + res + "&description=в скриншоте" + "&hist=" + document.referrer;
                }
            }
        });
    }
}
//проверка корректности даты
function checkDate(chDate) {
    var dateParts = chDate.split(".");
    if (dateParts.length<2) return false;
    var date = new Date(dateParts[2], (dateParts[1] - 1), dateParts[0]);

    var dd=date.getDate();
    var mm=date.getMonth() + 1;
    var yyyy=date.getFullYear();

    var bday2=(dd>9 ? '' : '0') +  dd+ "." + (mm>9 ? '' : '0')  + mm+ "." + yyyy;
    return (chDate==bday2);
}
//сравнение дат-строк
//return 0 if =
//1 if dateStr1>dateStr2
//-1 if dateStr1<dateStr2
function compareDates(dateStr1,dateStr2) {
    var dateParts1 = dateStr1.split(".");
    var dateParts2 = dateStr2.split(".");
    var date1 = new Date(dateParts1[2], (dateParts1[1] - 1), dateParts1[0]);
    var date2 = new Date(dateParts2[2], (dateParts2[1] - 1), dateParts2[0]);
    var res=0;
    if (date1>date2) res=1;
    if (date1<date2) res=-1;
    return res;
}
//Снять выделение с остальных столбцов (по которым не сортируем)
function uncheckTh(th,num) {
    var table = th.parentElement.parentElement.parentElement;
    if (table.rows.length>0) {
        //если есть чекбоксы для печати списка, пропустить первую пустую строку с кнопкой
        var startRow=table.rows.length>1 && table.rows[1].getElementsByTagName('th').length>0
            && table.rows[1].getElementsByTagName('th')[0].innerHTML.indexOf("onclick=\"theTableArrow.onCheckBoxClickAll(this)\"")!=-1?
            1 : 0;
        var cols = table.rows[startRow].getElementsByTagName('th');
        for (var i = 0; i < cols.length; i++) {
            if (i != num)
                cols[i].className = cols[i].className.replace('thSorted', '');
        }
    }
}
//Сортировка таблицы
function sortMshTable(th,num) {
    var direct = th.getAttribute('name');
    var table = th.parentElement.parentElement.parentElement;
    var rows, switching, i, x, y, shouldSwitch;
    switching = true;
    rows = table.rows;
    VocService.getSoftConfigByValue("smallSortMshTableLength",100, {
        callback: function (smallSortLength) {
            if (rows.length < smallSortLength || confirm('Таблица содержит много строк! Сортировка может занять много времени. Вы уверены?')) {
                var j= rows[rows.length - 1].getElementsByTagName("TD").length>1 ? 1 : 0; //первый столбец мб пустым
                var last = rows[rows.length - 1].getElementsByTagName("TD")[j].className.indexOf('sumTd') != -1 ? 2 : 1;
                while (switching) {
                    switching = false;
                    for (i = 1; i < (rows.length - last); i++) {
                        shouldSwitch = false;
                        x = rows[i].getElementsByTagName("TD")[num];
                        y = rows[i + 1].getElementsByTagName("TD")[num];
                        if (x != null && y != null && typeof x !== 'undefined' && typeof y !== 'undefined') {
                            if (x.innerHTML==' ' && y.innerHTML!=' ' && direct==0 || x.innerHTML!=' ' && y.innerHTML==' ' && direct!=0) {
                                shouldSwitch = true;
                                break;
                            }
                            if (isNaN(x.innerHTML)) {
                                if (direct == 0) {
                                    if (checkDate(x.innerHTML) && checkDate(y.innerHTML)) { //если даты
                                        if (compareDates(x.innerHTML, y.innerHTML) == -1) {
                                            shouldSwitch = true;
                                            break;
                                        }
                                    }
                                    else if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                                        shouldSwitch = true;
                                        break;
                                    }
                                }
                                else {
                                    if (checkDate(x.innerHTML) && checkDate(y.innerHTML)) { //если даты
                                        if (compareDates(x.innerHTML, y.innerHTML) == 1) {
                                            shouldSwitch = true;
                                            break;
                                        }
                                    }
                                    else if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                                        shouldSwitch = true;
                                        break;
                                    }
                                }
                            }
                            else if (!isNaN(x.innerHTML)) {
                                if (direct == 0) {
                                    if (+x.innerHTML < (+y.innerHTML)) {
                                        shouldSwitch = true;
                                        break;
                                    }
                                }
                                else {
                                    if (+x.innerHTML > (+y.innerHTML)) {
                                        shouldSwitch = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if (shouldSwitch) {
                        if (th.className.indexOf('thSorted')==-1) {
                            th.className += ' thSorted ';
                            uncheckTh(th,num);
                        }
                        rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                        switching = true;
                    }
                }
                direct = direct == 0 ? 1 : 0;
                th.setAttribute('name', direct);
                if (th.getElementsByTagName('i').length > 0)
                    th.getElementsByTagName('i')[0].className = direct == 0 ? 'arrow arrowUp' : 'arrow arrowDown';
            }
        }
    });
}

//мерцание строки с непрочитанными сообщениями
var colorArrays=new Array("#CD5C5C", "#7CFC00", "#00FFFF", "#7B68EE", "#00008B", "#2F4F4F	");
var numColor=0;
// эта функция будет менять цвет текста
function blinkUnreadMsgs() {
    if (+jQuery('#unreadMsg').text() > 0) {
        document.getElementById("clorRow").style.backgroundColor = colorArrays[numColor++];
        if (numColor > colorArrays.length) numColor = 0;
        setTimeout("blinkUnreadMsgs()", 300);
    }
    else
        document.getElementById("clorRow").style="";
}

//получить и проставить кол-во непрочитанных сообщений
function getCountUnreadMessages() {
    VocService.getCountUnreadMessages('', {
        callback: function(aCnt) {
            jQuery('#unreadMsg').text(aCnt);
            if (aCnt>0) {
                blinkUnreadMsgs();
                jQuery('clorRow').click(function() {
                    showUnreadMessages();
                });
            }
        }
        , errorHandler:function(message) {
            jQuery('#unreadMsg').text('-1');
            jQuery('clorRow').click(function() {
                return false;
            });
        }
    } ) ;
    setTimeout("getCountUnreadMessages()",theDefaultTimeOutCountMsg);}

    //поиск или создание человека по данным, полученным с эл. полиса ОМС
    function findOrCreatePatientEveryWhere(jsonData) {
        VocService.createOrGetPatient(jsonData, {
            callback: function (ret) {
                ret = JSON.parse(ret);
                if (ret.status==='error') {
                    return;
                }
                window.document.location = ret.link;
            }
        });
    }

/**
 * Вывести браслеты в таблице #151
 * @param table Таблица
 * @param tdResNum Номер столбца для вывода результата
 * @param tdJsonNum Номер столбца с json
 */
function setBr(table, tdResNum, tdJsonNum) {
    if (typeof table !== 'undefined') {
        for (var i = 1; i < table.rows.length; i++) {
            var row = table.rows[i];
            var tdRes=row.cells[tdResNum];
            var td=row.cells[tdJsonNum];
            var json = jQuery(td).text();
            var str = "";
            if (+json!=0) {
                var aResult = JSON.parse(json);
                str = '<table><tr>';
                var size = 25;
                for (var j = 0; j < aResult.length; j++) {
                    var brace = aResult[j];
                    var msg = brace.info ? brace.info : brace.vsipnamejust;
                    var style = 'style="width:' + size + 'px;height: ' + size + 'px;outline: 1px solid gray; border:2px;';
                    style += brace.picture ? '">' : ' background: ' + brace.colorcode + ';">';
                    if (brace.picture)
                        style += '<img src="/skin/images/bracelet/' + brace.picture + '" title="' + brace.vsipnamejust +
                            '" height="' + size + 'px" width="' + size + 'px">';
                    str += '<td><div title="' + msg + '" ' + style + '</div></td>';
                }
                str += "</tr></table>";
            }
            tdRes.innerHTML = str == '' ? '-' : str
        }
    }
}

/**
 * Получить таблицу по className строки (название sql-запроса) #151
 * @param sqlListName
 * @return Таблица
 */
function getTableToSetBracelets(sqlListName) {
    var tables = document.getElementsByTagName('table');
    for (var i=0; i<tables.length; i++) {
        var table = tables[i];
        if (table.rows.length>1 && table.rows[1].className.indexOf(sqlListName)!=-1)
            return table;
    }
    return null;
}