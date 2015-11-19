
/**
 * Перед созданием
 */
function onPreCreate(aForm, aCtx) {
	check(aForm,aCtx);
}

/**
 * Перед сохранением
 */
function onPreSave(aForm, aEntity, aCtx) {
	check(aForm,aCtx);
}

function check(aForm,aCtx) {
	if (+aForm.nosologyGroup<1 &&
			+aForm.medServiceGroup<1 &&
			 +aForm.guaranteeGroup<1) {
		throw "Обязательно надо заполнить хотя бы одно условие!!!" ;
	}
} 
