package com.example.android_routine.ui.screens.addtask

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_routine.data.model.Task
import com.example.android_routine.data.repository.TaskRepository
import com.example.android_routine.utils.RoutinesUtils
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddTaskViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddTaskUiState())
    val uiState: StateFlow<AddTaskUiState> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    // Sealed class for one-time UI events
    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        data class Navigate(val route: String) : UiEvent() // ← new
    }

    // Sealed class for user actions
    sealed class TaskEvent {
        data class UpdateTitle(val title: String) : TaskEvent()
        data class UpdateDueDate(val dueDate: String) : TaskEvent()
        data class UpdateDueTime(val dueTime: String) : TaskEvent()
        data class UpdatePeriodicity(val periodicity: String) : TaskEvent()
        data class UpdatePriority(val priority: String): TaskEvent()
        data class UpdateNotes(val notes: String) : TaskEvent()
        data class UpdateCategory(val id: Int, val name: String) : TaskEvent()
        data class Submit(val context: Context, val utils: RoutinesUtils) : TaskEvent()
    }

    fun onEvent(event: TaskEvent, context: Context) {
        when (event) {
            is TaskEvent.UpdateTitle -> updateTitle(event.title)
            is TaskEvent.UpdateDueDate -> updateDueDate(event.dueDate)
            is TaskEvent.UpdateDueTime -> updateDueTime(event.dueTime)
            is TaskEvent.UpdatePeriodicity -> updatePeriodicity(event.periodicity)
            is TaskEvent.UpdatePriority -> updatePriority(event.priority)
            is TaskEvent.UpdateNotes -> updateNotes(event.notes)
            is TaskEvent.UpdateCategory -> updateCategory(event.id, event.name)
            is TaskEvent.Submit -> submitTask(context, event.utils)
        }
    }

    private fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title, isError = false) }
    }

    private fun updateDueDate(dueDate: String) {
        _uiState.update { it.copy(dueDate = dueDate) }
    }

    private fun updateDueTime(dueTime: String) {
        _uiState.update { it.copy(dueTime = dueTime) }
    }

    private fun updatePeriodicity(periodicity: String) {
        _uiState.update { it.copy(periodicity = periodicity) }
    }

    private fun updatePriority(priority: String){
        _uiState.update { it.copy(priority = priority) }
    }

    private fun updateNotes(notes: String) {
        _uiState.update { it.copy(notes = notes) }
    }

    private fun updateCategory(id: Int, name: String) {
        _uiState.update { it.copy(categoryId = id, categoryName = name) }
    }



    private fun submitTask(context: Context, utils: RoutinesUtils) {
        val state = _uiState.value

        if (state.title.isBlank()) {
            _uiState.update { it.copy(isError = true, errorMessage = "Title can't be empty") }
            viewModelScope.launch {
                _eventFlow.emit(UiEvent.ShowSnackbar(" Enter a task title"))
            }
            return
        }
        if (state.categoryId == null) {
            viewModelScope.launch {
                _eventFlow.emit(UiEvent.ShowSnackbar("Please select a category"))
            }
            return
        }

        if (state.dueDate.isBlank()) {
            viewModelScope.launch {
                _eventFlow.emit(UiEvent.ShowSnackbar("Please select a due date"))
            }
            return
        }

        val formattedDueDate = try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val parsedDate = inputFormat.parse(state.dueDate)
            inputFormat.format(parsedDate ?: Date())
        } catch (e: Exception) {
            null
        }


        viewModelScope.launch {
            val newTask = Task(
                id = null,
                title = state.title,
                categoryId = state.categoryId,
                dueTime = state.dueTime.takeIf { it.isNotBlank() },
                dueDate = formattedDueDate,
                notes = state.notes.takeIf { it.isNotBlank() },
                priority = state.priority,
                periodicity = state.periodicity,
                isCompleted = false
            )

            val id = repository.upsert(newTask)

            if (newTask.dueTime != null && newTask.dueDate != null) {
                when (newTask.periodicity) {
                    "Once" -> {
                        utils.scheduleTaskReminder(
                            context = context,
                            taskId = id.toInt(),
                            title = newTask.title,
                            dueDate = newTask.dueDate,
                            dueTime = newTask.dueTime
                        )
                    }
                    "Daily" -> {
                        utils.scheduleDailyExactReminder(
                            context = context,
                            taskId = id.toInt(),
                            title = newTask.title,
                            dueTime = newTask.dueTime
                        )
                        Log.d("ReminderTest", "Scheduled DAILY task for ${newTask.title} at ${newTask.dueTime}")

                    }
                    "Weekly" -> {
                        utils.scheduleRecurringReminder(
                            context = context,
                            taskId = id.toInt(),
                            title = newTask.title,
                            intervalDays = 7
                        )
                    }
                    "Monthly" -> {
                        utils.scheduleRecurringReminder(
                            context = context,
                            taskId = id.toInt(),
                            title = newTask.title,
                            intervalDays = 30
                        )
                    }
                    "Yearly" -> {
                        utils.scheduleRecurringReminder(
                            context = context,
                            taskId =  id.toInt(),
                            title = newTask.title,
                            intervalDays = 365
                        )
                    }
                }
            }


            _eventFlow.emit(UiEvent.ShowSnackbar("Task added successfully"))
            _eventFlow.emit(UiEvent.Navigate(Screen.Home.route))

        }
    }
}
