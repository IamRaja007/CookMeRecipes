package com.example.cookmerecipes.ui.recipesfragment.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.cookmerecipes.R
import com.example.cookmerecipes.databinding.FragmentRecipesBinding
import com.example.cookmerecipes.databinding.FragmentRecipesBottomSheetBinding
import com.example.cookmerecipes.util.Constants
import com.example.cookmerecipes.viewmodels.RecipesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.lang.Exception
import java.util.*


class RecipesBottomSheet : BottomSheetDialogFragment() {

    private var _binding: FragmentRecipesBottomSheetBinding? = null
    private val binding get() = _binding!!

    private lateinit var recipesViewModel: RecipesViewModel

    private var mealTypeChip = Constants.DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietTypeChip = Constants.DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecipesBottomSheetBinding.inflate(inflater, container, false)

        recipesViewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner, { value ->
            mealTypeChip = value.selectedMealType
            dietTypeChip = value.selectedDietType

            updateChip(value.selectedMealTypeId, binding.chipGroupMealType)
            updateChip(value.selectedDietTypeId, binding.chipGroupDietType)

        })

        binding.chipGroupMealType.setOnCheckedChangeListener { group, checkedChipId ->
            val chip = group.findViewById<Chip>(checkedChipId)
            val selectedMealType = chip.text.toString().lowercase(Locale.getDefault())

            mealTypeChip = selectedMealType
            mealTypeChipId = checkedChipId

        }

        binding.chipGroupDietType.setOnCheckedChangeListener { group, checkedChipId ->
            val chip = group.findViewById<Chip>(checkedChipId)
            val selectedDietType = chip.text.toString().lowercase(Locale.getDefault())

            dietTypeChip = selectedDietType
            dietTypeChipId = checkedChipId

        }

        binding.btnApply.setOnClickListener {
            recipesViewModel.saveMealAndDietType(
                mealTypeChip,
                mealTypeChipId,
                dietTypeChip,
                dietTypeChipId
            )

            val action=RecipesBottomSheetDirections.actionRecipesBottomSheetToRecipesFragment(true)
            findNavController().navigate(action)
        }
        return binding.root
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {

        if (chipId != 0) {
            //Then we have made some selection and stored in the datastore
            try {
                chipGroup.findViewById<Chip>(chipId).isChecked = true
            } catch (e: Exception) {
                Log.d("RecipesBottomSheet", e.message.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}