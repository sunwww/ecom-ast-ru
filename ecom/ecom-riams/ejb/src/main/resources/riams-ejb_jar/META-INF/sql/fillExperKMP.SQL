1582889940#
    insert into vocqualityestimationkind(id,code,name) values (6,'KMP','Экспертиза KMP');
    alter sequence vocqualityestimationcrit_sequence restart with 2414;
--Неврология

--1 карта: 11 критериев 2414-2424 (геморрагический инсульт, диагноза нет)
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Выполнен полноценный неврологический осмотр с постановкой топического и клинического диагнозов с применением шкал ком Глазго, шкалы Н-Н'
,2414,'02414',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Выполнены консультация врачом-неврологом и врачом акушером-гинекологом'
,2415,'02415',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Выполнена КТ головного мозга или МРТ головного мозга'
,2416,'02416',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Выполнены электрокардиография в трех стандартных отведениях и шести грудных, УЗИ плода в оценкой маточно-плацентарного кровотока'
,2417,'02417',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Выполнена повторная консультация врачом акушером-гинекологом'
,2418,'02418',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Выполнена транскраниальная доплерография с вычислением индексов Линденгарда (при наличии медицинских показаний)'
,2419,'02419',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Выполнен общий анализ крови с подсчетом лейкоцитарной формулы и биохимический анализ крови (мочевина, креатинин, общий белок, альбумин, общий билирубин, АЛТ, АСТ, ЛДГ, натрий, калий, хлор) и МНО'
,2420,'02420',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Выполнено хирургическое вмешательство в отношении источника кровоизлияния и /или направленное на устранение декомпрессии головного мозга сгустками крови (при наличии медицинских показаний)'
,2421,'02421',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Проведен мониторинг уровня сознания, неврологического статуса не менее 3 раз в сутки'
,2422,'02422',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Выполнено не менее одного контрольного послеоперационного исследования КТ/МРТ головного мозга за госпитализацию при условии транспортабельности пациентки'
,2423,'02423',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Выполнена оценка St. localis, достигнуто заживление ран, сняты швы.'
,2424,'02424',6);

--2 карта 21 критерий 2425-2445
--при злокачественном инфаркте мозжечка(I63, при сопутствующем I66.3)
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Осмотр врача-невролога не позднее 10 минут от поступления в стационар'
,2425,'02425',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Выполнение КТ головного мозга или МРТ  с описанием не позднее 40 минут от момента поступления в стационар'
,2426,'02426',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Определение уровня глюкозы в периферической крови не позднее 20 минут от момента поступления в стационар'
,2427,'02427',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Определение уровня тромбоцитов в периферической крови не позднее 20 минут от момента поступления в стационар'
,2428,'02428',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Определение уровня МНО  в периферической крови не позднее 20 минут от момента поступления в стационар'
,2429,'02429',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Консультация врача-нейрохирурга при подозрении на злокачественный инфаркт  мозжечка'
,2430,'02430',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Начато лечение в БИТР или реанимации не позднее 60 минут от поступления в стационар'
,2431,'02431',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Выполнено стандартизированное скриннинговое исследование функции глотания не позднее 3 часов от момента поступления в стационар'
,2432,'02432',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Оценка степени нарушения сознания и комы по Шкале комы Глазго и неврологического статуса по шкале  инсульта NIHSS не позднее 3 часов от момента поступления в стационар'
,2433,'02433',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Мониторирование жизненно  важных  функций ( артериального давления, пульса, дыхания, уровня насыщения кислорода крови, диуреза)'
,2434,'02434',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Индивидуальная нутритивная поддержка не позднее 24 часов от момента поступления в стационар с последующей ежедневной коррекцией'
,2435,'02435',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Определение патогенетического варианта ишемического инсульта по TOAST'
,2436,'02436',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Профилактика повторных сосудистых событий лекарственными препаратами группы антиагреганты при некардиоэмболическом подтипе инсульта или лекарственными препаратами группы антикоагулянты при кардиоэмболическом подтипе инсульта ( при отсутствии медицинских противопоказаний)'
,2437,'02437',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Начало медицинской реабилитации не позднее 48 часов от момента поступления в стационар'
,2438,'02438',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Применение Шкалы РЭНКИН в первые 24 часа от от момента поступления в стационар'
,2439,'02439',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Уменьшение шкалы РЭНКИН не менее чем на 1 балл за время пребывания в стационаре'
,2440,'02440',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Выполнение хирургического вмешательства в соответствии с клиническими рекомендациями (при наличии медицинских показаний и отсутствии медицинских противопоказаний)'
,2441,'02441',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Отсутствие нагноения послеоперационной раны'
,2442,'02442',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Отсутствие пролежней в период госпитализации'
,2443,'02443',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Отсутствие тромбоэмболических осложнений в период госпитализации'
,2444,'02444',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Направление на 2 этап реабилитации (при наличии показаний)'
,2445,'02445',6);

