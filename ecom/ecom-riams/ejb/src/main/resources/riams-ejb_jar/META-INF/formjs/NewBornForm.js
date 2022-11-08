function onCreate(aForm, aEntity, aContext) {
    // Создание персоны
    var patient = new Packages.ru.ecom.mis.ejb.domain.patient.Patient();
    var mother = aEntity.childBirth.medCase.patient;

    patient.lastname = mother.lastname;
    patient.firstname = "Х";
    patient.middlename = aEntity.sex != null ? (aEntity.sex.omcCode == "1" ? "У" : "Х") : "Х";
    patient.birthday = aEntity.birthDate;
    patient.sex = aEntity.sex;
    aContext.manager.persist(patient);
    var kinsman = new Packages.ru.ecom.mis.ejb.domain.patient.Kinsman();
    kinsman.person = patient;
    kinsman.kinsman = mother;
    var role = aContext.manager.createQuery("from VocKinsmanRole where omccode = '1'"
    )
        .getResultList();

    kinsman.kinsmanRole = role.size() > 0 ? role.get(0) : null;
    aContext.manager.persist(kinsman);
    aEntity.patient = patient;
}

function onPreDelete(aEntityId, aCtx) {
    var entity = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.birth.NewBorn, new java.lang.Long(aEntityId));
    var pat = entity.patient;
    entity.patient = null;
}

function getSoftConfigValueOrDefault(aCtx, key, defaultValue) {
    var valueList = aCtx.manager
        .createNativeQuery("select keyvalue from softconfig  where key='" + key + "'")
        .getResultList();
    return valueList.isEmpty() ? defaultValue : +valueList.get(0);
}

function heightWeightValidate(height, weight, aCtx) {
    //Валидация роста и веса
    if (height != 0 && weight != 0) {
        var maxHeight = getSoftConfigValueOrDefault(aCtx, "maxHeightNewBorn", 70);
        var maxWeight = getSoftConfigValueOrDefault(aCtx, "maxWeightNewBorn", 7000);
        if (height > maxHeight)
            throw "Введено значение роста, которое превышает максимальное (" + maxHeight + ")!";
        if (weight > maxWeight)
            throw "Введено значение веса, которое превышает максимальное (" + maxWeight + ")!";
    }
}

function onSave(aForm, aEntity, aCtx) {
    heightWeightValidate(+aForm.birthHeight, +aForm.birthWeight, aCtx);
}