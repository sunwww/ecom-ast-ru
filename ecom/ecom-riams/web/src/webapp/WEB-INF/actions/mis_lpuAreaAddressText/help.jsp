<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<p>
Блок "Номер дома, владения" содержит номера домов (владений) - адресных объектов шестого уровня классификации. Длина блока не должна превышать 40 символов.
</p>

<p>
    Возможны следующие варианты записи обозначений домов в блоке "Номер дома, владения":
</p>

<ol>
    <li>
        В блоке "Номер дома, владения" записан номер одного дома (владения) или перечень номеров домов (владений), разделенных символом "," (запятая). При этом номер дома (владения) может содержать:
        <ul>
            <li>
                только цифровую или буквенную часть;
            </li>
            <li>цифровую часть и буквенную, набираемую прописными буквами русского алфавита (между цифровой и буквенной частями не должно быть пробела);</li>
            <li>знак дробной черты ("/") без пробелов, например:  25/3,  60Б/2,  12/3А;</li>
            <li>номер корпуса или (и) строения (владения):
                      к2,  10к3,  7Астр1,  4стр2Б,  39/43к1,  5к2стр1,  влд10;</li>
            <li>два номера, разделенных знаком подчеркивания ("_"): 19Б_21стр1.</li>
        </ul>
    </li>

    <li>В блоке "Номер дома, владения" записан интервал номеров домов.
        <p>Интервал записывается в виде двух номеров домов (нижней и верхней границ интервала), между которыми стоит знак "-" (дефис). Границами интервала могут быть только простые номера домов (без буквенного модификатора, знака "/", без номера корпуса, строения). В качестве верхней границы интервала может быть указано число "999" (в случае, когда необходимо задать интервал, включающий последний дом улицы, а номер этого дома неизвестен). Интервалы номеров домов одной улицы не должны пересекаться. Записи типа: 10-22А, 2Б-14к1 не допускаются (являются неверными).</p>

    <p>В интервал попадают все дома (вместе со своими корпусами и строениями), номера которых заключены между границами интервала, включая и сами эти границы (четные и нечетные, в том числе дома, обозначения которых содержат буквенные модификаторы и (или) дробную черту).
    </p>


    <p>В интервалы не входят дома, обозначения которых содержат только одни буквы или буквенную часть, стоящую перед номером, а также дома, обозначения которых содержат номера корпусов, строений (владений) без номеров домов. Их нужно указывать отдельно.
    </p>

    <p>Для обозначения интервалов с заданными четными (нечетными) номерами  домов перед границами интервала, заключёнными в круглые скобки, ставится признак четности -  прописная буква "Ч" русского алфавита или признак нечетности - прописная буква "Н" русского алфавита.
    </p>

    <p>
    Для обозначения всех четных номеров домов (в тех случаях, когда неизвестны конкретные номера домов) используется прописная буква "Ч" русского алфавита, а всех нечетных - прописная буква "Н" русского алфавита.
    </p>
    </li>


    <li>
        В блоке "Номер дома, владения" записано сочетание приведенных выше вариантов обозначения домов.
        <pre>Примеры:  2, 4, Н(1-17);  Н(21-999),Ч(8-32).</pre>
    </li>
</ol>