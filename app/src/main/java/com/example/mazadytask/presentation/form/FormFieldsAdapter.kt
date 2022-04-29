package com.example.mazadytask.presentation.form

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.mazadytask.databinding.BtmSheetFormOptionsBinding
import com.example.mazadytask.databinding.ItemFormFieldBinding
import com.example.mazadytask.domain.model.FormField
import com.example.mazadytask.domain.model.Option
import com.google.android.material.bottomsheet.BottomSheetDialog

class FormFieldsAdapter(
    private val formFields: MutableList<FormField>,
    private val onSelectOption: (Int, Int, Option) -> Unit
) :
    RecyclerView.Adapter<FormFieldsAdapter.FormFieldVH>() {

    private val selectedFields = mutableMapOf<Int, String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormFieldVH {
        return FormFieldVH(
            ItemFormFieldBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FormFieldVH, position: Int) {
        holder.bind(formFields[position])
    }

    override fun getItemCount(): Int {
        return formFields.size
    }

    fun removeItemAtPosition(position: Int) {
        if (position < formFields.size) {
            selectedFields.remove(formFields[position].formFieldId)
            formFields.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun removeItemsStartingFrom(start: Int) {
        while (start < itemCount) {
            removeItemAtPosition(itemCount - 1)
        }
    }

    fun removeItemWithId(id: Int) {
        var index = -1
        formFields.forEachIndexed { i, formField ->
            if (formField.formFieldId == id) {
                index = i
            }
        }
        if (index != -1) removeItemAtPosition(index)
    }



    fun removeItemWithName(name: String) {
        var index = -1
        formFields.forEachIndexed { i, formField ->
            if (formField.name == name) {
                index = i
            }
        }
        if (index != -1) removeItemAtPosition(index)
    }

    fun removeFormFieldChilds(start: Int){
        if(start < itemCount){
            while (formFields[start].isChild){
                removeItemAtPosition(start)
            }
        }
    }

    fun insertItemAtPosition(position: Int, item: FormField) {
        formFields.add(position, item)
        notifyItemInserted(position)
    }


    inner class FormFieldVH(private val binding: ItemFormFieldBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var selectedOption: Option? = null

        fun bind(formField: FormField) {
            binding.tilFormField.hint = formField.name
            if (selectedFields[formField.formFieldId] != null) {
                binding.tvSelectedOption.setText(selectedFields[formField.formFieldId])
            } else {
                binding.tvSelectedOption.setText("")
            }
            binding.tvSelectedOption.setOnClickListener {
                showOptionDialog(formField.formFieldId, formField.name, formField.options)
            }
        }

        private fun showOptionDialog(
            formFieldId: Int,
            formFieldName: String,
            options: List<Option>
        ) {

            val dialog = BottomSheetDialog(binding.root.context)
            val dialogBinding = BtmSheetFormOptionsBinding.inflate(
                LayoutInflater.from(binding.root.context),
                binding.root,
                false
            )
            dialog.setContentView(dialogBinding.root)
            val optionsAdapter = OptionsAdapter(options) {
                selectedOption = it
                selectedFields[formFieldId] = it.name
                binding.tvSelectedOption.setText(it.name)
                onSelectOption.invoke(formFieldId, adapterPosition, it)
            }
            selectedOption?.let {
                optionsAdapter.selectOption(it)
            }
            dialogBinding.tvTitle.text = formFieldName
            dialogBinding.btnClose.setOnClickListener {
                dialog.dismiss()
            }
            dialogBinding.btnSave.setOnClickListener {
                if (optionsAdapter.isAnyOptionSelected()) {
                    dialog.dismiss()
                } else {
                    Toast.makeText(binding.root.context, "Please Select Item", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            val dividerItemDecoration =
                DividerItemDecoration(binding.root.context, LinearLayoutManager.VERTICAL)
            dialogBinding.rvOptions.apply {
                layoutManager = LinearLayoutManager(binding.root.context)
                adapter = optionsAdapter
                (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
                addItemDecoration(dividerItemDecoration)
                setHasFixedSize(true)
            }
            dialog.show()
        }
    }
}