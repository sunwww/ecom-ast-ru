1565940167#
    alter table VocClassWound alter column id set default nextval('VocClassWound_sequence');
insert into VocClassWound (code,name) values('Класс I','чистая');
insert into VocClassWound (code,name) values('Класс II','условно-чистая');
insert into VocClassWound (code,name) values('Класс III','контаминированная');
insert into VocClassWound (code,name) values('Класс IV','инфицированная');

alter table VocAntibioticDrug alter column id set default nextval('VocAntibioticDrug_sequence');
insert into VocAntibioticDrug (code,name) values('1','Цефазолин');
insert into VocAntibioticDrug (code,name) values('2','Амоксициллин/клавуланат');
insert into VocAntibioticDrug (code,name) values('3','Ампициллин/сульбактам');
insert into VocAntibioticDrug (code,name) values('4','Цефуроксим');
insert into VocAntibioticDrug (code,name) values('5','Метронидазол');
insert into VocAntibioticDrug (code,name) values('6','Цефтриаксон');
insert into VocAntibioticDrug (code,name) values('7','Цефотаксим');
insert into VocAntibioticDrug (code,name) values('8','Ванкомицин');
insert into VocAntibioticDrug (code,name) values('9','Гентамицин');
insert into VocAntibioticDrug (code,name) values('10','Клиндамицин');
insert into VocAntibioticDrug (code,name) values('11','Ципрофлоксацин');
insert into VocAntibioticDrug (code,name) values('12','Линкомицин');
insert into VocAntibioticDrug (code,name) values('13','Доксициклин');
insert into VocAntibioticDrug (code,name) values('14','Нет');

alter table VocMethodsDrugAdm alter column id set default nextval('VocMethodsDrugAdm_sequence');
insert into VocMethodsDrugAdm (code,name) values('1','в/м');
insert into VocMethodsDrugAdm (code,name) values('2','в/в');
insert into VocMethodsDrugAdm (code,name) values('3','per os');