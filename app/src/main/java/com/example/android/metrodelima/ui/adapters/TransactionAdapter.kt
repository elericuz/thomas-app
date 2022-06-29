package com.example.android.metrodelima.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android.metrodelima.R
import com.example.android.metrodelima.databinding.TransactionItemBinding
import com.example.android.metrodelima.models.Transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import timber.log.Timber

class TransactionAdapter :
    PagingDataAdapter<Transaction, TransactionAdapter.ViewHolder>(DiffCallback),
    CoroutineScope by MainScope() {

    private lateinit var context: Context

    companion object DiffCallback : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem.reference_id == newItem.reference_id
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem == newItem
        }
    }

    inner class ViewHolder(private val binding: TransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction, position: Int) {
            val animation =
                AnimationUtils.loadAnimation(context, R.anim.item_animator)
            binding.transactionItem.startAnimation(animation)
            binding.transaction = transaction
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        val inflater = LayoutInflater.from(context)
        val view: TransactionItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.transaction_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val transaction = getItem(position)
            holder.bind(transaction!!, position)
        } catch (e: Exception) {
            Timber.e(e.localizedMessage)
        }
    }
}