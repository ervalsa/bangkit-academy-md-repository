package com.dicoding.todoapp.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery

//TODO 2 : Define data access object (DAO)
@Dao
interface TaskDao {

    @RawQuery(observedEntities = [Task::class])
    fun getTasks(query: SupportSQLiteQuery): DataSource.Factory<Int, Task>

    @Query("SELECT id, title, description, dueDate, completed FROM tasks where id = :taskId")
    fun getTaskById(taskId: Int): LiveData<Task>

    @Query("SELECT id, title, description, dueDate, completed FROM tasks WHERE completed = 0 ORDER BY dueDate ASC")
    fun getNearestActiveTask(): Task

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task: Task): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg tasks: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("UPDATE tasks SET completed = :completed WHERE id = :taskId")
    suspend fun updateCompleted(taskId: Int, completed: Boolean)
    
}
