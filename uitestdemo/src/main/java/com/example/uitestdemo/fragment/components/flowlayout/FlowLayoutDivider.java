package com.example.uitestdemo.fragment.components.flowlayout;
import androidx.annotation.Nullable;

import com.juziwl.uilibrary.recycler.divider.Divider;
import com.juziwl.uilibrary.recycler.divider.DividerBuilder;

/**
 * @author ArcherYc
 * @date on 2019/3/20  3:45 PM
 * @mail 247067345@qq.com
 */
public class FlowLayoutDivider extends DividerItemDecoration {

    private Divider divider;

    private Divider dividerLineEnd;

    private Divider dividerLineStart;

    private int spanCount;

    public FlowLayoutDivider(int color, int size, int spanCount) {
        this.spanCount = spanCount;
        divider = new DividerBuilder()
                .setBottomSideLine(color, size, 0, 0)
                .setRightSideLine(color, size, 0, 0)
                .create();


    }

    @Nullable
    @Override
    public Divider getDivider(int totalCount, int itemPosition) {
            return divider;
    }
}
