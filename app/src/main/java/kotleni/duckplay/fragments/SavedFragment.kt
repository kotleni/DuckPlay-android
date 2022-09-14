package kotleni.duckplay.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotleni.duckplay.activities.GameActivity
import kotleni.duckplay.adapters.SavedListAdapter
import kotleni.duckplay.createViewModel
import kotleni.duckplay.databinding.FragmentSavedBinding
import kotleni.duckplay.repositories.LocalGamesRepository
import kotleni.duckplay.viewmodels.SavedViewModel

class SavedFragment: Fragment() {
    private val binding: FragmentSavedBinding by lazy { FragmentSavedBinding.inflate(layoutInflater) }
    private val localGamesRepository: LocalGamesRepository by lazy { LocalGamesRepository() }
    private val viewModel: SavedViewModel by lazy { createViewModel(SavedViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val savedListAdapter = SavedListAdapter()
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = savedListAdapter
        savedListAdapter.setOnItemClickListener { pos, game ->
            val intent = Intent(requireActivity(), GameActivity::class.java)
            intent.putExtra("id", game.id)
            intent.putExtra("isoffline", true)
            startActivity(intent)
        }

        viewModel.getLocalGames().observe(this) {
            savedListAdapter.updateLocalGames(it)
        }

        viewModel.loadGames()
    }
}