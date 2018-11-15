1542257118#
  alter table VocWfConsultationCancelReason alter column id set default nextval('VocWfConsultationCancelReason_sequence');
insert into VocWfConsultationCancelReason (name, code) values ('Необоснованная консультация','0');
insert into VocWfConsultationCancelReason (name, code) values ('Переведён в другое отделение','1');
insert into VocWfConsultationCancelReason (name, code) values ('Выписан','2');
insert into VocWfConsultationCancelReason (name, code) values ('Смерть пациента','3');