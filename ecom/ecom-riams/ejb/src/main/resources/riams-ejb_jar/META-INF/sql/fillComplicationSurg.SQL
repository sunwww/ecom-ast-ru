1584622370#
  
  alter table VocComplication alter column id set default nextval('VocComplication_sequence');
  
  insert into VocComplication (code,name) values('1','Кровотечение');
  insert into VocComplication (code,name) values('2','Гематома');
  insert into VocComplication (code,name) values('3','Абсцесс');
  insert into VocComplication (code,name) values('4','Перитонит');
  insert into VocComplication (code,name) values('5','Нагноение послеоперационной раны');
  insert into VocComplication (code,name) values('6','Эвентрация');
  insert into VocComplication (code,name) values('7','Кишечная непроходимость');
  insert into VocComplication (code,name) values('8','Свищ');
  insert into VocComplication (code,name) values('OTHER','Другое');

   insert into VocComplication (code,name) values('9','Пневмония');
  insert into VocComplication (code,name) values('10','Плеврит');
  insert into VocComplication (code,name) values('11','Менингит');
  insert into VocComplication (code,name) values('12','Эндобронхит');
  insert into VocComplication (code,name) values('13','Сепсис');
  insert into VocComplication (code,name) values('14','Инфаркт миокарда');
  insert into VocComplication (code,name) values('15','Инсульт');
  insert into VocComplication (code,name) values('16','Тромбоз');
  insert into VocComplication (code,name) values('17','Тромбофлебит');
  insert into VocComplication (code,name) values('18','ТЭЛА');
  insert into VocComplication (code,name) values('19','Сердечно-сосудистая недостаточность');
  insert into VocComplication (code,name) values('20','Острая почечная недостаточность');
  insert into VocComplication (code,name) values('21','Антибиотик ассоциированный колит');
  insert into VocComplication (code,name) values('22','Задержка мочи');


  --alter table VocComplicationGroup alter column id set default nextval('VocComplicationGroup_sequence');
  --insert into VocComplicationGroup (code,name) values('1','Осложнения в органах и системах, на которых проводилось оперативное вмешательство');
  --insert into VocComplicationGroup (code,name) values('2','Осложнения, связанные с операцией, в органах и системах, на которых вмешательство не проводилось');