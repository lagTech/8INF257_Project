package com.example.android_routine.data.viewmodelobject

import com.example.android_routine.data.model.Task

data class TaskVM(
    val id: Int = -1,
    val title: String = "",
    val categoryName: String = "",
    val dueTime: String? = null,
    val dueDate: String? = null,
    val notes: String? = null,
    val priority: String? = null,
    val periodicity: String? = null,
    val isCompleted: Boolean = false
) {
    companion object {
        fun fromEntity(task: Task, categoryName: String): TaskVM {
            return TaskVM(
                id = task.id ?: -1,
                title = task.title,
                categoryName = categoryName,
                dueTime = task.dueTime,
                dueDate = task.dueDate,
                notes = task.notes,
                priority = task.priority,
                periodicity = task.periodicity,
                isCompleted = task.isCompleted
            )
        }
    }
}

fun TaskVM.toEntity(categoryId: Int?): Task {
    return Task(
        id = if (this.id == -1) null else this.id,
        title = this.title,
        categoryId = categoryId,
        dueTime = this.dueTime,
        dueDate = this.dueDate,
        notes = this.notes,
        priority = this.priority,
        periodicity = this.periodicity,
        isCompleted = this.isCompleted
    )
}
