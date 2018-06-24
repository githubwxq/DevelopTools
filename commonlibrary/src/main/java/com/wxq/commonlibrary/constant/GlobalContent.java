package com.wxq.commonlibrary.constant;

/**
 * @author nat.xue
 * @date 2017/10/21
 * @description 全局常量
 */

public class GlobalContent {
    /**
     * 手机号最大长度
     */
    public static final int MAX_PHONE_LENGTH = 11;
    /**
     * 验证码长度
     */
    public static final int VERIFY_CODE_LENGTH = 6;
    /**
     * 超级会员验证码长度
     */
    public static final int MAX_SUPER_VIP_LENGTH = 12;

    /**
     * 验证码开始时间
     */
    public static final int IDENTIFYING_CODE_TIME_START = 60;

    /**
     * 验证码结束时间
     */
    public static final int IDENTIFYING_CODE_TIME_END = 0;

    /**
     * 匹配所有字母
     */
    public static final String ALL_LETTER = "[A-Z]|[a-z]";

    /**
     * @ 人名后面添加的特殊字符
     */
    public static final String AT_SPECIAL_WORD = " ";
    public static final String AT_WORD = "@";

    /**
     * 最小密码长度
     */
    public static final int MIN_PASSWORD_LENGTH = 6;
    /**
     * 最小tab数
     */
    public static final int MIN_TAB_LENGTH = 2;

    /**
     * 账号最大长度
     */
    public static final int MAX_ACCOUNT = 9;

    public static final int MAX_MSGCOUNT = 99;

    public static final String EXTRA_IDENTIFY = "identify";
    public static final String EXTRA_IMID = "extra_imid";
    public static final String EXTRA_POSITION = "position";

    public static final int ROWS = 10;
    /**
     * 发布空间动态成功时
     */
    public static final String ACTION_PUBLISH_DYNACMIC = "action_publish_dynacmic_extra";

    /**
     * token过期包含的字符串
     */
    public static final String TOKEN_EXPIRE = "\"status\":4001";
    /**
     * token不合法包含的字符串
     */
    public static final String TOKEN_ILLEGAL = "\"status\":4002";
    /**
     * token为null
     */
    public static final String TOKEN_ISNULL = "\"status\":4005";
    /**
     * 服务器维护
     */
    public static final String SERVER_MAINTAIN = "\"status\":5050";

    public static final String ACCESSTOKEN = "AccessToken";

    public static final String HADER_CLIENTTYPE = "clientType";

    /**
     * 异地登录
     */
    public static final String TYPE_OFFLINE = "type_offline";
    /**
     * 闪退
     */
    public static final String TYPE_CRASH = "type_crash";
    /**
     * 修改手机号
     */
    public static final String TYPE_MODIFY_PHONE = "type_modify_phone";
    /**
     * 家长注册
     */
    public static final String TYPE_PARENT_REGISTER = "type_parent_register";

    public static final String EXTRA_RESET_PASSWORD = "extra_reset_password";

    public static final String EXTRA_MESSAGE = "extra_message";
    public static final String EXTRA_TYPE = "extra_type";
    public static final String EXTRA_COUNT = "extra_count";
    public static final String EXTRA_IS_SHOW_REDPOINT = "extra_is_show_redpoint";
    public static final String EXTRA_DELETE_STUDENTID = "extra_delete_studentid";


    public static final String USERTYPE_PARENT = "1";
    public static final String USERTYPE_TEACHER = "2";
    public static final String USERTYPE_PARENT_TEACHER = "3";
    public static final String USERTYPE_STUDENT = "4";

    public static final String STATUS_SUCCESS = "200";
    public static final String STATUS_IS_SET_PASSWORD = "-1";
    public static final String STATUS_SERVER_MAINTAIN = "5050";

    public static final String EXTRA_FRIENDDETAIL = "extra_frienddetail";
    public static final String EXTRA_INVITE_PARENT_INFO = "extra_Invite_parent_info";

    public static final int P720 = 720;
    public static final int P1080 = 1080;
    public static final int P1500 = 1500;

    public static final String SUFFIX_GIF = ".gif";

    public static final String ACTION_REFRESH_HOMEWORK = "ACTION_Refresh_Homework";

    /**
     * 教学端和家庭端切换学校或者孩子之后发送的广播
     */
    public static final String ACTION_TEA_UPDATESCHOOLINFO = "com.juziwl.exue_comprehensive.updateallschoolinfo_after_switch_class";
    public static final String ACTION_PAR_UPDATESCHOOLINFO = "com.juziwl.exue_parent.updateallschoolinfo_after_switch_class";
    public static final String ACTION_UPDATE_HOMEWORK_STATE = "com.juziwl.exue.update_homework_state";
    public static final String ACTION_CHANGELEARNSTATUS = "com.juziwl.updata.learnstatue";
    public static final String ACTION_CHANGELEGROWTHPATH = "com.juziwl.updata.growthpath";

