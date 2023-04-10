package com.example.mymobilemarkmanagement

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mymobilemarkmanagement.models.Course
import com.example.mymobilemarkmanagement.viewModels.CourseDisplayViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CourseAdapter(
    private var courses: List<Course>,
    private val vm: CourseDisplayViewModel,
    private val navController: NavController
) :
    RecyclerView.Adapter<CourseAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewCourseCode: TextView = view.findViewById(R.id.textViewItemCourseCode)
        val textViewMark: TextView = view.findViewById(R.id.textViewItemMark)
        val textViewTerm: TextView = view.findViewById(R.id.textViewItemTerm)
        val textViewDescription: TextView = view.findViewById(R.id.textViewItemDescription)
        val fabDelete: FloatingActionButton = view.findViewById(R.id.floatingActionButtonDelete)
        val fabModify: FloatingActionButton = view.findViewById(R.id.floatingActionButtonModify)
        val background: LinearLayout = view.findViewById(R.id.itemBackground)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.course_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = courses.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        courses[position].apply {
            holder.textViewCourseCode.text = code

            mark.observeForever {
                holder.textViewMark.text = if (it == Course.WDMark) "WD" else it.toString()
                holder.background.setBackgroundColor(
                    when (it) {
                        -1 -> Color.parseColor("#2F4F4F")
                        in 0..49 -> Color.parseColor("#F08080")
                        in 50..59 -> Color.parseColor("#ADD8E6")
                        in 60..90 -> Color.parseColor("#90EE90")
                        in 91..95 -> Color.parseColor("#C0C0C0")
                        else -> Color.parseColor("#FFD700")
                    }
                )
            }

            description.observeForever {
                holder.textViewDescription.text = it
            }

            term.observeForever {
                holder.textViewTerm.text = it.toString()
            }

            holder.fabDelete.setOnClickListener { vm.deleteCourse(code) }
            holder.fabModify.setOnClickListener {
                navController.navigate(
                    R.id.action_courseDisplayFragment_to_modifyCourseFragment,
                    Bundle().apply { putString("courseCode", code) })
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(courses: List<Course>) {
        this.courses = courses
        notifyDataSetChanged()
    }
}