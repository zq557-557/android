package com.jnu.myapplication;

import java.io.Serializable;

/**
 * Created by jszx on 2019/9/24.
 */

public class Book implements Serializable {
    private String title;
    private int coverRecourseId;

    public Book(String title, int coverRecourseId) {
        this.title = title;
        this.coverRecourseId = coverRecourseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCoverRecourseId() {
        return coverRecourseId;
    }

    public void setCoverRecourseId(int coverRecourseId) {
        this.coverRecourseId = coverRecourseId;
    }
}
