package com.tomasmacri.accessibilitybasicviews.ui.detail_coin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.tomasmacri.accessibilitybasicviews.databinding.FragmentDetailCoinBinding
import com.tomasmacri.accessibilitybasicviews.utils.setAccessibilityHeading
import com.tomasmacri.accessibilitybasicviews.utils.setImportanceForAccessibility
import kotlinx.coroutines.launch

class DetailCoinFragment : Fragment() {

    private val coinCode by lazy {
        val args by navArgs<DetailCoinFragmentArgs>()
        args.coinCode
    }

    private lateinit var binding: FragmentDetailCoinBinding

    private val viewModel = DetailCoinViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentDetailCoinBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        observeState()
        viewModel.loadCoin(coinCode)
    }

    private fun setUpListeners() {
        with(binding) {
            clCoinCard.setImportanceForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS)
            cvCoinType.setAccessibilityHeading()

            tvDescriptionTitle.setAccessibilityHeading()
            tvOperateTitle.setAccessibilityHeading()

            btnSmallChange.setOnClickListener {
                viewModel.changeDescription()
            }

            btnBigChange.setOnClickListener {
                viewModel.changePrice()
            }

            btnCreateCoin.setOnClickListener {
                findNavController().navigate(com.tomasmacri.accessibilitybasicviews.ui.detail_coin.DetailCoinFragmentDirections.actionCoinToForm())
            }

        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.coin.collect { coin ->
                coin?.let {
                    with(binding) {
                        ivLogo.load(coin.imageUrl)
                        tvCoinName.text = "${coin.name} (${coin.code})"
                        tvCoinValue.text = "${coin.price}€"
                        cvCoinType.contentDescription = "Moneda: ${coin.name}  (${coin.code}). Valor: ${coin.price}€"
                        tvDescription.text = coin.description

                    }
                }

            }
        }
    }
}