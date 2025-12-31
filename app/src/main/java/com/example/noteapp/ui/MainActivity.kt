package com.example.noteapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.viewmodel.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    
    private val viewModel: NoteViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var tabLayout: TabLayout
    private lateinit var fab: FloatingActionButton
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        setupViews()
        setupRecyclerView()
        setupObservers()
    }
    
    private fun setupViews() {
        recyclerView = findViewById(R.id.recyclerView)
        tabLayout = findViewById(R.id.tabLayout)
        fab = findViewById(R.id.fab)
        
        // Tab选择监听
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> observeAllNotes()
                    1 -> observeReviewNotes()
                }
            }
            
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        
        // 添加笔记按钮
        fab.setOnClickListener {
            val intent = Intent(this, AddEditNoteActivity::class.java)
            startActivity(intent)
        }
    }
    
    private fun setupRecyclerView() {
        noteAdapter = NoteAdapter(
            onItemClick = { note ->
                val intent = Intent(this, AddEditNoteActivity::class.java)
                intent.putExtra("NOTE_ID", note.id)
                startActivity(intent)
            },
            onReviewClick = { note ->
                viewModel.markAsReviewed(note)
                Toast.makeText(this, "已标记为已复习", Toast.LENGTH_SHORT).show()
            },
            onDeleteClick = { note ->
                showDeleteConfirmDialog(note)
            }
        )
        
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = noteAdapter
        }
    }
    
    private fun setupObservers() {
        observeAllNotes()
    }
    
    private fun observeAllNotes() {
        viewModel.allNotes.observe(this) { notes ->
            noteAdapter.submitList(notes)
        }
    }
    
    private fun observeReviewNotes() {
        viewModel.getNotesNeedingReview().observe(this) { notes ->
            noteAdapter.submitList(notes)
        }
    }
    
    private fun showDeleteConfirmDialog(note: com.example.noteapp.data.Note) {
        AlertDialog.Builder(this)
            .setTitle("删除笔记")
            .setMessage("确定要删除这条笔记吗？")
            .setPositiveButton("删除") { _, _ ->
                viewModel.delete(note)
                Toast.makeText(this, "笔记已删除", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("取消", null)
            .show()
    }
}
