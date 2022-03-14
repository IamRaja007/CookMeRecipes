package com.example.cookmerecipes.ui.instructionsfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.cookmerecipes.R
import com.example.cookmerecipes.data.model.ResultsItem
import com.example.cookmerecipes.databinding.FragmentIngredientBinding
import com.example.cookmerecipes.databinding.FragmentInstructionsBinding

class InstructionsFragment : Fragment() {

    private var _binding: FragmentInstructionsBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentInstructionsBinding.inflate(inflater, container, false)

        val args = arguments
        val bundle = args?.getParcelable<ResultsItem>("recipeBundle")

        binding.webviewInstructions.webViewClient = object : WebViewClient(){}
        binding.webviewInstructions.loadUrl(bundle?.sourceUrl!!)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}