--3 карта по неврологии 23 критерия 2446-2468
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Осмотр врача-невролога не позднее 10 минут от поступления в стационар'
,2446,'02446',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Выполнение КТ головного мозга или МРТ  с описанием не позднее 40 минут от момента поступления в стационар, 12 часов, 24 часа , 48 часов (для выявления развития отека и дислокации головного мозга)'
,2447,'02447',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Определение уровня глюкозы в периферической крови не позднее 20 минут от момента поступления в стационар'
,2448,'02448',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Определение уровня тромбоцитов в периферической крови не позднее 20 минут от момента поступления в стационар'
,2449,'02449',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Определение уровня МНО  в периферической крови не позднее 20 минут от момента поступления в стационар'
,2450,'02450',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Консультация врача-нейрохирурга при подозрении на злокачественный инфаркт  мозжечка'
,2451,'02451',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Выполнены клинические анализы крови и мочи, исследование коагулограммы'
,2452,'02452',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Начато лечение в БИТР или реанимации не позднее 60 минут от поступления в стационар'
,2453,'02453',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Выполнено стандартизированное скрининговое исследование функции глотания не позднее 3 часов от момента поступления в стационар'
,2454,'02454',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Оценка степени нарушения сознания и комы по Шкале комы Глазго и неврологического статуса по шкале  инсульта NIHSS не позднее 3 часов от момента поступления в стационар'
,2455,'02455',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Мониторирование жизненно  важных  функций ( артериального давления, пульса, дыхания, уровня насыщения кислорода крови, диуреза)'
,2456,'02456',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Индивидуальная нутритивная поддержка не позднее 24 часов от момента поступления в стационар с последующей ежедневной коррекцией'
,2457,'02457',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Определение патогенетического варианта ишемического инсульта по TOAST'
,2458,'02458',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Выполнение ультразвукового дуплексного сканирования магистральных артерий головы'
,2459,'02459',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Профилактика повторных сосудистых событий лекарственными препаратами группы антиагреганты при некардиоэмболическом подтипе инсульта или лекарственными препаратами группы антикоагулянты при кардиоэмболическом подтипе инсульта (при отсутствии медицинских противопоказаний)'
,2460,'02460',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Начало медицинской реабилитации не позднее 48 часов от момента поступления в стационар'
,2461,'02461',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Применение Шкалы РЭНКИН в первые 24 часа от момента поступления в стационар'
,2462,'02462',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Уменьшение шкалы РЭНКИН не менее чем на 1 балл за время пребывания в стационаре'
,2463,'02463',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Выполнение хирургического вмешательства в соответствии с клиническими рекомендациями (при наличии медицинских показаний и отсутствии медицинских противопоказаний)'
,2464,'02464',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Отсутствие нагноения послеоперационной раны'
,2465,'02465',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Отсутствие пролежней в период госпитализации'
,2466,'02466',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Отсутствие тромбоэмболических осложнений в период госпитализации'
,2467,'02467',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Направление на 2 этап реабилитации (при наличии показаний)'
,2468,'02468',6);

--Кардиология

