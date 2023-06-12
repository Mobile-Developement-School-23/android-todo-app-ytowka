package com.danilkha.yandextodo.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.danilkha.yandextodo.databinding.FragmentTodoListBinding
import com.danilkha.yandextodo.domain.TodoItemsRepository
import com.danilkha.yandextodo.ui.utils.viewModel

class TodoListFragment : Fragment() {


    private lateinit var binding: FragmentTodoListBinding

    private val viewModel: TodoListViewModel by viewModel { TodoListViewModel(TodoItemsRepository.Impl()) }

    private val taskAdapter = TodoListAdapter(
        onTaskCheck = { index, checked -> },
        onTaskClick = { index -> }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTodoListBinding.inflate(inflater)


        binding.list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = taskAdapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.taskList.observe(viewLifecycleOwner){
            taskAdapter.submitList(it)
        }
    }

}