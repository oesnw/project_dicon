package com.example.project_dicon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private lateinit var auth: FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth

        val register_NAME = findViewById<EditText>(R.id.register_name)
        val register_EMAIL = findViewById<EditText>(R.id.register_email)
        val register_ID = findViewById<EditText>(R.id.register_id)
        val register_PW = findViewById<EditText>(R.id.register_pw)
        val register_PW_CHK = findViewById<EditText>(R.id.register_pw_chk)
        val button_register = findViewById<Button>(R.id.button_register)

        val db = Firebase.firestore

        button_register.setOnClickListener {
            auth.createUserWithEmailAndPassword(register_EMAIL.toString(), register_PW.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        goMain()
                    } else {
                        Toast.makeText(this, "회원가입 실패...", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun goMain()
    {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}