-- 1 карта по кардиологии 33 критерия 2469-2501 I20.0, I21-22
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Каналы госпитализации: скорая медицинская помощь (СМП), перевод из ПСО и другие'
,2469,'02469',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Маршрутизация по времени (до ЧКВ) от начала заболевания'
,2470,'02470',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Маршрутизация по времени (до ЧКВ) от момента госпитализации'
,2471,'02471',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Проведение ТЛТ'
,2472,'02472',6);
--insert into vocqualityestimationcrit(name,id,code,kind_id)
--values('Проведение ТЛТ: ПСО'
--,2473,'02473',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Определение уровня маркеров повреждения миокарда: тропонин, МВ фракция креатинфоскокиназы (КФК)'
,2474,'02474',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Метод определения маркера (количественный или качественный)'
,2475,'02475',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Контроль уровня маркера в случае, если первое определение было недостоверным'
,2476,'02476',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Определение уровня общего холестерина, триглицеридови липопротеидов низкой плотности'
,2477,'02477',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Определение исходного уровня активированного частичного тромбопластинового времени (АЧТВ) при терапии гепарином'
,2478,'02478',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Выполнение контроля АЧТВ'
,2479,'02479',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Время контроля АЧТВ'
,2480,'02480',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Частота контроля АЧТВ'
,2481,'02481',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Применение двойной антиагрегантной терапии'
,2482,'02482',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Сроки применяемой и рекомендуемой двойной антиагрегантной терапии'
,2483,'02483',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Адекватность поддерживающей дозы двойной антиагрегантной терапии'
,2484,'02484',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Наличие нагрузочной дозы клопидогрела (тикагрелора) и ацетилсалициловой кислоты'
,2485,'02485',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Применение антикоагулянтной терапии'
,2486,'02486',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Сроки антикоагулянтной терапии'
,2487,'02487',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Методика применения антикоагулянтной терапии (внутривенная инфузия)'
,2488,'02488',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Применение шкал (CHA2DS2-VASс, HAS-BLED) при пероральной терапии'
,2489,'02489',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Отсутствие в схеме лечения препаратов с недоказанной эффективностью'
,2490,'02490',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Госпитализация в отделение анестезиологии и реанимации (ОАР)/ блок интенсивной терапии и реанимации (БИТР)'
,2491,'02491',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Время госпитализации в ОАР (БИТР) -непосредственно, минуя приёмное отделение'
,2492,'02492',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Сроки перевода из ОАР (БИТР)'
,2493,'02493',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Выполнение коронароангиографии'
,2494,'02494',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Выполнение реваскуляризации миокарда'
,2495,'02495',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Выполнение эхокардиографии'
,2496,'02496',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Выполнение суточногомониторирования электрокардиограммы (ЭКГ)'
,2497,'02497',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Реабилитация'
,2498,'02498',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Рекомендации'
,2499,'02499',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Шкалы риска (по тактике)'
,2500,'02500',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Протокол/заключение heart-time по тактике'
,2501,'02501',6);

--2 карта 19 критериев 2502-2520
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Наличие развернутого биохимического анализа крови (креатинин, глюкоза, калий, натрий, аспартатаминотрансфераза(АСТ), аланинаминотрансфераза(АЛТ))'
,2502,'02502',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Определение липидного спектра'
,2503,'02503',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Наличие показателей коагулограммы (АЧТВ, протромбиновый индекс, международное нормализованное отношение(МНО))'
,2504,'02504',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Наличие эхокардиографического исследования'
,2505,'02505',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Наличие данных суточногомониторирования ЭКГ'
,2506,'02506',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Наличие дуплексного сканирования брахиоцефальныхартерий при синдроме Морганьи-Адамса-Стокса'
,2507,'02507',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Наличие антиагрегантной терапии при ИБС'
,2508,'02508',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Наличие антикоагулянтной терапии'
,2509,'02509',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Сроки антикоагулянтной терапии'
,2510,'02510',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Методы антикоагулянтной терапии (парентеральный/пероральный)'
,2511,'02511',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Применение шкал (CHA2DS2-VASс, HAS-BLED)'
,2512,'02512',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Назначение и рекомендация гиполипидемической терапии'
,2513,'02513',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Назначение и рекомендация терапии ингибиторами ангиотензинпревращающего фермента (АПФ)/сартанами'
,2514,'02514',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Назначение и рекомендация терапии бета-адреноблокаторами'
,2515,'02515',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Назначение и рекомендация терапии диуретиками'
,2516,'02516',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Назначение и рекомендация терапии нитратами'
,2517,'02517',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Отсутствие в схеме терапии препаратов с недоказанной эффективностью'
,2518,'02518',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Проведение пробы с физической нагрузкой (велоэргометрия, тредмил-тест, стресс-эхокардиография)'
,2519,'02519',6);
insert into vocqualityestimationcrit(name,id,code,kind_id)
values('Выполнение коронароангиографии'
,2520,'02520',6);

--Диагнозы I20.0,I21-22
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.0'),2469);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.0'),2470);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.0'),2471);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.0'),2472);
--insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
--values((select id from vocidc10 where code like 'I20.0'),2473);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.0'),2474);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.0'),2475);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.0'),2476);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.0'),2477);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.0'),2478);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.0'),2479);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.0'),2480);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.0'),2481);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.0'),2482);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.0'),2483);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.0'),2484);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.0'),2485);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.0'),2486);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.0'),2487);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.0'),2488);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.0'),2489);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.0'),2490);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.0'),2491);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.0'),2492);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.0'),2493);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.0'),2494);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.0'),2495);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.0'),2496);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.0'),2497);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.0'),2498);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.0'),2499);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.0'),2500);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.0'),2501);

insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.2'),2469);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.2'),2470);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.2'),2471);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.2'),2472);
--insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
--values((select id from vocidc10 where code like 'I21.2'),2473);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.2'),2474);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.2'),2475);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.2'),2476);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.2'),2477);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.2'),2478);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.2'),2479);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.2'),2480);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.2'),2481);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.2'),2482);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.2'),2483);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.2'),2484);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.2'),2485);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.2'),2486);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.2'),2487);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.2'),2488);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.2'),2489);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.2'),2490);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.2'),2491);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.2'),2492);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.2'),2493);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.2'),2494);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.2'),2495);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.2'),2496);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.2'),2497);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.2'),2498);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.2'),2499);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.2'),2500);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.2'),2501);

insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.0'),2469);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.0'),2470);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.0'),2471);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.0'),2472);
--insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
--values((select id from vocidc10 where code like 'I21.0'),2473);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.0'),2474);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.0'),2475);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.0'),2476);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.0'),2477);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.0'),2478);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.0'),2479);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.0'),2480);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.0'),2481);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.0'),2482);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.0'),2483);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.0'),2484);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.0'),2485);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.0'),2486);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.0'),2487);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.0'),2488);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.0'),2489);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.0'),2490);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.0'),2491);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.0'),2492);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.0'),2493);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.0'),2494);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.0'),2495);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.0'),2496);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.0'),2497);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.0'),2498);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.0'),2499);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.0'),2500);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.0'),2501);


insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.4'),2469);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.4'),2470);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.4'),2471);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.4'),2472);
--insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
--values((select id from vocidc10 where code like 'I21.4'),2473);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.4'),2474);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.4'),2475);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.4'),2476);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.4'),2477);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.4'),2478);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.4'),2479);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.4'),2480);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.4'),2481);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.4'),2482);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.4'),2483);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.4'),2484);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.4'),2485);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.4'),2486);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.4'),2487);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.4'),2488);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.4'),2489);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.4'),2490);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.4'),2491);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.4'),2492);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.4'),2493);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.4'),2494);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.4'),2495);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.4'),2496);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.4'),2497);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.4'),2498);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.4'),2499);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.4'),2500);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.4'),2501);


insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.1'),2469);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.1'),2470);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.1'),2471);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.1'),2472);
--insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
--values((select id from vocidc10 where code like 'I21.1'),2473);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.1'),2474);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.1'),2475);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.1'),2476);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.1'),2477);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.1'),2478);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.1'),2479);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.1'),2480);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.1'),2481);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.1'),2482);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.1'),2483);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.1'),2484);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.1'),2485);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.1'),2486);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.1'),2487);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.1'),2488);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.1'),2489);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.1'),2490);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.1'),2491);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.1'),2492);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.1'),2493);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.1'),2494);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.1'),2495);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.1'),2496);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.1'),2497);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.1'),2498);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.1'),2499);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.1'),2500);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.1'),2501);


insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21'),2469);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21'),2470);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21'),2471);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21'),2472);
--insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
--values((select id from vocidc10 where code like 'I21'),2473);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21'),2474);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21'),2475);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21'),2476);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21'),2477);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21'),2478);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21'),2479);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21'),2480);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21'),2481);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21'),2482);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21'),2483);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21'),2484);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21'),2485);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21'),2486);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21'),2487);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21'),2488);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21'),2489);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21'),2490);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21'),2491);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21'),2492);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21'),2493);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21'),2494);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21'),2495);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21'),2496);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21'),2497);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21'),2498);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21'),2499);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21'),2500);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21'),2501);


insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.9'),2469);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.9'),2470);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.9'),2471);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.9'),2472);
--insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
--values((select id from vocidc10 where code like 'I21.9'),2473);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.9'),2474);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.9'),2475);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.9'),2476);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.9'),2477);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.9'),2478);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.9'),2479);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.9'),2480);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.9'),2481);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.9'),2482);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.9'),2483);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.9'),2484);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.9'),2485);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.9'),2486);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.9'),2487);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.9'),2488);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.9'),2489);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.9'),2490);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.9'),2491);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.9'),2492);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.9'),2493);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.9'),2494);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.9'),2495);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.9'),2496);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.9'),2497);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.9'),2498);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.9'),2499);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.9'),2500);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.9'),2501);


insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.3'),2469);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.3'),2470);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.3'),2471);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.3'),2472);
--insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
--values((select id from vocidc10 where code like 'I21.3'),2473);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.3'),2474);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.3'),2475);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.3'),2476);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.3'),2477);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.3'),2478);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.3'),2479);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.3'),2480);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.3'),2481);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.3'),2482);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.3'),2483);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.3'),2484);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.3'),2485);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.3'),2486);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.3'),2487);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.3'),2488);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.3'),2489);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.3'),2490);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.3'),2491);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.3'),2492);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.3'),2493);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.3'),2494);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.3'),2495);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.3'),2496);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.3'),2497);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.3'),2498);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.3'),2499);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.3'),2500);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I21.3'),2501);


insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I22'),2469);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I22'),2470);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I22'),2471);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I22'),2472);
--insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
--values((select id from vocidc10 where code like 'I22'),2473);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I22'),2474);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I22'),2475);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I22'),2476);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I22'),2477);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I22'),2478);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I22'),2479);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I22'),2480);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I22'),2481);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I22'),2482);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I22'),2483);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I22'),2484);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I22'),2485);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I22'),2486);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I22'),2487);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I22'),2488);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I22'),2489);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I22'),2490);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I22'),2491);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I22'),2492);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I22'),2493);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I22'),2494);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I22'),2495);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I22'),2496);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I22'),2497);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I22'),2498);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I22'),2499);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I22'),2500);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I22'),2501);


insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.8'),2502);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.8'),2503);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.8'),2504);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.8'),2505);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.8'),2506);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.8'),2507);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.8'),2508);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.8'),2509);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.8'),2510);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.8'),2511);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.8'),2512);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.8'),2513);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.8'),2514);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.8'),2515);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.8'),2516);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.8'),2517);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.8'),2518);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.8'),2519);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I20.8'),2520);


insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I25'),2502);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I25'),2503);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I25'),2504);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I25'),2505);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I25'),2506);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I25'),2507);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I25'),2508);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I25'),2509);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I25'),2510);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I25'),2511);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I25'),2512);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I25'),2513);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I25'),2514);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I25'),2515);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I25'),2516);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I25'),2517);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I25'),2518);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I25'),2519);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I25'),2520);


insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I50'),2502);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I50'),2503);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I50'),2504);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I50'),2505);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I50'),2506);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I50'),2507);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I50'),2508);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I50'),2509);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I50'),2510);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I50'),2511);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I50'),2512);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I50'),2513);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I50'),2514);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I50'),2515);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I50'),2516);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I50'),2517);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I50'),2518);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I50'),2519);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I50'),2520);


insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47'),2502);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47'),2503);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47'),2504);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47'),2505);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47'),2506);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47'),2507);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47'),2508);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47'),2509);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47'),2510);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47'),2511);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47'),2512);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47'),2513);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47'),2514);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47'),2515);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47'),2516);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47'),2517);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47'),2518);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47'),2519);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47'),2520);


insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.0'),2502);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.0'),2503);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.0'),2504);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.0'),2505);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.0'),2506);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.0'),2507);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.0'),2508);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.0'),2509);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.0'),2510);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.0'),2511);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.0'),2512);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.0'),2513);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.0'),2514);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.0'),2515);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.0'),2516);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.0'),2517);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.0'),2518);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.0'),2519);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.0'),2520);


insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.1'),2502);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.1'),2503);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.1'),2504);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.1'),2505);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.1'),2506);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.1'),2507);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.1'),2508);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.1'),2509);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.1'),2510);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.1'),2511);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.1'),2512);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.1'),2513);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.1'),2514);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.1'),2515);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.1'),2516);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.1'),2517);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.1'),2518);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.1'),2519);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.1'),2520);


insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.2'),2502);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.2'),2503);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.2'),2504);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.2'),2505);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.2'),2506);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.2'),2507);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.2'),2508);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.2'),2509);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.2'),2510);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.2'),2511);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.2'),2512);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.2'),2513);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.2'),2514);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.2'),2515);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.2'),2516);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.2'),2517);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.2'),2518);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.2'),2519);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.2'),2520);


insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.9'),2502);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.9'),2503);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.9'),2504);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.9'),2505);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.9'),2506);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.9'),2507);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.9'),2508);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.9'),2509);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.9'),2510);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.9'),2511);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.9'),2512);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.9'),2513);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.9'),2514);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.9'),2515);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.9'),2516);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.9'),2517);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.9'),2518);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.9'),2519);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I47.9'),2520);


insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49'),2502);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49'),2503);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49'),2504);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49'),2505);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49'),2506);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49'),2507);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49'),2508);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49'),2509);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49'),2510);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49'),2511);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49'),2512);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49'),2513);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49'),2514);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49'),2515);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49'),2516);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49'),2517);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49'),2518);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49'),2519);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49'),2520);


insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.0'),2502);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.0'),2503);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.0'),2504);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.0'),2505);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.0'),2506);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.0'),2507);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.0'),2508);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.0'),2509);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.0'),2510);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.0'),2511);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.0'),2512);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.0'),2513);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.0'),2514);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.0'),2515);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.0'),2516);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.0'),2517);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.0'),2518);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.0'),2519);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.0'),2520);


insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.1'),2502);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.1'),2503);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.1'),2504);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.1'),2505);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.1'),2506);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.1'),2507);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.1'),2508);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.1'),2509);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.1'),2510);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.1'),2511);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.1'),2512);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.1'),2513);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.1'),2514);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.1'),2515);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.1'),2516);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.1'),2517);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.1'),2518);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.1'),2519);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.1'),2520);


insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.2'),2502);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.2'),2503);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.2'),2504);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.2'),2505);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.2'),2506);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.2'),2507);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.2'),2508);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.2'),2509);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.2'),2510);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.2'),2511);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.2'),2512);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.2'),2513);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.2'),2514);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.2'),2515);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.2'),2516);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.2'),2517);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.2'),2518);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.2'),2519);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.2'),2520);


insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48'),2502);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48'),2503);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48'),2504);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48'),2505);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48'),2506);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48'),2507);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48'),2508);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48'),2509);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48'),2510);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48'),2511);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48'),2512);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48'),2513);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48'),2514);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48'),2515);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48'),2516);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48'),2517);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48'),2518);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48'),2519);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48'),2520);


insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.3'),2502);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.3'),2503);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.3'),2504);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.3'),2505);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.3'),2506);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.3'),2507);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.3'),2508);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.3'),2509);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.3'),2510);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.3'),2511);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.3'),2512);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.3'),2513);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.3'),2514);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.3'),2515);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.3'),2516);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.3'),2517);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.3'),2518);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.3'),2519);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.3'),2520);


insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.4'),2502);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.4'),2503);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.4'),2504);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.4'),2505);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.4'),2506);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.4'),2507);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.4'),2508);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.4'),2509);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.4'),2510);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.4'),2511);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.4'),2512);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.4'),2513);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.4'),2514);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.4'),2515);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.4'),2516);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.4'),2517);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.4'),2518);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.4'),2519);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.4'),2520);


insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.5'),2502);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.5'),2503);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.5'),2504);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.5'),2505);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.5'),2506);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.5'),2507);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.5'),2508);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.5'),2509);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.5'),2510);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.5'),2511);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.5'),2512);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.5'),2513);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.5'),2514);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.5'),2515);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.5'),2516);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.5'),2517);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.5'),2518);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.5'),2519);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.5'),2520);


insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.8'),2502);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.8'),2503);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.8'),2504);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.8'),2505);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.8'),2506);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.8'),2507);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.8'),2508);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.8'),2509);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.8'),2510);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.8'),2511);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.8'),2512);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.8'),2513);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.8'),2514);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.8'),2515);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.8'),2516);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.8'),2517);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.8'),2518);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.8'),2519);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.8'),2520);


insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.9'),2502);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.9'),2503);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.9'),2504);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.9'),2505);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.9'),2506);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.9'),2507);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.9'),2508);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.9'),2509);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.9'),2510);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.9'),2511);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.9'),2512);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.9'),2513);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.9'),2514);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.9'),2515);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.9'),2516);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.9'),2517);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.9'),2518);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.9'),2519);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I49.9'),2520);


insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.3'),2502);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.3'),2503);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.3'),2504);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.3'),2505);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.3'),2506);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.3'),2507);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.3'),2508);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.3'),2509);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.3'),2510);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.3'),2511);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.3'),2512);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.3'),2513);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.3'),2514);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.3'),2515);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.3'),2516);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.3'),2517);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.3'),2518);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.3'),2519);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.3'),2520);


insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.4'),2502);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.4'),2503);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.4'),2504);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.4'),2505);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.4'),2506);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.4'),2507);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.4'),2508);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.4'),2509);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.4'),2510);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.4'),2511);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.4'),2512);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.4'),2513);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.4'),2514);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.4'),2515);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.4'),2516);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.4'),2517);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.4'),2518);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.4'),2519);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.4'),2520);


insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.9'),2502);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.9'),2503);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.9'),2504);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.9'),2505);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.9'),2506);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.9'),2507);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.9'),2508);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.9'),2509);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.9'),2510);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.9'),2511);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.9'),2512);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.9'),2513);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.9'),2514);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.9'),2515);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.9'),2516);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.9'),2517);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.9'),2518);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.9'),2519);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.9'),2520);


insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.2'),2502);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.2'),2503);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.2'),2504);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.2'),2505);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.2'),2506);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.2'),2507);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.2'),2508);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.2'),2509);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.2'),2510);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.2'),2511);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.2'),2512);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.2'),2513);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.2'),2514);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.2'),2515);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.2'),2516);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.2'),2517);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.2'),2518);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.2'),2519);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.2'),2520);


insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.0'),2502);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.0'),2503);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.0'),2504);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.0'),2505);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.0'),2506);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.0'),2507);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.0'),2508);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.0'),2509);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.0'),2510);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.0'),2511);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.0'),2512);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.0'),2513);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.0'),2514);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.0'),2515);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.0'),2516);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.0'),2517);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.0'),2518);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.0'),2519);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.0'),2520);


insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.1'),2502);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.1'),2503);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.1'),2504);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.1'),2505);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.1'),2506);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.1'),2507);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.1'),2508);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.1'),2509);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.1'),2510);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.1'),2511);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.1'),2512);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.1'),2513);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.1'),2514);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.1'),2515);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.1'),2516);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.1'),2517);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.1'),2518);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.1'),2519);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I48.1'),2520);


insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2502,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2502,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2503,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2503,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2504,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2504,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2505,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2505,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2506,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2506,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2507,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2507,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2508,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2508,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2509,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2509,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Соблюдены','2510',1,2510,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Не соблюдены','2510',0,2510,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('парентеральный','2511',1,2511,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('пероральный','2511',0,2511,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2512,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2512,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2513,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2513,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2514,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2514,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2515,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2515,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2516,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2516,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2517,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2517,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2518,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2518,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2519,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2519,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2520,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2520,false);

insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('СМП','2469',1,2469,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('ПСО','2470',1,2469,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('другое','2480',1,2469,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('>60','2481',1,2470,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('<60','2482',0,2470,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Другое (отсрочено)','2483',0,2470,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2471,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2471,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2472,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',1,2472,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('По показаниям','KMP',1,2472,false);
--insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
--values('Да','KMP',1,2473,false);
--insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
--values('Нет','KMP',0,2473,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2474,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2474,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Количественный','2475',1,2475,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Качественный','2475',1,2475,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2476,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2476,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2477,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2477,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2478,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2478,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2479,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2479,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2480,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2480,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Соблюдено','2481',1,2481,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Не соблюдено','2481',0,2481,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2482,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2482,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Соблюдено','2483',1,2483,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Не соблюдено','2483',0,2483,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2484,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2484,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2485,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2485,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2486,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2486,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Соблюдено','2487',1,2487,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Не соблюдено','2487',0,2487,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2488,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2488,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2489,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2489,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2490,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2490,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2491,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2491,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Соблюдено','2492',1,2492,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Не соблюдено','2492',0,2492,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Соблюдено','2493',1,2493,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Не соблюдено','2493',0,2493,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2494,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2494,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2495,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2495,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2496,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2496,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2497,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2497,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2498,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2498,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2499,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2499,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2500,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2500,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2501,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2501,false);

--Диагнозы для 2 карта по неврологии 21 критерий 2425-2445
--при злокачественном инфаркте мозжечка(I63, при сопутствующем I66.3)
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2425);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2426);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2427);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2428);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2429);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2430);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2431);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2432);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2433);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2434);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2435);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2436);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2437);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2438);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2439);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2440);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2441);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2442);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2443);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2444);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2445);

insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.3'),2425);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.3'),2426);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.3'),2427);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.3'),2428);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.3'),2429);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.3'),2430);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.3'),2431);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.3'),2432);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.3'),2433);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.3'),2434);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.3'),2435);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.3'),2436);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.3'),2437);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.3'),2438);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.3'),2439);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.3'),2440);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.3'),2441);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.3'),2442);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.3'),2443);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.3'),2444);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.3'),2445);


insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2425,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2425,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2426,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2426,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2427,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2427,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2428,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2428,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2429,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2429,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2430,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2430,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2431,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2431,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2432,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2432,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2433,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2433,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2434,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2434,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2435,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2435,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2436,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2436,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2437,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2437,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2438,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2438,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2439,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2439,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2440,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2440,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2441,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',1,2441,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2442,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2442,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2443,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2443,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2444,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2444,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2445,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2445,false);

--Диагнозы 3 карта по неврологии 23 критерия 2446-2468
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2446);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2447);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2448);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2449);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2450);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2451);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2452);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2453);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2454);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2455);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2456);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2457);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2458);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2459);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2460);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2461);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2462);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2463);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2464);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2465);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2466);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2467);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63'),2468);

insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.0'),2446);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.0'),2447);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.0'),2448);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.0'),2449);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.0'),2450);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.0'),2451);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.0'),2452);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.0'),2453);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.0'),2454);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.0'),2455);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.0'),2456);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.0'),2457);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.0'),2458);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.0'),2459);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.0'),2460);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.0'),2461);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.0'),2462);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.0'),2463);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.0'),2464);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.0'),2465);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.0'),2466);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.0'),2467);
insert into vocqualityestimationcrit_diagnosis(isconcomitant,vocidc10_id,vqecrit_id)
values(true,(select id from vocidc10 where code like 'I66.0'),2468);


insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2446,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2446,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2447,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2447,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2448,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2448,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2449,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2449,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2450,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2450,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2451,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2451,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2452,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2452,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2453,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2453,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2454,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2454,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2455,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2455,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2456,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2456,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2457,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2457,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2458,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2458,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2459,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2459,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2460,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2460,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2461,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2461,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2462,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2462,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2463,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2463,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2464,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2464,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2465,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2465,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2466,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2466,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2467,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2467,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Да','KMP',1,2468,false);
insert into vocqualityestimationmark(name,code,mark,criterion_id,isneedcomment)
values('Нет','KMP',0,2468,false);
-------------------------------

insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2425);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2426);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2427);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2428);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2429);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2430);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2431);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2432);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2433);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2434);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2435);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2436);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2437);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2438);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2439);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2440);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2441);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2442);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2443);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2444);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2445);

insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2425);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2426);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2427);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2428);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2429);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2430);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2431);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2432);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2433);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2434);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2435);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2436);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2437);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2438);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2439);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2440);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2441);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2442);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2443);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2444);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2445);

insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2425);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2426);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2427);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2428);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2429);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2430);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2431);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2432);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2433);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2434);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2435);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2436);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2437);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2438);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2439);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2440);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2441);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2442);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2443);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2444);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2445);


insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2425);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2426);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2427);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2428);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2429);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2430);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2431);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2432);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2433);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2434);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2435);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2436);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2437);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2438);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2439);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2440);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2441);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2442);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2443);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2444);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2445);

insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2425);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2426);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2427);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2428);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2429);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2430);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2431);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2432);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2433);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2434);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2435);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2436);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2437);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2438);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2439);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2440);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2441);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2442);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2443);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2444);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2445);

insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2425);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2426);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2427);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2428);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2429);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2430);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2431);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2432);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2433);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2434);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2435);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2436);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2437);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2438);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2439);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2440);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2441);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2442);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2443);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2444);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2445);

insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2425);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2426);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2427);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2428);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2429);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2430);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2431);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2432);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2433);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2434);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2435);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2436);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2437);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2438);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2439);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2440);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2441);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2442);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2443);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2444);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2445);

insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2425);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2426);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2427);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2428);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2429);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2430);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2431);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2432);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2433);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2434);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2435);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2436);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2437);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2438);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2439);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2440);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2441);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2442);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2443);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2444);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2445);

insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2425);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2426);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2427);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2428);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2429);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2430);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2431);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2432);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2433);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2434);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2435);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2436);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2437);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2438);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2439);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2440);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2441);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2442);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2443);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2444);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2445);

------------
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2446);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2447);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2448);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2449);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2450);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2451);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2452);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2453);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2454);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2455);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2456);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2457);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2458);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2459);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2460);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2461);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2462);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2463);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2464);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2465);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2466);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2467);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.0'),2468);

insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2446);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2447);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2448);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2449);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2450);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2451);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2452);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2453);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2454);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2455);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2456);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2457);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2458);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2459);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2460);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2461);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2462);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2463);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2464);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2465);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2466);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2467);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.1'),2468);

insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2446);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2447);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2448);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2449);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2450);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2451);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2452);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2453);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2454);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2455);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2456);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2457);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2458);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2459);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2460);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2461);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2462);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2463);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2464);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2465);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2466);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2467);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.2'),2468);

insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2446);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2447);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2448);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2449);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2450);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2451);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2452);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2453);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2454);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2455);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2456);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2457);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2458);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2459);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2460);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2461);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2462);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2463);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2464);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2465);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2466);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2467);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.3'),2468);

insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2446);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2447);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2448);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2449);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2450);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2451);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2452);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2453);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2454);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2455);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2456);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2457);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2458);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2459);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2460);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2461);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2462);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2463);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2464);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2465);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2466);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2467);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.4'),2468);

insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2446);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2447);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2448);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2449);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2450);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2451);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2452);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2453);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2454);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2455);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2456);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2457);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2458);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2459);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2460);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2461);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2462);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2463);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2464);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2465);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2466);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2467);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.5'),2468);

insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2446);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2447);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2448);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2449);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2450);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2451);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2452);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2453);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2454);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2455);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2456);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2457);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2458);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2459);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2460);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2461);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2462);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2463);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2464);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2465);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2466);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2467);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.6'),2468);

insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2446);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2447);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2448);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2449);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2450);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2451);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2452);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2453);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2454);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2455);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2456);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2457);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2458);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2459);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2460);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2461);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2462);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2463);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2464);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2465);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2466);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2467);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.8'),2468);

insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2446);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2447);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2448);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2449);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2450);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2451);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2452);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2453);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2454);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2455);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2456);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2457);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2458);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2459);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2460);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2461);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2462);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2463);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2464);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2465);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2466);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2467);
insert into vocqualityestimationcrit_diagnosis(vocidc10_id,vqecrit_id)
values((select id from vocidc10 where code like 'I63.9'),2468);