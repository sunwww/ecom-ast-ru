package ru.ecom.jaas.ejb.service;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: esinev
 * Date: 16.10.2006
 * Time: 1:30:05
 * To change this template use File | Settings | File Templates.
 */
public class CreateReplaceMapHelper {

    public Map<String, String> create() {
        System.out.println("creating theMap = " + theMap);
        r("Edit","Изменение") ;
        r("View","Просмотр") ;
        r("Create","Создание") ;
        r("Delete","Удаление") ;
        r("Show","Показывать в боковой панели") ;
        r("CreateHour","Ограничивать создание в пределах 24 часов") ;
        r("EditHour","Ограничивать изменение в пределах 24 часов") ;

        r("Policy","Политики безопасности") ;
        r("Stac","Стационар") ;

        r("Jaas","Управление политиками безопасности") ;
        r("Mis","РИАМС") ;

        r("SecUser","Пользователь") ;
        r("SecPolicy","Политика безопасности") ;
        r("SecRole","Роль") ;

        r("LpuArea","Участок") ;
        r("LpuAreaAddressText","Адрес участка") ;
        r("MedPolicyDmc","Полис ДМС") ;
        r("MedPolicyOmcForm","Полис ОМС") ;
        r("MisLpu","ЛПУ") ;
        r("Usl","Услуги") ;
        r("MedPolicy","Медицинский полис") ;
        r("Patient","Пациент") ;

        r("Exp","Экспертиза") ;
        r("CheckProperty","Настройка проверки") ;
        r("Check","Проверка") ;
        r("Field","Поле") ;
        r("Format","Формат") ;
        r("Time","Импорт по дате") ;
        r("Document","Документ") ;
        r("Areas","Участки") ;
        r("MisLpuDynamic","Список ЛПУ") ;
//        r("","") ;

        r("Admission","Поступление") ;
        r("AllVoc","Справочники") ;
        r("Blood","Переливание крови") ;
        r("CustomizeMode","Изменение вида форм") ;
        r("Disability","Нетрудоспособность") ;
        r("Discharge","Выписка") ;
        r("Ekom","СЛО для экспертов") ;
        r("EntityForm","Справочники") ;
        r("Expert","Экспертная оценка") ;    // todo
        r("ExpertCard","Экспертная карта") ; // todo
        r("Import","Импорт") ;
        r("LpuFond","LPUFOND.DBF") ;
        r("InfectiousDisease","Инфекционные болезни") ;
        r("Info","Просмотр общей информации") ;
        r("MedPolicy","Мед. полис") ;
        r("Militia","Сообщение в милицию") ;
        r("Nurse","Лица по уходу") ;
        r("NurseDays","Лица по уходу: пребывание") ;
        r("NurseService","Лица по уходу: сервис") ;
        r("Operation","Операции") ;
        r("Patient","Пациент") ;
        r("PriemJournal","Журнал обращений") ;
        r("Render","Услуги") ;
        r("Slo","Лечение в отделении") ;
        r("Vaccination","Вакцинация") ;
        r("Sls","СЛС") ; // todo
        r("AlwaysCreateStatCardNumber","Создавать номер стат. карты при отказе в госпитализации") ;
        r("ChangeStatCardNumber","Изменять номер стат. карты") ;
        r("CreateStatCardNumberByHand","Создавать новый номер стат. карты вручную") ;
        r("ConfirmAfterAdd","Подтвержение полиса при его создании") ;
        r("AcquirePatient","Брать ФИО, дату рождения, адрес и др. при создании полиса из паспортных данных") ;
//        r("","") ;
//        r("","") ;
//        r("","") ;

        return theMap ;
    }

    private void r(String s, String s1) {
        theMap.put(s, s1) ;
    }

    private final Map<String, String> theMap = new TreeMap<String, String>();
}
