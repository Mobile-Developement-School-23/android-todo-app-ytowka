package com.danilkha.yandextodo.ui.editor

import android.R.color
import android.app.DatePickerDialog
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import com.danilkha.yandextodo.R
import com.danilkha.yandextodo.databinding.FragmentTaskEditBinding
import com.danilkha.yandextodo.ui.models.TodoItem
import com.danilkha.yandextodo.ui.utils.app
import com.danilkha.yandextodo.ui.utils.collectWithLifecycle
import com.danilkha.yandextodo.ui.utils.setTextDrawableColor
import com.danilkha.yandextodo.ui.utils.viewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


class TaskEditorFragment : Fragment(){

    private val task by lazy { arguments?.getParcelable(TASK_ARG) as TodoItem? }
    private lateinit var binding: FragmentTaskEditBinding

    private val taskEditorViewModel by viewModel { TaskEditorViewModel(app.repository) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskEditBinding.inflate(inflater)

        binding.deadlineSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                setDate()
            }else{
                taskEditorViewModel.processEvent(TaskEditorUserEvent.SetDate(null))
            }
        }
        task?.let {
            taskEditorViewModel.processEvent(TaskEditorUserEvent.SetEditingTask(it))
        }

        binding.delete.setOnClickListener {
            taskEditorViewModel.processEvent(TaskEditorUserEvent.Delete)
        }

        binding.save.setOnClickListener {
            taskEditorViewModel.processEvent(TaskEditorUserEvent.Save)
        }

        binding.close.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskEditorViewModel.state.collectWithLifecycle(this, this::renderState)

        taskEditorViewModel.sideEffects.collectWithLifecycle(this){
            when(it){
                TaskEditorSideEffect.Close -> {
                    parentFragmentManager.popBackStack()
                }
            }
        }
    }

    private fun renderState(state: TaskEditorState){
        if(state.task.time != null){
            binding.date.isGone = false
            binding.date.text = SimpleDateFormat("dd MMMM yyyy").format(state.task.time)
        }else {
            binding.date.isGone = true
        }

        if(state.isEditorMode){
            binding.delete.setTextDrawableColor(requireContext().getColor(R.color.color_red))
            binding.delete.setTextColor(requireContext().getColor(R.color.color_red))

        }else{
            binding.delete.setTextColor(requireContext().getColor(R.color.label_disable))
            binding.delete.setTextDrawableColor(requireContext().getColor(R.color.label_disable))
        }
    }

    private fun setDate(){
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            R.style.DatePickerStyle,
            { view, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                taskEditorViewModel.processEvent(TaskEditorUserEvent.SetDate(calendar.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }


    companion object{
        const val TASK_ARG = "task"
    }
}