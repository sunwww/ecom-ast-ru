function onPreCreate(aForm, aCtx) {
	if (aForm.parity!=null && aForm.parity>0 && (aForm.calendarParity==null || aForm.calendarParity==0)) throw "При указании типа дня необходимо указать тип четности" ; 
	if (aForm.calendarParity!=null && aForm.calendarParity>0 && (aForm.parity==null || aForm.parity==0)) throw "При указании типа четности необходимо указать тип дня" ; 
}

function onPreSave(aForm,aEntity , aCtx) {
	if (aForm.parity!=null && aForm.parity>0 && (aForm.calendarParity==null || aForm.calendarParity==0)) throw "При указании типа дня необходимо указать тип четности" ; 
	if (aForm.calendarParity!=null && aForm.calendarParity>0 && (aForm.parity==null || aForm.parity==0)) throw "При указании типа четности необходимо указать тип дня" ; 
}

