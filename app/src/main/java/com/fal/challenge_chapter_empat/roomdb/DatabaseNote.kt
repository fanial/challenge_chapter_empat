package com.fal.challenge_chapter_empat.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fal.challenge_chapter_empat.AddNoteFragment

@Database(entities = [EntityNote::class], version = 1)
abstract class DatabaseNote : RoomDatabase() {
    abstract fun noteDao() : InterfaceNote

    companion object{
        private var INSTANCE : DatabaseNote? = null

        fun getInstance(context: Context): DatabaseNote?{
            if (INSTANCE == null){
                synchronized(DatabaseNote::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    DatabaseNote::class.java, "NoteDB").build()
                }
            }
            return  INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}