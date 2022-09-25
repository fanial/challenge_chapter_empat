package com.fal.challenge_chapter_empat.roomdb

import androidx.room.*

@Dao
interface InterfaceNote {
    @Insert
    fun insertNote(note: EntityNote)

    @Query("SELECT * FROM EntityNote ORDER BY id DESC")
    fun getDataNote() : List<EntityNote>

    @Delete
    fun deleteNote(note: EntityNote)

    @Update
    fun updateNOte(note: EntityNote)
}