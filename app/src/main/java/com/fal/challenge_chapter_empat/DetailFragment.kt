package com.fal.challenge_chapter_empat

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.fal.challenge_chapter_empat.databinding.FragmentDetailBinding
import com.fal.challenge_chapter_empat.roomdb.DatabaseNote
import com.fal.challenge_chapter_empat.roomdb.EntityNote

class DetailFragment : Fragment() {
    lateinit var binding : FragmentDetailBinding
    lateinit var share : SharedPreferences
    lateinit var viewModel: MainViewModel
    var DBNote : DatabaseNote? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        share = requireActivity().getSharedPreferences("ACC", Context.MODE_PRIVATE)!!
        val fullname = share.getString("username","username")
        binding.welcomeBar.text = "Welcome, $fullname!"
        Log.d("Detailscreen", "Username : $fullname")

        binding.btnLogout.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_detailFragment_to_loginFragment)
        }

        //Get Data from Adapter
        DBNote = DatabaseNote.getInstance(requireContext())
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        var getNote = arguments?.getSerializable("datanotes") as EntityNote
        binding.vDetail.setText(getNote.title)
        binding.vContent.setText(getNote.content)
    }

}