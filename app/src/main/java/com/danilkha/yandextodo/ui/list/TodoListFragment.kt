package com.danilkha.yandextodo.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.danilkha.yandextodo.databinding.FragmentTodoListBinding
import com.danilkha.yandextodo.ui.models.TodoItem
import com.danilkha.yandextodo.ui.MainActivity
import com.danilkha.yandextodo.ui.editor.TaskEditorFragment
import com.danilkha.yandextodo.ui.utils.app
import com.danilkha.yandextodo.ui.utils.collectWithLifecycle
import com.danilkha.yandextodo.ui.utils.viewModel

class TodoListFragment : Fragment() {


    private lateinit var binding: FragmentTodoListBinding

    private val viewModel: TodoListViewModel by viewModel { TodoListViewModel(app.repository) }

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

        return binding.root
    }


    private fun navigateToEditor(task: TodoItem? = null){
        val args = task?.let { bundleOf(TaskEditorFragment.TASK_ARG to it) }
        (requireActivity() as MainActivity).navigate(TaskEditorFragment::class.java, args)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.collectWithLifecycle(this){
            taskAdapter.submitList(it.tasks)
        }
    }

}