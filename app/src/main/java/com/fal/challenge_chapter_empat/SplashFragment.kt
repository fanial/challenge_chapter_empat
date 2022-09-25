package com.fal.challenge_chapter_empat

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.fal.challenge_chapter_empat.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    lateinit var binding : FragmentSplashBinding
    lateinit var share : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        share = requireActivity().getSharedPreferences("ACC", Context.MODE_PRIVATE)!!
        val getUser = share.getString("username", "")

        if(getUser != "") {
            var dataUsername = share.getString("username", "username")
            var bundle = Bundle()
            bundle.putString("username", dataUsername)

            Handler().postDelayed({
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_mainFragment, bundle)
            }, 3000)
        }
        else {
            Handler().postDelayed({
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_loginFragment)
            }, 3000)
        }
    }

}