package com.example.juvenilediabetesmanager

import androidx.room.*

@Entity
data class Entry(
    @ColumnInfo(name = "time") val time: String
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
    @ColumnInfo(name = "approved") var approved: Boolean = false

    override fun toString(): String {
        return "[Entry $id: $time, $approved]"
    }
}