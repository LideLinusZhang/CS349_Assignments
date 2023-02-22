package ca.uwaterloo.a2enhanced.model

import ca.uwaterloo.a2enhanced.model.enums.CourseType
import ca.uwaterloo.a2enhanced.model.enums.Term
import ca.uwaterloo.a2enhanced.view.CourseHBox
import ca.uwaterloo.a2enhanced.view.IView
import javafx.beans.property.ReadOnlyListWrapper
import javafx.collections.FXCollections

object CourseList {
    private val coursesObservableList = FXCollections.observableArrayList<CourseAndHBoxProperty> { arrayOf(it) }
    private val sortedCoursesProperty = ReadOnlyListWrapper(
        coursesObservableList.sorted(compareBy { it.value.termProperty.value })
    )

    val courses: List<Course> get() = coursesObservableList.map { it.value }
    val coursesByTerm: Map<Term, List<Course>> get() = courses.groupBy { it.termProperty.value }
    val coursesByType: Map<CourseType, List<Course>> get() = courses.groupBy { it.type }
    val courseHBoxes: List<CourseHBox> get() = sortedCoursesProperty.map { it.courseHBox }
    val courseAverage: Double get() = courses.mapNotNull { it.scoreProperty.value }.average()
    val courseCount: Int get() = courses.count()
    val courseWDCount: Int get() = courses.count { it.scoreProperty.value == null }
    val courseFailedCount: Int get() = courses.count { it.isFailed }

    fun deleteCourse(uniqueId: Int) {
        coursesObservableList.removeIf { it.value.uniqueId == uniqueId }
    }

    fun addCourse(code: String, score: Int?, term: Term) {
        val property = CourseAndHBoxProperty(code, score, term)
        coursesObservableList.add(property)
    }

    fun registerView(view: IView) {
        sortedCoursesProperty.addListener { _, _, _ -> view.update() }
    }
}