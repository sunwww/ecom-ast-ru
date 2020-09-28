package ru.nuzmsh.web.tags;

import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.tags.decorator.ITableDecorator;
import ru.nuzmsh.web.tags.helper.JavaScriptContext;
import ru.nuzmsh.web.tags.helper.RolesHelper;
import ru.nuzmsh.web.util.DemoModeUtil;
import ru.nuzmsh.web.util.IdeTagHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.*;

/**
 * @jsp.tag
 *            name = "table"
 *    display-name = "Table"
 *    body-content = "JSP"
 *     description = "Table JSP tag."
 */
public class TableTag extends AbstractGuidSupportTag {

    /**
     * Не выводить шапку таблицы
     * @jsp.attribute   description = "Не выводить шапку таблицы"
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
    public boolean getHideTitle() {
        return theHideTitle;
    }

    public void setHideTitle(boolean aHideTitle) {
        theHideTitle = aHideTitle;
    }

    /** Не выводить шапку таблицы */
    private boolean theHideTitle = false;

    /** Отображение по каждой ячейке
     * @jsp.attribute   description = "Не выводить шапку таблицы"
     *                     required = "false"
     *                  rtexprvalue = "true"
     * */
    public boolean getCellFunction() {return theCellFunction;}
    public void setCellFunction(boolean aCellFunction) {theCellFunction = aCellFunction;}

    /** Отображение по каждой ячейке */
    private boolean theCellFunction;

    /**
     * Запретить поддержку клавиш
     * @jsp.attribute   description = "Запретить поддержку клавиш"
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
    public boolean getDisableKeySupport() { return theDisableKeySupport ; }
    public void setDisableKeySupport(boolean aDisableKeySupport) { theDisableKeySupport = aDisableKeySupport ; }

    /**
     * Тип выделения
     * @jsp.attribute   description = "Тип выделения"
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
    public String getSelection() { return theSelection ; }
    public void setSelection(String aSelection) { theSelection = aSelection ; }

    /**
     * Url просмотра записи
     * @jsp.attribute   description = "Url просмотра записи"
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
    public String getViewUrl() {return theViewUrl;}
    public void setViewUrl(String aViewUrl) {theViewUrl = aViewUrl;}

    /** Url печати
     * @jsp.attribute   description = "Url печати записи"
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
    public String getPrintUrl() {return thePrintUrl;}
    public void setPrintUrl(String aPrintUrl) {thePrintUrl = aPrintUrl;}

    /**
     * Url удаления записи
     * @jsp.attribute   description = "Url удаления записи"
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
    public String getDeleteUrl() {return theDeleteUrl;}
    public void setDeleteUrl(String aDeleteUrl) {theDeleteUrl = aDeleteUrl;}

    /**
     * Поле с идентификатором
     * @jsp.attribute   description="Поле с идентификатором"
     *                  required="false"
     *                  rtexprvalue="true"
     */
    public String getIdField() { return theIdField ; }
    public void setIdField(String aIdField) { theIdField = aIdField ; }

    /**
     * Декоратор для класса стиля Css (ITableDecorator). Через request.getAttribute()
     * @jsp.attribute description="Декоратор для класса стиля Css (ITableDecorator)"
     *                  required="false"
     *                  rtexprvalue="true"
     */
    public String getDecorator() {
        return theDecorator;
    }

    public void setDecorator(String aCssClassDecorator) {
        theDecorator = aCssClassDecorator;
    }

    /** Декоратор для класса стиля Css (ITableDecorator) */
    private String theDecorator;

    /**
     * Стиль строк
     * @jsp.attribute description="Стиль строк"
     *                  required="false"
     *                  rtexprvalue="true"
     * */
    public String getStyleRow() {
        return theStyleRow;
    }

    public void setStyleRow(String aStyleRow) {
        theStyleRow = aStyleRow;
    }

    /** Стиль строк */
    private String theStyleRow;
    /**
     * Название атррибута в request или в session
     *
     * @jsp.attribute description="Название атррибута в request или в session"
     * required="true"
     * rtexprvalue="true"
     */
    public String getName() {
        return theName;
    }

    public void setName(String aName) {
        theName = aName;
    }

    /**
     * Action для навигации
     * @jsp.attribute description="Action для навигации"
     *                  required="false"
     *                  rtexprvalue="true"
     */
    public String getNavigationAction() {
        return theNavigationAction;
    }

    public void setNavigationAction(String aNavigationAction) {
        theNavigationAction = aNavigationAction;
    }

    /** Action для навигации */
    private String theNavigationAction;

    /** Action
     *  Если значение javascript, выполнять функцию go()
     * @jsp.attribute description="Action"
     *                  required="true"
     *                  rtexprvalue="true"
     *
     * */
    public String getAction() {
        return theAction;
    }

    public void setAction(String aAction) {
        theAction = aAction;
    }

    /** Action */
    private String theAction;

