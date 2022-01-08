package com.example.cookmerecipes.ui.instructionsfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.cookmerecipes.R
import com.example.cookmerecipes.data.model.ResultsItem
import kotlinx.android.synthetic.main.fragment_instructions.view.*

class InstructionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_instructions, container, false)

        val args = arguments
        val bundle = args?.getParcelable<ResultsItem>("recipeBundle")

        view.webviewInstructions.webViewClient = object : WebViewClient(){}
        view.webviewInstructions.loadUrl(bundle?.sourceUrl!!)
        return view
    }

}