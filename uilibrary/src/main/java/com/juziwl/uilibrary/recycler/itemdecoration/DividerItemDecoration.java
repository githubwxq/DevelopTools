package com.juziwl.uilibrary.recycler.itemdecoration;

import android.content.Context;
import androidx.annotation.ColorInt;

public class DividerItemDecoration extends BaseItemDecoration {

        private DividerItemDecoration(Context context, int lineWidthDp, @ColorInt int colorRGB) {
            super(context, lineWidthDp, colorRGB);
        }

        @Override
        public boolean[] getItemSidesIsHaveOffsets(int itemPosition) {
            //顺序:left, top, right, bottom
            boolean[] booleans = {false, false, false, false};
            if ((itemPosition >= 1 && itemPosition <= 6) || itemPosition == 9 || itemPosition == 10) {
                booleans[3] = true;
            } else if (itemPosition == 0 || itemPosition == 7 || itemPosition == 8) {
                booleans[2] = true;
                booleans[3] = true;
            } else if (itemPosition > 10 && itemPosition < 22) {

                switch ((itemPosition - 10) % 4) {
                    case 1:
                    case 2:
                    case 3:
                        booleans[2] = true;
                        booleans[3] = true;
                        break;
                    case 0:
                        booleans[3] = true;
                        break;
                    default:
                        break;
                }
            }

            return booleans;
        }
    }