    @SuppressWarnings("unchecked")
    public void add(Object aTag,String aRole) {
        boolean isView =true ;
        try {
            if (aRole!=null &&!aRole.equals("")) isView = RolesHelper.checkRoles(aRole, (HttpServletRequest)pageContext.getRequest()) ;
        }catch (Exception e) {
            isView=false ;
        }
        if (isView) {
            if (aTag instanceof TableColumnTag) {
                TableColumnTag tag = (TableColumnTag) aTag ;
                theCells.add(

                        new Column(tag.getProperty(), tag.getColumnName()
                                , tag.isIdentificator(), tag.getCssClass(), (HttpServletRequest)pageContext.getRequest()
                                , tag.getGuid(),tag.getIsCalcAmount(),tag.getAddParam(),tag.getWidth(),theEscapeSymbols, tag.getHidden())

                );
            } else if (aTag instanceof TableButtonTag) {
                TableButtonTag tag = (TableButtonTag) aTag ;
                theCells.add(
                        new Button(tag.getProperty(), tag.getButtonShortName(), tag.getButtonName()
                                , tag.getButtonFunction()
                                , tag.getCssClass(),tag.getAddParam(), tag.getHideIfEmpty(),tag.getGuid(), (HttpServletRequest)pageContext.getRequest()
                        )

                );
            } else if (aTag instanceof TableTextfieldTag){
                TableTextfieldTag tag = (TableTextfieldTag) aTag;
                theCells.add(
                        new Textfield(tag.getProperty(), tag.getTextfieldShortName(), tag.getTextfieldName()
                                , tag.getTextfieldFunction()
                                , tag.getCssClass(),tag.getAddParam(), tag.getHideIfEmpty(), (HttpServletRequest)pageContext.getRequest())
                );
            }
        }
    }


    /**
     * Сообщение об отсутствии данных
     * @jsp.attribute description="Сообщение об отсутствии данных"
     *                  required="false"
     *                  rtexprvalue="true"
     * */
    public String getNoDataMessage() {
        return theNoDataMessage;
    }

    public void setNoDataMessage(String aNoDataMessage) {
        theNoDataMessage = aNoDataMessage;
    }

    /**
     * Url редактировать
     * @jsp.attribute   description = "Url редактирования записи"
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
    public String getEditUrl() {return theEditUrl;}
    public void setEditUrl(String aEditUrl) {theEditUrl = aEditUrl;}

    /** Url редактировать */
    private String theEditUrl;

    public int doStartTag() throws JspException {
        printIdeStart("Table");
        try {
            if(theDecorator!=null && theIdField!=null) {
                throw new JspException("Нужно установить только одно поле из двух: idField или decorator") ;
            }

            createGoFunctionName(theAction,theOpenNewWindow);



            Collection col = getCollection();

            boolean isEmpty;
            if (col == null) {
                throw new JspException("Нет Collection в request.getAttribute(" + theName + ")");
            } else {
                if (col.size() == 0) {
                    JspWriter out = pageContext.getOut();
                    try {
                        out.print("<p>");
                        out.print(theNoDataMessage != null ? theNoDataMessage : "Нет данных");
                        out.println("</p>");
                        isEmpty = true;
                    } catch (IOException e) {
                        throw new JspException(e);
                    }
                } else {
                    isEmpty = false;
                }
            }

            theCells.clear();

            if (!isEmpty) {
                try {
                    JspWriter out = pageContext.getOut();
                    out.println("<form>") ;
                    if (thePrintToExcelButton!=null&&!thePrintToExcelButton.equals("")) { //Если есть кнопка "Сохранить в excel", будем сохранять
                        out.println("<input type='button' onclick='mshSaveNextTableToExcel(this)' value='"+thePrintToExcelButton+"'>");
                    }
                    out.println("<table border='1' class='tabview sel tableArrow'"+(getGuid()!=null?"id='"+getGuid()+"'":"")+">");
                } catch (Exception e) {
                    throw new JspException(e);
                }
            }
            theIsEmpty = isEmpty ;
        } catch (Exception e) {
            showException(e);
        }
        return EVAL_BODY_INCLUDE;
    }

    /**
     * Нужно ли печатать таблицу
     */
    protected boolean isEmpty() {
        return theIsEmpty;
    }

    @SuppressWarnings("unchecked")
    public Collection getCollection() {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        Object attr = request.getAttribute(theName);
        if(attr instanceof Object[]) {
            Object[] arr = (Object[]) attr ;
            ArrayList list = new ArrayList(arr.length) ;
            Collections.addAll(list, arr);
            return list ;
        } else {
            return (Collection) attr ;
        }
    }
    private void printCellView(JspWriter aOut,String aValue, String aGoFunctionName) throws IOException {
        String styleClass = "";

        aOut.print("<td width='14px' onclick=\"");
        aOut.print(aGoFunctionName) ;
        aOut.print("\"");
        aOut.print(" class='") ;
        aOut.print(styleClass);
        aOut.print(' ');
        aOut.print("'>");

        aOut.print(aValue);
        aOut.println("</td>");
    }

    private String theFunctionGoName = null;

    private synchronized void createGoFunctionName(String aAction, Boolean aOpenNewWindow) {
        theFunctionGoName = "goToPage"+(Boolean.TRUE.equals(aOpenNewWindow) ? "NewWindow" : "")+"('"+aAction+"',";
    }

