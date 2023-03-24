package com.game.localnotesappkotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.game.localnotesappkotlin.R
import com.game.localnotesappkotlin.database.NotesEntity
import com.game.localnotesappkotlin.databinding.NoteItemBinding

class NotesAdapter(
    val context: Context,
    val notecClickInterface: NoteClickInterface
) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private val allNotes = ArrayList<NotesEntity>()

    inner class ViewHolder(private val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: NotesEntity) {
            binding.title.text = note.noteTitle
            binding.desc.text = note.noteDescription

            binding.root.setOnClickListener{
                notecClickInterface.onNoteClick(note)
            }
        }
    }

    interface NoteClickInterface {
        fun onNoteClick(note: NotesEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = allNotes[position]
        note.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    fun updateList(newList: List<NotesEntity>) {
        // on below line we are clearing
        // our notes array list
        allNotes.clear()
        // on below line we are adding a
        // new list to our all notes list.
        allNotes.addAll(newList)
        // on below line we are calling notify data
        // change method to notify our adapter.
        notifyDataSetChanged()
    }

}