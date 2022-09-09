package kotleni.duckplay.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotleni.duckplay.databinding.FragmentSavedBinding

class SavedFragment: Fragment() {
    private val binding: FragmentSavedBinding by lazy { FragmentSavedBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }
}