    private synchronized void createDeleteFunctionName(String aDeleteAction) {
        theFunctionDeleteName="if (confirm('Удалить?')) goToPage('" + aDeleteAction+"',";
    }
    private synchronized void createEditFunctionName(String aAction) {
        theFunctionEditName="goToPage('" + aAction+"','";
    }
    private synchronized void createPrintFunctionName(String aAction) {
        theFunctionPrintName="goToPage('" + aAction+"','";
    }
    private synchronized void createViewFunctionName(String aAction) {
        if (aAction.indexOf("?")==-1) {
            theFunctionViewName="getDefinition('"+aAction+"?id=";
        } else if(aAction.endsWith("&")) {
            theFunctionViewName="getDefinition('"+aAction+"id=";
        } else {
            theFunctionViewName="getDefinition('"+aAction+"&id=";
        }
    }
    private static String getGoFunctionName(String theFunctionGoName, Object aParam,String aAddParam) {
        return theFunctionGoName +
                "('" + aParam + "'" +
                (aAddParam != null && !aAddParam.equals("") ? "," + aAddParam : "") +
                ")";
    }

    private String getDeleteFunctionName(String aId) {
        return theFunctionDeleteName + "'" + aId + "');";
    }
    private String getGoFunctionName(String aId) {
        return theFunctionGoName + "'" + aId + "');";
    }
    private String getGoFunctionCellName(String aId,String aCellName) {
        return theFunctionGoName + "'" + aId + "','" + aCellName + "');";
    }

    private String getViewFunctionName(String aId) {
        if (theFunctionViewName==null) {
            createViewFunctionName(theViewUrl) ;
        }
        return theFunctionViewName + aId + "',event); ";
    }
    private String getEditFunctionName(String aId) {
        return theFunctionEditName + aId + "'); ";
    }
    private String getPrintFunctionName(String aId) {
        return theFunctionPrintName + aId + "'); ";
    }

