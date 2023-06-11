package com.danilkha.yandextodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.core.view.WindowCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val content = findViewById<FrameLayout>(R.id.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.root, TodoListFragment::class.java, null, TodoListFragment::class.simpleName)
        transaction.commit()

    }
}