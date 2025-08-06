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

class loginpage : AppCompatActivity() {

    // ✅ Declare mAuth as a class-level variable
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_loginpage)

        // ✅ Initialize mAuth
        mAuth = FirebaseAuth.getInstance()

        val etEmail = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val etPassword = findViewById<EditText>(R.id.editTextTextPassword)
        val btnLogin = findViewById<Button>(R.id.button2)
        val signupTextView = findViewById<TextView>(R.id.textView6)

        signupTextView.setOnClickListener {
            val intent = Intent(this, SignupusingEmail::class.java)
            startActivity(intent)
        }



        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            login(email, password) // ✅ Call correct function
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }




    private fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@loginpage, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@loginpage, "User does not exist", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
