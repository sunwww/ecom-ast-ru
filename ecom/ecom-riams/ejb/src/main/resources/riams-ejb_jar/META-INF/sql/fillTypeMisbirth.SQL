1545730928#
    alter table VocTypeMisbirth alter column id set default nextval('VocTypeMisbirth_sequence');
INSERT INTO VocTypeMisbirth(name,code) values('самопроизвольный, бер. 12-22 неделя у первобеременной','1'),
('самопроизвольный, бер. 12-22 неделя у повторнобеременной','2'),
('по мед. покзаниям из-за врождённых пороков развития плода, бер. 12-22 неделя у первобеременной','3'),
('по мед. покзаниям из-за врождённых пороков развития плода, бер. 12-22 неделя у повторнобеременной','4');


 alter table misbirth alter column id set default nextval('misbirth_sequence');