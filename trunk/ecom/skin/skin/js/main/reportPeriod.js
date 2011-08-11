var reportperiod =
{
        version: '0.0'
}
var theReportperiodDiv ;
var theReportperiodDats ;
var theReportperiodDatpo ;
reportperiod.CreateDiv = function(aDiv, aDats, aDatpo)
{
    // Объявление глобальных переменных
     theReportperiodDiv = aDiv ;
     theReportperiodDats = aDats ;
     theReportperiodDatpo = aDatpo ;

//    function Phase_1(aDiv) {
//        alert(aDiv);
        aDiv.innerHTML = ""
                + "<ul id='getPeriodMain' name='getPeriodMain' style='list-style-position:outside;list-style-type:none;display:block; right:5px; "
                + "margin-top:0pt;margin-bottom:0pt;margin-right-value:0pt;margin-left-value:0pt'>"
                + " <li style='float:left;margin-left-value:7px;'><a href='javascript:reportperiod.change(/'id/')'>ПОЛУГОДИЕ</a>&nbsp;"
                + "   <layer id='li_half_year' class='layer'>"
                + "      <ul  id='ul1'>"
                + "         <li>Введите год: <input type='text' size='4' maxlength='4'></li>"
                + "         <li>Выберите полугодие:"
                + "              <select>"
                + "                 <option>I</option>"
                + "                  <option>II</option>"
                + "              </select>"
                + "         </li>"
                + "     </ul>"
                + "   </layer>"
                + " </li>"
                + " <li style='float:left'><a href='javascript:alert(2)'>КВАРТАЛ</a>&nbsp;"
                + "   <layer id='li_half_year' class='layer'>"
                + "     <ul id='ul2'>"
                + "         <li>Введите год: <input type='text' size='4' maxlength='4'></li>"
                + "         <li>Выберите квартал:"
                + "              <select>"
                + "                 <option>I</option>"
                + "                 <option>II</option>"
                + "                 <option>III</option>"
                + "                 <option>IV</option>"
                + "              </select>"
                + "         </li>"
                + "     </ul>"
                + "   </layer>"
                + " </li>"
                + " <li style='float:left'><a href='javascript:alert(3)'>МЕСЯЦ</a>&nbsp;"
                + "   <layer id='li_half_year' class='layer'>"
                + "     <ul id='ul3'>"
                + "         <li>Введите год: <input type='text' size='4' maxlength='4'>&nbsp;</li>"
                + "         <li>Выберите месяц: "
                + "              <select>"
                + "                 <option>Январь</option>"
                + "                 <option>Февраль</option>"
                + "                 <option>Март</option>"
                + "                 <option>Апрель</option>"
                + "                 <option>Май</option>"
                + "                 <option>Июнь</option>"
                + "                 <option>Июль</option>"
                + "                 <option>Август</option>"
                + "                 <option>Сентябрь</option>"
                + "                 <option>Октябрь</option>"
                + "                 <option>Ноябрь</option>"
                + "                 <option>Декабрь</option>"
                + "              </select>"
                + "         </li>"
                + "     </ul>"
                + "   </layer>"
                + " </li>"
                + " <li style='float:left'><a href='javascript:alert(4)'>НЕДЕЛЯ</a>&nbsp;</li>"
                + " <li style='float:left'><a href='javascript:alert(5)'>ПРОИЗВОЛЬНЫЙ</a>&nbsp;"
                + "   <layer id='li_half_year' class='layer'>"
                + "     <ul id='ul5'>"
                + "         <li>Введите дату начала: <input type='text' size='4' maxlength='4'></li>"
                + "         <li>Введите дату конца: <input type='text' size='4' maxlength='4'></li>"
                + "     </ul>"
                + "   </layer>"
                + " </li>"
                + "</ul>"

    function ChangeVisiblLayer()
    {

    }
//    }
}
reportperiod.Remove = function(aDiv,aDats,aDatpo) {

}

reportperiod.change = function(id)
{
     for (var i=0;i<theReportperiodDiv.getPeriodMain.childNodes.length;i++)
     {
        alert(theReportperiodDiv.getPeriodMain.childNodes[i].nodeName) ;
     }
}