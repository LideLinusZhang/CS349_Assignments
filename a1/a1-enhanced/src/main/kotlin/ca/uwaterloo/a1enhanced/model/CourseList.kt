package ca.uwaterloo.a1enhanced.model

import ca.uwaterloo.a1enhanced.view.CourseHBox
import ca.uwaterloo.a1enhanced.view.IView
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections

object CourseList {
    private val rawCoursesProperty = SimpleListProperty<Pair<Course, CourseHBox>>(
        FXCollections.observableArrayList(mutableListOf())
    ).apply {
        addListener { _, _, _ -> filterAndSortCourses() }
    }

    // Cache for courses
    private var filteredAndSortedCourses: List<Pair<Course, CourseHBox>> = listOf()

    // Internal properties for updating cache on demand
    private val sortByProperty = SimpleObjectProperty(SortBy.Code).apply {
        addListener { _, _, _ -> filterAndSortCourses() }
    }
    private val thenByProperty = SimpleObjectProperty(SortBy.Term).apply {
        addListener { _, _, _ -> filterAndSortCourses() }
    }
    private val ascendingProperty = SimpleBooleanProperty(true).apply {
        addListener { _, _, _ -> filterAndSortCourses() }
    }
    private val includeWDProperty = SimpleBooleanProperty(false).apply {
        addListener { _, _, _ -> filterAndSortCourses() }
    }
    private val courseTypeProperty = SimpleObjectProperty(CourseType.All).apply {
        addListener { _, _, _ -> filterAndSortCourses() }
    }

    // Properties for Controllers to modify
    var sortBy: SortBy
        get() = sortByProperty.value
        set(value) {
            sortByProperty.value = value
        }
    var thenBy: SortBy
        get() = thenByProperty.value
        set(value) {
            thenByProperty.value = value
        }
    var ascending: Boolean
        get() = ascendingProperty.value
        set(value) {
            ascendingProperty.value = value
        }
    var includeWD: Boolean
        get() = includeWDProperty.value
        set(value) {
            includeWDProperty.value = value
        }
    var courseType: CourseType
        get() = courseTypeProperty.value ?: CourseType.All
        set(value) {
            courseTypeProperty.value = value
        }

    // Properties for Views
    val courseHBoxes: List<CourseHBox>
        get() {
            filterAndSortCourses()
            return filteredAndSortedCourses.map { it.second }
        }
    val courseAverage: Double
        get() = filteredAndSortedCourses.mapNotNull { it.first.scoreProperty.value }.average()
    val courseMedian: Double
        get() = filteredAndSortedCourses.mapNotNull { it.first.scoreProperty.value }.let {
            if (it.isEmpty()) 0.0
            else if (it.size % 2 == 0) (it[it.size / 2] + it[(it.size - 1) / 2]) / 2.0
            else it[it.size / 2].toDouble()
        }
    val courseCount: Int get() = filteredAndSortedCourses.count()
    val courseWDCount: Int get() = filteredAndSortedCourses.count { it.first.scoreProperty.value == null }
    val courseFailedCount: Int get() = filteredAndSortedCourses.count { it.first.isFailed }

    private val registeredViews = mutableListOf<IView>()

    // Update cache of courses based on filtering and sorting options specified by Controllers
    private fun filterAndSortCourses() {
        filteredAndSortedCourses = rawCoursesProperty
            .run { if (!includeWD) filter { it.first.scoreProperty.value != null } else this }
            .run {
                when (courseType) {
                    CourseType.Math -> filter { it.first.isMathCourse }
                    CourseType.CS -> filter { it.first.isCSCourse }
                    CourseType.Other -> filter { !(it.first.isCSCourse || it.first.isMathCourse) }
                    CourseType.All -> this
                }
            }
            .run {
                val comparator: (SortBy) -> (Pair<Course, CourseHBox>) -> Comparable<*>? = {
                    when (it) {
                        SortBy.Code -> { pair -> pair.first.code }
                        SortBy.Score -> { pair -> pair.first.scoreProperty.value }
                        SortBy.Term -> { pair -> pair.first.termProperty.value }
                    }
                }
                sortedWith(compareBy(comparator(sortBy), comparator(thenBy)))
            }
            .run { if (!ascending) reversed() else this }
    }

    fun deleteCourse(uniqueId: Int) {
        rawCoursesProperty.removeIf { it.first.uniqueId == uniqueId }
    }

    fun addCourse(code: String, score: Int?, name: String, term: Term) {
        val course = Course(code, score, term, name)
        registeredViews.forEach { course.registerView(it) }

        val hBox = CourseHBox(course)
        rawCoursesProperty.value.add(Pair(course, hBox))
    }

    fun registerView(view: IView, forSortingOnly: Boolean = false) {
        registeredViews.add(view)

        if (forSortingOnly) {
            // without this, stats get updated unnecessarily
            sortByProperty.addListener { _, _, _ -> view.update() }
            thenByProperty.addListener { _, _, _ -> view.update() }
            ascendingProperty.addListener { _, _, _ -> view.update() }
        } else {
            rawCoursesProperty.addListener { _, _, _ -> view.update() }
            includeWDProperty.addListener { _, _, _ -> view.update() }
            courseTypeProperty.addListener { _, _, _ -> view.update() }
            rawCoursesProperty.forEach { it.first.registerView(view) }
        }
    }
}