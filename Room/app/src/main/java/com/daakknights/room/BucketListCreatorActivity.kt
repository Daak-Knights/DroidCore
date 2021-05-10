package com.daakknights.room

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class BucketListCreatorActivity : AppCompatActivity() {
    private lateinit var etDream: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bucket_list_creator)
        etDream = findViewById(R.id.etDream)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(etDream.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val dream = etDream.text.toString()
                replyIntent.putExtra(EXTRA_DREAM, dream)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_DREAM = "DREAM"
    }
}