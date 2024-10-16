package com.example.mvitestapplication.data.di

import android.content.Context
import androidx.room.Room
import com.example.mvitestapplication.data.dao.NoteDao
import com.example.mvitestapplication.data.database.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext app: Context): NoteDatabase {
        return Room.databaseBuilder(app, NoteDatabase::class.java, "note_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteDao(db: NoteDatabase): NoteDao = db.noteDao()
}
