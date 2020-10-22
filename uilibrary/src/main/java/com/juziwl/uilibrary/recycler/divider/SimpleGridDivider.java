package com.juziwl.uilibrary.recycler.divider;
import androidx.annotation.Nullable;

/**
 * @author ArcherYc
 * @date on 2019/3/20  3:45 PM
 * @mail 247067345@qq.com
 */
public class SimpleGridDivider extends DividerItemDecoration {

    private Divider divider;

    private Divider dividerLineEnd;

    private int spanCount;

    public SimpleGridDivider(int color, int size, int spanCount) {
        this.spanCount = spanCount;
        divider = new DividerBuilder()
                .setBottomSideLine(color, size, 0, 0)
                .setRightSideLine(color, size, 0, 0)
                .create();
        dividerLineEnd = new DividerBuilder()
                .setBottomSideLine(color,size,0,0)
                .create();
    }

    @Nullable
    @Override
    public Divider getDivider(int totalCount, int itemPosition) {
        if (itemPosition%spanCount==(spanCount-1)){
            return dividerLineEnd;
        }else{
            return divider;
        }
    }
}