    public int doEndTag() throws JspException {
        if (!isEmpty()) {

            try {
                JspWriter out = pageContext.getOut();

                ITableDecorator decorator = theDecorator!=null ? getDecoratorObject() : null;

                Collection col = getCollection();
                if (col == null) {
                    throw new JspException("Нет Collection в request.getAttribute(\"" + theName + "\")");
                } else {
                    if (col.size() == 0) {
                        return EVAL_PAGE;
                    }
                    // шапка таблицы
                    if(!theHideTitle) {
                        int i=0;
                        out.println("<tr>");
                        if(theSelection!=null) {
                            //out.println("<th></th>") ;

                            out.println("<th>") ;
                            String typeId = theName ;
                            out.println("<input id='"+typeId+"' name='"+typeId+"' type='checkbox' onclick='theTableArrow.onCheckBoxClickAll(this)' title='Выделить все'/>") ;
                            out.println("<input id='"+typeId+"' name='"+typeId+"' type='checkbox' onclick='theTableArrow.onCheckBoxClickInvert(this)' title='Инвертировать все'/>") ;
                            out.println("</th>") ;
                            i++;
                        }
                        if (theViewUrl!=null) {
                            out.println("<th  width='14px'>") ;
                            out.println("&nbsp;") ;
                            out.println("</th>") ;
                            createViewFunctionName(theViewUrl);
                            i++;
                        }
                        if (thePrintUrl!=null) {
                            out.println("<th  width='14px'>") ;
                            out.println("&nbsp;") ;
                            out.println("</th>") ;
                            createPrintFunctionName(thePrintUrl);
                            i++;
                        }
                        if (theEditUrl!=null) {
                            out.println("<th  width='14px'>") ;
                            out.println("&nbsp;") ;
                            out.println("</th>") ;
                            createEditFunctionName(theEditUrl);
                            i++;
                        }
                        if (theDeleteUrl!=null) {
                            createDeleteFunctionName(theDeleteUrl);
                            out.println("<th  width='14px'>") ;
                            out.println("&nbsp;") ;
                            out.println("</th>") ;
                            i++;
                        }
                        for (Iterator iterator = theCells.iterator(); iterator.hasNext();) {
                            Object obj = iterator.next();
                            if (obj instanceof Button) {
                                Button button = (Button) obj;
                                button.printHeader(out);
                                i++;
                            } else if (obj instanceof Column) {
                                Column column = (Column) obj;
                                column.printHeader(out,i,theSortable && col.size()>1);
                                i++;
                            } else if (obj instanceof Textfield){
                                Textfield textfield = (Textfield) obj;
                                textfield.printHeader(out);
                                i++;
                            }
                        }
                        out.println("</tr>");
                    }

                    String firstId = null;
                    String lastId = null;
                    Object rowSum = null ;
                    boolean isFirstRow = true ;
                    boolean isSumColumn = false ;
                    for (Iterator colIter = col.iterator(); colIter.hasNext();) {
                        Object row = colIter.next();
                        if (isFirstRow) {
                            rowSum = row ;
                            //isFirstRow = false ;
                        }

                        //Tablable tablable = (Tablable) row ;
                        String currentId = decorator != null ? decorator.getId(row)
                                : getIdByIdField(row) ;
                        if (firstId == null) {
                            firstId = currentId;
                        }
                        lastId = currentId;

                        out.print("<tr onclick='' ");
                        if (theStyleRow!=null && !theStyleRow.equals("")) {out.print(" style=\"");out.print(getValueByProperty(row,theStyleRow));out.print("\"");} ;
                        out.print(" class='") ;
                        out.print(theName) ;
                        out.print(' ') ;
                        out.print(currentId) ;
                        out.print(' ') ;
                        if (decorator != null) {
                            out.print(decorator.getRowCssClass(row));
                        }
                        out.print("'>");

                        if(theSelection!=null) {
                            out.println("<td>") ;
                            String typeId = theName+"_"+currentId ;
                            out.println("<input id='"+typeId+"' name='"+typeId+"' type='checkbox' onclick='theTableArrow.onCheckBoxClick(this)'/>") ;
                            out.println("</td>") ;
                        }



                        if (theViewUrl!=null) {
                            String goFunctionViewName=getViewFunctionName(currentId) ;
                            //out.println() ;
                            printCellView(out,"<img src='/skin/images/main/view1.png' alt='Просмотр записи' title='Просмотр записи' height='14' width='14'/>", goFunctionViewName) ;
                        }
                        if (thePrintUrl!=null) {
                            String goFunctionPrintName=getPrintFunctionName(currentId) ;
                            printCellView(out,"<img src='/skin/images/main/print.png' alt='Печать записи' title='Печать записи' height='14' width='14'/>", goFunctionPrintName) ;
                        }
                        if (theEditUrl!=null) {
                            String goFunctionEditName=getEditFunctionName(currentId) ;
                            printCellView(out,"<img src='/skin/images/main/edit.png' alt='Редактирование записи' title='Редактирование записи' height='14' width='14'/>", goFunctionEditName) ;
                        }
                        if (theDeleteUrl!=null) {
                            //out.println() ;
                            String goFunctionDeleteName=getDeleteFunctionName(currentId) ;
                            printCellView(out,"<img src='/skin/images/main/delete.png' alt='Удалить запись' title='Удалить запись' height='14' width='14'/>", goFunctionDeleteName) ;

                        }

                        String goFunctionMainName = getGoFunctionName(currentId) ;

                        for (Iterator iterator = theCells.iterator(); iterator.hasNext();) {
                            Object obj = iterator.next();
                            if (obj instanceof Button) {
                                Button button = (Button) obj;
                                button.printFunction(out, row);
                            }
                            else if (obj instanceof Textfield){
                                Textfield textfield = (Textfield) obj;
                                textfield.printFunction(out,row,currentId);
                            }
                            else if (obj instanceof Column) {
                                Column column = (Column) obj;
                                Object valueC ;
                                if (theCellFunction) {
                                    valueC = column.printCell(out, row, getGoFunctionCellName(currentId, column.theAddParam), currentId,column.theColumnName,column.theWidth,column.theHidden);
                                } else {
                                    valueC = column.printCell(out, row, goFunctionMainName, currentId,null,column.theWidth,column.theHidden);
                                }
                                if (!isFirstRow) {
                                    if (valueC!=null) {
                                        rowSum = column.amountCell(out, rowSum, valueC) ;
                                        isSumColumn = true ;
                                    }
                                }
                            }
                        }


                        //System.out.println("--->");
                        out.println("</tr>");
                        if (isFirstRow) {
                            //rowSum = row ;
                            isFirstRow = false ;
                        }
                    }
                    if (isSumColumn) {
                        out.println("<tr>");
                        if(theSelection!=null) {
                            out.println("<td>&nbsp;</td>") ;
                        }
                        if (theViewUrl!=null) {
                            out.println("<td>&nbsp;</td>") ;
                        }
                        if (thePrintUrl!=null) {
                            out.println("<td>&nbsp;</td>") ;
                        }
                        if (theEditUrl!=null) {
                            out.println("<td>&nbsp;</td>") ;
                        }
                        if (theDeleteUrl!=null) {
                            out.println("<td>&nbsp;</td>") ;
                        }
                        for (Iterator iterator = theCells.iterator(); iterator.hasNext();) {
                            Object obj = iterator.next();
                            if (obj instanceof Button) {
                                out.println("<td>&nbsp;</td>") ;
                            }
                            else if (obj instanceof Textfield) {
                                out.println("<td>&nbsp;</td>") ;
                            }
                            else if (obj instanceof Column) {
                                Column column = (Column) obj;
                                column.printSumCell(out, rowSum, "", "");
                            }
                        }
                        out.println("</tr>");
                    }

                    if (theNavigationAction != null) {
                        out.println("<tr><td colspan='" + (theCells.size())
                                + "' class='navigationBottom'>");
                        printNavigation(out, firstId, lastId);
                        out.println("</td></tr>");
                    }
                    out.println("</table>");
                    out.println("</form>") ;
                }

                if(!theDisableKeySupport && !IdeTagHelper.getInstance().isInIdeMode(pageContext)) {
                    String tableName = "theTableArrow" ; //"the"+theName.substring(0,1).toUpperCase()+theName.substring(1)+"TableArrow" ;
                    JavaScriptContext js = JavaScriptContext.getContext(pageContext, this);
                    js.println(" var "+tableName+" ;") ;
                    js.println(" window.onload = function () {");
                    js.println("  "+tableName+" = new tablearrow.TableArrow('tab1') ;");
                    js.println(" }");
                }
            }
            catch (Exception e) {
                showException(e);
            } finally {
                theCells.clear();
            }
        }
        printIdeEnd();
        return EVAL_PAGE;
    }
    private Object getValueByProperty(Object aObject,String aProperty) {
        Object value;
        try {
            value = PropertyUtil.getPropertyValue(aObject, aProperty) ;
        } catch (Exception e) {
            value = e + "";
        }
        return value ;
    }
    private String getIdByIdField(Object aRow) throws JspException {
        try {
            return String.valueOf(PropertyUtil.getPropertyValue(aRow, theIdField));
        } catch (Exception e) {

            throw new JspException("Ошибка получения свойства "+theIdField+ " у объекта "+
                    aRow+" класса "
                    +(aRow!=null?aRow.getClass().getName() : "NULL" ), e) ;
        }
    }

