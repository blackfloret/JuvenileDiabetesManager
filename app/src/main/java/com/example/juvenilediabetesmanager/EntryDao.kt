package com.example.juvenilediabetesmanager

import androidx.room.*

@Dao
interface EntryDao {
    @Query("Select * FROM Entry")
    fun getAll(): List<Entry>

    @Query("SELECT * FROM entry WHERE id IN (:entryIds)")
    fun loadAllByIds(entryIds: IntArray): List<Entry>

    @Query("SELECT * FROM entry WHERE time BETWEEN :time1 AND :time2")
    fun loadByTimeRange(time1: String, time2: String): List<Entry>

    @Query("UPDATE entry SET approved = 1 WHERE id = :id")
    fun approveEntry(id: Int)

    @Query("UPDATE entry SET approved = 1 WHERE id IN (:entryIds)")
    fun approveEntry(entryIds: IntArray)

    @Query("UPDATE entry SET approved = 1")
    fun approveAll()

    @Insert
    fun insertAll(vararg entry: Entry)

    @Delete
    fun delete(entry: Entry)

}