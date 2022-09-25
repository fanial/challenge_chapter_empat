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
import com.fal.challenge_chapter_empat.databinding.FragmentEditBinding
import com.fal.challenge_chapter_empat.roomdb.DatabaseNote
import com.fal.challenge_chapter_empat.roomdb.EntityNote
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class EditFragment : Fragment() {

    lateinit var binding : FragmentEditBinding
    lateinit var share : SharedPreferences
    var dbNote : DatabaseNote? = null
    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        share = requireActivity().getSharedPreferences("ACC", Context.MODE_PRIVATE)!!
        val fullname = share.getString("username","username")
        binding.welcomeBar.text = "Welcome, $fullname!"
        Log.d("Editscreen", "Username : $fullname")

        binding.btnLogout.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_editFragment_to_loginFragment)
        }

        //Update Note
        dbNote = DatabaseNote.getInstance(requireContext())
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        var getNote = arguments?.getSerializable("updaters") as EntityNote
        binding.addTitle.setText(getNote.title)
        binding.addNote.setText(getNote.content)
        binding.idNote.setText(getNote.id.toString())
        binding.btnInputNote.setOnClickListener {
            editNote()
        }
    }

    private fun editNote() {
        GlobalScope.async {
            var getNote = arguments?.getSerializable("updaters") as EntityNote
            var title = binding.addTitle.text.toString()
            var note = binding.addNote.text.toString()

            var edit = EntityNote(getNote.id,title,note)
            viewModel.update(edit)
            activity?.runOnUiThread {
                toast("Note berhasil diupdate")
            }

            Navigation.findNavController(binding.root).navigate(R.id.action_editFragment_to_mainFragment)
        }
    }

    private fun toast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG)
            .show()
    }
}