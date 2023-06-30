package com.danilkha.yandextodo.ui.editor

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.danilkha.yandextodo.R
import com.danilkha.yandextodo.databinding.FragmentTaskEditBinding
import com.danilkha.yandextodo.ui.list.TodoListUserEvent
import com.danilkha.yandextodo.ui.list.TodoListViewModel
import com.danilkha.yandextodo.ui.models.Importance
import com.danilkha.yandextodo.ui.models.TodoItem
import com.danilkha.yandextodo.ui.utils.activityViewModel
import com.danilkha.yandextodo.ui.utils.app
import com.danilkha.yandextodo.ui.utils.collectWithLifecycle
import com.danilkha.yandextodo.ui.utils.setTextDrawableColor
import com.danilkha.yandextodo.ui.utils.viewModel
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.Calendar


class TaskEditorFragment : Fragment(){

    private val task by lazy { arguments?.getParcelable(TASK_ARG) as TodoItem? }
    private lateinit var binding: FragmentTaskEditBinding

    private val taskEditorViewModel by viewModel { TaskEditorViewModel(
        app.mainModule.updateTaskUseCase(),
        app.mainModule.deleteTaskUseCase(),
        app.mainModule.createTaskUseCase()
    ) }
    private val taskListViewModel by activityViewModel { TodoListViewModel(
        app.mainModule.getAllTaskUseCase(),
        app.mainModule.updateTaskCompeteUseCase()
    ) }


    private val textWatcher = object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            taskEditorViewModel.processEvent(TaskEditorUserEvent.UpdateText(s?.toString() ?: ""))
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskEditBinding.inflate(inflater)

        task?.let {
            taskEditorViewModel.processEvent(TaskEditorUserEvent.SetEditingTask(it))
        }

        binding.save.setOnClickListener {
            taskEditorViewModel.processEvent(TaskEditorUserEvent.Save)
        }

        binding.close.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.importanceLayout.let { view ->
            registerForContextMenu(view)
            view.setOnClickListener {
                view.showContextMenu(it.pivotX - it.width, it.pivotY - it.height)
            }
        }

        binding.text.addTextChangedListener(textWatcher)


        return binding.root
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        requireActivity().menuInflater.inflate(R.menu.importance_menu, menu)
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        val importance = when(item.itemId){
            R.id.item_no -> Importance.NORMAL
            R.id.item_low ->  Importance.LOW
            R.id.item_high -> Importance.HIGH
            else -> Importance.NORMAL
        }
        taskEditorViewModel.processEvent(TaskEditorUserEvent.SetImportance(importance))
        return super.onContextItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskEditorViewModel.state.collectWithLifecycle(this, this::renderState)
        taskEditorViewModel.sideEffects.collectWithLifecycle(this, this::renderSideEffects)
    }

    private fun renderState(state: TaskEditorState){

        binding.deadlineSwitch.setOnCheckedChangeListener(null)
        if(state.task.time != null){
            binding.deadlineSwitch.isChecked = true
            binding.date.isInvisible = false
            binding.date.text = SimpleDateFormat("dd MMMM yyyy").format(state.task.time)
        }else {
            binding.deadlineSwitch.isChecked = false
            binding.date.isInvisible = true
        }

        if(state.isEditorMode){
            binding.delete.setOnClickListener {
                taskEditorViewModel.processEvent(TaskEditorUserEvent.Delete)
            }
        }

        binding.importanceText.text = getString(
            when(state.task.importance){
                Importance.LOW ->  R.string.importance_low
                Importance.NORMAL -> R.string.no
                Importance.HIGH -> R.string.importance_high
            }
        )

        if(state.isEditorMode){
            binding.delete.setTextDrawableColor(requireContext().getColor(R.color.color_red))
            binding.delete.setTextColor(requireContext().getColor(R.color.color_red))

        }else{
            binding.delete.setTextColor(requireContext().getColor(R.color.label_disable))
            binding.delete.setTextDrawableColor(requireContext().getColor(R.color.label_disable))
        }
        if(binding.text.text.toString() != state.task.text){
            binding.text.setText(state.task.text)
        }

        binding.deadlineSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                setDate()
            }else{
                taskEditorViewModel.processEvent(TaskEditorUserEvent.SetDate(null))
            }
        }
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
        ).apply {
            setOnCancelListener {
                binding.deadlineSwitch.isChecked = false
            }
            show()
        }
    }


    companion object{
        const val TASK_ARG = "task"
    }
}