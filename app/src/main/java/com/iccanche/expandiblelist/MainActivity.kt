package com.iccanche.expandiblelist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.iccanche.expandiblelist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupExpandableList()
    }

    private fun setupExpandableList() {
        val expandableListView = binding.contactsExpandibleListView
        val data = getContacts()
        val listAdapter = ContactsListAdapter(baseContext, data.keys.toList(), data)
        expandableListView.setAdapter(listAdapter)
        data.keys.forEachIndexed { index, _ -> expandableListView.expandGroup(index) }
    }

    private fun getContacts(): Map<String, List<String>> {
        var listData: MutableMap<String, List<String>> = mutableMapOf()

        val a = listOf("Ana", "Alberto", "Arturo")
        val b = listOf("Berta", "Boby")
        val c = listOf("Carlos", "Carolina", "Camila")

        listData["A"] = a
        listData["B"] = b
        listData["C"] = c

        return listData
    }
}