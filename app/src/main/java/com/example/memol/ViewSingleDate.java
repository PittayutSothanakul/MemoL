package com.example.memol;

public class ViewSingleDate {
    private String Image_URL, Memo_Date ;

    public ViewSingleDate(String image_URL, String memo_date ) {
        Image_URL = image_URL;

        Memo_Date = memo_date;

    }

    public ViewSingleDate() {

    }

    public String getImage_url() {
        return Image_URL;
    }


    public String getMemo_Date() {
        return Memo_Date;
    }

    public void setMemo_Date(String memo_Date) { Memo_Date = memo_Date;}




}





