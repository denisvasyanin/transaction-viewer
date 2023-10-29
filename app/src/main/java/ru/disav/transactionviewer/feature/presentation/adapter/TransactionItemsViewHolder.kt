package ru.disav.transactionviewer.feature.presentation.adapter

import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ru.disav.transactionviewer.R
import ru.disav.transactionviewer.databinding.ItemTimeBinding
import ru.disav.transactionviewer.databinding.ItemTransactionBinding
import ru.disav.transactionviewer.feature.domain.entity.TransactionStatus
import ru.disav.transactionviewer.feature.presentation.model.TransactionUiModel

sealed class TransactionItemsViewHolder(itemBinding: ViewBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    class TransactionViewHolder(private val itemBinding: ItemTransactionBinding) :
        TransactionItemsViewHolder(itemBinding) {

        val resources = itemBinding.root.resources

        fun bind(transactionModel: TransactionUiModel.TransactionItem) {
            itemBinding.idView.text = transactionModel.id.toString()
            itemBinding.typeView.text = transactionModel.type
            itemBinding.createdView.text = transactionModel.created
            itemBinding.amountView.text = transactionModel.amount
            bindStatus(transactionModel.status)
        }

        fun bindStatus(status: TransactionStatus) {
            val icon = when (status) {
                TransactionStatus.IN_PROGRESS -> R.drawable.ic_loading
                TransactionStatus.SUCCESS -> R.drawable.ic_done
                TransactionStatus.FAILURE -> R.drawable.ic_failure
            }
            val res = ResourcesCompat.getDrawable(resources, icon, null)
            itemBinding.statusView.setImageDrawable(res)
        }
    }

    class TimeViewHolder(private val itemBinding: ItemTimeBinding) :
        TransactionItemsViewHolder(itemBinding) {

        fun bind(model: TransactionUiModel.TimeItem) {
            itemBinding.timeView.text = model.time
        }
    }
}