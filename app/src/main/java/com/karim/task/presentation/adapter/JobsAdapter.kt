package com.karim.task.presentation.adapter

import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import com.karim.task.BR
import com.karim.task.R
import com.karim.task.data.entity.JobsItem
import com.karim.task.databinding.ItemJobBinding
import com.karim.task.presentation.base.BaseSimpleBindingAdapter
import com.squareup.picasso.Picasso

class JobsAdapter(private val onItemClickListener: OnItemClickListener<JobsItem>) :
    BaseSimpleBindingAdapter<JobsItem, ItemJobBinding>(object :
        DiffUtil.ItemCallback<JobsItem>() {
        override fun areItemsTheSame(oldItem: JobsItem, newItem: JobsItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: JobsItem, newItem: JobsItem) =
            oldItem.toString() == newItem.toString()
    }) {

    override fun getItemLayout(viewType: Int) = R.layout.item_job

    override fun onBindViewHolder(holder: ViewHolder<ItemJobBinding>, position: Int) {

        holder.viewBinding.setVariable(BR.job, getItem(position))
        holder.viewBinding.setVariable(BR.onItemClickListener, onItemClickListener)

        Picasso.get().load(getItem(position).company_logo)
            .into(holder.viewBinding.logoImg);

        holder.viewBinding.executePendingBindings()
        if (getItem(position).favorite) {
            Picasso.get().load(R.drawable.star)
                .into(holder.viewBinding.favImg);
        } else {
            Picasso.get().load(R.drawable.not_star)
                .into(holder.viewBinding.favImg);
        }

    }

}