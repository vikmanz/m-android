package com.vikmanz.shpppro.presentation.screens.auth

import com.vikmanz.shpppro.databinding.ActivityAuthBinding
import com.vikmanz.shpppro.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * Class represents SignIn or SignUp screen activity .
 */
@AndroidEntryPoint
class AuthActivity : BaseActivity<ActivityAuthBinding>(ActivityAuthBinding::inflate)