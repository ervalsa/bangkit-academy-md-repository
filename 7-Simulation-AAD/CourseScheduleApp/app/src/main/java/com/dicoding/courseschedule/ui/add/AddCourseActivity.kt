package com.dicoding.courseschedule.ui.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.util.TimePickerFragment
import java.text.SimpleDateFormat
import java.util.*

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {

    private lateinit var viewModel: AddCourseViewModel

    private lateinit var dialogStartTime: ImageButton
    private lateinit var dialogEndTime: ImageButton
    private lateinit var tvStartTime: TextView
    private lateinit var tvEndTime: TextView
    private lateinit var edCourseName: EditText
    private lateinit var edLecturer: EditText
    private lateinit var edNote: EditText
    private lateinit var spinnerDay: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        supportActionBar?.title = getString(R.string.add_course)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = AddCourseViewModelFactory.createFactory(this)
        viewModel = ViewModelProvider(this, factory)[AddCourseViewModel::class.java]

        val timePicker = TimePickerFragment()

        dialogStartTime = findViewById(R.id.ib_start_time)
        dialogEndTime = findViewById(R.id.ib_end_time)
        tvStartTime = findViewById(R.id.tv_start_time)
        tvEndTime = findViewById(R.id.tv_end_time)
        edCourseName = findViewById(R.id.ed_course_name)
        edLecturer = findViewById(R.id.ed_lecturer)
        edNote = findViewById(R.id.ed_note)
        spinnerDay = findViewById(R.id.spinner_day)

        dialogStartTime.setOnClickListener {
            timePicker.show(supportFragmentManager, START_TIME)
        }

        dialogEndTime.setOnClickListener {
            timePicker.show(supportFragmentManager, END_TIME)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_insert -> {
                val startTime = tvStartTime.text.toString()
                val endTime = tvEndTime.text.toString()
                val courseName = edCourseName.text.toString().trim()
                val lecturer = edLecturer.text.toString().trim()
                val note = edNote.text.toString().trim()
                val day = spinnerDay.selectedItemPosition

                if (courseName.isNotEmpty() && lecturer.isNotEmpty() && note.isNotEmpty() && startTime.isNotEmpty() && endTime.isNotEmpty()) {
                    viewModel.insertCourse(courseName, day, startTime, endTime, lecturer, note)
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.input_empty_message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)

        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        if (tag == START_TIME) {
            tvStartTime.text = timeFormat.format(calendar.time)
        } else {
            tvEndTime.text = timeFormat.format(calendar.time)
        }
    }

    companion object {
        const val START_TIME = "start_time"
        const val END_TIME = "end_time"
    }
}