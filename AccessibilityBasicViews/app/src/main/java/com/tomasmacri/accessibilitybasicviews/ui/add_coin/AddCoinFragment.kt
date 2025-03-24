package com.tomasmacri.accessibilitybasicviews.ui.add_coin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tomasmacri.accessibilitybasicviews.R
import com.tomasmacri.accessibilitybasicviews.databinding.FragmentAddCoinBinding
import com.tomasmacri.accessibilitybasicviews.domain.model.Coin
import kotlinx.coroutines.launch

class AddCoinFragment : Fragment() {

    private lateinit var binding: FragmentAddCoinBinding

    private val viewModel = AddCoinViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentAddCoinBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListener()
        observeState()

    }

    private fun setUpListener() {
        with(binding) {
            btnAddCoin.setOnClickListener {
                val name = editTextName.text.toString()
                val code = editTextCoinCode.text.toString()
                val value = editTextValue.text.toString().toIntOrNull()
                val imageUrl = editTextImage.text.toString()

                if (name.isEmpty() || code.isEmpty() || value == null || imageUrl.isEmpty()) {
                    return@setOnClickListener
                }

                viewModel.addCoin(Coin(name, code, getString(R.string.lorem_ipsum), value, imageUrl, false))
            }
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.wasCoinAdded.collect {
                if (it) {
                    findNavController().navigateUp()
                }
            }
        }
    }
}