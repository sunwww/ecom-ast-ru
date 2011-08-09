package ru.amokb.asset.ejb.form;

import ru.amokb.asset.ejb.domain.Terminal;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = Terminal.class)
@Comment("Терминал")
@WebTrail(comment = "Терминал", nameProperties= "id", list="entityParentList-asset_terminal.do", view="entityParentView-asset_terminal.do")
@Parent(property="automatedWorkplace", parentForm=AutomatedWorkplaceForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Equipment/Terminal")
public class TerminalForm extends ComputerForm{
}
