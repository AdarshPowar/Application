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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase



class SignupusingEmail : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSignup: Button
    private lateinit var loginWithMobile: TextView
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signupusing_email)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        val etEmail = findViewById<EditText>(R.id.editTextTextEmailAddress2)
        val etPassword = findViewById<EditText>(R.id.editTextTextPassword2)
        val btnSignup = findViewById<Button>(R.id.button5)
        val loginWithMobile = findViewById<TextView>(R.id.textView14)
        val etName = findViewById<EditText>(R.id.NameoftheUser)

        loginWithMobile.setOnClickListener {
            val intent = Intent(this, signup::class.java)
            startActivity(intent)
        }

        btnSignup.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Enter a valid email", Toast.LENGTH_SHORT).show()
            } else {
                signup(name, email, password)
            }
        }

        btnSignup.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            signup(name,email, password) // ✅ Call correct function
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // ✅ Moved out and marked private
    private fun signup(name: String, email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            return
        }

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    addUserToDatabase(name,email,mAuth.currentUser?.uid)

                    val intent = Intent(this@SignupusingEmail, MainActivity::class.java)
                    finish()
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

    private fun addUserToDatabase(name: String, email: String, uid: String?) {
        if (uid == null) {
            Toast.makeText(this, "UID is null, user not added to database", Toast.LENGTH_SHORT).show()
            return
        }

        mDbRef = FirebaseDatabase.getInstance().getReference()

        mDbRef.child("User").child(uid).setValue(User(name, email, uid))

    }



}


