package com.android.blendit.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.android.blendit.adapter.AdapterListFavorite
import com.android.blendit.databinding.FragmentFavoriteBinding
import com.android.blendit.preference.AccountPreference
import com.android.blendit.remote.Result
import com.android.blendit.remote.response.ItemsFavorite
import com.android.blendit.ui.ViewModelFactory
import com.android.blendit.ui.main.MainViewModel

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private val accountPreference by lazy { AccountPreference(requireActivity()) }
    private val adapter = AdapterListFavorite()
    private val mainViewModel by activityViewModels<MainViewModel> {
        ViewModelFactory(
            accountPreference
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFullscreen()
        setView()
    }

    private fun setView() {
        mainViewModel.getListFavorite().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Error -> {
                    showLoading(false)
                }

                is Result.Success -> {
                    adapter.setList(result.data)
                    showLoading(false)
                }
            }
        }

        binding.recyclerView.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setFullscreen() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.appBar.setPadding(0, systemBars.top, 0, 0)
            insets
        }
    }

}