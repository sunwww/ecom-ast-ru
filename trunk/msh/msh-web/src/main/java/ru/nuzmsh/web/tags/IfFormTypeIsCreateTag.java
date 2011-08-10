package ru.nuzmsh.web.tags;

import javax.servlet.jsp.tagext.SimpleTag;

import ru.nuzmsh.forms.validator.BaseValidatorForm;

/**
 * Если форма только для создания
 * @jsp.tag  name="ifFormTypeIsCreate"
 *          display-name="Если форма только для создания"
 *          body-content="scriptless"
 *          description="Если форма только для создания"
 */
public class IfFormTypeIsCreateTag extends IfFormTypeIsViewTag implements SimpleTag {
    boolean checkForm(BaseValidatorForm aForm) {
        return aForm.isTypeCreate() ;
    }
}
