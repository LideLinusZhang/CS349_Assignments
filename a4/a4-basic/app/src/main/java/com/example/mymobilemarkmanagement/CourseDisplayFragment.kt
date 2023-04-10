package com.example.mymobilemarkmanagement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymobilemarkmanagement.enums.Filter
import com.example.mymobilemarkmanagement.enums.SortOrder
import com.example.mymobilemarkmanagement.viewModels.CourseDisplayViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CourseDisplayFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_course_display, container, false)
        val vm = ViewModelProvider(requireActivity())[CourseDisplayViewModel::class.java]

        view.apply {
            val sortOrderSpinner = findViewById<Spinner>(R.id.sortOrderSpinner)
            vm.getSortOrder().observe(viewLifecycleOwner) {
                sortOrderSpinner.setSelection(it.ordinal)
            }
            sortOrderSpinner.apply {
                adapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, SortOrder.values().toList())
                onItemSelectedListener = object : OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        vm.setSortOrder(SortOrder.values()[position])
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }

            val filterSpinner = findViewById<Spinner>(R.id.filterSpinner)
            vm.getFilter().observe(viewLifecycleOwner) {
                filterSpinner.setSelection(it.ordinal)
            }
            filterSpinner.apply {
                adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, Filter.values())
                onItemSelectedListener = object : OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        vm.setFilter(Filter.values()[position])
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }

            val addCourseButton = findViewById<FloatingActionButton>(R.id.addCourseButton)
            addCourseButton.setOnClickListener {
                findNavController().navigate(R.id.action_courseDisplayFragment_to_addCourseFragment)
            }

            val recyclerViewCourseList = findViewById<RecyclerView>(R.id.recyclerViewCourseList).apply {
                adapter = CourseAdapter(listOf(), vm, this@CourseDisplayFragment.findNavController())
                layoutManager = LinearLayoutManager(requireContext())
            }
            vm.getCourses().observe(viewLifecycleOwner) {
                (recyclerViewCourseList.adapter as CourseAdapter).update(it)
            }
        }

        return view
    }
}