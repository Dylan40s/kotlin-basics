package com.example.myworklifeapp

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.myworklifeapp.databinding.FragmentHomeScreenBinding
import kotlinx.android.synthetic.main.fragment_home_screen.*
import kotlinx.android.synthetic.main.fragment_home_screen.view.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HomeScreenFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HomeScreenFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class HomeScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentHomeScreenBinding>(inflater, R.layout.fragment_home_screen, container, false)

        binding.root.timeCapture.setOnClickListener { view: View? ->
            if (view != null) {
                view.findNavController().navigate(HomeScreenFragmentDirections.actionHomeScreenFragmentToTimeCaptureFragment())
            }
        }

        binding.goToTest.setOnClickListener {
            view?.findNavController()?.navigate(HomeScreenFragmentDirections.actionHomeScreenFragmentToLeaveFragment())
        }

        return binding.root
    }
}