    /**
     * Не показывать переход на первый элемент
     * @jsp.attribute   description = "Не показывать переход на первый элемент"
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
    public boolean getDisabledGoFirst() { return theDisabledGoFirst ; }
    public void setDisabledGoFirst(boolean aDisabledGoFirst) { theDisabledGoFirst = aDisabledGoFirst ; }

    /**
     * Не показывать переход на последний элемент
     * @jsp.attribute   description = "Не показывать переход на последний элемент"
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
    public boolean getDisabledGoLast() { return theDisabledGoLast ; }
    public void setDisabledGoLast(boolean aDisabledGoLast) { theDisabledGoLast = aDisabledGoLast ; }

    /** Не показывать переход на последний элемент */
    private boolean theDisabledGoLast = false ;
    /** Не показывать переход на первый элемент */
    private boolean theDisabledGoFirst = false ;

    private final void printNavigation(JspWriter aOut, String aFirstId, String aLastId) throws IOException {

//        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String first = "<a href='%1$snext=%2$d'>|&lt;&nbsp;Первый</a>";
        String last = "<a href='%1$snext=-%2$d'>Последний&nbsp;&gt;|</a>";
        String next = "<a href='%1$sid=%2$s&amp;idna=%2$s&amp;next=%3$d'>Вперед&nbsp;&gt;</a>";
        String previous = "<a href='%1$sid=%2$s&amp;idna=%2$s&amp;next=-%3$d'>&lt;&nbsp;Назад</a>";
        StringBuffer sb = new StringBuffer(theNavigationAction) ;
        if(theNavigationAction.indexOf(".do")>=0) {
            if(theNavigationAction.indexOf('?')>=0) {
                sb.append("&amp;") ;
            } else {
                sb.append("?") ;
            }
        } else {
            sb.append(".do?") ;
        }
        theNavigationAction=sb.toString() ;

        int countNext = 100;

        if(!theDisabledGoFirst) {
            aOut.print(String.format(first, theNavigationAction, countNext));
            aOut.print("&nbsp;&nbsp;");
        }
        aOut.print(String.format(previous, theNavigationAction, aFirstId, countNext));
        aOut.print("&nbsp;&nbsp;");
        aOut.print(String.format(next, theNavigationAction, aLastId, countNext));
        if(!theDisabledGoLast) {
            aOut.print("&nbsp;&nbsp;");
            aOut.print(String.format(last, theNavigationAction, countNext));
        }
    }

    private final ITableDecorator getDecoratorObject() {
        if (theDecorator == null) {
            return null;
        } else {
            HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

            ITableDecorator decorator = (ITableDecorator) request.getAttribute(theDecorator);
            if(decorator==null) {
                try {
                    Class clazz = Class.forName(theDecorator) ;
                    decorator = (ITableDecorator) clazz.newInstance() ;
                } catch (Exception e) {
                    throw new IllegalStateException("В request нет аттрибута "+theDecorator+". Ошибка при попытке из класса сделать объект: "+e.getMessage(),e) ;
                }
            }
            return decorator;
        }
    }

    static final class Button {
        public Button(String aProperty,String aButtonShortName, String aButtonName
                , String aButtonFunction, String aCssClass, String aAddParam,boolean aHideIfEmpty,String aGuid,HttpServletRequest aRequest) {
            theProperty = aProperty;
            theButtonName = aButtonName;
            theCssClass = aCssClass;
            theServleRequest = aRequest ;
            if (aAddParam==null) aAddParam="" ;
            theAddParam =aAddParam ;
            theButtonFunction = aButtonFunction ;
            theButtonShortName = aButtonShortName ;
            theHideIfEmpty = aHideIfEmpty ;
            theGuid = aGuid ;
        }




        private void printHeader(JspWriter aOut) throws IOException {
            if (theCssClass != null) {
                aOut.print("<th");
                aOut.print(" class='");
                aOut.print(theCssClass);
                aOut.print("'>");
            } else {
                aOut.print("<th>");
            }
            //IdeTagHelper.getInstance().printMarker(, aJspContext)
            aOut.print("<div id='"+theGuid+"' class='idetag tagnameCol'></div>");
            //aOut.print(theButtonName);
            aOut.println("</th>");
        }


