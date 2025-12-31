package com.example.noteapp.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.data.Note
import com.example.noteapp.util.MemoryCurve

class NoteAdapter(
    private val onItemClick: (Note) -> Unit,
    private val onReviewClick: (Note) -> Unit,
    private val onDeleteClick: (Note) -> Unit
) : ListAdapter<Note, NoteAdapter.NoteViewHolder>(NoteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.textTitle)
        private val contentTextView: TextView = itemView.findViewById(R.id.textContent)
        private val reviewStatusTextView: TextView = itemView.findViewById(R.id.textReviewStatus)
        private val nextReviewDateTextView: TextView = itemView.findViewById(R.id.textNextReviewDate)
        private val reviewButton: Button = itemView.findViewById(R.id.buttonReview)
        private val deleteButton: Button = itemView.findViewById(R.id.buttonDelete)

        fun bind(note: Note) {
            titleTextView.text = note.title
            contentTextView.text = note.content
            reviewStatusTextView.text = note.getReviewStatusText()
            nextReviewDateTextView.text = "下次复习: ${MemoryCurve.formatDate(note.nextReviewDate)}"

            // 根据是否需要复习设置状态文本颜色
            if (note.needsReviewToday() && note.reviewStage < 5) {
                reviewStatusTextView.setTextColor(Color.parseColor("#F44336"))
                nextReviewDateTextView.setTextColor(Color.parseColor("#F44336"))
            } else {
                reviewStatusTextView.setTextColor(Color.parseColor("#4CAF50"))
                nextReviewDateTextView.setTextColor(Color.parseColor("#666666"))
            }

            // 复习按钮
            if (note.reviewStage >= 5) {
                reviewButton.visibility = View.GONE
            } else {
                reviewButton.visibility = View.VISIBLE
                reviewButton.isEnabled = note.needsReviewToday()
                reviewButton.setOnClickListener { onReviewClick(note) }
            }

            // 点击项目编辑
            itemView.setOnClickListener { onItemClick(note) }

            // 删除按钮
            deleteButton.setOnClickListener { onDeleteClick(note) }
        }
    }

    private class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }
}
