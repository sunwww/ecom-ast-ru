package ru.nuzmsh.util.dbf;

import java.nio.ByteOrder;
import java.nio.ByteBuffer;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Logger;

/**
 * Файл DBF
 * @author esinev
 * Date: 28.01.2006
 * Time: 18:03:59
 */
public class DbfFile {

    private static final Logger LOG  = Logger.getLogger(DbfFile.class.getName());


    private static ByteBuffer read(InputStream aIn, int aSize) throws IOException {
        byte[] bytes= new byte[aSize] ;
        aIn.read(bytes) ;
        return ByteBuffer.wrap(bytes) ;
    }

    private static ByteBuffer read(InputStream aIn, int aSize, byte aFirstValue) throws IOException {
        byte[] bytes= new byte[aSize] ;
        aIn.read(bytes, 1,aSize-1) ;
        bytes[0] = aFirstValue ;
        return ByteBuffer.wrap(bytes) ;
    }


    public void load(InputStream aIn) throws IOException, ParseException {
        theHeader = new DbfFileHeader();


        //MappedByteBuffer headerBuf = aChannel.map(FileChannel.MapMode.READ_ONLY, 0, 12);
        ByteBuffer headerBuf = read(aIn, 32) ;
        headerBuf.order(ByteOrder.LITTLE_ENDIAN) ;
        theHeader.load(headerBuf);
        headerBuf.clear() ;

        //LOG.trace(new StringBuilder("header = ").append(theHeader.toString()) );

        long offset = 0 ;
        long lastLength = 1 ;
        for(int i=1; i<1000; i++) {

            //LOG.("i = " + i +" i*32="+i*32);
//            ru.nuzmsh.log.SystemLog.TRACE("i = " + i +" i*18="+i*18 +"  size="+aChannel.size());

//            MappedByteBuffer buf = aChannel.map(FileChannel.MapMode.READ_ONLY, i*32, 18);

            int read = aIn.read() ;
            if(read!=0x0d) {
                ByteBuffer buf = read(aIn, 32, (byte)read) ;//aChannel.map(FileChannel.MapMode.READ_ONLY, i*32, 18);

                buf.order(ByteOrder.LITTLE_ENDIAN) ;
                DbfField field = new DbfField();
                if(field.load(buf)) {
                    offset += lastLength ;
                    lastLength = field.getLength() ;
                    if(offset!=field.getFieldOffset()) {
                        //LOG.warn(new StringBuilder("Смещение поля ")
                        //        .append(field.getFieldOffset()).append(" не соответсвует действительному смещению - ")
                        //.append(offset).append(". Будет использоваться смещение: ").append(offset));
                    }
//                ru.nuzmsh.log.SystemLog.TRACE(field.getFieldOffset()+ " offset " + offset);
                    field.setFieldOffset((int)offset);
                    theFields.add(field) ;
                    buf.clear() ;
                    //LOG.trace(new StringBuilder("field = ").append(field.toString()));
                } else {
                    buf.clear() ;
                    break ;
                }
            } else {
                break ;
            }
        }

        // загражаем описание полей
    }

    public Object[] getFields() {
        return theFields.toArray() ;
    }

    private void trace(ByteBuffer aByteBuffer) {
        //ru.nuzmsh.log.SystemLog.TRACE("aByteBuffer = " + aByteBuffer);
        int oldPosition = aByteBuffer.position() ;

        for(int c=0; c<aByteBuffer.limit(); c++) {
            if((c % 4)==0 && c!=0) {
                System.out.print(" | ");
            } else if((c % 8)==0) {
                System.out.print("\n");
            }
            System.out.print(" ");
            System.out.print(aByteBuffer.get());
            c++ ;
        }
        aByteBuffer.position(oldPosition) ;
        System.out.print("\n");
    }

    /**
     * Список полей
     */
    public Collection<DbfField> getDbfFields() {
        return Collections.unmodifiableCollection(theFields) ;
    }

    private ArrayList<DbfField> theFields = new ArrayList<DbfField>();
    private DbfFileHeader theHeader = null;

    public DbfFileHeader getHeader() {
        return theHeader ;
    }

    public long getRecordsCount() {
        return theHeader.getRecordsCount() ;
    }
}
