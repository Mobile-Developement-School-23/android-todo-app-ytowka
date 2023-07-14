package com.danilkha.yandextodo.ui.editor

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.danilkha.yandextodo.R
import com.danilkha.yandextodo.databinding.FragmentTaskEditBinding
import com.danilkha.yandextodo.ui.list.TodoListUserEvent
import com.danilkha.yandextodo.ui.models.TodoItem
import com.danilkha.yandextodo.ui.theme.YandexTodoTheme
import com.danilkha.yandextodo.ui.utils.activityViewModel
import com.danilkha.yandextodo.ui.utils.collectWithLifecycle
import com.danilkha.yandextodo.ui.utils.viewModel
import java.net.UnknownHostException
import java.util.Calendar

class TaskEditorComposeFragment : Fragment(){

    private val task by lazy { arguments?.getParcelable(TaskEditorFragment.TASK_ARG) as TodoItem? }
    private lateinit var binding: FragmentTaskEditBinding

    private val taskEditorViewModel by viewModel { it.taskEditorViewModel }
    private val taskListViewModel by activityViewModel { it.todoListViewModel}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            this.setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            task?.let {
                taskEditorViewModel.processEvent(TaskEditorUserEvent.SetEditingTask(it))
            }
            setContent {
                YandexTodoTheme{
                    val state by taskEditorViewModel.state.collectAsState(taskEditorViewModel.startState)

                    TaskEditor(
                        state = state,
                        onBackClicked = {
                            parentFragmentManager.popBackStack()
                        },
                        onTextUpdate = {
                            taskEditorViewModel.processEvent(TaskEditorUserEvent.UpdateText(it ?: ""))
                        },
                        onDateChecked = {
                            if(it){
                                setDate()
                            }
                        },
                        onSaveClicked = {
                            taskEditorViewModel.processEvent(TaskEditorUserEvent.Save)
                        },
                        onDeleteClicked = {
                            taskEditorViewModel.processEvent(TaskEditorUserEvent.Delete)
                        },
                        onImportanceSelected = {

                        }
                    )
                }
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskEditorViewModel.sideEffects.collectWithLifecycle(this, this::renderSideEffects)
    }

    private fun renderSideEffects(sideEffect: TaskEditorSideEffect){
        when(sideEffect){
            TaskEditorSideEffect.DataUpdated -> {
                taskListViewModel.processEvent(TodoListUserEvent.UpdateData)
                parentFragmentManager.popBackStack()
            }

            is TaskEditorSideEffect.Error -> {
                val text = if(sideEffect.throwable is UnknownHostException){
                    getString( R.string.internetError)
                }else{
                    sideEffect.throwable.toString()
                }
                Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setDate(){
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { view, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                taskEditorViewModel.processEvent(TaskEditorUserEvent.SetDate(calendar.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            setOnCancelListener {
                binding.deadlineSwitch.isChecked = false
            }
            show()
        }
    }
}