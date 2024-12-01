package com.example.novnavex

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.novnavex.databinding.ActivityLoginBinding
import com.example.novnavex.viewmodel.UserViewModel

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        // ViewModel bağlama
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        // ViewModel ve LifecycleOwner ayarları
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // Login butonuna tıklama olayı
        binding.logInButton.setOnClickListener {
            val playerName = binding.textInputLayout.editText?.text.toString()
            // Burada navigation işlemi yapılabilir veya başka bir işlem gerçekleştirilebilir.
            // Örneğin:
            // startActivity(Intent(this, AchievementActivity::class.java))
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}
