
package com.example.project_dicon

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private lateinit var auth: FirebaseAuth

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        var login_EMAIL = findViewById<EditText>(R.id.login_email)
        var login_PW = findViewById<EditText>(R.id.login_pw)
        var button_login = findViewById<Button>(R.id.button_login)

        button_login.setOnClickListener {
            login_EMAIL = findViewById<EditText>(R.id.login_email)
            login_PW = findViewById<EditText>(R.id.login_pw)
            auth.signInWithEmailAndPassword(login_EMAIL.text.toString(), login_PW.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        goMain()
                    } else {
                        Toast.makeText(this, "로그인 실패...", Toast.LENGTH_SHORT).show()
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