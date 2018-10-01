1531985361#
alter table VocOncologyN013 alter column id set default nextval('VocOncologyN013_sequence');

INSERT INTO VocOncologyN013 (code,name,startdate) VALUES
('1','Хирургическое лечение','01.09.2018'),
('2','Лекарственная противоопухолевая терапия','01.09.2018'),
('3','Лучевая терапия','01.09.2018'),
('4','Химиолучевая терапия','01.09.2018');
INSERT INTO VocOncologyN013 (code,name,startdate) VALUES
('5','Неспецифическое лечение (осложнения противоопухолевой терапии, установка/замена порт системы (катетера))','01.09.2018');
