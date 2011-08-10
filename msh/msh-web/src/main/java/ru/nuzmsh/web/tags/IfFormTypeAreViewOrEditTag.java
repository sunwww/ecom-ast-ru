package ru.nuzmsh.web.tags;

import javax.servlet.jsp.tagext.SimpleTag;

import ru.nuzmsh.forms.validator.BaseValidatorForm;

/**
 * Если форма для просмотра и редактирования
 * @jsp.tag  name="ifFormTypeAreViewOrEdit"
 *          display-name="Если форма для просмотра и редактирования"
 *          body-content="scriptless"
 *          description="Если форма для просмотра и редактирования"
 */
public class IfFormTypeAreViewOrEditTag extends IfFormTypeIsViewTag implements SimpleTag {
    boolean checkForm(BaseValidatorForm aForm) {
        return aForm.isViewOnly() || aForm.getSaveType()==BaseValidatorForm.TYPE_SAVE ;
    }
}
