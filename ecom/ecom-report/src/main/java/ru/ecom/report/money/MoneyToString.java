package ru.ecom.report.money;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;

/**
 *  Класс money2str - Приведение денежного выражения к строковому
 *  25/06/2003 Алексей Федоров
 *
 *  Разрешается использование без всех известных мне ограничений
 *  Выражаю благодарность неизвестному программисту,
 *  описавшему алгоритм этой задачи
 *
 * --------------------------------
 * ECOM
 *   2006.09.29
 *   // 1. Добавлена правильная работа с копейками 0 копеек, 11 копеек - 19 копеек
 *   // 2. Инициализация по-умолчанию рублями с копейками
 *   // 3. ThreadSafe
 */

public class MoneyToString {

    public MoneyToString() {
//        <RUR CurrID="810" CurrName="Российские рубли"
//             RubOneUnit="рубль" RubTwoUnit="рубля" RubFiveUnit="рублей" RubSex="M"
//             KopOneUnit="копейка" KopTwoUnit="копейки" KopFiveUnit="копеек" KopSex="F"
//        />
        rubOneUnit = "рубль";
        rubTwoUnit = "рубля";
        rubFiveUnit = "рублей";
        kopOneUnit = "копейка";
        kopTwoUnit = "копейки";
        kopFiveUnit = "копеек";
        rubSex = "M";
        kopSex = "F";
    }

    public MoneyToString(String aISOstr) {
        org.w3c.dom.Document xmlDoc = null;

        javax.xml.parsers.DocumentBuilderFactory docFactory = javax.xml.parsers.DocumentBuilderFactory.newInstance();

        try {
            javax.xml.parsers.DocumentBuilder xmlDocBuilder = docFactory.newDocumentBuilder();
            xmlDoc = xmlDocBuilder.parse(new java.io.File("currlist.xml"));
        } catch (org.xml.sax.SAXException sxe) {
            Exception x = sxe;
            if (sxe.getException() != null)
                x = sxe.getException();
            x.printStackTrace();
        } catch (ParserConfigurationException | IOException pce) {
            pce.printStackTrace();
        }
        org.w3c.dom.Element theISOElement = (org.w3c.dom.Element) (xmlDoc.getElementsByTagName(aISOstr)).item(0);

        rubOneUnit = theISOElement.getAttribute("RubOneUnit");
        rubTwoUnit = theISOElement.getAttribute("RubTwoUnit");
        rubFiveUnit = theISOElement.getAttribute("RubFiveUnit");
        kopOneUnit = theISOElement.getAttribute("KopOneUnit");
        kopTwoUnit = theISOElement.getAttribute("KopTwoUnit");
        kopFiveUnit = theISOElement.getAttribute("KopFiveUnit");
        rubSex = theISOElement.getAttribute("RubSex");
        kopSex = theISOElement.getAttribute("KopSex");
    }

    public String transform(BigDecimal aMoney) {
        return transform(aMoney !=null ? aMoney.doubleValue():0) ;
    }

    public String transform(Double aMoney) {
        if(aMoney ==null) aMoney =0.0 ;
        StringBuilder money2str = new StringBuilder();
        int triadNum = 0;
        int theTriad;

        int intPart = aMoney.intValue();
        int fractPart = (int) Math.round((aMoney.doubleValue() - intPart) * 100);
        if (intPart == 0) money2str.append("Ноль ");
        do {
            theTriad = intPart % 1000;
            money2str.insert(0, triad2Word(theTriad, triadNum, rubSex));
            if (triadNum == 0) {
                int range10 = (theTriad % 100) / 10;
                int range = theTriad % 10;
                if (range10 == 1) {
                    money2str = money2str.append(rubFiveUnit);
                } else {
                    switch (range) {
                        case 1:
                            money2str = money2str.append(rubOneUnit);
                            break;
                        case 2:
                        case 3:
                        case 4:
                            money2str = money2str.append(rubTwoUnit);
                            break;
                        default:
                            money2str = money2str.append(rubFiveUnit);
                            break;
                    }
                }
            }
            intPart = intPart / 1000;
            triadNum++;
        } while (theTriad != 0);

        money2str = money2str.append(" ").append(triad2Word(fractPart, 0, kopSex));
        if (fractPart == 0) {
            money2str.append("ноль ");
        }
        if (fractPart > 10 && fractPart < 20) {
            money2str = money2str.append(kopFiveUnit);
        } else {
            if ((fractPart % 10) == 1) {
                money2str = money2str.append(kopOneUnit);
            } else {
                switch (fractPart % 10) {
                    case 1:
                        money2str = money2str.append(kopOneUnit);
                        break;
                    case 2:
                    case 3:
                    case 4:
                        money2str = money2str.append(kopTwoUnit);
                        break;
                    default:
                        money2str = money2str.append(kopFiveUnit);
                        break;
                }
            }
        }
        money2str.setCharAt(0, Character.toUpperCase(money2str.charAt(0)));
        return money2str.toString();
    }

