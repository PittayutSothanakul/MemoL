package com.example.memol;

public class ViewSingleLedger {
    private String Image_URL,Ledger_Name, Ledger_Date , Ledger_Time , Ledger_Location ,Ledger_Price, Ledger_Description;

    public ViewSingleLedger(String image_URL, String memo_name, String memo_date ,String ledgerPrice, String memo_time , String memo_location , String memo_description) {
        Image_URL = image_URL;
        Ledger_Name = memo_name;
        Ledger_Date = memo_date;
        Ledger_Time = memo_time;
        Ledger_Price = ledgerPrice;
        Ledger_Location = memo_location;
        Ledger_Date = memo_description;
    }

    public ViewSingleLedger() {

    }

    public String getImage_url() {
        return Image_URL;
    }

    public void setImage_url(String image_url) {
        Image_URL = image_url;
    }

    public String getLedger_Name() {
        return Ledger_Name;
    }

    public void setLedger_Name(String memo_Name) {
        Ledger_Name = memo_Name;
    }

    public String getLedger_Date() {
        return Ledger_Date;
    }

    public void setLedger_Date(String memo_Date) { Ledger_Date = memo_Date;}

    public String getLedger_Time() { return Ledger_Time; }

    public void setLedger_Time(String memo_Time) { Ledger_Time = memo_Time;}

    public String getLedger_Location() { return Ledger_Location; }

    public void setLedger_Location(String memo_Location) { Ledger_Location = memo_Location;}

    public String getLedger_Description() { return Ledger_Description; }

    public void setLedger_Description(String memo_Description) { Ledger_Description = memo_Description;}

    public String getLedger_Price() { return Ledger_Price; }

    public void setLedger_Price(String ledger_Price) { Ledger_Price = ledger_Price;}



}



