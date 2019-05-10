package com.example.memol;

public class ViewSingleMemo {
    private String Image_URL,Memo_Name, Memo_Date , Memo_Time , Memo_Location , Memo_Description;

    public ViewSingleMemo(String image_URL, String memo_name, String memo_date , String memo_time , String memo_location , String memo_description) {
        Image_URL = image_URL;
        Memo_Name = memo_name;
        Memo_Date = memo_date;
        Memo_Time = memo_time;
        Memo_Location = memo_location;
        Memo_Description = memo_description;
    }

    public ViewSingleMemo() {

    }

    public String getImage_url() {
        return Image_URL;
    }

    public void setImage_url(String image_url) {
        Image_URL = image_url;
    }

    public String getMemo_Name() {
        return Memo_Name;
    }

    public void setMemo_Name(String memo_Name) {
        Memo_Name = memo_Name;
    }

    public String getMemo_Date() {
        return Memo_Date;
    }

    public void setMemo_Date(String memo_Date) { Memo_Date = memo_Date;}

    public String getMemo_Time() { return Memo_Time; }

    public void setMemo_Time(String memo_Time) { Memo_Time = memo_Time;}

    public String getMemo_Location() { return Memo_Location; }

    public void setMemo_Location(String memo_Location) { Memo_Location = memo_Location;}

    public String getMemo_Description() { return Memo_Description; }

    public void setMemo_Description(String memo_Description) { Memo_Description = memo_Description;}


}



