package com.palsaloid.myalarmmanager

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.palsaloid.myalarmmanager.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener, DatePickerFragment.DialogDateListener, TimePickerFragment.DialogTimeListener {

    private var binding: ActivityMainBinding? = null
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btnOnceDate?.setOnClickListener(this)
        binding?.btnOnceTime?.setOnClickListener(this)
        binding?.btnSetOnceAlarm?.setOnClickListener(this)

        binding?.btnRepeatingTime?.setOnClickListener(this)
        binding?.btnSetRepeatingAlarm?.setOnClickListener(this)

        binding?.btnCancelRepeatingAlarm?.setOnClickListener(this)

        alarmReceiver = AlarmReceiver()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_once_date -> {
                val datePickerFragment = DatePickerFragment()
                datePickerFragment.show(supportFragmentManager, DATE_PICKER_TAG)
            }

            R.id.btn_once_time -> {
                val timePickerFragmentOne = TimePickerFragment()
                timePickerFragmentOne.show(supportFragmentManager, TIME_PICKER_ONCE_TAG)
            }

            R.id.btn_set_once_alarm -> {
                val onceDate = binding?.tvOnceDate?.text.toString()
                val onceTime = binding?.tvOnceTime?.text.toString()
                val onceMessage = binding?.edtOnceMessage?.text.toString()

                alarmReceiver.setOneTimeAlarm(
                    this,
                    AlarmReceiver.TYPE_ONE_TIME,
                    onceDate,
                    onceTime,
                    onceMessage
                )
            }

            R.id.btn_repeating_time -> {
                val timePickerFragmentRepeat = TimePickerFragment()
                timePickerFragmentRepeat.show(supportFragmentManager, TIME_PICKER_REPEAT_TAG)
            }

            R.id.btn_set_repeating_alarm -> {
                val repeatTime = binding?.tvRepeatingTime?.text.toString()
                val repeatMessage = binding?.edtRepeatingMessage?.text.toString()
                alarmReceiver.setRepeatingAlarm(
                    this,
                    AlarmReceiver.TYPE_REPEATING,
                    repeatTime,
                    repeatMessage
                )
            }

            R.id.btn_cancel_repeating_alarm -> alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_REPEATING)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        binding?.tvOnceDate?.text = dateFormat.format(calendar.time)
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        when(tag) {
            TIME_PICKER_ONCE_TAG -> binding?.tvOnceTime?.text = dateFormat.format(calendar.time)
            TIME_PICKER_REPEAT_TAG -> binding?.tvRepeatingTime?.text = dateFormat.format(calendar.time)
            else -> {

            }
        }
    }

    companion object {
        private const val DATE_PICKER_TAG = "DatePicker"
        private const val TIME_PICKER_ONCE_TAG = "TimePickerOnce"
        private const val TIME_PICKER_REPEAT_TAG = "TimePirkcerRepeat"
    }
}