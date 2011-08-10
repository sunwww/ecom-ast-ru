package ru.nuzmsh.web.tags;

import javax.servlet.jsp.tagext.SimpleTag;

import ru.nuzmsh.forms.validator.BaseValidatorForm;

/**
 * Если форма только для создания
 * @jsp.tag  name="ifFormTypeIsNotView"
 *          display-name="Если форма только не для просмотра"
 *          body-content="scriptless"
 *          description="Если форма только не для просмотра"
 */
public class IfFormTypeIsNotViewTag extends IfFormTypeIsViewTag implements SimpleTag {
    boolean checkForm(BaseValidatorForm aForm) {
        return !aForm.isViewOnly() ;
    }
}
