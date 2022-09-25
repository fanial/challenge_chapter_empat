package com.fal.challenge_chapter_empat

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.fal.challenge_chapter_empat.databinding.FragmentMainBinding
import com.fal.challenge_chapter_empat.roomdb.DatabaseNote
import com.fal.challenge_chapter_empat.roomdb.EntityNote
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel
    lateinit var share : SharedPreferences
    lateinit var adapter: MainAdapter
    var DBNote : DatabaseNote? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        share = requireActivity().getSharedPreferences("ACC", Context.MODE_PRIVATE)!!
        val fullname = share.getString("username","username")
        binding.welcomeBar.text = "Welcome, $fullname!"
        Log.d("Homescreen", "Username : $fullname")

        binding.btnLogout.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_mainFragment_to_loginFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        //ViewModel
        adapter = MainAdapter(ArrayList())
        binding.rvMain.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvMain.adapter = adapter
        //LiveData
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getNoteObservers().observe(viewLifecycleOwner, Observer {
            adapter.setNotes(it as ArrayList<EntityNote>)
        })
        //Room
        DBNote = DatabaseNote.getInstance(requireContext())
        //Add Note
        binding.floatingActionButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_mainFragment_to_addNoteFragment)
        }
    }


    override fun onStart() {
        super.onStart()
        GlobalScope.launch {
            var data = DBNote?.noteDao()?.getDataNote()
            activity?.runOnUiThread {
                adapter = MainAdapter(data!!)
                binding.rvMain.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.rvMain.adapter = adapter
            }
        }
    }

}