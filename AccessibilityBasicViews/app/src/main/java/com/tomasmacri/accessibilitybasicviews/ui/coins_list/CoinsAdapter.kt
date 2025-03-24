package com.tomasmacri.accessibilitybasicviews.ui.coins_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View.IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.tomasmacri.accessibilitybasicviews.R
import com.tomasmacri.accessibilitybasicviews.databinding.ItemCoinBinding
import com.tomasmacri.accessibilitybasicviews.domain.model.Coin
import com.tomasmacri.accessibilitybasicviews.utils.setImportanceForAccessibility

class CoinsAdapter(
    val context: Context,
    val coinActions: CoinActions
) : ListAdapter<Coin, CoinsAdapter.CoinViewHolder>(CoinDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val binding = ItemCoinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val coin = getItem(position)
        holder.bind(coin)
    }

    inner class CoinViewHolder(private val binding: ItemCoinBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(coin: Coin) {
            binding.coinName.text = coin.name
            binding.coinCode.text = coin.code
            binding.coinDescription.text = coin.description
            binding.coinPrice.text = "${coin.price}€"
            coin.imageUrl?.let {
                binding.coinImage.load(it){
                    crossfade(true)
                }
            }

            binding.root.setOnClickListener {
                coinActions.onCoinClicked(coin)
            }

            var changeFavoriteActionLabel = context.getString(R.string.change_favorite_positive_action_label)
            if (coin.isFavorite) {
                changeFavoriteActionLabel = context.getString(R.string.change_favorite_negative_action_label)
                binding.favoriteIcon.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.baseline_favorite_24))
                binding.favoriteIcon.contentDescription = context.getString(R.string.favorito)
            } else {
                binding.favoriteIcon.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.baseline_favorite_border_24))
                binding.favoriteIcon.contentDescription = context.getString(R.string.no_favorito)
            }
            binding.favoriteIcon.setOnClickListener {
                coinActions.onChangeFavoriteCoin(coin)
            }

            ViewCompat.addAccessibilityAction(binding.root, changeFavoriteActionLabel) { view, commandArguments ->
                coinActions.onChangeFavoriteCoin(coin)
                true
            }

            ViewCompat.addAccessibilityAction(binding.root, context.getString(R.string.delete_coin)) { view, commandArgunments ->
                coinActions.onDeleteCoin(getItem(adapterPosition))
                true
            }

            binding.llParentLayout.setImportanceForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS)
            binding.root.contentDescription = listOf(binding.coinName.text, binding.coinCode.text, binding.coinPrice.text, binding.favoriteIcon.contentDescription, binding.coinDescription.text).joinToString()
        }
    }

    class CoinDiffCallback : DiffUtil.ItemCallback<Coin>() {
        override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem.code == newItem.code
        }

        override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem == newItem
        }
    }
}

interface CoinActions {
    fun onDeleteCoin(coin: Coin)
    fun onChangeFavoriteCoin(coin: Coin)
    fun onCoinClicked(coin: Coin)
}
