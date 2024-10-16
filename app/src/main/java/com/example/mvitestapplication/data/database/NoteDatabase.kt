package com.example.mvitestapplication.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mvitestapplication.data.dao.NoteDao
import com.example.mvitestapplication.data.entity.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}