    /**
     * 发布作业发的广播
     */
    public static final String ACTION_FIRST_PUBLISH = "com.juziwl.exue_comprehensive.action_first_publish";
    public static final String ACTION_PUBLISH_HOMERWORK = "com.juziwl.exue_comprehensive.ACTION_PUBLISH_HomerWork";

    public static final String ACTION_GIFT_NUMBER = "com.juzi.exue.gift_number";
    public static final String ACTION_GIFT_SEND = "com.juzi.exue.goto.sendGift";

    public static final String ACTION_REFRESH = "action_refresh";
    public static final String ACTION_LOADMORE = "action_loadmore";

    public static final String IMAGELIMIT = "?imageView2/2/w/%d/h/%d";

    public static final String EXTRA_MATERIAL_ID = "extra_material_id";
    public static final String EXTRA_MATERIAL_NAME = "extra_material_name";

    public static final int TITLE_MAX_LENGTH = 10;

    public static final String NULL_CONTENT = "-99";

    public static final String EXTRAL_NORMAL_TUI = "Extral_normal_tui";

    public static final String ACTION_DEAL_WITH_HOMEWORK = "action_deal_with_homework";

    public static final String ACTION_REFRESH_STATE = "action_dealwith_refresh_state";

    /**
     * 修改名字添加广播
     */

    public static final String ACTION_MODIFY_NAME = "extra_modify_name";
    /**
     * 修改图像的广播
     */
    public static final String ACTION_MODIFY_HEAD_PIC = "extra_modify_head_pic";



      /**
     * 支付成功的广播
     */
    public static final String ACTION_PAYSUCCESS = "ACTION_PAYSUCCESS";





    /**
     * 单选
     */
    public static final String SINGLE_SELECT = "11";
    /**
     * 判断
     */
    public static final String JUDGE = "2";

    public static final int MAX_PUBLISH_SUBJECT_NUM = 20;

    /**
     * 切换到签退统计时
     */
    public static final String ACTION_TEACHER_SIGN_OUT = "action_teacher_sign_out";

    /**
     * 额外的uid 和token
     */
    String currentUid;
    String currentToken;

    public static final String ACTION_SWITCH_CLASS_OR_CHILD = "com.juziwl.exue.switch_class_or_child";

    public static final String ZERO = "0";
    public static final String ONE = "1";

    /**
     * 在设置那一行显示红点
     */
    public static final String ACTION_SHOW_SETTING_REDPOINT = "com.juziwl.exue.action_show_setting_redpoint";

    /**
     * 修改地址的广播
     */
    public static final String ACTION_MODIFY_ADDRESS = "com.juziwl.exue.action_modify_address";

    /**
     * 获取三结合的广播
     */
    public static final String ACTION_MODIFY_SANJIEHE = "com.juziwl.exue.action_modify_sanjiehe";

    /**
     * 清除系统消息红点的广播
     */
    public static final String ACTION_CLEAR_SYSTEMMSG_REDPOINT = "com.juziwl.exue.clear_systemmsg_redpoint";

    /**
     * 清除通知红点的广播
     */
    public static final String ACTION_CLEAR_NOTICE_REDPOINT = "com.juziwl.exue.clear_notice_redpoint";

    public static final String EXTRA_TIME = "extra_time";

    public static final int MAX_IMAGE_COUNT = 9;
    public static final int MAX_SPACE_IMAGE_COUNT = 30;

    /**
     * 分享
     */
    public static final String EXTRA_CONTENTID = "contentID";
    public static final String EXTRA_CONTENTTITLE = "contentTitle";

    public static final String ACTION_SERVER_MAINTAIN = "com.juziwl.exue.action_server_maintain";

    public static final String ACTION_GOTOTOP = "gotoshoptop";

    public static final String ACTION_INIT_LOACTION = "action_init_loaction";

    public static final String ACTION_BUS_LOACTION = "action_bus_loaction";
    public static final String ACTION_CARD_LOACTION = "action_card_loaction";

    /**
     * 用户签到从个人入口
     */

    public static final String ACTION_FORM_PRESON_SIGN = "action_form_person_sign";

    /**
     * 获取考勤信息的方法
     */
    public static final String ACTION_REPORT_STUDENT_INFOR = "action_report_student_infor";
    public static final String ACTION_REPORT_STUDENT_INFOR_NOW = "action_report_student_infor_now";

    public static final String ACTION_REPORT_STUDENT_ATTENDANCE_QINGJIA = "action_report_student__qingjia";

