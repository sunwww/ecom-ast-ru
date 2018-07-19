1531985361#
alter table VocOncologyN016 alter column id set default nextval('VocOncologyN016_sequence');

INSERT INTO VocOncologyN016 (id_voc,name,startdate) VALUES
('1','Первый цикл линии','01.09.2018'),
('2','Последующие циклы линии (кроме последнего)','01.09.2018'),
('3','Последний цикл линии (лечение прервано)','01.09.2018'),
('4','Последний цикл линии (лечение завершено)','01.09.2018');