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

    private val task by lazy { arguments?.getString(TaskEditorFragment.TASK_ARG_ID)}

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
                taskEditorViewModel.processEvent(TaskEditorUserEvent.SetEditingTaskId(it))
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
                        onDateSelected = {
                            taskEditorViewModel.processEvent(TaskEditorUserEvent.SetDate(it))
                        },
                        onSaveClicked = {
                            taskEditorViewModel.processEvent(TaskEditorUserEvent.Save)
                        },
                        onDeleteClicked = {
                            taskEditorViewModel.processEvent(TaskEditorUserEvent.Delete)
                        },
                        onImportanceSelected = {
                            taskEditorViewModel.processEvent(TaskEditorUserEvent.SetImportance(it))
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
}