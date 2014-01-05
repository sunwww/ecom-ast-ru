package ru.nuzmsh.util.dbf;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;

public class DbfField  {
    /** Строка */
    public static final byte CHAR = 'C';
    /** Число */
    public static final byte NUMERIC = 'N';
    /** Дата */
    public static final byte DATE = 'D';
    /** Boolean */
    public static final byte BOOLEAN = 'L';

    //                                                                1
    //                                     1  2  3  4  5  6  7  8  9  0  1  2  3  4
    private static final byte[] PADDING = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    //                                        1    2   3   4   5   6   7   8
    private static final byte[] EMPTY_DATE = {32, 32, 32, 32, 32, 32, 32, 32};

    private static final byte SPACE = (byte) 0x20;


    public DbfField() {
    }

    /**
     * Создание поля DBF
     *
     * Размер поля: для NUMERIC и CHAR - 10, для DATE - 8,BOOLEAN - 1
     * @param aName название поля
     * @param aType тип поля
     */
    public DbfField(String aName, byte aType) {
        this(aName, aType, aType == DATE ?  8 : (aType == BOOLEAN?1:10), 0);
    }

    /**
     * Создание поля DBF
     *
     * Для NUMERIC - количество знаков после запятой - 0
     * @param aName название поля
     * @param aType тип
     * @param aLength размер поля
     */
    public DbfField(String aName, byte aType, int aLength) {
        this(aName, aType, aLength, 0);
    }

    /**
     * Создание DBF поля
     * @param aName   название поля
     * @param aType   тип поля
     * @param aLength размер поля
     * @param aDecimalLength количество знаков после запятой
     */
    public DbfField(String aName, byte aType, int aLength, int aDecimalLength) {
        theName = aName;
        theType = aType;
        theLength = aLength;
        theDecimalLength = aDecimalLength;
    }

    public boolean load(ByteBuffer aBuf) {
        StringBuilder sb = new StringBuilder();

        byte first = aBuf.get();
        if (first == 0x0d) return false; // конец описания полей

        sb.append((char) first);               // название поля (11)

        boolean isEnd = first == 0;
        byte[] buf = new byte[11] ;
        buf[0] = first ;
        int to = 0 ;
        for (int i = 0; i < 10; i++) {
            byte b = aBuf.get() ;
            char c = (char) b ; //(char) aBuf.get();

            buf[i+1] = b ;
            if (c == 0) {
                isEnd = true;
            } else {
                if (!isEnd) {
                    to = i+1 ;
                    sb.append(c);
                }
            }
        }

        try {
            theName = new String(buf,0, to+1, "Cp866");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace() ;
            theName = sb.toString() ;
        }

        theType = aBuf.get();                   // тип поля (1)

        theFieldOffset = aBuf.getInt();         // смещение поля (4)

        theLength = getUnsignedByte(aBuf.get());                 // длина поля (1)

        theDecimalLength = aBuf.get();          // длина дробной части

        return true;
    }

    private static int getUnsignedByte(byte aByte) {
        int ub = aByte ;
        if(ub<0) {
            return 256 + aByte ;
        } else {
            return aByte ;
        }
    }

    public void storeHeader(ByteBuffer buf, int aFieldOffset) throws UnsupportedEncodingException {

        byte[] fieldName = theName.getBytes("Cp866") ;  // название поля (11)
        for (int i = 0; i < 10; i++) {
            if(i<fieldName.length) {
                buf.put(fieldName[i]);
            } else {
                buf.put((byte) 0x00);
            }
        }
        buf.put((byte) 0x00);

        buf.put(theType);                     // тип поля (1)

        buf.putInt(aFieldOffset);             // смещение поля (4)

        buf.put((byte) theLength);             // длина поля (1)

        buf.put((byte) theDecimalLength);      // длина дробной части

        buf.put(PADDING);                      // padding (14)
    }

    /**
     * Название поля
     */
    public String getName() {
        return theName;
    }

    public void setName(String aName) {
        theName = aName;
    }

    /**
     * Тип поля
     */
    public byte getType() {
        return theType;
    }

    public void setType(byte aType) {
        theType = aType;
    }

    /**
     * Смещение поля от начальной записи
     */
    public int getFieldOffset() {
        return theFieldOffset;
    }