        private Object printFunction(JspWriter aOut, Object aObject) throws IOException {
            String styleClass = "";

            Object value;
            //Object valueSum = null ;
            try {
                // value = PropertyUtils.getProperty(aObject, theProperty);
                value = PropertyUtil.getPropertyValue(aObject, theProperty) ;
//                System.out.println("value.getClass() = " + value.getClass());
                if (value instanceof Time) {
                    value = DateFormat.formatToTime((Time) value);

                } else  if (value instanceof Date) {
                    value = DateFormat.formatToDate((Date) value);

                } else if (value instanceof Boolean) {
                    value =  (Boolean) value ?  "1" :  "0" ;
                }

            } catch (Exception e) {
                value = e + "";
            }
            if (theCssClass != null) {
                styleClass += " " + theCssClass;
            }
            aOut.print("<td ");

            if (theButtonName!=null) {
                aOut.print("title=\"");
                aOut.print(theButtonName) ;
                aOut.print("\" ");
            }
            aOut.print(" class='") ;
            aOut.print(styleClass);
            //aOut.print(' ');
            aOut.print(" _"+theProperty);
            aOut.print("'>");
            if ((theHideIfEmpty && value!=null)||!theHideIfEmpty) {
                aOut.print("<input type='button' onclick=\"");
                aOut.print(getGoFunctionName(theButtonFunction,value != null ? value : "",theAddParam)) ;
                aOut.print("\" value=\""+theButtonShortName+"\"") ;
                if (theButtonName!=null) {
                    aOut.print(" title=\"");
                    aOut.print(theButtonName) ;
                    aOut.print("\" ");
                }
                aOut.println(">");
            }
            aOut.print("</td>");

            return "" ;
        }


        private final String theProperty;
        private final String theButtonShortName;
        private final String theButtonName;
        private final String theButtonFunction;
        private final boolean theHideIfEmpty;

        private final String theCssClass;
        private final HttpServletRequest theServleRequest ;
        private final String theAddParam ;
        private final String theGuid ;
    }

    static final class Textfield {

        public Textfield(String aProperty,String aTextfieldShortName, String aTextfieldName
                , String aTextfieldFunction, String aCssClass, String aAddParam,boolean aHideIfEmpty, HttpServletRequest aRequest) {
            theProperty = aProperty;
            theTextfieldName = aTextfieldName;
            theCssClass = aCssClass;
            theServleRequest = aRequest ;
            if (aAddParam==null) aAddParam="" ;
            theAddParam =aAddParam ;
            theTextfieldFunction = aTextfieldFunction ;
            theTextfieldShortName = aTextfieldShortName ;
            theHideIfEmpty = aHideIfEmpty ;
        }


        private void printHeader(JspWriter aOut) throws IOException {
            if (theCssClass != null) {
                aOut.print("<th");
                aOut.print(" class='");
                aOut.print(theCssClass);
                aOut.print("'>");
            } else {
                aOut.print("<th>");
            }
            aOut.println("</th>");
        }


        private Object printFunction(JspWriter aOut, Object aObject,String aGoFunctionName) throws IOException {
            String styleClass = "";

            Object value;
            //Object valueSum = null ;
            try {
                value = PropertyUtil.getPropertyValue(aObject, theProperty) ;
                if (value instanceof Time) {
                    value = DateFormat.formatToTime((Time) value);

                } else  if (value instanceof Date) {
                    value = DateFormat.formatToDate((Date) value);

                } else if (value instanceof Boolean) {
                    value =  (Boolean) value ?  "1" :  "0" ;
                }

            } catch (Exception e) {
                value = e + "";
            }
            if (theCssClass != null) {
                styleClass += " " + theCssClass;
            }
            aOut.print("<td ");

            if (theTextfieldName!=null) {
                aOut.print("title=\"");
                aOut.print(theTextfieldName) ;
                aOut.print("\" ");
            }
            aOut.print(" class='") ;
            aOut.print(styleClass);
            //aOut.print(' ');
            aOut.print(" _"+theProperty);
            aOut.print("'>");
            if ((theHideIfEmpty && value!=null)||!theHideIfEmpty) {
                aOut.print("<input type='text' onclick=\"");
                aOut.print(aGoFunctionName) ;
                aOut.print(getGoFunctionName(theTextfieldFunction,value != null ? value : "",theAddParam)) ;
                aOut.print("\" value=\""+theTextfieldShortName+"\"") ;
                if (theTextfieldName!=null) {
                    aOut.print(" title=\"");
                    aOut.print(theTextfieldName) ;
                    aOut.print("\" ");
                }

                //aOut.print("'");
                aOut.print("id=\"text_");
                aOut.print(aGoFunctionName);
                aOut.print("\"");
                aOut.println(">");
            }
            aOut.print("</td>");

            return "" ;
        }


        private final String theProperty;
        private final String theTextfieldShortName;
        private final String theTextfieldName;
        private final String theTextfieldFunction;
        private final boolean theHideIfEmpty;

        private final String theCssClass;
        private final HttpServletRequest theServleRequest ;
        private final String theAddParam;
    }

    static final class Column {
        public Column(String aProperty, String aColumnname, boolean aIdColumn, String aCssClass, HttpServletRequest aRequest, String aGuid, boolean aIsCalcAmount,String aAddParam, String aWidth,boolean aEscapeSymbols, boolean aHidden) {
            theProperty = aProperty;
            theColumnName = aColumnname;
            theIdColumn = aIdColumn;
            theCssClass = aCssClass;
            theServleRequest = aRequest ;
            theGuid = aGuid ;
            theIsCalcAmount = aIsCalcAmount ;
            if (aAddParam==null) aAddParam="" ;
            theAddParam =aAddParam ;
            theWidth=aWidth;
            theEscapeSymbols=aEscapeSymbols;
            theHidden = aHidden;
        }

