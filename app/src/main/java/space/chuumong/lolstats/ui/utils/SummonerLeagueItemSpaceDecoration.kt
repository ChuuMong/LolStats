package space.chuumong.lolstats.ui.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by Home on 2019-10-29.
 */
class SummonerLeagueItemSpaceDecoration : RecyclerView.ItemDecoration() {
    companion object {
        private const val FIRST_ITEM_SPACE = 12
        private const val LAST_ITEM_SPACE = 12
        private const val ITEM_SPACE = 4
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        outRect.right += ITEM_SPACE.toPx()
        outRect.left += ITEM_SPACE.toPx()

        val position = parent.getChildAdapterPosition(view)
        when (position) {
            0 -> outRect.left += FIRST_ITEM_SPACE.toPx()
            state.itemCount - 1 -> outRect.right += LAST_ITEM_SPACE.toPx()
        }
    }
}