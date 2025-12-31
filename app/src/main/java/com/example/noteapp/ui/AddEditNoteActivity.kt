package com.example.noteapp.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.noteapp.R
import com.example.noteapp.data.Note
import com.example.noteapp.util.MemoryCurve
import com.example.noteapp.viewmodel.NoteViewModel
import kotlinx.coroutines.launch

class AddEditNoteActivity : AppCompatActivity() {
    
    private val viewModel: NoteViewModel by viewModels()
    private lateinit var editTitle: EditText
    private lateinit var editContent: EditText
    private lateinit var buttonSave: Button
    
    private var noteId: Int = -1
    private var existingNote: Note? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)
        
        setupViews()
        loadNoteIfEditing()
    }
    
    private fun setupViews() {
        editTitle = findViewById(R.id.editTitle)
        editContent = findViewById(R.id.editContent)
        buttonSave = findViewById(R.id.buttonSave)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        buttonSave.setOnClickListener {
            saveNote()
        }
    }
    
    private fun loadNoteIfEditing() {
        noteId = intent.getIntExtra("NOTE_ID", -1)
        
        if (noteId != -1) {
            supportActionBar?.title = "编辑笔记"
            
            lifecycleScope.launch {
                existingNote = viewModel.getNoteById(noteId)
                existingNote?.let { note ->
                    editTitle.setText(note.title)
                    editContent.setText(note.content)
                }
            }
        } else {
            supportActionBar?.title = "新建笔记"
        }
    }
    
    private fun saveNote() {
        val title = editTitle.text.toString().trim()
        val content = editContent.text.toString().trim()
        
        if (title.isEmpty()) {
            Toast.makeText(this, "请输入标题", Toast.LENGTH_SHORT).show()
            return
        }
        
        if (content.isEmpty()) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show()
            return
        }
        
        if (existingNote != null) {
            // 更新现有笔记
            val updatedNote = existingNote!!.copy(
                title = title,
                content = content
            )
            viewModel.update(updatedNote)
            Toast.makeText(this, "笔记已更新", Toast.LENGTH_SHORT).show()
        } else {
            // 创建新笔记
            viewModel.insert(title, content)
            Toast.makeText(this, "笔记已保存", Toast.LENGTH_SHORT).show()
        }
        
        finish()
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
