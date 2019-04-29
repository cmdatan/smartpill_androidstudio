package com.example.smartpill;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "word_table")
public class Word {

    @PrimaryKey(autoGenerate = true)
    public int sid;
    @NonNull
    @ColumnInfo(name = "medicine")
    private String medicine;
    @NonNull
    @ColumnInfo(name = "quantity")
    private String quantity;
    @NonNull
    @ColumnInfo(name = "hour")
    private Integer hour;
    @NonNull
    @ColumnInfo(name = "minute")
    private Integer minute;
    @NonNull
    @ColumnInfo(name = "monday")
    private Integer mon;
    @NonNull
    @ColumnInfo(name = "tuesday")
    private Integer tue;
    @NonNull
    @ColumnInfo(name = "wednesday")
    private Integer wed;
    @NonNull
    @ColumnInfo(name = "thursday")
    private Integer thurs;
    @NonNull
    @ColumnInfo(name = "friday")
    private Integer fri;
    @NonNull
    @ColumnInfo(name = "saturday")
    private Integer sat;
    @NonNull
    @ColumnInfo(name = "sunday")
    private Integer sun;
    @NonNull
    @ColumnInfo(name = "duration")
    private Integer duration;

    public Word(@NonNull String medicine, @Nullable String quantity, @NonNull Integer duration, @Nullable Integer hour, @Nullable Integer minute,
                @NonNull Integer mon, @NonNull Integer tue, @NonNull Integer wed, @NonNull Integer thurs,
                @NonNull Integer fri, @NonNull Integer sat, @NonNull Integer sun) {
        this.medicine = medicine;
        this.quantity = quantity;
        this.duration = duration;
        this.hour = hour;
        this.minute = minute;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thurs = thurs;
        this.fri = fri;
        this.sat = sat;
        this.sun = sun;
    }
    public int getSID(){return this.sid;}

    public String getMedicine(){return this.medicine;}

    public String getQuantity(){return this.quantity;}

    public Integer getDuration(){return this.duration;}

    public Integer getHour(){return this.hour;}

    public Integer getMinute(){return this.minute;}

    public Integer getMon(){return this.mon;}

    public Integer getTue(){return this.tue;}

    public Integer getWed(){return this.wed;}

    public Integer getThurs(){return this.thurs;}

    public Integer getFri(){return this.fri;}

    public Integer getSat(){return this.sat;}

    public Integer getSun(){return this.sun;}

}