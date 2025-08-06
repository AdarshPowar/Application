package com.techad.application

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class SignupusingEmail : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signupusing_email)

        mAuth = FirebaseAuth.getInstance()

        val etEmail = findViewById<EditText>(R.id.editTextTextEmailAddress2)
        val etPassword = findViewById<EditText>(R.id.editTextTextPassword2)
        val btnSignup = findViewById<Button>(R.id.button5)
        val loginWithMobile = findViewById<TextView>(R.id.textView14)

        loginWithMobile.setOnClickListener {
            val intent = Intent(this, signup::class.java)
            startActivity(intent)
        }

        btnSignup.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Enter a valid email", Toast.LENGTH_SHORT).show()
            } else {
                signup(email, password)
            }
        }

        btnSignup.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            signup(email, password) // ✅ Call correct function
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // ✅ Moved out and marked private
    private fun signup(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            return
        }

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@SignupusingEmail, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this@SignupusingEmail,
                        "Some error occurred",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}


