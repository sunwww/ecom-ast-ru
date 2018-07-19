1531919997#
alter table VocOncologyReasonTreat alter column id set default nextval('VocOncologyReasonTreat_sequence');

insert into VocOncologyReasonTreat (code,name) VALUES
('1','Рецедив'),
('2','Прогрессирование');