package com.dicoding.habitapp.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery

//TODO 2 : Define data access object (DAO)
@Dao
interface HabitDao {

    @RawQuery(observedEntities = [Habit::class])
    fun getHabits(query: SupportSQLiteQuery): DataSource.Factory<Int, Habit>

    @Query("SELECT id, title, minutesFocus, startTime, priorityLevel FROM habits WHERE id = :habitId")
    fun getHabitById(habitId: Int): LiveData<Habit>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertHabit(habit: Habit): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg habits: Habit)

    @Delete
    fun deleteHabit(habits: Habit)

    @Query("SELECT id, title, minutesFocus, startTime, priorityLevel FROM habits WHERE priorityLevel = :level ORDER BY RANDOM() LIMIT 1")
    fun getRandomHabitByPriorityLevel(level: String): LiveData<Habit>
}
