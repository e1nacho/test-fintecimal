package com.test_fintecimal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query



@Dao
interface LocationDao {
    @Query("SELECT * FROM locationentity ")
    fun getAll(): List<LocationEntity?>?

    @Query("SELECT * FROM LocationEntity WHERE id IN (:id)")
    fun loadAllByIds(id: IntArray?): List<LocationEntity?>?

    @Query("SELECT * FROM LocationEntity WHERE streetName LIKE :location LIMIT 1")
    fun findByLocation(location: String?): LocationEntity?

    @Insert
    fun insert(location: LocationEntity?): Long?


    @Query("UPDATE locationentity SET visited=:visited WHERE id = :id")
     fun update(visited: Boolean?, id: Int)



}