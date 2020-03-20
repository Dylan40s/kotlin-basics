package com.example.myworklifeapp

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.myworklifeapp.databinding.FragmentTimeCaptureBinding
import kotlinx.android.synthetic.main.fragment_time_capture.view.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TimeCaptureFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TimeCaptureFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TimeCaptureFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentTimeCaptureBinding>(inflater, R.layout.fragment_time_capture, container, false)
        binding.root.submitButton.setOnClickListener {
            runClickMethod(binding)

        }

        Toast.makeText(context, "Loaded", Toast.LENGTH_LONG).show()
        // Inflate the layout for this fragment
        return binding.root
    }

    fun runClickMethod(binding : FragmentTimeCaptureBinding) {
        var mondayTime : Double = 0.0
        var tuesdayTime : Double = 0.0

        try {
            mondayTime = binding.root.mondayTextBox.toString().toDouble()
            tuesdayTime = binding.root.tuesdayTextBox.toString().toDouble()
        } catch (e : Exception) {
            Toast.makeText(context, "Values are incorrect", Toast.LENGTH_SHORT).show()
        }

        Toast.makeText(context, "Test", Toast.LENGTH_LONG).show()
    }

}
