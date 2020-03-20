package com.example.myworklifeapp


import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.myworklifeapp.Network.BifrostApi
import com.example.myworklifeapp.Network.LeaveForm
import com.example.myworklifeapp.databinding.FragmentLeaveBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class LeaveFragment : Fragment() {

    private lateinit var startDate : Date
    private lateinit var endDate : Date
    public var difference = 0.0

    private val scope = CoroutineScope(Dispatchers.Default)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        startDate = c.time
        endDate = startDate

        difference = 1.0
        month += 1
        val binding = DataBindingUtil.inflate<FragmentLeaveBinding>(inflater, R.layout.fragment_leave, container, false)

        binding.startDateText.setText("$day-$month-$year")
        binding.endDateText.setText("" + day + "-" + month + "-" + year)
        binding.totalLeave.setText("1")
        month--

        binding.startDateText.setOnClickListener { view: View ->
            DatePickerDialog(view.context, android.R.style.Theme_DeviceDefault_Light_Dialog, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                startDate = Date(year, monthOfYear, dayOfMonth)
                difference = calculateDifference(startDate, endDate) + 1
                if (difference > 0) {
                    if (binding.startHalfDay.isChecked) {
                        difference -= 0.5
                    }

                } else {
                    if (binding.startHalfDay.isChecked) {
                        difference = 0.5
                    } else {
                        difference = 1.0
                    }
                    endDate = startDate
                }

                var tempMonth = monthOfYear + 1
                var endDate = endDate.month + 1

                binding.startDateText.setText("" + dayOfMonth + "-" + tempMonth + "-" + year)
                binding.endDateText.setText("" + dayOfMonth + "-" + endDate + "-" + year)
                binding.totalLeave.setText("" + difference)

            }, year, month, day).show()
        }

        binding.endDateText.setOnClickListener { view: View ->
            DatePickerDialog(view.context, android.R.style.Theme_DeviceDefault_Light_Dialog, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                var tempEndDate = Date(year, monthOfYear, dayOfMonth)
                difference = calculateDifference(startDate, tempEndDate) + 1
                if (difference > 0) {
                    endDate = tempEndDate
                    var tempMonth = tempEndDate.month + 1
                    binding.endDateText.setText("" + dayOfMonth + "-" + tempMonth + "-" + year)
                    binding.totalLeave.setText("" + difference)
                } else {
                    endDate = startDate
                    var tempMonth = endDate.month + 1
                    binding.endDateText.setText("" + dayOfMonth + "-" + tempMonth + "-" + year)
                    Toast.makeText(context, "End date must be after start date", Toast.LENGTH_SHORT).show()
                }
            }, year, month, day).show()
        }

        binding.submitLeaveButton.setOnClickListener {view: View ->
            var selected = binding.leaveTypeSpinner.selectedItem.toString()
            var leaveform = LeaveForm(binding.username.text.toString(), binding.startDateText.text.toString(), binding.endDateText.text.toString(), binding.startHalfDay.isChecked, selected)

            scope.launch {
                doCall(leaveform)
            }

        }

        binding.startHalfDay.setOnClickListener { view: View? ->
            if (binding.startHalfDay.isChecked) {
                difference -= 0.5
            } else {
                difference += 0.5
            }

            binding.totalLeave.setText("" + difference)
        }

        return binding.root
    }

    suspend fun doCall(leaveForm: LeaveForm) {
        try {
           var test = BifrostApi.retrofitService.createLeaveForm(leaveForm)
            var result = test.await()
            Toast.makeText(context, result.toString(), Toast.LENGTH_LONG).show()
        } catch(t: Throwable) {
        }

    }

    fun calculateDifference(startDateTime: Date, endDateTime: Date) : Double {
        return (endDateTime.time - startDateTime.time) / (1000 * 3600 * 24.0)
    }


}
