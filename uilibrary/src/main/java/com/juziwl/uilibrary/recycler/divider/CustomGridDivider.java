package com.juziwl.uilibrary.recycler.divider;

import androidx.annotation.Nullable;

/**
 * @author ArcherYc
 * @date on 2019/3/20  3:45 PM
 * @mail 247067345@qq.com
 */
public class CustomGridDivider extends DividerItemDecoration {

    private Divider divider;

    private Divider dividerLineEnd;

    private Divider dividerLineStart;

    private int spanCount;

    private int size;

    public CustomGridDivider(int color, int size, int spanCount) {
        this.spanCount = spanCount;
        this.size = size;
        int halfSize = size / 2 == 0 ? 1 : size / 2;
        divider = new DividerBuilder()
                .setLeftSideLine(color, halfSize, 0, 0)
                .setBottomSideLine(color, size, 0, 0)
                .create();
        dividerLineStart = new DividerBuilder()
                .setRightSideLine(color, halfSize, 0, 0)
                .setBottomSideLine(color, size, 0, 0)
                .create();
        dividerLineEnd = new DividerBuilder()
                .setLeftSideLine(color, halfSize, 0, 0)
                .setBottomSideLine(color, size, 0, 0)
                .create();
    }

    @Nullable
    @Override
    public Divider getDivider(int totalCount, int itemPosition) {
        if (itemPosition / spanCount == (totalCount-1) / spanCount) {
            divider.bottomSideLine.setSize(0);
            dividerLineStart.bottomSideLine.setSize(0);
            dividerLineEnd.bottomSideLine.setSize(0);
        } else {
            divider.bottomSideLine.setSize(size);
            dividerLineStart.bottomSideLine.setSize(size);
            dividerLineEnd.bottomSideLine.setSize(size);
        }
        if (itemPosition % spanCount == (spanCount - 1)) {
            return dividerLineEnd;
        } else if (itemPosition % spanCount == 0) {
            return dividerLineStart;
        } else {
            return divider;
        }
    }
}
