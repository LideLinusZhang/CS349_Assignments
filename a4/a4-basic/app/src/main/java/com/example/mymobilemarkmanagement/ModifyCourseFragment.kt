package com.example.mymobilemarkmanagement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mymobilemarkmanagement.enums.Term
import com.example.mymobilemarkmanagement.models.Course
import com.example.mymobilemarkmanagement.viewModels.CourseDisplayViewModel
import com.google.android.material.switchmaterial.SwitchMaterial

private const val ARG_COURSE_CODE = "courseCode"

class ModifyCourseFragment : Fragment() {
    private var courseCode: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            courseCode = it.getString(ARG_COURSE_CODE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_modify_course, container, false)
        val vm = ViewModelProvider(requireActivity())[CourseDisplayViewModel::class.java]
        val course = vm.getCourse(courseCode!!)

        view.apply {
            findViewById<TextView>(R.id.textViewCourseCode).apply {
                text = courseCode!!
            }

            val editTextDescription = findViewById<TextView>(R.id.editTextDescription).apply {
                text = course.description.value
            }
            val editTextNumberMark = findViewById<TextView>(R.id.editTextNumberMark).apply {
                if (course.mark.value != Course.WDMark)
                    text = course.mark.value.toString()
            }
            val switchWD = findViewById<SwitchMaterial>(R.id.switchWD).apply {
                isChecked = (course.mark.value == Course.WDMark)
                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        editTextNumberMark.apply {
                            text = ""
                            isEnabled = false
                        }
                    } else {
                        isEnabled = false
                        if (course.mark.value != Course.WDMark)
                            text = course.mark.value.toString()
                    }
                }
            }
            val spinnerTerm = findViewById<Spinner>(R.id.spinnerTerm).apply {
                adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, Term.values())
                setSelection(course.term.value!!.ordinal)
            }

            findViewById<Button>(R.id.buttonCancel).setOnClickListener {
                findNavController().navigate(R.id.action_modifyCourseFragment_to_courseDisplayFragment)
            }

            findViewById<Button>(R.id.buttonSubmit).setOnClickListener {
                val description = editTextDescription.text.toString()
                val isWD = switchWD.isChecked
                val mark: Int? = if (isWD) Course.WDMark else editTextNumberMark.text.toString().toIntOrNull()
                val term: Term = Term.values()[spinnerTerm.selectedItemPosition]

                if (mark != null) {
                    vm.updateCourse(courseCode!!, description, mark, term)
                    findNavController().navigate(R.id.action_modifyCourseFragment_to_courseDisplayFragment)
                }
            }
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(courseCode: String) =
            ModifyCourseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_COURSE_CODE, courseCode)
                }
            }
    }
}