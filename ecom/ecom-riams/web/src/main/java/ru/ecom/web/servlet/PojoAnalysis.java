package ru.ecom.web.servlet;

public class PojoAnalysis {
    String firstname;
    String lastname;
    String middlename;
    String birthday;
    String result;
    String dateDirect;
    String dateResult;
    String numProt;
    String dateProt;
    String lab;

    public PojoAnalysis(String[] array) {
        firstname=array[0];
        lastname=array[1];
        middlename=array[2];
        birthday=array[3];
        result=array[4];
        dateDirect=array[5];
        dateResult=array[6];
        numProt=array[7];
        dateProt=array[8];
        lab=array[9];
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getResult() {
        return result;
    }

    public String getDateDirect() {
        return dateDirect;
    }

    public String getDateResult() {
        return dateResult;
    }

    public String getNumProt() {
        return numProt;
    }

    public String getDateProt() {
        return dateProt;
    }

    public String getLab() {
        return lab;
    }
}
