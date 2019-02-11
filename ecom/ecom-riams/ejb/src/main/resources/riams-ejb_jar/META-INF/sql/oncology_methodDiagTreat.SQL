1531919997#
alter table VocOncologyMethodDiagTreat alter column id set default nextval('VocOncologyMethodDiagTreat_sequence');

insert into VocOncologyMethodDiagTreat (code,name) VALUES
('1','Лабораторная диагностика'),
('2','Инструментальная диагностика'),
('3','Методы лучевой диагностики за исключ. дорогостоящих'),
('4','Дорогостоящие методы лучевой диагностики(КТ,МРТ, ангиография)');
