package com.example.gdbaker_sizebook;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Gregory on 2017-02-04.
 * Holds values for clothing measurements
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
     *
     * @param name
     */
    public Record(String name) {
        this.name = name;
        this.date = null;
        this.neck = null;
        this.bust = null;
        this.chest = null;
        this.waist = null;
        this.hip = null;
        this.inseam = null;
        this.comments = "";
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getNeck() {
        return neck;
    }

    public void setNeck(Float neck) {
        this.neck = neck;
    }

    public Float getBust() {
        return bust;
    }

    public void setBust(Float bust) {
        this.bust = bust;
    }

    public Float getChest() {
        return chest;
    }

    public void setChest(Float chest) {
        this.chest = chest;
    }

    public Float getWaist() {
        return waist;
    }

    public void setWaist(Float waist) {
        this.waist = waist;
    }

    public Float getHip() {
        return hip;
    }

    public void setHip(Float hip) {
        this.hip = hip;
    }

    public Float getInseam() {
        return inseam;
    }

    public void setInseam(Float inseam) {
        this.inseam = inseam;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    //strings only

    /**
     * converts float into string then returns
     * @return string of neck
     */
    public String getNeckString(){
        if(this.neck == null){
            return "";
        }
        else{
            return String.valueOf(this.neck);
        }
    }

    /**
     * sets neck Float from string
     * @param entry string to convert
     */
    public void setNeckString(String entry){
        try{
            this.neck = Float.valueOf(entry);
        }
        catch (NumberFormatException e){
            this.neck = null;
        }
    }

    /**
     * converts float into string then returns
     * @return string of bust
     */
    public String getBustString(){
        if(this.bust == null){
            return "";
        }
        else{
            return String.valueOf(this.bust);
        }
    }

    /**
     * set bust Float from string
     * @param entry string to convert
     */
    public void setBustString(String entry){
        try{
            this.bust = Float.valueOf(entry);
        }
        catch (NumberFormatException e){
            this.bust = null;
        }
    }

    /**
     * converts float into string then returns
     * @return string of chest
     */
    public String getChestString(){
        if(this.chest == null){
            return "";
        }
        else{
            return String.valueOf(this.chest);
        }
    }

    /**
     * set chest Float from string
     * @param entry string to convert
     */
    public void setChestString(String entry){
        try{
            this.chest = Float.valueOf(entry);
        }
        catch (NumberFormatException e){
            this.chest = null;
        }
    }

    /**
     * converts float into string then returns
     * @return string of waist
     */
    public String getWaistString(){
        if(this.waist == null){
            return "";
        }
        else{
            return String.valueOf(this.waist);
        }
    }

    /**
     * set waist Float from string
     * @param entry string to convert
     */
    public void setWaistString(String entry){
        try{
            this.waist = Float.valueOf(entry);
        }
        catch (NumberFormatException e){
            this.waist = null;
        }
    }

    /**
     * converts float into string then returns
     * @return
     */
    public String getHipString(){
        if(this.hip == null){
            return "";
        }
        else{
            return String.valueOf(this.hip);
        }
    }

    /**
     * set hip Float from string
     * @param entry string to convert
     */
    public void setHipString(String entry){
        try{
            this.hip = Float.valueOf(entry);
        }
        catch (NumberFormatException e){
            this.hip = null;
        }
    }

    /**
     * converts float into string then returns
     * @return string of inseam
     */
    public String getInseamString(){
        if(this.inseam == null){
            return "";
        }
        else{
            return String.valueOf(this.inseam);
        }
    }

    /**
     * set inseam Float from String
     * @param entry string to convert
     */
    public void setInseamString(String entry){
        try{
            this.inseam = Float.valueOf(entry);
        }
        catch (NumberFormatException e){
            this.inseam = null;
        }
    }

    /**
     * get date as a string
     * @param df SimpleDateFormat, format of the way the string should look
     * @return string of date
     */
    public String getDateString(SimpleDateFormat df){
        if(this.date == null){
            return "";
        }
        else{
            return df.format(this.date);
        }
    }
}