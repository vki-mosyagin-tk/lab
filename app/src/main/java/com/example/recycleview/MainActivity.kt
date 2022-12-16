package com.example.recycleview

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_KEY = "EXTRA"
    }

    private val contactDatabase by lazy {
        ContactDatabase.getDatabase(this).contactDao()
    }

    private lateinit var adapter: RecyclerAdapter
    var ListOfContacts = mutableListOf<ContactEntity>()
    var newFilterList = mutableListOf<ContactEntity>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contactDao = contactDatabase
        setContentView(R.layout.activity_main)
        setTitle("Контакты")
        adapter = RecyclerAdapter(ListOfContacts) {
            val intent = Intent(this, ContactActivity::class.java)
            intent.putExtra(EXTRA_KEY, ListOfContacts[it].id)
            startActivity(intent)
            finish()
        }
        val RecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        ListOfContacts.clear()
        ListOfContacts.addAll(contactDao.all)
        val FindContact = findViewById<EditText>(R.id.EditText)
        val findButton = findViewById<Button>(R.id.findButton)
        val CreateContact = findViewById<Button>(R.id.button)
        RecyclerView.layoutManager = LinearLayoutManager(this)
        RecyclerView.adapter = adapter
        CreateContact.setOnClickListener() {
            val editContactActivity = Intent(this, EditContactActivity::class.java)
            startActivity(editContactActivity)
            finish()
        }
        findButton.setOnClickListener() {
            if (FindContact.text.isEmpty()) {
                ListOfContacts.clear()
                ListOfContacts.addAll(contactDao.all)
                adapter = RecyclerAdapter(ListOfContacts) {
                    val intent = Intent(this, ContactActivity::class.java)
                    intent.putExtra(EXTRA_KEY, ListOfContacts[it].id)
                    startActivity(intent)
                    finish()
                }
                RecyclerView.adapter = adapter
                adapter.run { notifyDataSetChanged() }
            } else {
                newFilterList = ListOfContacts.filter {
                    FindContact.text.toString().lowercase() in it.firstName.lowercase() ||
                            FindContact.text.toString().lowercase() in it.lastName.lowercase()
                }.toMutableList()
                adapter = RecyclerAdapter(newFilterList) {
                    val intent = Intent(this, ContactActivity::class.java)
                    intent.putExtra(EXTRA_KEY, newFilterList[it].id)
                    startActivity(intent)
                    finish()
                }
                RecyclerView.adapter = adapter
                adapter.run { notifyDataSetChanged() }
            }
        }
    }
}