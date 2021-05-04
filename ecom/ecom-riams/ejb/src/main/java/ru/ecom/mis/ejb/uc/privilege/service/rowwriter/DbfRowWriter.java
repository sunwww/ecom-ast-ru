package ru.ecom.mis.ejb.uc.privilege.service.rowwriter;

import ru.ecom.mis.ejb.uc.privilege.service.ColumnMapping;
import ru.ecom.mis.ejb.uc.privilege.service.IRowWriter;
import ru.nuzmsh.dbf.DbfField;
import ru.nuzmsh.dbf.DbfWriter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DbfRowWriter implements IRowWriter {

    public DbfRowWriter(List<ColumnMapping> aMapping, File aFile, int aRecordCount) {
        mapping = aMapping;
        file = aFile;
        count = aRecordCount;
    }

    public void open() {
        LinkedList<DbfField> fields = new LinkedList<>();
        for (ColumnMapping map : mapping) {
            DbfField f = new DbfField(map.getField(), DbfField.CHAR, map.getLength());
            fields.add(f);
        }
        writer = new DbfWriter(count, fields);
        try {
            writer.open(file);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(List<String> aRow) {
        Iterator<ColumnMapping> it = mapping.iterator();
        HashMap<String, Object> values = new HashMap<>();
        for (String value : aRow) {
            if (it.hasNext()) {
                ColumnMapping map = it.next();
                values.put(map.getField(), value);
            } else {
                break;
            }
        }
        try {
            writer.write(values);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private DbfWriter writer = null;
    private final int count;
    private final List<ColumnMapping> mapping;
    private final File file;
}
