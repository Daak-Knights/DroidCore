package com.daakknights.room

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = DreamListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        dreamViewModel.allDreams.observe(this, { dreams ->
            dreams?.let { adapter.submitList(it) }
        })
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, BucketListCreatorActivity::class.java)
            startActivityForResult(intent, activityRequestCode)
        }
    }

    private val activityRequestCode = 999
    private val dreamViewModel: DreamViewModel by viewModels {
        DreamViewModelFactory((application as RoomApplication).repository)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == activityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(BucketListCreatorActivity.EXTRA_DREAM)?.let {
                val dream = Dream(it)
                dreamViewModel.insert(dream)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_error,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}