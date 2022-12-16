package com.example.recycleview

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class EditContactActivity : AppCompatActivity() {


    private val contactDatabase by lazy {
        ContactDatabase.getDatabase(this).contactDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)
        setTitle("Изменение контакта")
        val id = intent.getLongExtra(ContactActivity.EXTRA_KEY, -1)
        val Name = findViewById<EditText>(R.id.editTextName)
        val LastName = findViewById<EditText>(R.id.editTextLastName)
        val Birthday = findViewById<EditText>(R.id.editTextBirthday)
        val Number = findViewById<EditText>(R.id.editTextPhoneNumber)
        val buttonDone = findViewById<Button>(R.id.buttonDone)
        if (id >= 0) {
            val conEntity = contactDatabase.getById(id)
            Name.setText(conEntity.firstName)
            LastName.setText(conEntity.lastName)
            Birthday.setText(conEntity.birthdayDate)
            Number.setText(conEntity.phoneNumber)
        }
        var contactEntity: ContactEntity = ContactEntity()
        buttonDone.setOnClickListener {
            if (id >= 0)    //update
            {
                contactEntity.id = id
                contactEntity.firstName = Name.text.toString()
                contactEntity.lastName = LastName.text.toString()
                contactEntity.birthdayDate = Birthday.text.toString()
                contactEntity.phoneNumber = Number.text.toString()
                contactDatabase.update(contactEntity)
                val startContactActivity = Intent(this, ContactActivity::class.java)
                startContactActivity.putExtra(ContactActivity.EXTRA_KEY, id)
                startActivity(startContactActivity)
                finish()
            } else {         //insert
                contactEntity.firstName = Name.text.toString()
                contactEntity.lastName = LastName.text.toString()
                contactEntity.birthdayDate = Birthday.text.toString()
                contactEntity.phoneNumber = Number.text.toString()
                contactDatabase.insert(contactEntity)
                val mainActivity = Intent(this, MainActivity::class.java)
                startActivity(mainActivity)
                finish()
            }
        }
    }
}