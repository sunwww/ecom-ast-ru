function onPreCreate(aForm, aCtx) {
	checkedData(aForm.monthOrder,aForm.weekDay,aForm.monthDay);
}

function onPreSave(aForm,aEntity , aCtx) {
	checkedData(aForm.monthOrder,aForm.weekDay,aForm.monthDay);
}

function checkedData(aMonthOrder,aWeekDay,aMonthDay) {
	if (+aMonthDay==0 && aMonthOrder==0 && aWeekDay==0) throw "Необходимо заполнить данные либо число месяца, либо параметры дня недели" ;

	if (+aMonthDay>0 && (aMonthOrder>0 || aWeekDay>0)) throw "Необходимо заполнить данные либо число месяца, либо параметры дня недели" ;
	
	
	if (aMonthOrder>0 && aWeekDay==0) throw "Необходимо заполнить день недели" ;
	if (aWeekDay>0 && aMonthOrder==0) throw "Необходимо заполнить порядок недели в месяце" ;
	
}