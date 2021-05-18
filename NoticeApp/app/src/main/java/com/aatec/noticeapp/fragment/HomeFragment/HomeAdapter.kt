package com.aatec.noticeapp.fragment.HomeFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aatec.noticeapp.data.Network.NoticeNetworkEntity
import com.aatec.noticeapp.data.Notice
import com.aatec.noticeapp.databinding.RowNoticeBinding

class HomeAdapter(private val listener: OnClickListener) :
    ListAdapter<Notice, HomeAdapter.HomeViewHolder>(DiffNoticeCallback()) {

    inner class HomeViewHolder(private val binding: RowNoticeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {

            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val notice = getItem(position)
                    listener.setOnClickListener(notice, binding.root)
                }
            }
        }

        fun bind(notice: Notice) {
            binding.apply {
                binding.root.transitionName = notice.title
                tvNotice.text = notice.title
                dateNotice.text = notice.date
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder =
        HomeViewHolder(
            RowNoticeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class DiffNoticeCallback : DiffUtil.ItemCallback<Notice>() {
        override fun areItemsTheSame(oldItem: Notice, newItem: Notice): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: Notice, newItem: Notice): Boolean =
            oldItem.title == newItem.title &&
                    newItem.body == oldItem.body &&
                    newItem.date == oldItem.date &&
                    newItem.link == oldItem.link

    }

    interface OnClickListener {
        fun setOnClickListener(notice: Notice, view: View)
    }
}