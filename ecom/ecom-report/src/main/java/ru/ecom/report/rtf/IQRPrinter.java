package ru.ecom.report.rtf;

/**
 * Created by Milamesher on 24.09.2018.
 * Интерфейс с характеристиками qr-кодов
 */
public interface IQRPrinter {
    /**
     * Текст
     * @return
     */
    String getQR_text() ;
    /**
     * Ширина
     * @return
     */
    int getQR_w() ;
    /**
     * Высота
     * @return
     */
    int getQR_h() ;
    /**
     * Строка для замены
     * @return
     */
    String getReplaceString() ;
    /**
     * Расширение
     * @return
     */
    String getExtension();
}
