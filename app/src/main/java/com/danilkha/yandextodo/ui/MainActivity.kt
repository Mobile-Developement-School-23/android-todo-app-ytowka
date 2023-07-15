package com.danilkha.yandextodo.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.danilkha.yandextodo.App
import com.danilkha.yandextodo.R
import com.danilkha.yandextodo.ui.editor.TaskEditorComposeFragment
import com.danilkha.yandextodo.ui.editor.TaskEditorFragment
import com.danilkha.yandextodo.ui.list.TodoListFragment
import com.danilkha.yandextodo.worker.NotificationAlarmReceiver

class MainActivity : AppCompatActivity() {

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var isNotifPermissionGranted = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //WindowCompat.setDecorFitsSystemWindows(window, false)

        navigate(TodoListFragment::class.java, null,false)

        val id = intent?.getStringExtra(TASK_ID)
        if(id != null){
            val args = bundleOf(TaskEditorFragment.TASK_ARG_ID to id)
            navigate(TaskEditorComposeFragment::class.java, args)
        }

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ permissions ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                isNotifPermissionGranted = permissions[Manifest.permission.POST_NOTIFICATIONS] ?: isNotifPermissionGranted
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            isNotifPermissionGranted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED


            val permissionRequest : MutableList<String> = ArrayList()

            if (!isNotifPermissionGranted){
                permissionRequest.add(Manifest.permission.POST_NOTIFICATIONS)
            }

            if (permissionRequest.isNotEmpty()){
                permissionLauncher.launch(permissionRequest.toTypedArray())
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

    }

    fun navigate(fragmentClass: Class<out Fragment>, args: Bundle? = null, addToBackStack: Boolean = true){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.root, fragmentClass, args, fragmentClass.name)
        if(addToBackStack){
            transaction.addToBackStack(fragmentClass.name)
        }

        transaction.commit()
    }

    companion object{
        const val TASK_ID = "task_id"
    }
}