    /**
     * 家长端成长轨迹推送时
     */
    public static final String ACTION_GOTO_GROWTHTRACK_REPORT = "action_goto_growthtrack_report";

    /**
     * 教师端考勤的几种状态
     */
    public static final String ATTENDANCE_NORMAL = "0";
    public static final String ATTENDANCE_LATE = "1";
    public static final String ATTENDANCE_LEAVE = "2";
    public static final String ATTENDANCE_MISS = "-1";
    public static final String ATTENDANCE_UNBIND = "-2";

    /**
     * 签退错误的页面
     */

    public static final String ATTENDACNE_ERRO = "attendance_erro";

    /**
     * 家长端报平安开通服务
     */
    public static final String REPORTSAFE = "reportSafe";
    public static final String UPDATE_REPORT_SAFE = "updateReportSafeData";

    /**
     * 题库作业和课外作业
     */
    public static final String ACTION_ZUOYE = "com.juziwl.exue_comprehensive.ui.main.zuoye";
    public static final String ACTION_TIKU = "com.juziwl.exue_comprehensive.ui.main.tiku";

    /**
     * 是否是第一次登陆
     */
    public static final String EXTRA_IS_FIRST_LOGIN = "extra_is_first_login";

    /**
     * 第一次登陆修改密码
     */
    public static final String FIRST_LOGIN_MODIFY_PWD = "first_login_modify_pwd";
    /**
     * 作业被删除的状态码
     */
    public static final String CODE_HOMEWORK_DELETED = "4006";

    public static final String ACTION_DELETE_HOMEWORK_ITEM = "HomeworkFragment.action_delete_homework_item";

    public static final String ACTION_GETVIPDATA = "HomeworkFragment.getvipdata";

    public static final String TYPE_QQ = "qq";
    public static final String TYPE_WECHAT = "weixin";
    public static final String TYPE_WEIBO = "sina";

    public static final String EXTRA_THIRDID = "thirdid";
    /**
     * 是否跳转到MainActivity
     */
    public static final String EXTRA_IS_GOTO_MAIN = "extra_is_goto_main";

    /**
     * 第三方未绑定手机号的时候用此参数
     */
    public static final String BUNDLE_DATA_TITLE_SANFANG = "家长您好，请绑定手机号";
    public static final String BUNDLE_ACTION_TITLE = "GetVerificationCodeActivity_BUNDLE_DATA_TITLE";
    public static final String V = "v";

    /**
     * 成长轨迹选择班级返回码
     */
    public static final int CODE_SELECT_CLASS_REQUSET = 3000;
    public static final int CODE_SELECT_TI_MU_REQUSET = 1000;
    public static final String EXTRA_CONTENT = "extra_content";

    public static final String ACTION_POSTION = "PublishGrowthLogActivity.postion";
    public static final String CLASSID = "PublishGrowthLogActivity.classId";
    public static final String CLASSNAME = "PublishGrowthLogActivity.className";

    /**
     * 成长轨迹同步到空间的广播
     */
    public static final String ACTION_SYNCO_SPACE = "PublishGrowthLogActivity.synco.space";

    public static final String ACTION_REFRESH_FRIEND_PROFILE = "com.juziwl.exuecloud.action_refresh_friend_profile";

    public static final String TYPE_CAPTURE_STUDENT_REGISTER = "studentRegister";

    public static final String EXTRA_URL = "extra_url";

    public static final String HTTP_SCHEME = "http://";
    public static final String HTTPS_SCHEME = "https://";

    public static boolean isFirst = true;
    /**
     * 新的添加教材
     */
    public static final String ACTION_ADD_MATERIAL = "action_add_Material";

    /**
     * 更新当前教材
     */
    public static final String UPDATEMATERIAL = "TeachSettingActivity.updatematerial";

    public static final String ADD_FRIEND_AGREE_MSG = "我通过了你的朋友验证请求，现在我们可以开始聊天了";

    public static final String ENCRYPT_PREFIX = "en_";

    /**
     * 用户范围
     */
    public static final String EXTRA_USER_RANGE = "extra_user_range";
    public static final int TYPE_FRIENDS = 0;
    public static final int TYPE_GROUPS = 1;
    public static final int TYPE_ALL = 2;


    /**
     * 跳转到教学端的来源  ipad
     */
    public static String JUMP_SOURCE = "jump_source";

    /**
     * pad跳转过来的
     */
    public static String JUMP_SOURCE_IPAD = "jump_source_ipad";
    /**
     * app自身跳转过来的
     */
    public static String JUMP_SOURCE_APP = "jump_source_app";

    /**
     * 从什么活动的入口发作业的，埋点需要
     */
    public static final String EXTRA_FROM_ENTRANCE = "extra_from_entrance";
}
