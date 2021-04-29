<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@  attribute name="currentAction" required="true" description="Текущее меню" %>

<style type="text/css">
    a#${currentAction}, #side ul li a#${currentAction}, #side ul li a#${currentAction} {
        color: black;
        background-color: rgb(195, 217, 255);
        cursor: text;
        border: none;
        text-decoration: none;
        background-image: url("/skin/images/main/sideSelected.gif");
        background-repeat: no-repeat;
        background-position: center left;;
        font-weight: bold;
        -moz-border-radius-topleft: 4px;
        -moz-border-radius-bottomleft: 4px;
    }
</style>
<msh:sideMenu>
    <msh:sideLink name="Забор биомат." styleId="pres_intake" action="/pres_journal_intake"
                  roles="/Policy/Mis/Journal/Prescription/LabSurvey/IntakeByCurrentDepartment"/>
    <msh:sideLink name="Передача биомат. в лаборатории" styleId="pres_transfer"
                  action="/pres_journal_intake_transfer.do?serviceSubType=&serviceSubTypeName=&prescriptType=&prescriptTypeName="
                  roles="/Policy/Mis/Journal/Prescription/LabSurvey/TransferToLaboratory"/>
    <msh:sideLink name="Передача ПЦР" styleId="pres_transfer"
                  action="/pres_journal_intake_transfer.do?typeDate=1&typeIntake=3&typeTransfer=2&serviceSubType=24&department=&departmentName="
                  roles="/Policy/Mis/Journal/Prescription/LabSurvey/TransferToLaboratoryPCR"/>
    <msh:sideLink name="Рабочее место" styleId="pres_cabinet"
                  action="/pres_journal_prescript_cab_lab.do?serviceSubType=&serviceSubTypeName="
                  roles="/Policy/Mis/Journal/Prescription/LabSurvey/DoctorLaboratory"/>
    <msh:sideLink name="Рабочее место ПЦР" styleId="pres_cabinet"
                  action="/pres_journal_prescript_cab_lab.do?serviceSubType=24&serviceSubTypeName="
                  roles="/Policy/Mis/Journal/Prescription/LabSurvey/DoctorLaboratoryPCR"/>
    <msh:sideLink name="Поиск назначений по ФИО" styleId="pres_cabinetFio" action="/pres_prescription_findFIO"
                  roles="/Policy/Mis/Journal/Prescription/LabSurvey/DoctorLaboratory"/>
    <msh:sideLink name="Направление на ВИЧ" styleId="stac_journalCurrentByUserDepartmentMicroBio"
                  action="/stac_directionHIVByUserDepartment" roles="/Policy/Mis/MedCase/Stac/Journal/HIVDirection"/>
    <msh:sideLink name="Направления на исследования и темп. листы" styleId="stac_journalCurrentByUserDepartmentMicroBio"
                  action="/stac_directionsByUserDepartment" roles="/Policy/Mis/MedCase/Stac/Journal/HIVDirection"/>
    <msh:sideLink name="Печать" styleId="pres_lab_print" action="/pres_lab_print"
                  roles="/Policy/Mis/Journal/Prescription/LabSurvey/PrintPrescripton"/>
    <msh:sideLink name="Журнал аннулированных назначений" styleId="pres_annul_journal" action="/pres_annul_journal"
                  roles="/Policy/Mis/Journal/Prescription/LabSurvey/PresAnnulJournal"/>
    <msh:sideLink name="Положительные ПЦР" action="/lab_plus_covidPcr"
                  roles="/Policy/Mis/Journal/Prescription/LabSurvey/DoctorLaboratoryPCR"/>
    <msh:sideLink name="Свод анализов ПЦР SARV-COV2" action="/journal_svodPCR"
                  roles="/Policy/Mis/Journal/Prescription/ReestrPCR"/>
</msh:sideMenu>

<msh:sideMenu title="Статистика">
    <msh:sideLink name="Журнал назначений" styleId="pres_journal" action="/pres_journal"
                  roles="/Policy/Mis/Journal/Prescription/LabSurvey/JournalPrescript"/>
    <msh:sideLink name="Список отчетов"
                  action="/javascript:getDefinition('riams_journals.do?short=Short',null)" styleId="viewShort"/>
</msh:sideMenu>

<msh:sideMenu title="Поиск">
    <msh:sideLink key="CTRL+7" params="" action="/pres_prescription_find" name="⇧ Назначений"
                  roles="/Policy/Mis/Journal/Prescription/LabSurvey/IntakeByCurrentDepartment"
                  styleId="pres_find"/>
    <msh:sideLink params="" action="/pres_lab_print" name="Печать"
                  styleId="pres_print" roles="/Policy/Mis/Journal/Prescription/LabSurvey/IntakeByCurrentDepartment"/>
</msh:sideMenu>