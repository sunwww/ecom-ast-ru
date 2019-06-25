1561010298#
    alter table VocQualityEstimationChangeReason alter column id set default nextval('VocQualityEstimationChangeReason_sequence');
insert into VocQualityEstimationChangeReason (name) values ('Выполнено, оформлено в истории');
insert into VocQualityEstimationChangeReason (name) values ('Было выполнено амбулаторно');
insert into VocQualityEstimationChangeReason (name) values ('Отсутствует фарм. препарат');
insert into VocQualityEstimationChangeReason (name) values ('Отсутствует фарм. препарат, рекомендовано амбулаторно');
insert into VocQualityEstimationChangeReason (name) values ('Рекомендовано выполнить амбулаторно');
insert into VocQualityEstimationChangeReason (name) values ('Выполнено в предыдущую госпитализацию');
insert into VocQualityEstimationChangeReason (name) values ('По техническим причинам выполнить невозможно');
insert into VocQualityEstimationChangeReason (name) values ('Отказ пациента');
insert into VocQualityEstimationChangeReason (name) values ('Нет показаний');
insert into VocQualityEstimationChangeReason (name) values ('Пациент самовольно покинул стационар');
insert into VocQualityEstimationChangeReason (name) values ('Заболевание установлено ранее');
insert into VocQualityEstimationChangeReason (name) values ('Исследование проводилось на этапе постановки диагноза');
insert into VocQualityEstimationChangeReason (name) values ('Выполнено, но без соблюдения временного режима');
insert into VocQualityEstimationChangeReason (name) values ('Не оплачено');
insert into VocQualityEstimationChangeReason (name) values ('На момент выписки в работе');