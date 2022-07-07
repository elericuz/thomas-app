package com.example.android.metrodelima.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.AttrRes
import androidx.core.util.Pair
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.metrodelima.R
import com.example.android.metrodelima.databinding.FragmentFilterBinding
import com.example.android.metrodelima.ui.viewmodels.TransactionViewModel
import com.example.android.metrodelima.utils.Dates
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import timber.log.Timber
import java.util.*

class FilterFragment : Fragment() {
    private lateinit var binding: FragmentFilterBinding
    private lateinit var datePicker: MaterialDatePicker<Long>
    private lateinit var transactionViewModel: TransactionViewModel

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_filter, container, false)
        binding.lifecycleOwner = requireActivity()
        binding.filter = this

        transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        binding.selectedDate.text = transactionViewModel.currentDateFormatted.value

        getDatePicker()

        return binding.root
    }

    fun dateClick() {
        datePicker.show(childFragmentManager, "tag");
    }

    private fun getDatePicker() {
        val today = MaterialDatePicker.todayInUtcMilliseconds()
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.timeInMillis = today

        val calendarConstraints = CalendarConstraints.Builder()
            .setEnd(calendar.timeInMillis)

        datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Escoja un día")
                .setNegativeButtonText("Cancelar")
                .setCalendarConstraints(calendarConstraints.build())
                .build()

        datePicker.addOnPositiveButtonClickListener {
            val selectedDate = Dates.getDateFromLong(it, "dd-MM-yyyy")
            binding.selectedDate.text = selectedDate
            transactionViewModel.updateCurrentDateFormatted(selectedDate)
            transactionViewModel.updateCurrentDate(Dates.getDateFromLong(it, "yyyy-MM-dd"))
        }
        datePicker.addOnNegativeButtonClickListener {
            // Respond to negative button click.
        }
        datePicker.addOnCancelListener {
            // Respond to cancel button click.
        }
        datePicker.addOnDismissListener {
            // Respond to dismiss events.
        }
    }

    fun filterClick() {
        transactionViewModel.getTransactions()
        findNavController().popBackStack()
    }

    fun backClick() {
        findNavController().popBackStack()
    }
}