    public void setFieldOffset(int aFieldOffset) {
        theFieldOffset = aFieldOffset;
    }

    /**
     * Длина поля
     */
    public int getLength() {
        return theLength;
    }

    public void setLength(int aLength) {
        theLength = aLength;
    }

    /**
     * Разрядность
     */
    public int getDecimalLength() {
        return theDecimalLength;
    }

    public void setDecimalLength(int aDecimalLength) {
        theDecimalLength = aDecimalLength;
    }

    public String toString() {
        return new StringBuilder()
                .append("name='").append(theName)
                .append("', type=").append((char) theType)
                .append(", fieldOffset=").append(theFieldOffset)
                .append(", length=").append(theLength)
                .append(", decimalLength=").append(theDecimalLength)
                .toString();

    }

    /**
     * Разрядность
     */
    private int theDecimalLength;
    /**
     * Длина поля
     */
    private int theLength;
    /**
     * Смещение поля от начальной записи
     */
    private int theFieldOffset;
    /**
     * Тип поля
     */
    private byte theType;
    /**
     * Название поля
     */
    private String theName;

    public void storeValue(Object aValue, ByteBuffer buf) throws UnsupportedEncodingException {
        switch (theType) {
            case CHAR:
                writeChar(buf, aValue, theLength);
                break;
            case DATE:
                writeDate(buf, aValue);
                break;
            case NUMERIC:
            	writeNumeric(buf, aValue, theLength, theDecimalLength);
            	break;
            case BOOLEAN:
                writeChar(buf, aValue, 1);
                break;
        }
    }


    private static void writeChar(ByteBuffer buf, Object aObject, int aLength) throws UnsupportedEncodingException {
        String str;
        if (aObject != null) {
            if (aObject instanceof String) {
                str = aObject.toString();
            } else {
                str = aObject.toString();
            }
        } else {
            str = "";
        }

        if (str.length() > aLength) {
            buf.put(str.substring(0, aLength).getBytes("Cp866"));
        } else {
            buf.put(str.getBytes("Cp866"));

            int strLength = str.length();
            for (int i = strLength; i < aLength; i++) {
                buf.put(SPACE); // пробел
            }
        }

    }

    private static void writeDate(ByteBuffer buf, Object aObject) {
    	if (aObject != null) {
    		buf.put(FORMAT.format(aObject).getBytes());
    	} else {
    		buf.put(EMPTY_DATE);
    	}
    }


    private static void writeNumeric(ByteBuffer buf, Object aObject, int aLength, int aDecimalLength) {
        //ru.nuzmsh.log.SystemLog.TRACE("aObject = " + aObject);
//        ru.nuzmsh.log.SystemLog.TRACE("aObject.getClass() = " + aObject.getClass());
        String numberString = getNumberString(aObject, aDecimalLength) ;
        if (numberString!=null) {
            String str = numberString ;
            int strLength = str.length();
            if (strLength > aLength) {
                buf.put(str.substring(0, aLength).getBytes());
                //ru.nuzmsh.log.SystemLog.TRACE("1");
            } else {
                //ru.nuzmsh.log.SystemLog.TRACE("2");
                int counts = aLength - str.length();
                for (int i = 0; i < counts; i++) {
                    buf.put(SPACE);
                }
                buf.put(str.getBytes());
            }
        } else {
            //ru.nuzmsh.log.SystemLog.TRACE("3");
            for(int i=0; i<aLength; i++) {
                //ru.nuzmsh.log.SystemLog.TRACE("i = " + i);
                buf.put(SPACE) ;
            }
        }
    }

    private static String getNumberString(Object aObject, int aDecimalLength) {
        if(aObject != null) {
            if(aObject instanceof BigDecimal) {
                BigDecimal number = (BigDecimal) aObject ;
                number = number.setScale(2, RoundingMode.HALF_UP) ;
                //ru.nuzmsh.log.SystemLog.TRACE("number = " + number);
                return number.toString() ;
            } else {
                try {
                    Double.parseDouble(aObject.toString()) ;
                    return aObject.toString() ;
                } catch (Exception e) {

                }
            }
        }
        return null ;
    }


    private final static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyyMMdd");

}
