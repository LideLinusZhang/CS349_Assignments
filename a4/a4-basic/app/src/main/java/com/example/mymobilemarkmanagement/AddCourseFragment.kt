package com.example.mymobilemarkmanagement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mymobilemarkmanagement.enums.Term
import com.example.mymobilemarkmanagement.models.Course
import com.example.mymobilemarkmanagement.viewModels.CourseDisplayViewModel
import com.google.android.material.switchmaterial.SwitchMaterial

class AddCourseFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_course, container, false)
        val vm = ViewModelProvider(requireActivity())[CourseDisplayViewModel::class.java]

        view.apply {
            val editTextCourseCode = findViewById<EditText>(R.id.editTextCourseCode)
            val editTextDescription = findViewById<EditText>(R.id.editTextDescription)
            val editTextNumberMark = findViewById<EditText>(R.id.editTextNumberMark).apply {
                doAfterTextChanged {
                    val converted: Int? = it.toString().toIntOrNull()
                    if (converted == null || converted !in 0..100)
                        text.clear()
                }
            }
            val switchWD = findViewById<SwitchMaterial>(R.id.switchWD).apply {
                setOnCheckedChangeListener { _, isChecked ->
                    editTextNumberMark.isEnabled = !isChecked
                }
            }
            val spinnerTerm = findViewById<Spinner>(R.id.spinnerTerm).apply {
                adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, Term.values())
            }

            findViewById<Button>(R.id.buttonCancel).setOnClickListener {
                findNavController().navigate(R.id.action_addCourseFragment_to_courseDisplayFragment)
            }

            findViewById<Button>(R.id.buttonCreate).setOnClickListener {
                val courseCode = editTextCourseCode.text.toString()
                val description = editTextDescription.text.toString()
                val isWD = switchWD.isChecked
                val mark: Int? = if (isWD) Course.WDMark else editTextNumberMark.text.toString().toIntOrNull()
                val term = Term.values()[spinnerTerm.id]

                if (courseCode.isNotEmpty() && mark != null && vm.addCourse(courseCode, description, mark, term))
                    findNavController().navigate(R.id.action_addCourseFragment_to_courseDisplayFragment)
            }
        }

        return view
    }
}