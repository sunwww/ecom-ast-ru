package ru.nuzmsh.web.tags;

import lombok.Getter;
import lombok.Setter;
import ru.nuzmsh.web.util.IdeTagHelper;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

/**
 * @jsp.tag name="tableTextfield"
 *          display-name="TableTextfield"
 *          body-content="empty"
 *          description="Table Textfield JSP tag."
 */
@Getter
@Setter
public class TableTextfieldTag extends AbstractGuidSupportTag {
    /**
     * CSS Класс
     * @jsp.attribute   description="CSS Класс"
     *                  required="false"
     *                  rtexprvalue="true"
     */
    private String cssClass = null ;

    /** Название колонки
     *
     * @jsp.attribute   description="Название кнопки"
     *                  required="false"
     *                  rtexprvalue="true"
     *
     * */
    private String textfieldName;

    /** Название колонки
     *
     * @jsp.attribute   description="Название сокращенное кнопки"
     *                  required="false"
     *                  rtexprvalue="true"
     *
     * */
    private String textfieldShortName;

    /**
     * Название свойства
     * @jsp.attribute   description="Название свойства"
     *                  required="true"
     *                  rtexprvalue="true"
     *
     * */
    private String property;

    @Override
    public int doStartTag() throws JspException {
        Tag parent = getParent() ;
        if(parent instanceof TableTag) {
            TableTag tableTag = (TableTag) parent ;
            tableTag.add(this,getRole());
        } else {
            IdeTagHelper.getInstance().showException(this, new Exception("TableTextfield должен быть внутри тэга Table"), pageContext);
        }
        return EVAL_BODY_INCLUDE;
    }

    /** Дополнительные параметры
     * @jsp.attribute   description="Дополнительные параметры"
     *                  required="false"
     *                  rtexprvalue="true"
     */
    private String addParam;
    /** Дополнительные параметры
     * @jsp.attribute   description="Функция кнопки"
     *                  required="false"
     *                  rtexprvalue="true"
     */
    private String textfieldFunction;

    /** Роль *
     * @jsp.attribute   description="Роль"
     *                  required="false"
     *                  rtexprvalue="true"
     */
    private String role;

    /**
     * Не выводить шапку таблицы
     * @jsp.attribute   description = "Не выводить, если нет значения"
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
    private boolean hideIfEmpty = false;
}
