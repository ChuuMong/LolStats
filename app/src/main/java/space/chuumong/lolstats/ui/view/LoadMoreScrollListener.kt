package space.chuumong.lolstats.ui.view

import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class LoadMoreScrollListener : NestedScrollView.OnScrollChangeListener {

    companion object {
        private const val LOAD_MORE_ITEM_COUNT = 5
    }

    var isLoad = false

    override fun onScrollChange(
        view: NestedScrollView,
        scrollX: Int,
        scrollY: Int,
        oldScrollX: Int,
        oldScrollY: Int
    ) {
        val lastItemView = view.getChildAt(view.childCount - 1) ?: return
        if (scrollY >= lastItemView.measuredHeight - view.measuredHeight && scrollY > oldScrollY) {
            if (isLoad) {
                loadDate()
            }
        }
    }

    abstract fun loadDate()
}