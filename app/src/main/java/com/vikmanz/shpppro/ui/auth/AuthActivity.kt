package com.vikmanz.shpppro.ui.auth

import com.vikmanz.shpppro.base.BaseActivity
import com.vikmanz.shpppro.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Class represents SignIn or SignUp screen activity .
 */
@AndroidEntryPoint
class AuthActivity : BaseActivity<ActivityAuthBinding>(ActivityAuthBinding::inflate)