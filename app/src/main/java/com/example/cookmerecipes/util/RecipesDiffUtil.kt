package com.example.cookmerecipes.util

import androidx.recyclerview.widget.DiffUtil
import com.example.cookmerecipes.data.model.ResultsItem

class RecipesDiffUtil<T>(
    private val oldList: List<T>,
    private val newList: List<T>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
        //Triple equals ’===’ is used for referential equality, i.e. it checks if both
       // variables are pointing to the same object or not. This is same as == of Java.
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
        //Double equals ”==” is used for structural equality check. That means it checks if
       // two variables contains equal data. It is different than double equal of Java. In Java, double equals == is used to compare reference of variables but in Kotlin it checks only data.
    }
}