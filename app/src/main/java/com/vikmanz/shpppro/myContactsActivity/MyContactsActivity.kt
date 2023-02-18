package com.vikmanz.shpppro.myContactsActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.vikmanz.shpppro.authActivity.AuthActivity
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.constants.Constants
import com.vikmanz.shpppro.databinding.ActivityMyContactsBinding
import com.vikmanz.shpppro.myContactsActivity.contactsRecycler.MarginItemDecoration
import com.vikmanz.shpppro.myContactsActivity.contactsRecycler.OneContact
import com.vikmanz.shpppro.myContactsActivity.contactsRecycler.OneContactAdapter

/**
 * Class represents MyContacts screen activity.
 */
class MyContactsActivity : AppCompatActivity() {

    // Binding, Data Store and Coroutine Scope variables.
    private lateinit var binding: ActivityMyContactsBinding
    private val adapter = OneContactAdapter()


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

    }

    private fun initRecyclerView() {
        with(binding) {
            recyclerViewMyContacts.layoutManager = LinearLayoutManager(this@MyContactsActivity)
            recyclerViewMyContacts.addItemDecoration(MarginItemDecoration(20))
            recyclerViewMyContacts.adapter = adapter

            val images = listOf(
                R.drawable.avatar1,
                R.drawable.avatar2,
                R.drawable.avatar3,
                R.drawable.avatar4,
                R.drawable.avatar5,
                R.drawable.avatar6,
                R.drawable.avatar7,
                R.drawable.avatar8,
                R.drawable.avatar9,
                R.drawable.avatar10,
                R.drawable.avatar11,
                R.drawable.avatar12,
                R.drawable.avatar13,
                R.drawable.avatar14,
                R.drawable.avatar15,
                R.drawable.avatar16,
                R.drawable.avatar17,
                R.drawable.avatar18,
                R.drawable.avatar19,
                R.drawable.avatar20
            )

            val names = listOf(
                "Sarah Conor",
                "Djonny",
                "Chestonosec",
                "Arestovich",
                "Mamasha",
                "Andre",
                "Dick",
                "Index",
                "Lala",
                "Po",
                "Tinki Winki",
                "Винни Пух",
                "Сова",
                "Пятачок",
                "Ослик Иа",
                "Пчёлы",
                "Kozel",
                "Kurwa",
                "Ejik",
                "Unknown"
            )

            val careers = listOf(
                "Мать спасителя",
                "Ничего не знающий Сноу",
                "Deus Vult",
                "2-3 тижні",
                "С прицепом",
                "Стефано",
                "James Dick",
                "i = 0",
                "lalalalalalala",
                "Her",
                "Dixi, Lala, Po",
                "Грех чревоугодия",
                "Грех гордости",
                "Грех похоти",
                "Грех лени",
                "Грех гнева",
                "Pivo",
                "Plyatska",
                "Perviy raz povijav eja!",
                "Return to sender"
            )


            for (i in 0 until images.size) {
                adapter.addContact(OneContact(images[i], names[i], careers[i]))
            }
        }
    }

    private fun startAuthActivity() {
        val intentObject = Intent(this, AuthActivity::class.java)
        intentObject.putExtra(Constants.INTENT_LANG_ID, false)
        startActivity(intentObject)
        overridePendingTransition(R.anim.zoom_in_inner, R.anim.zoom_in_outter)
        finish()
    }


}