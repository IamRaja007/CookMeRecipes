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
import com.example.cookmerecipes.util.Constants
import com.example.cookmerecipes.viewmodels.RecipesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.fragment_recipes_bottom_sheet.view.*
import java.lang.Exception
import java.util.*


class RecipesBottomSheet : BottomSheetDialogFragment() {

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
        val mView = inflater.inflate(R.layout.fragment_recipes_bottom_sheet, container, false)

        recipesViewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner, { value ->
            mealTypeChip = value.selectedMealType
            dietTypeChip = value.selectedDietType

            updateChip(value.selectedMealTypeId, mView.chipGroup_mealType)
            updateChip(value.selectedDietTypeId, mView.chipGroup_dietType)

        })

        mView.chipGroup_mealType.setOnCheckedChangeListener { group, checkedChipId ->
            val chip = group.findViewById<Chip>(checkedChipId)
            val selectedMealType = chip.text.toString().lowercase(Locale.getDefault())

            mealTypeChip = selectedMealType
            mealTypeChipId = checkedChipId

        }

        mView.chipGroup_dietType.setOnCheckedChangeListener { group, checkedChipId ->
            val chip = group.findViewById<Chip>(checkedChipId)
            val selectedDietType = chip.text.toString().lowercase(Locale.getDefault())

            dietTypeChip = selectedDietType
            dietTypeChipId = checkedChipId

        }

        mView.btnApply.setOnClickListener {
            recipesViewModel.saveMealAndDietType(
                mealTypeChip,
                mealTypeChipId,
                dietTypeChip,
                dietTypeChipId
            )

            val action=RecipesBottomSheetDirections.actionRecipesBottomSheetToRecipesFragment(true)
            findNavController().navigate(action)
        }
        return mView
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

}