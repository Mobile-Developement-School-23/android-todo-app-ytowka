package com.danilkha.yandextodo.ui.list

import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.danilkha.yandextodo.R
import com.danilkha.yandextodo.databinding.ItemNewTaskBinding
import com.danilkha.yandextodo.databinding.ItemTodoListBinding
import com.danilkha.yandextodo.models.Importance
import com.danilkha.yandextodo.models.TodoItem
import java.security.AccessController.getContext


class TodoListAdapter(
    val onTaskClick: (Int) -> Unit,
    val onTaskCheck: (Int, Boolean) -> Unit,
) : RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder>() {

    fun submitList(list: List<TodoItem>){
        val callback = TodoItemDiffer(
            currentList,
            list
        )
        val diff = DiffUtil.calculateDiff(callback)
        diff.dispatchUpdatesTo(this)
        currentList = list
    }

    var currentList: List<TodoItem> = listOf()
        private set

    override fun getItemCount(): Int {
        return currentList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == currentList.size){
            ITEM_TYPE_NEW_TASK
        }else{
            ITEM_TYPE_TASK
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        return when(viewType){
            ITEM_TYPE_TASK -> TodoListViewHolder.TaskViewHolder(
                ItemTodoListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            ITEM_TYPE_NEW_TASK -> TodoListViewHolder.NewTaskViewHolder(
                ItemNewTaskBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> TodoListViewHolder.TaskViewHolder(
                ItemTodoListBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        }

    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        when(holder){
            is TodoListViewHolder.NewTaskViewHolder -> Unit
            is TodoListViewHolder.TaskViewHolder -> {
                holder.bind(currentList[position])
                val context = holder.binding.root.context
                holder.binding.root.background = if (position == 0) {
                    ContextCompat.getDrawable(context, R.drawable.background_top_round)
                } else {
                    ContextCompat.getDrawable(context, R.drawable.background_rect)
                }
            }
        }

    }

    sealed class TodoListViewHolder(view: View) : RecyclerView.ViewHolder(view){
        class TaskViewHolder(val binding: ItemTodoListBinding) : TodoListViewHolder(binding.root){

            fun bind(todoItem: TodoItem) = with(binding){
                text.text = todoItem.text
                checkBox.isChecked = todoItem.completed

                val img: Drawable? = when(todoItem.importance){
                    Importance.LOW -> ContextCompat.getDrawable(root.context, R.drawable.ic_importance_high)
                    Importance.NORMAL -> null
                    Importance.HIGH -> ContextCompat.getDrawable(root.context, R.drawable.ic_importance_low)
                }
                text.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null)

                text.paintFlags = if(todoItem.completed){
                    text.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }else{
                    text.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }


            }
        }

        class NewTaskViewHolder(val binding: ItemNewTaskBinding) : TodoListViewHolder(binding.root)
    }


    companion object{
        const val ITEM_TYPE_TASK = 0
        const val ITEM_TYPE_NEW_TASK = 1

        class TodoItemDiffer(
            val oldList: List<TodoItem>,
            val newList: List<TodoItem>,
        ): DiffUtil.Callback() {
            override fun getOldListSize(): Int = oldList.size

            override fun getNewListSize(): Int = newList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition].id == newList[newItemPosition].id

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}