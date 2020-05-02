package studio.forface.covid.android.classic.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_country.view.*
import studio.forface.covid.android.classic.R
import studio.forface.covid.android.classic.utils.ItemClickListener
import studio.forface.covid.android.classic.utils.inflate
import studio.forface.covid.android.classic.utils.onClick
import studio.forface.covid.domain.entity.Country
import studio.forface.covid.domain.entity.CountryId
import studio.forface.theia.dsl.imageDrawableRes
import studio.forface.theia.dsl.invoke
import studio.forface.theia.dsl.theia

class CountriesAdapter(
    private val onClick: ItemClickListener<CountryId>,
    private val onFavorite: ItemClickListener<Country>
) : ListAdapter<Country, CountriesAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Country>() {
        override fun areItemsTheSame(oldItem: Country, newItem: Country) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Country, newItem: Country) = oldItem == newItem
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.item_country), onClick, onFavorite)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        view: View,
        private val clickListener: ItemClickListener<CountryId>,
        private val favoriteListener: ItemClickListener<Country>
    ) : RecyclerView.ViewHolder(view) {

        fun bind(item: Country) {
            itemView.apply {
                country_name_text.text = item.name.s
                onClick { clickListener(item.id) }

                favorite.apply {
                    // TODO: do not apply tint if not favorite
                    // TODO: show ic_star_32 if favorite
                    theia.invoke {
                        imageDrawableRes = if (item.favorite) R.drawable.ic_star_32 else R.drawable.ic_star_bw_32
                    }
                    onClick { favoriteListener(item) }
                }
            }
        }
    }
}
