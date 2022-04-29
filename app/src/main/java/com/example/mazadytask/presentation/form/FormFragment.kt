package com.example.mazadytask.presentation.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.mazadytask.databinding.BtmSheetFormOptionsBinding
import com.example.mazadytask.databinding.FragmentFormBinding
import com.example.mazadytask.domain.model.*
import com.example.mazadytask.domain.model.categories.Category
import com.example.mazadytask.domain.model.categories.toOption
import com.example.mazadytask.domain.model.properties.toFormField
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FormFragment : Fragment() {

    private lateinit var binding: FragmentFormBinding
    private val formVM: FormViewModel by viewModels()
    private lateinit var categories: List<Category>
    private lateinit var mainOptions: List<Option>
    private var childPropertyPosition: Int = 0
    private var propertyWithChilds: MutableMap<String,List<String>> = mutableMapOf()
    private lateinit var formFieldsAdapter: FormFieldsAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFormBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        formVM.state.observe(viewLifecycleOwner, { formState ->
            if (formState.isLoading) {
                binding.pbProgress.visibility = View.VISIBLE
            }
            if (formState.error.isNotEmpty()) {
                binding.pbProgress.visibility = View.GONE
                Toast.makeText(requireContext(), formState.error, Toast.LENGTH_SHORT).show()
            }
            if (formState.categories.isNotEmpty()) {
                binding.pbProgress.visibility = View.GONE
                categories = formState.categories
                mainOptions = formState.categories.map { it.toOption() }
                val formFields =
                    mutableListOf(FormField(MAIN_CATEGORY, "Main Category", false,mainOptions))
                formFieldsAdapter = FormFieldsAdapter(formFields) { formFieldId,position, option ->
                    when (formFieldId) {
                        MAIN_CATEGORY -> {
                            val selectedCategory = categories.find { it.name == option.name }
                            val subOptions = selectedCategory?.children?.map { it.toOption() }

                            formFieldsAdapter.removeItemsStartingFrom(1)
                            formFieldsAdapter.insertItemAtPosition(
                                1,
                                FormField(SUB_CATEGORY, "Sub Category", false,subOptions ?: listOf())
                            )
                        }
                        SUB_CATEGORY -> {
                            formFieldsAdapter.removeItemsStartingFrom(2)
                            formVM.getProperties(option.id)
                        }
                        else -> {
                            if(option.hasChild) {
                                childPropertyPosition = position + 1
                                formFieldsAdapter.removeFormFieldChilds(childPropertyPosition)
                                formVM.getPropertyChild(option.id)
                            }
                        }
                    }

                }

                binding.rvFormFields.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = formFieldsAdapter
                }
            }
            if (formState.properties.isNotEmpty()){
                binding.pbProgress.visibility = View.GONE
                val formFields = formState.properties.map { it.toFormField() }
                var startIndex = 2
                formFields.forEach {
                    formFieldsAdapter.insertItemAtPosition(startIndex,it)
                    startIndex++
                }
            }
            if (formState.childProperty != null){
                binding.pbProgress.visibility = View.GONE
                val formField = formState.childProperty.toFormField()
                formField.isChild = true
                formFieldsAdapter.insertItemAtPosition(position = childPropertyPosition,formField)
            }

        })
    }




}