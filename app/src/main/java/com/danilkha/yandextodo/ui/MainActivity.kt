package com.danilkha.yandextodo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.danilkha.yandextodo.R
import com.danilkha.yandextodo.ui.list.TodoListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //WindowCompat.setDecorFitsSystemWindows(window, false)

        navigate(TodoListFragment::class.java, null,false)
    }

    fun navigate(fragmentClass: Class<out Fragment>, args: Bundle? = null, addToBackStack: Boolean = true){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.root, fragmentClass, args, fragmentClass.name)
        if(addToBackStack){
            transaction.addToBackStack(fragmentClass.name)
        }

        transaction.commit()
    }
}