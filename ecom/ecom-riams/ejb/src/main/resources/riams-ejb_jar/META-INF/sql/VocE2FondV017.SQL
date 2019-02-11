1543496811#
--Справочник результатов доп. диспансеризации
alter table VocE2FondV017 alter column id set default nextval('VocE2FondV017_sequence');
insert into VocE2FondV017(startDate,code,name) values(to_date('26.12.2013','dd.MM.yyyy'),'1','Присвоена I группа здоровья');
insert into VocE2FondV017(startDate,code,name) values(to_date('26.12.2013','dd.MM.yyyy'),'2','Присвоена II группа здоровья');
insert into VocE2FondV017(startDate,code,name) values(to_date('26.12.2013','dd.MM.yyyy'),'3','Присвоена III группа здоровья');
insert into VocE2FondV017(startDate,code,name) values(to_date('01.04.2015','dd.MM.yyyy'),'31','Присвоена IIIа группа здоровья');
insert into VocE2FondV017(startDate,code,name) values(to_date('01.04.2015','dd.MM.yyyy'),'32','Присвоена IIIб группа здоровья');
insert into VocE2FondV017(startDate,code,name) values(to_date('26.12.2013','dd.MM.yyyy'),'4','Присвоена IV группа здоровья');
insert into VocE2FondV017(startDate,code,name) values(to_date('26.12.2013','dd.MM.yyyy'),'5','Присвоена V группа здоровья');
insert into VocE2FondV017(startDate,code,name) values(to_date('01.04.2018','dd.MM.yyyy'),'11','Направлен на II этап профилактического медицинского осмотра несовершеннолетних или диспансеризации всех типов, предварительно присвоена I группа здоровья');
insert into VocE2FondV017(startDate,code,name) values(to_date('01.04.2018','dd.MM.yyyy'),'12','Направлен на II этап профилактического медицинского осмотра несовершеннолетних или диспансеризации всех типов, предварительно присвоена II группа здоровья');
insert into VocE2FondV017(startDate,code,name) values(to_date('01.04.2018','dd.MM.yyyy'),'19','Направлен на II этап диспансеризации или профилактического медицинского осмотра несовершеннолетних, предварительно присвоена III группа здоровья');
insert into VocE2FondV017(startDate,code,name) values(to_date('01.04.2018','dd.MM.yyyy'),'14','Направлен на II этап диспансеризации определенных групп взрослого населения, предварительно присвоена IIIа группа здоровья');
insert into VocE2FondV017(startDate,code,name) values(to_date('01.04.2018','dd.MM.yyyy'),'15','Направлен на II этап диспансеризации определенных групп взрослого населения, предварительно присвоена IIIб группа здоровья');
insert into VocE2FondV017(startDate,code,name) values(to_date('01.01.2018','dd.MM.yyyy'),'16','Проведен I этап диспансеризации определенных групп взрослого населения с периодичностью 1 раз в 2 года');
insert into VocE2FondV017(startDate,code,name) values(to_date('01.04.2018','dd.MM.yyyy'),'17','Направлен на II этап диспансеризации или профилактического медицинского осмотра несовершеннолетних, предварительно присвоена IV группа здоровья');
insert into VocE2FondV017(startDate,code,name) values(to_date('01.04.2018','dd.MM.yyyy'),'18','Направлен на II этап диспансеризации или профилактического медицинского осмотра несовершеннолетних, предварительно присвоена V группа здоровья');