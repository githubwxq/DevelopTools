package com.wxq.commonlibrary.pay;

import android.text.TextUtils;

/**
 * Created by zh on 2017/10/11.
 * describe: 支付结果
 */

public class PayResult {
    private String resultStatus;
    private String result;
    private String memo;
    public PayResult(String rawResult) {
        if (TextUtils.isEmpty(rawResult))
            return;
        String[] resultParams = rawResult.split(";");
        for (String resultParam : resultParams) {
            if (resultParam.startsWith("resultStatus")) {
                resultStatus = getValue(resultParam, "resultStatus");
            }
            if (resultParam.startsWith("result")) {
                result = getValue(resultParam, "result");
            }
            if (resultParam.startsWith("memo")) {
                memo = getValue(resultParam, "memo");
            }
        }
    }

    private String getValue(String content, String key) {
        String prefix = key + "={";
        return content.substring(content.indexOf(prefix) + prefix.length(),
                content.lastIndexOf("}"));
    }

    /**
     * @return the resultStatus
     */
    public String getResultStatus() {
        return resultStatus;
    }

    /**
     * @return the memo
     */
    public String getMemo() {
        return memo;
    }

    /**
     * @return the result
     */
    public String getResult() {
        return result;
    }

}
