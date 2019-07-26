package com.wxq.commonlibrary.pay;

import android.content.Context;
import android.util.Xml;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wxq.commonlibrary.constant.GlobalContent;
import com.wxq.commonlibrary.util.ToastUtils;

import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by zh on 2017/10/9.
 * describe: 微信支付
 */

public class WeiXinPay {
    private static WeiXinPay instance = null;
    private String appId = "", partnerId = "";

    private WeiXinPay() {
            appId = GlobalContent.WEIXIN_APPID;
        partnerId = GlobalContent.WEIXIN_MCHID;
    }

    public static WeiXinPay getInstance() {
        if (instance == null) {
            instance = new WeiXinPay();
        }
        return instance;
    }

    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    public Map<String, String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:

                        if (!"xml".equals(nodeName)) {
                            //实例化student对象
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    default:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
        }
        return null;
    }

    private void genPayReq(PayReq req, Map<String, String> result, String apiKey) {
        req.appId = appId;
        req.partnerId = result.get("mch_id");
        req.prepayId = result.get("prepay_id");
        req.packageValue = "Sign=WXPay";
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());

        StringBuilder sb = new StringBuilder(100);
        sb.append("appid").append('=').append(req.appId).append('&');
        sb.append("noncestr").append('=').append(req.nonceStr).append('&');
        sb.append("package").append('=').append(req.packageValue).append('&');
        sb.append("partnerid").append('=').append(req.partnerId).append('&');
        sb.append("prepayid").append('=').append(req.prepayId).append('&');
        sb.append("timestamp").append('=').append(req.timeStamp).append('&');
        sb.append("key=").append(apiKey);
        req.sign =MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();;
    }

    /**
     * md5加密
     * @param origin 需要加密的字符串
     * @param charsetname 字符编码
     * @return 加密后的字符串
     */
    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }

    //字节数组转字符串
    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    //字节转字符
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
    public void pay(Context mContext, String result, String apiKey) {
         IWXAPI msgApi = WXAPIFactory.createWXAPI(mContext, GlobalContent.WEIXIN_APPID);
         PayReq req = new PayReq();
        Map<String, String> xml = decodeXml(result);
        genPayReq(req, xml, apiKey);
        msgApi.registerApp(appId);
        if (!msgApi.isWXAppInstalled()) {
            ToastUtils.showShort("请安装微信客户端");
        } else {
            msgApi.sendReq(req);
        }
    }


}