    private static  String triad2Word(int triad, int triadNum, String sex) {
        StringBuilder triadWord = new StringBuilder(28); // девятьсот восемьдесят четыре - 28 достаточно ?

        if (triad == 0) {
            return triadWord.toString();
        }

        int range = triad / 100;
        switch (range) {
            case 1:
                triadWord = triadWord.append("сто ");
                break;
            case 2:
                triadWord = triadWord.append("двести ");
                break;
            case 3:
                triadWord = triadWord.append("триста ");
                break;
            case 4:
                triadWord = triadWord.append("четыреста ");
                break;
            case 5:
                triadWord = triadWord.append("пятьсот ");
                break;
            case 6:
                triadWord = triadWord.append("шестьсот ");
                break;
            case 7:
                triadWord = triadWord.append("семьсот ");
                break;
            case 8:
                triadWord = triadWord.append("восемьсот ");
                break;
            case 9:
                triadWord = triadWord.append("девятьсот ");
                break;
            default:
                break;
        }

        range = (triad % 100) / 10;
        switch (range) {
            case 2:
                triadWord = triadWord.append("двадцать ");
                break;
            case 3:
                triadWord = triadWord.append("тридцать ");
                break;
            case 4:
                triadWord = triadWord.append("сорок ");
                break;
            case 5:
                triadWord = triadWord.append("пятьдесят ");
                break;
            case 6:
                triadWord = triadWord.append("шестьдесят ");
                break;
            case 7:
                triadWord = triadWord.append("семьдесят ");
                break;
            case 8:
                triadWord = triadWord.append("восемьдесят ");
                break;
            case 9:
                triadWord = triadWord.append("девяносто ");
                break;
            default:
                break;
        }

        int range10 = range;
        range = triad % 10;
        if (range10 == 1) {
            switch (range) {
                case 0:
                    triadWord = triadWord.append("десять ");
                    break;
                case 1:
                    triadWord = triadWord.append("одиннадцать ");
                    break;
                case 2:
                    triadWord = triadWord.append("двенадцать ");
                    break;
                case 3:
                    triadWord = triadWord.append("тринадцать ");
                    break;
                case 4:
                    triadWord = triadWord.append("четырнадцать ");
                    break;
                case 5:
                    triadWord = triadWord.append("пятнадцать ");
                    break;
                case 6:
                    triadWord = triadWord.append("шестнадцать ");
                    break;
                case 7:
                    triadWord = triadWord.append("семнадцать ");
                    break;
                case 8:
                    triadWord = triadWord.append("восемнадцать ");
                    break;
                case 9:
                    triadWord = triadWord.append("девятнадцать ");
                    break;
            }
        } else {
            switch (range) {

                case 1:
                    if (triadNum == 1)
                        triadWord = triadWord.append("одна ");
                    else if (sex.equals("M")) triadWord = triadWord.append("один ");
                    if (sex.equals("F")) triadWord = triadWord.append("одна ");
                    break;
                case 2:
                    if (triadNum == 1)
                        triadWord = triadWord.append("две ");
                    else if (sex.equals("M")) triadWord = triadWord.append("два ");
                    if (sex.equals("F")) triadWord = triadWord.append("две ");
                    break;
                case 3:
                    triadWord = triadWord.append("три ");
                    break;
                case 4:
                    triadWord = triadWord.append("четыре ");
                    break;
                case 5:
                    triadWord = triadWord.append("пять ");
                    break;
                case 6:
                    triadWord = triadWord.append("шесть ");
                    break;
                case 7:
                    triadWord = triadWord.append("семь ");
                    break;
                case 8:
                    triadWord = triadWord.append("восемь ");
                    break;
                case 9:
                    triadWord = triadWord.append("девять ");
                    break;
                default:
                    break;
            }
        }

        switch (triadNum) {
            case 1:
                if (range10 == 1)
                    triadWord = triadWord.append("тысяч ");
                else {
                    switch (range) {
                        case 1:
                            triadWord = triadWord.append("тысяча ");
                            break;
                        case 2:
                        case 3:
                        case 4:
                            triadWord = triadWord.append("тысячи ");
                            break;
                        default:
                            triadWord = triadWord.append("тысяч ");
                            break;
                    }
                }
                break;
            case 2:
                if (range10 == 1)
                    triadWord = triadWord.append("миллионов ");
                else {
                    switch (range) {
                        case 1:
                            triadWord = triadWord.append("миллион ");
                            break;
                        case 2:
                        case 3:
                        case 4:
                            triadWord = triadWord.append("миллиона ");
                            break;
                        default:
                            triadWord = triadWord.append("миллионов ");
                            break;
                    }
                }
                break;
            case 3:
                if (range10 == 1)
                    triadWord = triadWord.append("миллиардов ");
                else {
                    switch (range) {
                        case 1:
                            triadWord = triadWord.append("миллиард ");
                            break;
                        case 2:
                        case 3:
                        case 4:
                            triadWord = triadWord.append("миллиарда ");
                            break;
                        default:
                            triadWord = triadWord.append("миллиардов ");
                            break;
                    }
                }
                break;
            case 4:
                if (range10 == 1)
                    triadWord = triadWord.append("триллионов ");
                else {
                    switch (range) {
                        case 1:
                            triadWord = triadWord.append("триллион ");
                            break;
                        case 2:
                        case 3:
                        case 4:
                            triadWord = triadWord.append("триллиона ");
                            break;
                        default:
                            triadWord = triadWord.append("триллионов ");
                            break;
                    }
                }
                break;
            default:
                break;
        }
        return triadWord.toString();
    }

    private final String rubOneUnit, rubTwoUnit, rubFiveUnit, rubSex,
            kopOneUnit, kopTwoUnit, kopFiveUnit, kopSex;

}
