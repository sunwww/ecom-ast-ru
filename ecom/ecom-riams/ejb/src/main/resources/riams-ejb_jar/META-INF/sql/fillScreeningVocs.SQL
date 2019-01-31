1548837901#
    alter table VocScreeningArterialPulsation alter column id set default nextval('VocScreeningArterialPulsation_sequence');
    alter table VocScreeningCardiacActivity alter column id set default nextval('VocScreeningCardiacActivity_sequence');
    alter table VocScreeningCNS alter column id set default nextval('VocScreeningCNS_sequence');
    alter table VocScreeningLocalization alter column id set default nextval('VocScreeningLocalization_sequence');
    alter table VocScreeningSkin alter column id set default nextval('VocScreeningSkin_sequence');
    alter table VocScreeningDiuresis alter column id set default nextval('VocScreeningDiuresis_sequence');

insert into VocScreeningArterialPulsation(name,code) VALUES('отчётливая','1'),('сниженная','2'),('отсутствует','3');
insert into VocScreeningCardiacActivity(name,code) VALUES('ритмичный','1'),('аритмичный','2');
insert into VocScreeningCNS(name,code) VALUES('активность нормальная','1'),('угнетение','2');
insert into VocScreeningLocalization(name,code) VALUES('слева от грудины','1'),('справа от грудины','1'),
('в правом предреберье','2'),('в левом предреберье','2'),('по срединной линии','2');
insert into VocScreeningSkin(name,code) VALUES('розовые','1'),('цианотичные','2'),('бледные','3');
insert into VocScreeningDiuresis(name,code) VALUES('нормальный','1'),('снижен','2');

update mislpu set IsCreateCardiacScreening=true where id=265;