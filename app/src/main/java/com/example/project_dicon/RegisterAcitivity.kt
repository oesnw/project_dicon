package com.example.project_dicon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
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

        val db = Firebase.firestore

        auth = Firebase.auth

        var register_NAME = findViewById<EditText>(R.id.register_name)
        var register_EMAIL = findViewById<EditText>(R.id.register_email)
        var register_ID = findViewById<EditText>(R.id.register_id)
        var register_PW = findViewById<EditText>(R.id.register_pw)
        var register_PW_CHK = findViewById<EditText>(R.id.register_pw_chk)
        var button_register = findViewById<Button>(R.id.button_register)
        var register_year = findViewById<Spinner>(R.id.register_year)
        var register_month = findViewById<Spinner>(R.id.register_month)
        var register_day = findViewById<Spinner>(R.id.register_day)

        val yearAdapter = ArrayAdapter(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, (2022 downTo 1900).toList() )
        val monthAdapter = ArrayAdapter(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, (1..12).toList() )
        val dayAdapter = ArrayAdapter(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, (1..31).toList() )

        register_year.adapter = yearAdapter
        register_month.adapter = monthAdapter
        register_day.adapter = dayAdapter

        button_register.setOnClickListener {
            register_EMAIL = findViewById<EditText>(R.id.register_email)
            register_PW = findViewById<EditText>(R.id.register_pw)
            auth.createUserWithEmailAndPassword(
                register_EMAIL.text.toString(),
                register_PW.text.toString()
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        goMain()
                    } else {
                        Toast.makeText(this, "회원가입 실패...", Toast.LENGTH_SHORT).show()
                    }
                }

            val user = hashMapOf(
                "name" to register_NAME.text.toString(),
                "ID" to register_ID.text.toString(),
                "year" to register_year.selectedItem.toString(),
                "month" to register_month.selectedItem.toString(),
                "day" to register_day.selectedItem.toString()
            )

            db.collection("users")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)
                }

            db.collection("users")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d("TAG", "${document.id} => ${document.data}")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("TAG", "Error getting documents.", exception)
                }
        }
    }

    private fun goMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}