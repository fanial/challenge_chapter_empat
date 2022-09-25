package com.fal.challenge_chapter_empat

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.fal.challenge_chapter_empat.databinding.ItemListBinding
import com.fal.challenge_chapter_empat.roomdb.DatabaseNote
import com.fal.challenge_chapter_empat.roomdb.EntityNote
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class MainAdapter(var notes : List<EntityNote>) : RecyclerView.Adapter<MainAdapter.ViewHolder>(){
    var DBNote : DatabaseNote? = null

    class ViewHolder(var binding : ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (item : EntityNote){
            binding.datanotes = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notes[position])
        //delete note
        holder.binding.btnDelete.setOnClickListener {
            DBNote = DatabaseNote.getInstance(it.context)
            GlobalScope.async {
                MainViewModel(Application()).delete(notes[position])
                DBNote?.noteDao()?.deleteNote(notes[position])
                kotlin.run {
                    Navigation.findNavController(it).navigate(R.id.mainFragment)
                }
            }
        }
        //update
        holder.binding.btnEdit.setOnClickListener {
            var update = Bundle()
            update.putSerializable("updaters", notes[position])
            Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_editFragment, update)
        }
        //detail
        holder.binding.btnDetail.setOnClickListener {
            var detail = Bundle()
            detail.putSerializable("datanotes", notes[position])
            Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_detailFragment, detail)
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun setNotes(itemNote : ArrayList<EntityNote>){
        this.notes = itemNote
    }
}