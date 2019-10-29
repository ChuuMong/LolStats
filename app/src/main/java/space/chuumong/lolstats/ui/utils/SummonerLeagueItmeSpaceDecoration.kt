package space.chuumong.lolstats.ui.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by Home on 2019-10-29.
 */
class SummonerLeagueItmeSpaceDecoration : RecyclerView.ItemDecoration() {
    companion object {
        private const val LEFT_SPACE = 16
        private const val RIGHT_SPACE = 16
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        when (position) {
            0 -> outRect.left = LEFT_SPACE.toPx()
            state.itemCount - 1 -> outRect.right = RIGHT_SPACE.toPx()
        }
    }
}