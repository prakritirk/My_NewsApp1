package com.example.android.my_newsapp1;

/**
 * Created by PRAJBHANDA on 7/17/2018.
 */

public class News {
    /**
     * Author of com.example.android.my_newsapp.News
     */

    private String mAuthor;

    /**
     * Date of com.example.android.my_newsapp.News
     */
    private String mDate;

    /**
     * category of com.example.android.my_newsapp.News
     */
    private String mSection;

    /**
     * Title of com.example.android.my_newsapp.News
     */
    private String mTitle;

    /**
     * Link of com.example.android.my_newsapp.News
     */
    private String mLink;


    /**
     * Create a new com.example.android.report.News object.
     *
     * @param newsDate    is the date of news published
     * @param newsSection is the category of news
     * @param newsTitle   is the Title of news
     * @param newsLink    is the Title of news
     * @param newsAuthor  is the author of news
     */
    public News(String newsSection, String newsTitle, String newsDate, String newsLink, String newsAuthor) {
        mTitle = newsTitle;
        mSection = newsSection;
        mDate = newsDate;
        mLink = newsLink;
        mAuthor = newsAuthor;

    }

    /**
     * Get the date of news
     */
    public String getDate() {

        return mDate;
    }

    public String getLink() {
        return mLink;
    }

    /**
     * Get the magnitude of news
     */
    public String getSection() {
        return mSection;
    }

    public String getTitle() {
        return mTitle;
    }

    /**
     * Get the author of news
     */
    public String getAuthor() {
        return mAuthor;
    }

}
