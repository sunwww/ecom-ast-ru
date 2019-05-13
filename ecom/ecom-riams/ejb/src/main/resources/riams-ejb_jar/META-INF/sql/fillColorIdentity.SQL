1557746774#
    alter table voccoloridentitypatient alter column id set default nextval('voccoloridentitypatient_sequence');
alter table coloridentitypatient alter column id set default nextval('coloridentitypatient_sequence');
alter table voccolor alter column id set default nextval('voccolor_sequence');

insert into voccolor (code,name) values('#FF0000','красный');
insert into voccolor (code,name) values('#FFFF00','жёлтый');
insert into voccolor (code,name) values('#0000FF','синий');
insert into voccolor (code,name) values('#DDA0DD','фиолетовый');

insert into voccoloridentitypatient(name,code,isfornewborn,lpu_id,color_id)
values('аллергия','1',false,1,1);
insert into voccoloridentitypatient(name,code,isfornewborn,lpu_id,color_id)
values('может упасть','2',false,1,3);
insert into voccoloridentitypatient(name,code,isfornewborn,lpu_id,color_id)
values('гепатит','3',false,1,2);
insert into voccoloridentitypatient(name,code,isfornewborn,lpu_id,color_id)
values('диабет у матери','4',true,1,4);