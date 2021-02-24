package com.example.foursquaresearch

import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

fun atPosition(
    position: Int,
    itemMatcher: Matcher<View>
) = object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
    override fun matchesSafely(view: RecyclerView): Boolean {
        val viewHolder =
            view.findViewHolderForAdapterPosition(position)
                ?: // has no item on such position
                return false
        return itemMatcher.matches(viewHolder.itemView)
    }
    override fun describeTo(description: Description?) {
        description?.appendText("has item at position $position: ")
        itemMatcher.describeTo(description)
    }
}
