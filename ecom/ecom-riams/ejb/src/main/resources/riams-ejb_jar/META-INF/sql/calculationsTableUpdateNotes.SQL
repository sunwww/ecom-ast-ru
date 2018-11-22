1542791637#
ALTER TABLE calculations ALTER COLUMN note TYPE character varying(400);

update calculations set note='Артериальная гипертония: систолическое АД выше 160 мм рт. ст.' where id=9;
update calculations set note='Нарушение функции почек: постоянный гемодиализ, трансплантация почки или креатинин сыворотки выше 200 мкмоль/л.' where id=10;
update calculations set note='Нарушение функции печени: хроническое заболевание печение (например, цирроз печени) или биохимические показатели значительного поражения печени (билирубин в два раза выше верхней границы нормы в сочетании с повышением активности АСТ, АЛТ и ЩФ в 3 раза выше верхней границы нормы).' where id=11;
update calculations set note='Инсульт: подтвержденный инсульт в анамнезе.' where id=12;
update calculations set note='Кровотечение: кровотечение в анамнезе, геморрагический диатез или анемия. ' where id=13;
update calculations set note='Неустойчивое МНО: нестабильное или высокое МНО или выход за пределы терапевтического интервала более 40% времени' where id=14;
update calculations set note='' where id=15;
update calculations set note='Препараты: прием препаратов, повышающих риск кровотечения, таких как антиагреганты и НПВС. ' where id=16;
update calculations set note='Алкоголь: употребление более 8 порций алкогольных напитков в неделю.' where id=17;

alter table calculationsresult alter column id set default nextval('calculationsresult_sequence');
alter table interpretation alter column id set default nextval('interpretation_sequence');
ALTER TABLE calculationsresult ALTER COLUMN result TYPE character varying(400);

insert into interpretation(value,result,calculator_id) values('1','Риск кровотечений невысокий. Риск кровотечений считается высоким при 3 и более баллах.',3);
insert into interpretation(value,result,calculator_id) values('2','Риск кровотечений невысокий. Риск кровотечений считается высоким при 3 и более баллах.',3);
insert into interpretation(value,result,calculator_id) values('3','Риск кровотечений высокий. Риск кровотечений считается высоким при 3 и более баллах.',3);
insert into interpretation(value,result,calculator_id) values('4','Риск кровотечений высокий. Риск кровотечений считается высоким при 3 и более баллах.',3);
insert into interpretation(value,result,calculator_id) values('5','Риск кровотечений высокий. Риск кровотечений считается высоким при 3 и более баллах.',3);
insert into interpretation(value,result,calculator_id) values('6','Риск кровотечений высокий. Риск кровотечений считается высоким при 3 и более баллах.',3);
insert into interpretation(value,result,calculator_id) values('7','Риск кровотечений высокий. Риск кровотечений считается высоким при 3 и более баллах.',3);
insert into interpretation(value,result,calculator_id) values('8','Риск кровотечений высокий. Риск кровотечений считается высоким при 3 и более баллах.',3);
insert into interpretation(value,result,calculator_id) values('9','Риск кровотечений высокий. Риск кровотечений считается высоким при 3 и более баллах.',3);

update calculator set creatediary=true where id=1 or id=3;

insert into interpretation(value,result,calculator_id) values('1','Ожидаемая частота инсультов за год - 1.3%. Лечение: индивидуально.',1);
insert into interpretation(value,result,calculator_id) values('2','Ожидаемая частота инсультов за год — 2.2%. Лечение: антикоагулянты.',1);
insert into interpretation(value,result,calculator_id) values('3','Ожидаемая частота инсультов за год - 3.2%. Лечение: индивидуально.',1);
insert into interpretation(value,result,calculator_id) values('4','Ожидаемая частота инсультов за год - 4.0%. Лечение: индивидуально.',1);
insert into interpretation(value,result,calculator_id) values('5','Ожидаемая частота инсультов за год - 6.7%. Лечение: индивидуально.',1);
insert into interpretation(value,result,calculator_id) values('6','Ожидаемая частота инсультов за год - 9.8%. Лечение: индивидуально.',1);
insert into interpretation(value,result,calculator_id) values('7','Ожидаемая частота инсультов за год - 9.6%. Лечение: индивидуально.',1);
insert into interpretation(value,result,calculator_id) values('8','Ожидаемая частота инсультов за год - 6.7%. Лечение: индивидуально.',1);
insert into interpretation(value,result,calculator_id) values('9','Ожидаемая частота инсультов за год - 15.2%. Лечение: индивидуально.',1);