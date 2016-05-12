package ru.ecom.expomc.ejb.services.form.importformat;

/**
 * @author ikouzmin 05.04.2007 10:21:53
 */


public class ImportStatistics {
    private long loadedTotal;
    private long addTotal;
    private long updTotal;
    private long syncTotal;
    private long errTotal;

    private long loadedEntity;
    private long addEntity;
    private long updEntity;
    private long syncEntity;
    private long errEntity;

    private long importedRecords;
    private long totalRecords;

    public ImportStatistics() {
        loadedTotal = 0;
        addTotal = 0;
        updTotal = 0;
        syncTotal = 0;
        errTotal = 0;
        loadedEntity = 0;
        addEntity = 0;
        updEntity = 0;
        syncEntity = 0;
        errEntity = 0;
        importedRecords = 0;
        totalRecords = 0;
    }


    public long getLoadedTotal() {
        return loadedTotal;
    }

    public void setLoadedTotal(long loadedTotal) {
        this.loadedTotal = loadedTotal;
    }

    public long getAddTotal() {
        return addTotal;
    }

    public void setAddTotal(long addTotal) {
        this.addTotal = addTotal;
    }

    public long getUpdTotal() {
        return updTotal;
    }

    public void setUpdTotal(long updTotal) {
        this.updTotal = updTotal;
    }

    public long getSyncTotal() {
        return syncTotal;
    }

    public void setSyncTotal(long syncTotal) {
        this.syncTotal = syncTotal;
    }

    public long getErrTotal() {
        return errTotal;
    }

    public void setErrTotal(long errTotal) {
        this.errTotal = errTotal;
    }

    public long getLoadedEntity() {
        return loadedEntity;
    }

    public void setLoadedEntity(long loadedEntity) {
        this.loadedEntity = loadedEntity;
    }

    public long getUpdEntity() {
        return updEntity;
    }

    public void setUpdEntity(long updEntity) {
        this.updEntity = updEntity;
    }

    public long getSyncEntity() {
        return syncEntity;
    }

    public void setSyncEntity(long syncEntity) {
        this.syncEntity = syncEntity;
    }

    public long getErrEntity() {
        return errEntity;
    }

    public void setErrEntity(long errEntity) {
        this.errEntity = errEntity;
    }

    public long getAddEntity() {
        return addEntity;
    }

    public void setAddEntity(long addEntity) {
        this.addEntity = addEntity;
    }

    public long getImportedRecords() {
        return importedRecords;
    }

    public void setImportedRecords(long importedRecords) {
        this.importedRecords = importedRecords;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public long addTotalRecords(long c) {
        totalRecords += c; 
        return totalRecords;
    }

    public void clearEntityCounters() {
        loadedEntity = 0;
        addEntity = 0;
        updEntity = 0;
        syncEntity = 0;
        errEntity = 0;
    }

    public void incLoadedEntity() {
        loadedEntity++;
    }

    public void incUpdEntity() {
        updEntity++;
    }

    public void incSyncEntity() {
        syncEntity++;
    }

    public void incAddEntity() {
        addEntity++;
    }

    public void incErrEntity() {
        errEntity++;
    }

    public void flushEntityCounters(long i) {
        loadedTotal += loadedEntity;
        addTotal    += addEntity;
        updTotal    += updEntity;
        syncTotal   += syncEntity;
        errTotal    += errEntity;

        importedRecords += i;
    }
}
