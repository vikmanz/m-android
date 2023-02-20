package com.vikmanz.shpppro.myContactsActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.vikmanz.shpppro.authActivity.AuthActivity
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.constants.Constants
import com.vikmanz.shpppro.databinding.ActivityMyContactsBinding
import com.vikmanz.shpppro.myContactsActivity.contactModel.*

/**
 * Class represents MyContacts screen activity.
 */
class MyContactsActivity : AppCompatActivity() {

    // Binding, Data Store and Coroutine Scope variables.
    private lateinit var binding: ActivityMyContactsBinding

    // ініціалізуємо viewModel з використанням viewModels()
    private val viewModel: MyContactsViewModel by viewModels()

    // Recycler View variables.
    private lateinit var adapter: ContactsAdapter

    private var imgCounter = 0

    /**
     * Main function, which used when activity was create.
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        // Init activity.
        super.onCreate(savedInstanceState)

        // Others init operations.
        binding = ActivityMyContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Back button.
        binding.btnBack.setOnClickListener {
            startAuthActivity()
        }

        initRecyclerView()

        binding.tvAddContacts.setOnClickListener {
           addNewContact()
        }

    }

    private fun initRecyclerView() {
        with(binding) {
            recyclerViewMyContacts.layoutManager = LinearLayoutManager(this@MyContactsActivity)
            recyclerViewMyContacts.addItemDecoration(MarginItemDecoration(20))
            adapter = ContactsAdapter(viewModel)
            recyclerViewMyContacts.adapter = adapter
        }
    }

    private fun startAuthActivity() {
        val intentObject = Intent(this, AuthActivity::class.java)
        intentObject.putExtra(Constants.INTENT_LANG_ID, false)
        startActivity(intentObject)
        overridePendingTransition(R.anim.zoom_in_inner, R.anim.zoom_in_outter)
        finish()
    }

    private fun addNewContact() {
       // Log.d("mylog", "add contact in Activity start!")
        imgCounter++
        adapter.addContact(Contact(
                imgCounter.toLong(),
                ContactsService.IMAGES[imgCounter % ContactsService.IMAGES.size],
                "Namene",
                "Coompany"))
        // Log.d("mylog", "add contact in Activity end!")
    }

}