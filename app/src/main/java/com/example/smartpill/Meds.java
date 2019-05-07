package com.example.smartpill;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "meds_table")
public class Meds {

    @PrimaryKey(autoGenerate = true)
    public int sid;
    @NonNull
    @ColumnInfo(name = "boxNo")
    private String boxNo;
    @NonNull
    @ColumnInfo(name = "medicine")
    private String medicine;

    public Meds(@NonNull String boxNo, @NonNull String medicine) {
        this.medicine = medicine;
        this.boxNo = boxNo;
    }

    public int getSID(){return this.sid;}

    public String getMedicine(){return this.medicine;}

    public String getBoxNo() { return this.boxNo; }

    @Ignore
    public Meds(int sid, @NonNull String boxNo, @NonNull String medicine) {
        this.sid = sid;
        this.medicine = medicine;
        this.boxNo = boxNo;
    }

    public void setId(int sid) {
        this.sid = sid;
    }

}