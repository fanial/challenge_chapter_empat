package com.fal.challenge_chapter_empat

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.fal.challenge_chapter_empat.databinding.FragmentAddNoteBinding
import com.fal.challenge_chapter_empat.roomdb.DatabaseNote
import com.fal.challenge_chapter_empat.roomdb.EntityNote
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class AddNoteFragment : Fragment() {

    lateinit var binding : FragmentAddNoteBinding
    lateinit var share : SharedPreferences
    lateinit var viewModel: MainViewModel
    var dbNote : DatabaseNote? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddNoteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        share = requireActivity().getSharedPreferences("ACC", Context.MODE_PRIVATE)!!
        val fullname = share.getString("username","username")
        binding.welcomeBar.text = "Welcome, $fullname!"
        Log.d("Insertscreen", "Username : $fullname")

        binding.btnLogout.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_addNoteFragment_to_loginFragment)
        }

        dbNote = DatabaseNote.getInstance(requireContext())
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.btnInputNote.setOnClickListener {
            addNote()
        }
    }

    private fun addNote() {
        GlobalScope.async {
            var title = binding.addTitle.text.toString()
            var content = binding.addNote.text.toString()

            val dataInsert = EntityNote(0,title,content)
            viewModel.insert(dataInsert)

            activity?.runOnUiThread {
                Toast.makeText(context,"Berhasil menambahkan note", Toast.LENGTH_LONG)
            }
        }
        Navigation.findNavController(binding.root).navigate(R.id.action_addNoteFragment_to_mainFragment)
    }

}