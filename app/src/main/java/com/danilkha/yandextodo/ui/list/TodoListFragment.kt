package com.danilkha.yandextodo.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.danilkha.yandextodo.App
import com.danilkha.yandextodo.R
import com.danilkha.yandextodo.databinding.FragmentTodoListBinding
import com.danilkha.yandextodo.ui.models.TodoItem
import com.danilkha.yandextodo.ui.MainActivity
import com.danilkha.yandextodo.ui.editor.TaskEditorComposeFragment
import com.danilkha.yandextodo.ui.editor.TaskEditorFragment
import com.danilkha.yandextodo.ui.editor.TaskEditorSideEffect
import com.danilkha.yandextodo.ui.utils.activityViewModel
import com.danilkha.yandextodo.ui.utils.app
import com.danilkha.yandextodo.ui.utils.collectWithLifecycle
import com.danilkha.yandextodo.ui.utils.viewModel
import com.danilkha.yandextodo.worker.NotificationAlarmReceiver
import java.net.UnknownHostException

class TodoListFragment : Fragment() {

    val receiver: NotificationAlarmReceiver by lazy {
       app.appComponent.notificationReceiver
    }

    private lateinit var binding: FragmentTodoListBinding

    private val viewModel: TodoListViewModel by activityViewModel { it.todoListViewModel }

    private val taskAdapter = TodoListAdapter(
        onTaskCheck = { index, checked ->
            viewModel.processEvent(TodoListUserEvent.UpdateCheckedState(index, checked))
        },
        onTaskClick = { navigateToEditor(it) },
        onNewTask = { navigateToEditor() }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoListBinding.inflate(inflater)

        binding.fabAdd.setOnClickListener {
            navigateToEditor()
        }

        binding.list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = taskAdapter
        }

        binding.completedVisibility.setOnClickListener {
            viewModel.processEvent(TodoListUserEvent.ToggleCompletedTasks)
        }

        return binding.root
    }


    private fun navigateToEditor(task: TodoItem? = null){
        val args = task?.let { bundleOf(TaskEditorFragment.TASK_ARG_ID to it.id) }
        (requireActivity() as MainActivity).navigate(TaskEditorComposeFragment::class.java, args)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.collectWithLifecycle(this, this::renderState)

        viewModel.sideEffects.collectWithLifecycle(this, this::renderSideEffect)
    }

    private fun renderState(state: TodoListState){
        binding.list.post {
            taskAdapter.submitList(state.resultTasks)
        }

        receiver.setNotifications(requireContext(), state.resultTasks)


        binding.completedVisibility.setImageDrawable( requireContext().getDrawable(
            if(state.showCompleted) R.drawable.ic_visibility
            else R.drawable.ic_visibility_off
        ))

        binding.tvCompleted.text = getString(R.string.todo_list_completed, state.completed.toString())
    }

    private fun renderSideEffect(sideEffect: TodoListSideEffect){
        when(sideEffect){
            is TodoListSideEffect.Error -> {
                val text = if(sideEffect.throwable is UnknownHostException){
                    getString( R.string.listInternetError)
                }else{
                    sideEffect.throwable.toString()
                }
                Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
            }
        }
    }

}