        @SuppressWarnings("unused")
        private boolean isIdColumn() {
            return theIdColumn;
        }

        private void printHeader(JspWriter aOut, int i, boolean theSortInner) throws IOException {
            aOut.print("<th");
            if (theHidden)
                aOut.print(" hidden");
            if (theCssClass != null) {
                aOut.print(" class='");
                aOut.print(theCssClass);
                aOut.print("' ");
            }
            if (theSortInner) aOut.print(" onclick='sortMshTable(this,"+i+")'  name='0'");
            if (theWidth!=null && !theWidth.equals(""))  aOut.print(" width=\""+theWidth+"%\"");
            aOut.print(">");

            //IdeTagHelper.getInstance().printMarker(, aJspContext)
            aOut.print("<div id='"+theGuid+"' class='idetag tagnameCol'></div>");
            aOut.print(theColumnName);
            if (theSortInner) aOut.print("<i class='arrow arrowUp'></i>");
            aOut.println("</th>");
        }

        @SuppressWarnings("unused")
        private void printCellView(JspWriter aOut,String aValue, String aGoFunctionName) throws IOException {
            String styleClass = " viewButton ";

            aOut.print("<td onclick=\"");
            aOut.print(aGoFunctionName) ;
            //aOut.print("('") ;
//            aOut.print(URLEncoder.encode(aId, "utf-8")) ;
            //aOut.print(aId) ;
            //aOut.print("')");
            aOut.print("\"");
            if (theWidth!=null && !theWidth.equals(""))
                aOut.print(" width=\""+theWidth+"%\"");
            aOut.print(" class='") ;
            aOut.print(styleClass);
            aOut.print(' ');
            aOut.print("'>");
            aOut.print(aValue);
            aOut.println("</td>");
        }

        private Object printCell(JspWriter aOut, Object aObject, String aGoFunctionName, String aId,String aTitle,String aWidth, Boolean aHidden) throws IOException {
            String styleClass = "";

            Object value;
            Object valueSum = null ;
            try {
                // value = PropertyUtils.getProperty(aObject, theProperty);
                value = PropertyUtil.getPropertyValue(aObject, theProperty) ;
//                System.out.println("value.getClass() = " + value.getClass());
                if (value instanceof Time) {
                    value = DateFormat.formatToTime((Time) value);
                    styleClass = "time";
                } else  if (value instanceof Date) {
                    value = DateFormat.formatToDate((Date) value);
                    styleClass = "date";
                } else if (value instanceof Number) {
                    styleClass = "number";
                } else if (value instanceof Boolean) {
                    Boolean booleanValue = (Boolean) value ;
                    if(booleanValue) {
                        value = "Да" ;
                        styleClass = "boolean";
                    } else {
                        value = "Нет" ;
                        styleClass = "boolean booleanNoValue";
                    }
                } else if(value instanceof String) {
                    if (theEscapeSymbols) {
                        value = value.toString().replace("<br/>", "\r\n");
                        value = value.toString().replace("<br>", "\r\n");
                        value = value.toString().replace("<br />", "\r\n");
                        value = value.toString().replace("<", "&lt;");
                        value = value.toString().replace(">", "&gt;");
                        value = value.toString().replace("&lt;div id=\"circle\"&gt;&lt;/div&gt;", "<div id=\"circle\"></div>");
                    }
                    if(DemoModeUtil.isInDemoMode(theServleRequest)) {
                        value = DemoModeUtil.secureValue(value);
                    }
                }

            } catch (Exception e) {
                value = e + "";
            }
            if (theCssClass != null) {
                styleClass += " " + theCssClass;
            }
            aOut.print("<td ");
            if (aHidden) aOut.print("hidden ");
            if (aWidth!=null && !aWidth.equals(""))
                aOut.print(" width=\""+aWidth+"%\"");
            if (aTitle!=null) {
                aOut.print("title=\"");
                aOut.print(aTitle) ;
                aOut.print("\" ");
            }
            aOut.print("onclick=\"");
            aOut.print(aGoFunctionName) ;
            //aOut.print("('") ;
//            aOut.print(URLEncoder.encode(aId, "utf-8")) ;
            //aOut.print(aId) ;
            //aOut.print("')");
            aOut.print("\"");
            aOut.print(" class='") ;
            aOut.print(styleClass);
            //aOut.print(' ');
            aOut.print(" _"+theProperty);
            aOut.print("'>");
            aOut.print(value != null ? value : "&nbsp;");
            aOut.println("</td>");
            if (theIsCalcAmount) {
                valueSum = value ;
            }
            return valueSum ;
        }
        private void printSumCell(JspWriter aOut, Object aObject, String aGoFunctionName, String aId) throws IOException {
            String styleClass = "";
            Object value;
            try {
                // value = PropertyUtils.getProperty(aObject, theProperty);
                value = PropertyUtil.getPropertyValue(aObject, theProperty) ;
//                System.out.println("value.getClass() = " + value.getClass());
                if (value instanceof Time) {
                    value = DateFormat.formatToTime((Time) value);
                    styleClass = "time";
                } else  if (value instanceof Date) {
                    value = DateFormat.formatToDate((Date) value);
                    styleClass = "date";
                } else if (value instanceof Number) {
                    styleClass = "number";
                } else if (value instanceof Boolean) {
                    Boolean booleanValue = (Boolean) value ;
                    if(booleanValue) {
                        value = "Да" ;
                        styleClass = "boolean";
                    } else {
                        value = "Нет" ;
                        styleClass = "boolean booleanNoValue";
                    }
                } else if(value instanceof String) {
                    if(DemoModeUtil.isInDemoMode(theServleRequest)) {
                        value = DemoModeUtil.secureValue(value);
                    }
                }

            } catch (Exception e) {
                value = e + "";
            }
            if (theCssClass != null) {
                styleClass += " " + theCssClass;
            }
            aOut.print("<td ") ;
            //aOut.print("onclick=\"");
            //aOut.print(aGoFunctionName) ;
            //aOut.print("\"");
            aOut.print(" class='sumTd ") ;
            aOut.print(styleClass);
            //aOut.print(' ');
            aOut.print(" _"+theProperty);
            aOut.print("'>");
            if (theIsCalcAmount) {
                aOut.print(value != null ? value : "&nbsp;");
            } else {
                aOut.print("&nbsp;");
            }
            aOut.println("</td>");
        }
        private Object amountCell(JspWriter aOut, Object aObject, Object aValue) throws IOException {
            Object value;
            try {
                BigDecimal val1 = new BigDecimal(aValue!=null?""+aValue:"0") ;
                value = PropertyUtil.getPropertyValue(aObject, theProperty) ;
                BigDecimal val2 ;
                if (value instanceof Number) {
                    val2 = new BigDecimal(""+value) ;
                } else {
                    val2 = new BigDecimal(value!=null ? (String)value : "0");
                }
                //val2 = val1+val2 ;
                String val3 = val1.add(val2).toString() ;

                PropertyUtil.setPropertyValue(aObject, theProperty, val3) ;
            //    value = PropertyUtil.getPropertyValue(aObject, theProperty) ;
                //System.out.println("val1="+val1+" val2="+val2+" val3="+val3+" valSum="+value) ;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return aObject ;
//
        }

        private final String theProperty;
        private final String theColumnName;
        private final boolean theIdColumn;
        private final String theCssClass;
        private final HttpServletRequest theServleRequest ;
        private final String theGuid ;
        private final boolean theIsCalcAmount ;
        private final String theAddParam ;
        //Milamesher 31052018 - ширина столбца в процентах
        private final String theWidth;
        // Milamesher 25102018 экранировать символы
        private boolean theEscapeSymbols=true;
        // прятать столбец
        private boolean theHidden=false;
    }


