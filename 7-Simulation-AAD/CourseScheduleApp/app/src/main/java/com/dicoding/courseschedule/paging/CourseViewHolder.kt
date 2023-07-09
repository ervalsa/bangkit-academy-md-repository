package com.dicoding.courseschedule.paging

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.data.Course
import com.dicoding.courseschedule.util.DayName.Companion.getByNumber

class CourseViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private lateinit var course: Course
    private val timeString = itemView.context.resources.getString(R.string.time_format)

    //TODO 7 : Complete ViewHolder to show item
    fun bind(course: Course, clickListener: (Course) -> Unit) {
        this.course = course

        val tvLecturer: TextView = itemView.findViewById(R.id.tv_lecturer)
        val tvTime: TextView = itemView.findViewById(R.id.tv_time)
        val tvCourseName: TextView = itemView.findViewById(R.id.tv_course)

        course.apply {
            val dayName = getByNumber(day)
            val timeFormat = String.format(timeString, dayName, startTime, endTime)

            tvLecturer.text = lecturer
            tvTime.text = timeFormat
            tvCourseName.text = courseName
        }

        itemView.setOnClickListener {
            clickListener(course)
        }
    }

    fun getCourse(): Course = course
}