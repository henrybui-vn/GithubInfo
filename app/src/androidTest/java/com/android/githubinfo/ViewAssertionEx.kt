package com.example.userpurchases

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.ViewAssertion
import org.junit.Assert

object ViewAssertionEx {
    fun hasItemCountOfRecyclerView() = ViewAssertion { view, noViewFoundException ->
        if (noViewFoundException != null) throw noViewFoundException
        val v = view as RecyclerView
        Assert.assertNotEquals(v.adapter!!.itemCount, 0)
    }
}