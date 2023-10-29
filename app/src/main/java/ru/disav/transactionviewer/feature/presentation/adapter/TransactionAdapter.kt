package ru.disav.transactionviewer.feature.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.disav.transactionviewer.databinding.ItemTimeBinding
import ru.disav.transactionviewer.databinding.ItemTransactionBinding
import ru.disav.transactionviewer.feature.domain.entity.TransactionStatus
import ru.disav.transactionviewer.feature.presentation.model.TransactionUiModel

class TransactionAdapter() :
    ListAdapter<TransactionUiModel, TransactionItemsViewHolder>(
        TransactionDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionItemsViewHolder {

        return when (viewType) {
            VIEW_TYPE_TIME -> {
                val itemBinding =
                    ItemTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TransactionItemsViewHolder.TimeViewHolder(itemBinding)
            }

            VIEW_TYPE_TRANSACTION -> {
                val itemBinding =
                    ItemTransactionBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                TransactionItemsViewHolder.TransactionViewHolder(itemBinding)
            }

            else -> {
                throw IllegalStateException()
            }
        }
    }

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is TransactionUiModel.TransactionItem -> VIEW_TYPE_TRANSACTION
        else -> VIEW_TYPE_TIME
    }

    override fun onBindViewHolder(
        holder: TransactionItemsViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (holder is TransactionItemsViewHolder.TransactionViewHolder) {
            when (val latestPayload = payloads.lastOrNull()) {
                is TransactionChangePayload.Status -> holder.bindStatus(latestPayload.status)
                else -> onBindViewHolder(holder, position)
            }
        } else if (holder is TransactionItemsViewHolder.TimeViewHolder) {
            onBindViewHolder(holder, position)
        }
    }

    override fun onBindViewHolder(holder: TransactionItemsViewHolder, position: Int) {
        if (holder is TransactionItemsViewHolder.TransactionViewHolder) {
            holder.bind(getItem(position) as TransactionUiModel.TransactionItem)

        } else if (holder is TransactionItemsViewHolder.TimeViewHolder) {
            holder.bind(getItem(position) as TransactionUiModel.TimeItem)
        }
    }

    private class TransactionDiffCallback : DiffUtil.ItemCallback<TransactionUiModel>() {

        override fun areItemsTheSame(
            oldItem: TransactionUiModel,
            newItem: TransactionUiModel
        ): Boolean {
            return when {
                oldItem is TransactionUiModel.TransactionItem && newItem is TransactionUiModel.TransactionItem -> {
                    oldItem.id == newItem.id
                }

                oldItem is TransactionUiModel.TimeItem && newItem is TransactionUiModel.TimeItem -> {
                    oldItem.time == newItem.time
                }

                else -> false
            }
        }

        override fun areContentsTheSame(
            oldItem: TransactionUiModel,
            newItem: TransactionUiModel
        ): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(
            oldItem: TransactionUiModel,
            newItem: TransactionUiModel
        ): Any? {
            return when {
                oldItem is TransactionUiModel.TransactionItem && newItem is TransactionUiModel.TransactionItem && oldItem.status != newItem.status -> {
                    TransactionChangePayload.Status(newItem.status)
                }

                else -> super.getChangePayload(oldItem, newItem)
            }
        }
    }

    private sealed interface TransactionChangePayload {

        data class Status(val status: TransactionStatus) : TransactionChangePayload

    }

    companion object {
        const val VIEW_TYPE_TIME = 1
        const val VIEW_TYPE_TRANSACTION = 2
    }

}