    /**
     * Название атррибута в request или в session
     */
    private String theName;
    //private final ArrayList theColumns = new ArrayList();
    //private final ArrayList theButtons = new ArrayList();
    private final ArrayList theCells = new ArrayList();
    private boolean theIsEmpty = false;
    /** Сообщение об отсутствии данных */
    private String theNoDataMessage = null;
    /** Поле с идентификатором */
    private String theIdField ;
    /** Тип выделения */
    private String theSelection ;



    /** Url печати */
    private String thePrintUrl;
    /** Url удаления записи */
    private String theDeleteUrl;
    /** Url просмотра записи */
    private String theViewUrl;
    /** Запретить поддержку клавиш */
    private boolean theDisableKeySupport = false ;
    String theFunctionDeleteName ;
    String theFunctionEditName ;
    String theFunctionPrintName ;
    String theFunctionViewName ;


    /**
     * Аттрибут для кнопки отправки таблицы в excel
     * @jsp.attribute   description = "Кнопка для печати "
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
    public String getPrintToExcelButton() {return thePrintToExcelButton;}
    public void setPrintToExcelButton(String aPrintToExcelButton) {thePrintToExcelButton = aPrintToExcelButton;}
    /** Кнопка для печати */
    String thePrintToExcelButton;

    /**
     * Открывать ссылки в новом окне
     * @jsp.attribute   description = "Открывтаь ссылку в новом окне"
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
    public Boolean getOpenNewWindow() {return theOpenNewWindow;}
    public void setOpenNewWindow(Boolean aOpenNewWindow) {theOpenNewWindow = aOpenNewWindow;}
    /** Открыть ссылку в новом окне */
    Boolean theOpenNewWindow;



    /**
     * Экранировать символы
     * @jsp.attribute   description = "Экранировать символы"
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
    public Boolean getEscapeSymbols() {return theEscapeSymbols;}
    public void setEscapeSymbols(Boolean aEscapeSymbols) {theEscapeSymbols = aEscapeSymbols;}
    /** Milamesher 25102018 экранировать символы */
    private boolean theEscapeSymbols=true;

    /**
     * Сортировать таблицу
     * @jsp.attribute   description = "Сортировать таблицу"
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
    public Boolean getSortable() {return theSortable;}
    public void setSortable(Boolean aSortable) {theSortable = aSortable;}
    /** Сортировать таблицу */
    private boolean theSortable=true;
}