1579153366#
alter table VocBirthNosology alter column id set default nextval('VocBirthNosology_sequence');
alter table birthnosologycard alter column id set default nextval('birthnosologycard_sequence');
insert into VocBirthNosology (code,idccode_id)
values('1',(select id from vocidc10 where code like 'O10.0'));
insert into VocBirthNosology (code,idccode_id)
values('1',(select id from vocidc10 where code like 'O12.0'));
insert into VocBirthNosology (code,idccode_id)
values('1',(select id from vocidc10 where code like 'O14.0'));
insert into VocBirthNosology (code,idccode_id)
values('1',(select id from vocidc10 where code like 'O14.1'));
insert into VocBirthNosology (code,idccode_id)
values('1',(select id from vocidc10 where code like 'O15.0'));
insert into VocBirthNosology (code,idccode_id)
values('1',(select id from vocidc10 where code like 'O22.0'));
insert into VocBirthNosology (code,idccode_id)
values('1',(select id from vocidc10 where code like 'O23.0'));
insert into VocBirthNosology (code,idccode_id)
values('1',(select id from vocidc10 where code like 'O23.5'));
insert into VocBirthNosology (code,idccode_id)
values('1',(select id from vocidc10 where code like 'O24.0'));
insert into VocBirthNosology (code,idccode_id)
values('1',(select id from vocidc10 where code like 'O24.4'));

insert into VocBirthNosology (code,idccode_id)
values('2',(select id from vocidc10 where code like 'O30.0'));
insert into VocBirthNosology (code,idccode_id)
values('2',(select id from vocidc10 where code like 'O30.1'));
insert into VocBirthNosology (code,idccode_id)
values('2',(select id from vocidc10 where code like 'O30.2'));
insert into VocBirthNosology (code,idccode_id)
values('2',(select id from vocidc10 where code like 'O32.0'));
insert into VocBirthNosology (code,idccode_id)
values('2',(select id from vocidc10 where code like 'O32.1'));
insert into VocBirthNosology (code,idccode_id)
values('2',(select id from vocidc10 where code like 'O32.2'));
insert into VocBirthNosology (code,idccode_id)
values('2',(select id from vocidc10 where code like 'O32.5'));
insert into VocBirthNosology (code,idccode_id)
values('2',(select id from vocidc10 where code like 'O33.1'));

insert into VocBirthNosology (code,idccode_id)
values('1',(select id from vocidc10 where code like 'O35.0'));
insert into VocBirthNosology (code,idccode_id)
values('1',(select id from vocidc10 where code like 'O36.0'));
insert into VocBirthNosology (code,idccode_id)
values('1',(select id from vocidc10 where code like 'O36.2'));
insert into VocBirthNosology (code,idccode_id)
values('1',(select id from vocidc10 where code like 'O36.3'));
insert into VocBirthNosology (code,idccode_id)
values('1',(select id from vocidc10 where code like 'O36.4'));
insert into VocBirthNosology (code,idccode_id)
values('1',(select id from vocidc10 where code like 'O36.5'));
insert into VocBirthNosology (code,idccode_id)
values('1',(select id from vocidc10 where code like 'O36.6'));
insert into VocBirthNosology (code,idccode_id)
values('1',(select id from vocidc10 where code like 'O40'));
insert into VocBirthNosology (code,idccode_id)
values('1',(select id from vocidc10 where code like 'O41.0'));

insert into VocBirthNosology (code,idccode_id)
values('2',(select id from vocidc10 where code like 'O42.0'));
insert into VocBirthNosology (code,idccode_id)
values('2',(select id from vocidc10 where code like 'O42.1'));
insert into VocBirthNosology (code,idccode_id)
values('2',(select id from vocidc10 where code like 'O45.0'));
insert into VocBirthNosology (code,idccode_id)
values('2',(select id from vocidc10 where code like 'O47.0'));
insert into VocBirthNosology (code,idccode_id)
values('2',(select id from vocidc10 where code like 'O47.1'));
insert into VocBirthNosology (code,idccode_id)
values('2',(select id from vocidc10 where code like 'O62.0'));
insert into VocBirthNosology (code,idccode_id)
values('2',(select id from vocidc10 where code like 'O62.1'));
insert into VocBirthNosology (code,idccode_id)
values('2',(select id from vocidc10 where code like 'O62.3'));
insert into VocBirthNosology (code,idccode_id)
values('2',(select id from vocidc10 where code like 'O69.0'));
insert into VocBirthNosology (code,idccode_id)
values('2',(select id from vocidc10 where code like 'O69.1'));
insert into VocBirthNosology (code,idccode_id)
values('2',(select id from vocidc10 where code like 'O72.0'));
insert into VocBirthNosology (code,idccode_id)
values('2',(select id from vocidc10 where code like 'O72.2'));
insert into VocBirthNosology (code,idccode_id)
values('2',(select id from vocidc10 where code like 'O73.0'));


insert into VocBirthNosology (code,idccode_id)
values('1',(select id from vocidc10 where code like 'O99.0'));
insert into VocBirthNosology (code,idccode_id)
values('1',(select id from vocidc10 where code like 'O99.1'));
insert into VocBirthNosology (code,idccode_id)
values('1',(select id from vocidc10 where code like 'O99.2'));
insert into VocBirthNosology (code,idccode_id)
values('1',(select id from vocidc10 where code like 'O99.3'));
insert into VocBirthNosology (code,idccode_id)
values('1',(select id from vocidc10 where code like 'O99.4'));
insert into VocBirthNosology (code,idccode_id)
values('1',(select id from vocidc10 where code like 'O99.5'));
insert into VocBirthNosology (code,idccode_id)
values('1',(select id from vocidc10 where code like 'O99.6'));
insert into VocBirthNosology (code,idccode_id)
values('1',(select id from vocidc10 where code like 'O99.7'));