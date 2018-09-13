1536819615#
alter table VocDispensaryEnd alter column id set default nextval('VocDispensaryEnd_sequence');
insert into VocDispensaryEnd (code, name,  comment) values ( upper('1'),upper('Выздоровление'),upper('Выздоровление или достижение стойкой компенсации физиологических функций после перенесенного острого заболевания'));
insert into VocDispensaryEnd (code, name,  comment) values ( upper('2'),upper('Стойкая ремиссия'),upper('Достижение стойкой компенсации физиологических функций или стойкой ремиссии хронического заболевания (состояния)'));
insert into VocDispensaryEnd (code, name,  comment) values ( upper('3'),upper('Устранение факторов риска'),upper('Устранение (коррекция) факторов риска и снижение степени риска развития хронических неинфекционных заболеваний и их осложнений до умеренного или низкого уровня ( у лиц со II группой здоровья)'));
insert into VocDispensaryEnd (code, name,  comment) values ( upper('4'),upper('Смерть'),upper('null'));
insert into VocDispensaryEnd (code, name,  comment) values ( upper('5'),upper('Смена места жительства'),upper('Смена места жительства, как следствие, прикрепление к другой МО'));
insert into VocDispensaryEnd (code, name,  comment) values ( upper('6'),upper('Отказ'),upper('Отказ от диспансерного наблюдения (наличие письменного отказа в медицинской документации'));
