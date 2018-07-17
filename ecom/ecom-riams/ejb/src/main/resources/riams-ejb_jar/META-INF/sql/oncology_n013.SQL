1531818079#
alter table VocTypeTreatment_N013 alter column id set default nextval('VocTypeTreatment_N013_sequence');

INSERT INTO VocTypeTreatment_N013 (id_voc,name,startdate) VALUES
('1','Хирургическое лечение','01.09.2018'),
('2','Лекарственная противоопухолевая терапия','01.09.2018'),
('3','Лучевая терапия','01.09.2018'),
('4','Химиолучевая терапия','01.09.2018');
