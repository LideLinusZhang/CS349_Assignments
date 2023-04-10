package com.example.mymobilemarkmanagement.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymobilemarkmanagement.enums.Filter
import com.example.mymobilemarkmanagement.enums.SortOrder
import com.example.mymobilemarkmanagement.enums.Term
import com.example.mymobilemarkmanagement.models.Course

class CourseDisplayViewModel: ViewModel() {
    private val courseDictionary = mutableMapOf<String, Course>()

    private val sortOrder = MutableLiveData(SortOrder.ByCourseName)
    private val filter = MutableLiveData(Filter.AllCourses)
    private val courses = MutableLiveData<List<Course>>()

    fun getSortOrder(): LiveData<SortOrder> = sortOrder
    fun setSortOrder(order: SortOrder) {
        sortOrder.value = order
    }

    fun getFilter(): LiveData<Filter> = filter
    fun setFilter(filter: Filter) {
        this.filter.value = filter
    }

    fun addCourse(courseCode: String, description: String, mark: Int, term: Term): Boolean {
        return if (!courseDictionary.containsKey(courseCode)) {
            courseDictionary[courseCode] = Course(description, mark, term)
            updateCourses()
            true
        } else false
    }

    fun updateCourse(courseCode: String, description: String, mark: Int, term: Term) {
        courseDictionary[courseCode]?.apply {
            this.description.value = description
            this.mark.value = mark
            this.term.value = term
        }
        updateCourses()
    }

    fun getCourse(courseCode: String): Course = courseDictionary[courseCode]!!

    fun deleteCourse(courseCode: String) {
        courseDictionary.remove(courseCode)
        updateCourses()
    }

    private fun updateCourses() {

    }
}