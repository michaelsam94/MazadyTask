package com.example.mazadytask.presentation.form

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mazadytask.R
import com.example.mazadytask.databinding.ItemOptionBinding
import com.example.mazadytask.domain.model.Option

class OptionsAdapter(
    private val options: List<Option>,
    private val onOptionSelected: (option: Option) -> Unit
) : RecyclerView.Adapter<OptionsAdapter.OptionsVH>() {

    private var currentSelectedOption = -1

    private var lastSelectedOption = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionsVH {
        return OptionsVH(
            ItemOptionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OptionsVH, position: Int) {
        holder.bind(options[position])
        if (position == currentSelectedOption) {
            holder.select()
        } else {
            holder.deSelect()
        }
    }

    override fun getItemCount(): Int {
        return options.size
    }

    fun isAnyOptionSelected() : Boolean {
        return currentSelectedOption != -1 && lastSelectedOption != -1
    }

    fun selectOption(option: Option) {
        var itemPosition = -1
        options.forEachIndexed() { index, item ->  if(option.id == item.id ) itemPosition = index }
        currentSelectedOption = itemPosition
        lastSelectedOption = itemPosition
        notifyItemChanged(currentSelectedOption)
    }

    inner class OptionsVH(private val binding: ItemOptionBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(option: Option) {
            binding.tvOption.text = option.name
            binding.root.setOnClickListener {
                currentSelectedOption = adapterPosition
                lastSelectedOption = if (lastSelectedOption == -1) {
                    currentSelectedOption
                } else {
                    notifyItemChanged(lastSelectedOption)
                    currentSelectedOption
                }
                onOptionSelected.invoke(option)
                notifyItemChanged(currentSelectedOption)
            }
        }

        fun select() {
            binding.tvOption.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.purple_500
                )
            )
            binding.ivCheckMark.visibility = View.VISIBLE
        }

        fun deSelect() {
            binding.tvOption.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.dark_gray
                )
            )
            binding.ivCheckMark.visibility = View.GONE
        }
    }
}