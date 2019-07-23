1563794785#
    alter table VocObservationResult alter column id set default nextval('VocObservationResult_sequence');
insert into VocObservationResult (code, name) values ('1','выздоровел');
insert into VocObservationResult (code, name) values ('2','умер');
  alter table observationsheet alter column id set default nextval('observationsheet_sequence');