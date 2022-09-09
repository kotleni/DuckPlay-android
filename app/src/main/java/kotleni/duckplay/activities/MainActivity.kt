package kotleni.duckplay.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotleni.duckplay.R
import kotleni.duckplay.databinding.ActivityMainBinding
import kotleni.duckplay.fragments.GamesFragment
import kotleni.duckplay.fragments.SavedFragment

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.appBar)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, GamesFragment())
        transaction.commit()

        binding.navigation.setOnItemSelectedListener {
            val transaction = supportFragmentManager.beginTransaction()
            when(it.itemId) {
                R.id.games -> {
                    transaction.replace(R.id.container, GamesFragment())
                }
                R.id.saved -> {
                    transaction.replace(R.id.container, SavedFragment())
                }
            }
            transaction.commit()

            true
        }
    }
}