package com.example.gdbaker_sizebook;

import java.util.Date;

/**
 * Created by Gregory on 2017-02-04.
 */

public class Record {
    private String name;
    private Date date;
    private Float neck;
    private Float bust;
    private Float chest;
    private Float waist;
    private Float hip;
    private Float inseam;
    private String comments;

    /**
     * Creates record with attributes
     * @param name
     * @param date
     * @param neck
     * @param bust
     * @param chest
     * @param waist
     * @param hip
     * @param inseam
     * @param comments
     */
    public Record(String name, Date date, Float neck, Float bust, Float chest, Float waist,
                  Float hip, Float inseam, String comments) {
        this.name = name;
        this.date = date;
        this.neck = neck;
        this.bust = bust;
        this.chest = chest;
        this.waist = waist;
        this.hip = hip;
        this.inseam = inseam;
        this.comments = comments;
    }

}

