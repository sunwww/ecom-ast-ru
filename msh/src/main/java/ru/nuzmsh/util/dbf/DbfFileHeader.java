package ru.nuzmsh.util.dbf;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class DbfFileHeader {

    public static final int SIZE = 32;

    private static final byte[] PADDING
            =
            {
//                  1  2  3  4  5  6  7  8  9 10
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0
//                    1  2  3  4  5  6  7  8  9 20
                    , 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
            };

    /**
     * Версия
     */
    public int getVersion() {
        return theVersion;
    }

    public void setVersion(int aVersion) {
        theVersion = aVersion;
    }

    /**
     * Дата
     */
    public Date getLastUpdate() {
        return theDate;
    }

    public void setLastUpdate(Date aDate) {
        theDate = aDate;
    }

    /**
     * Количество записей
     */
    public long getRecordsCount() {
        return theRecordsCount;
    }

    public void setRecordsCount(long aRecordsCount) {
        theRecordsCount = aRecordsCount;
    }

    /**
     * Длина заголовка (LE)
     */
    public int getHeaderLength() {
        return theHeaderLength;
    }

    /**
     * Длина поля (LE)
     */
    public int getRecordLength() {
        return theRecordLength;
    }

    /**
     * Длина поля (LE)
     */
    private int theRecordLength;
    /**
     * Длина заголовка (LE)
     */
    private int theHeaderLength;
    /**
     * Количество записей
     */
    private long theRecordsCount;
    /**
     * Дата
     */
    private Date theDate;
    /**
     * Версия
     */
    private int theVersion;

    public void load(Collection<DbfField> aFields) {
        // длина записи
        int recordLength = 1;
        for (DbfField field : aFields) {
            recordLength += field.getLength();
        }
        theRecordLength = recordLength;

        // размер заголовка
        theHeaderLength = aFields.size() * 32 + 33;

    }


    public void load(ByteBuffer buf) throws IOException {

        theVersion = buf.get();                  // версия  1
        if (theVersion != 3) {
            throw new RuntimeException("Версия " + theVersion + " не поддерживается");
        }

        // дата
        int year = buf.get();                    // год     (1)
        int month = buf.get();                   // месяц   (1)
        int day = buf.get();                   // число   (1)
        Calendar cal = Calendar.getInstance();
        cal.set(2000 + year, month - 1, day, 0, 0, 0);
        theDate = cal.getTime();

        theRecordsCount = buf.getInt();          // количество записей  (4)

        theHeaderLength = buf.getChar();         // длина заголовка    (2)
        theRecordLength = buf.getChar();         // длина записи       (2)

        //buf.get(buf.position()+19) ;              // выравнивание 20 bytes (20)
    }

    public void store(ByteBuffer buf) {
        buf.put((byte) theVersion);           // версия   (1)

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR) - 2000;
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        buf.put((byte) year);                // год       (1)
        buf.put((byte) month);               // месяц     (1)
        buf.put((byte) day);                 // число     (1)

        buf.putInt((int) theRecordsCount);   // количество записей (4)

        buf.putChar((char) theHeaderLength); // длина заголока (2)
        buf.putChar((char) theRecordLength); // длина записи    (2)

        buf.put(PADDING);                    // padding (20)

    }


    public String toString() {
        return new StringBuilder()
                .append("version=").append(theVersion)
                .append(", date=").append(DATE_FORMAT.format(theDate))
                .append(", recordsCount=").append(theRecordsCount)
                .append(", headerLength=").append(theHeaderLength)
                .append(", recordLength=").append(theRecordLength)
                .toString();

    }

    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");


}
