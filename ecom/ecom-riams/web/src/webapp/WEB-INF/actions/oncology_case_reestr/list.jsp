<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
    <tiles:put name="title" type="string">
        <msh:title mainMenu="Patient">Случаи ЗНО</msh:title>
    </tiles:put>

    <tiles:put name="side" type="string">
        <msh:sideMenu>
            <msh:sideLink roles="/Policy/Mis/Oncology/Case/Create" key="ALT+N" action="/entityParentPrepareCreate-oncology_case_reestr.do" params="id" name="Заполнить случай ЗНО" />
            <msh:sideLink action="/javascript:goToMedCase()" name="СЛС/СПО" />
        </msh:sideMenu>
    </tiles:put>
    <tiles:put name="body" type="string">
        <ecom:webQuery name="onccase" nativeSql="
        select o.id,
        n002.name as stad,
        case when n003.name is not null and n003.name!='' then n003.name else n003.tumorcode end as tumor,
        case when n004.name is not null and n004.name!='' then n004.name else n004.noduscode end as nodus,
        case when n005.name is not null and n005.name!='' then n005.name else n005.metastasiscode end as metas
        from oncologycase o
        left join voconcologyn002 n002 on n002.id= o.stad_id
        left join voconcologyn003 n003 on n003.id=o.tumor_id
        left join voconcologyn004 n004 on n004.id =o.nodus_id
        left join voconcologyn005 n005 on n005.id = o.metastasis_id"/>
        <msh:tableNotEmpty name="onccase" >
            <msh:table  name="onccase" action="entityParentView-oncology_case_reestr.do" idField="1" >
                <msh:tableColumn columnName="#" property="sn" />
                <msh:tableColumn columnName="Стадия" property="2" />
                <msh:tableColumn columnName="Tumor" property="3" />
                <msh:tableColumn columnName="Nodus" property="4" />
                <msh:tableColumn columnName="Metastasis" property="5" />
            </msh:table>
        </msh:tableNotEmpty>
    </tiles:put>
    <tiles:put name="body" type="string">
        <ecom:webQuery name="list" nativeSql="
                    select od.id,otd.name as type ,od.date as date
                    from oncologydirection od
                    left join vocOncologyTypeDirection otd on otd.id = od.typedirection_id
                    left join oncologycase c on c.id=od.oncologycase_id
                    where c.medcase_id = ${param.id}"/>
        <msh:table viewUrl="entityView-oncology_direction.do?short=Short" name="list"
                   action="javascript:void(0)" idField="1" >
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Вид направления" property="2"/>
            <msh:tableColumn columnName="Дата направления" property="3"/>
        </msh:table>
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type="text/javascript" src="./dwr/interface/OncologyService.js"></script>
        <script type="text/javascript" >
            function goToMedCase() {
                OncologyService.getMedCaseDtype(${param.id}, {
                    callback: function(res) {
                        var view='entityParentView-';
                        if (res==0)  view+='stac_ssl';
                        else if (res==1) view+='smo_spo';
                        if (res==0 || res==1) {
                            view+='.do?id='+${param.id};
                            window.location.href=view;
                        }
                        else alert('Не удалось найти СЛС/СПО!');
                    }
                });
            }
        </script>
    </tiles:put>
</tiles:insert>