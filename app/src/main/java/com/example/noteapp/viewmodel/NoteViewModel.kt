package com.example.noteapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.Note
import com.example.noteapp.data.NoteDatabase
import com.example.noteapp.util.MemoryCurve
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    
    private val noteDao = NoteDatabase.getDatabase(application).noteDao()
    
    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()
    
    fun getNotesNeedingReview(): LiveData<List<Note>> {
        val currentTime = System.currentTimeMillis()
        return noteDao.getNotesNeedingReview(currentTime)
    }
    
    fun insert(title: String, content: String) = viewModelScope.launch {
        val createdAt = System.currentTimeMillis()
        val note = Note(
            title = title,
            content = content,
            createdAt = createdAt,
            reviewStage = 0,
            nextReviewDate = MemoryCurve.calculateNextReviewDate(createdAt, 0)
        )
        noteDao.insert(note)
    }
    
    fun update(note: Note) = viewModelScope.launch {
        noteDao.update(note)
    }
    
    fun delete(note: Note) = viewModelScope.launch {
        noteDao.delete(note)
    }
    
    fun markAsReviewed(note: Note) = viewModelScope.launch {
        val nextStage = MemoryCurve.getNextReviewStage(note.reviewStage)
        val nextReviewDate = MemoryCurve.calculateNextReviewDate(note.createdAt, nextStage)
        
        val updatedNote = note.copy(
            reviewStage = nextStage,
            nextReviewDate = nextReviewDate
        )
        noteDao.update(updatedNote)
    }
    
    suspend fun getNoteById(noteId: Int): Note? {
        return noteDao.getNoteById(noteId)
    }
}
