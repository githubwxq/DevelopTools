package com.juziwl.uilibrary.recycler.divider;
import androidx.annotation.Nullable;

/**
 * @author ArcherYc
 * @date on 2019/3/20  3:32 PM
 * @mail 247067345@qq.com
 */
public class SimpleDivider extends DividerItemDecoration {

    private Divider divider;

    public SimpleDivider(int color, int size, int paddingStart, int paddingRight) {
        divider = new DividerBuilder()
                .setBottomSideLine(color, size, paddingStart, paddingRight)
                .create();
    }


    @Nullable
    @Override
    public Divider getDivider(int totalCount, int itemPosition) {
        if (itemPosition < totalCount-1) {
            return divider;
        } else {
            return null;
        }
    }
}
