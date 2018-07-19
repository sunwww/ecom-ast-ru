1531919997#

alter table VocOncologyDiagType alter column id set default nextval('VocOncologyDiagType_sequence');

insert into VocOncologyDiagType (code,name) VALUES
('1','Гистологический признак'),
('2','Маркёр(ИГХ)');