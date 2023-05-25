package com.vikmanz.shpppro.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * Base activity with binding to extend it in activities.
 */
abstract class BaseActivity<VBinding : ViewBinding>(
    private val inflaterMethod: (LayoutInflater) -> VBinding
) :
    AppCompatActivity() {

    private var _binding: VBinding? = null
    val binding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflaterMethod.invoke(layoutInflater).also{setContentView(it.root)}
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}