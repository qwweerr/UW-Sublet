package com.baimingze369gmail.uw_sublet.Data;

import java.util.ArrayList;

/**
 * Created by luda on 16-06-17.
 */
public class Room {

    public String roomid;
    private String Uid;
    private String Address;     //328 Regina North
    private String Post_code;   //N2J 0B5
    private String Price;       // 680
    private String Strat_Time; // 2016/09
    private String End_Time;    // 2016/12
    private String Type;//condo apartment house
    private boolean Furnished;
    private boolean Pet_Friendly;
    private boolean Parking_Available;
    private boolean Washroom;

    public boolean isWashroom() {
        return Washroom;
    }

    public void setWashroom(boolean washroom) {
        Washroom = washroom;
    }

    public String getParking_Price() {
        return Parking_Price;
    }

    public void setParking_Price(String parking_Price) {
        Parking_Price = parking_Price;
    }

    private String Parking_Price;
    private String Contact_Name;
    private String Phone_Number;
    private String Email;
     //shared or private
    private String Description; //additional info
    private ArrayList<String> Pics;

    public Room() {
    }





    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPost_code() {
        return Post_code;
    }

    public void setPost_code(String post_code) {
        Post_code = post_code;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getStrat_Time() {
        return Strat_Time;
    }

    public void setStrat_Time(String strat_Time) {
        Strat_Time = strat_Time;
    }

    public String getEnd_Time() {
        return End_Time;
    }

    public void setEnd_Time(String end_Time) {
        End_Time = end_Time;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public boolean isFurnished() {
        return Furnished;
    }

    public void setFurnished(boolean furnished) {
        Furnished = furnished;
    }

    public boolean isPet_Friendly() {
        return Pet_Friendly;
    }

    public void setPet_Friendly(boolean pet_Friendly) {
        Pet_Friendly = pet_Friendly;
    }

    public boolean isParking_Available() {
        return Parking_Available;
    }

    public void setParking_Available(boolean parking_Available) {
        Parking_Available = parking_Available;
    }


    public String getContact_Name() {
        return Contact_Name;
    }

    public void setContact_Name(String contact_Name) {
        Contact_Name = contact_Name;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }

    public void setPhone_Number(String phone_Number) {
        Phone_Number = phone_Number;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }



    public ArrayList<String> getPics() {
        return Pics;
    }

    public void setPics(ArrayList<String> pics) {
        Pics = pics;
    }


    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }








}
