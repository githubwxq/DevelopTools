//package com.wxq.commonlibrary.retrofit.Interceptor;
//
//import android.provider.Settings;
//import android.text.TextUtils;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.juziwl.commonlibrary.config.Global;
//import com.juziwl.commonlibrary.config.GlobalContent;
//import com.juziwl.commonlibrary.model.NewResponseData;
//import com.juziwl.commonlibrary.utils.CommonTools;
//import com.juziwl.commonlibrary.utils.DeviceUtils;
//import com.juziwl.commonlibrary.utils.Md5;
//import com.juziwl.commonlibrary.utils.StringUtils;
//import com.juziwl.commonlibrary.utils.TimeUtils;
//import com.juziwl.commonlibrary.utils.VersionUtils;
//import com.juziwl.lingyu.config.LingYu;
//import com.orhanobut.logger.Logger;
//
//import java.io.IOException;
//import java.nio.charset.Charset;
//import java.util.Collections;
//import java.util.Date;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Locale;
//
//import okhttp3.Interceptor;
//import okhttp3.MediaType;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//import okio.Buffer;
//
///**
// * @author Army
// * @version V_1.0.0
// * @date 2018/5/30
// * @description 给请求加头
// */
//public class HeaderInterceptor implements Interceptor {
//    private static final Charset UTF8 = Charset.forName("UTF-8");
//    private List<String> transIds = Collections.synchronizedList(new LinkedList<String>());
//    private static final int MAX_COUNT = 40;
//    public static final String LINGYU_SUB = "lyupdata";
//    public static final String[] ECOURSE_HOST = {"/exue-exueyun-api-web/", "/eke.api.jzexueyun.com/"};
//    /**
//     * 新的请求格式的请求体包含的部分内容
//     */
//    public static final String NEW_REQUEST_FORMAT_CONTENT = "\"ROUTE\":{";
//
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//        Request request = chain.request();
//        Date date = new Date();
//        String transId = TimeUtils.YYYYMMDDHHMMSS_NUMBER.get().format(date) + CommonTools.getSixRandomNo();
//        try {
//            while (transIds.contains(date.getTime() + transId)) {
//                Logger.d("-------------------鉴权码重复-------------------");
//                date = new Date();
//                transId = TimeUtils.YYYYMMDDHHMMSS_NUMBER.get().format(date) + CommonTools.getSixRandomNo();
//            }
//            if (transIds.size() >= MAX_COUNT) {
//                transIds.remove(0);
//            }
//            transIds.add(date.getTime() + transId);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String token = request.header(GlobalContent.ACCESSTOKEN);
//        String url = request.url().toString();
//        if (CommonTools.arrayContainString(url, ECOURSE_HOST)) {
//            //e课的请求重新封装请求体
//            return chain.proceed(generateEcourseRequest(request, date, transId));
//        } else {
//            if (StringUtils.isEmpty(token) && url != null && url.contains(LINGYU_SUB)) {
//                if (LingYu.URL.equals(url)) {
//                    url = LingYu.URL_INNER;
//                }
//                if (LingYu.URL_TEST.equals(url)) {
//                    url = LingYu.URL_TEST_INNER;
//                }
//                if (LingYu.URL_TEST_PRESS.equals(url)) {
//                    url = LingYu.URL_TEST_PRESS_INNER;
//                }
//            }
//            RequestBody body = request.body();
//            RequestBody newRequestBody = null;
//            if (body != null) {
//                if (isJson(body.contentType())) {
//                    String json = bodyToString(request);
//                    if (!StringUtils.isEmpty(json) && ApiService.IS_HTTP_ENCRYPT) {
//                        String encryptJson = Aes.aesEncrypt(json, transId.substring(transId.length() - ApiService.AES_KEY_LENGTH));
//                        newRequestBody = RequestBody.create(body.contentType(), encryptJson);
//                    }
//                }
//            }
//            String androidID = Settings.Secure.getString(Global.application.getContentResolver(), Settings.Secure.ANDROID_ID);
//            Request.Builder builder = request.newBuilder()
//                    .header("SENDER", "exueyun")
//                    .header("RECEIVER", "exueyunServ")
//                    .header("TIME", TimeUtils.YYYYMMDDHHMMSS.get().format(date))
//                    .header("SERVCODE", "exueyunServ")
//                    .header("MSGTYPE", "request")
//                    .header("TRANSID", transId)
//                    .header("VERSION", "2.0")
//                    .header("SYSSIGN", Md5.MD5("exueyunServ" + transId).toLowerCase())
//                    .header(GlobalContent.HADER_CLIENTTYPE, ApiService.getClientType())
//                    .header("User-Agent", request.header("User-Agent") + " Android/" + VersionUtils.getVersionName())
//                    .header("TAG", String.format(Locale.getDefault(), "%s/%s",
//                            DeviceUtils.getModel(), Md5.MD5(androidID + Global.SERIAL)));
//            if (!TextUtils.isEmpty(url)) {
//                builder.url(url);
//            }
//            if (newRequestBody != null) {
//                builder.post(newRequestBody);
//            }
//            return chain.proceed(builder.build());
//        }
//    }
//
//    private Request generateEcourseRequest(Request oldRequest, Date date, String transId) {
//        RequestBody body = oldRequest.body();
//        if (body != null) {
//            if (isJson(body.contentType())) {
//                String json = bodyToString(oldRequest);
//                if (!StringUtils.isEmpty(json)) {
//                    //已经加了鉴权码了
//                    if (!json.contains(NEW_REQUEST_FORMAT_CONTENT)) {
//                        JSONObject main = new JSONObject();
//                        NewResponseData.ROUTEBean routeBean = new NewResponseData.ROUTEBean();
//                        routeBean.ACCESSTOKEN = oldRequest.header(GlobalContent.ACCESSTOKEN);
//                        routeBean.TIME = TimeUtils.YYYYMMDD.get().format(date);
//                        routeBean.TRANSID = transId;
//                        routeBean.SYSSIGN = Md5.MD5(routeBean.SERVCODE + transId).toLowerCase();
//                        main.put("ROUTE", routeBean);
//                        main.put("ROOT", JSON.parse(json));
//                        RequestBody newRequestBody = RequestBody.create(MediaType.parse(ApiService.JSON_CONTENT_TYPE), main.toJSONString());
//                        return oldRequest.newBuilder().post(newRequestBody).build();
//                    }
//                }
//            }
//        }
//        return oldRequest;
//    }
//
//    public static boolean isJson(MediaType mediaType) {
//        if (mediaType == null) {
//            return false;
//        }
//        String subtype = mediaType.subtype();
//        if (subtype != null) {
//            subtype = subtype.toLowerCase();
//            return subtype.contains("json");
//        }
//        return false;
//    }
//
//    public static String bodyToString(Request request) {
//        try {
//            RequestBody body = request.body();
//            if (body == null) {
//                return "";
//            }
//            Buffer buffer = new Buffer();
//            body.writeTo(buffer);
//            Charset charset = getCharset(body.contentType());
//            return buffer.readString(charset);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//
//    private static Charset getCharset(MediaType contentType) {
//        Charset charset = contentType != null ? contentType.charset(UTF8) : UTF8;
//        if (charset == null) {
//            charset = UTF8;
//        }
//        return charset;
//    }
//}
