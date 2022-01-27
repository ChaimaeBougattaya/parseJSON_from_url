package com.example.parsejson;

public class Picture {
    //Picture information :: title / author / url
    String title,author,urlPic;

    public Picture(String title, String author, String urlPic) {
        this.title = title;
        this.author = author;
        this.urlPic = urlPic;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrlPic() {
        return urlPic;
    }
}
