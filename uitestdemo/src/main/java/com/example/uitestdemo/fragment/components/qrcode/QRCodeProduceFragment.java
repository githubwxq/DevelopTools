package com.example.uitestdemo.fragment.components.qrcode;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.MainThread;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.PathUtils;
import androidx.fragment.app.Fragment;

import com.example.qrlibrary.XQRCode;
import com.example.qrlibrary.util.QRCodeProduceUtils;
import com.example.uitestdemo.R;
import com.luck.picture.lib.cameralibrary.util.FileUtil;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.util.FileUtils;
import com.wxq.commonlibrary.util.ImageUtils;
import com.wxq.commonlibrary.util.IntentUtils;
import com.wxq.commonlibrary.util.StringUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QRCodeProduceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QRCodeProduceFragment extends BaseFragment {
    private final int SELECT_FILE_REQUEST_CODE = 700;

    /**
     * 二维码背景图片
     */
    private Bitmap backgroundImage = null;

    @BindView(R.id.et_input)
    EditText mEtInput;
    @BindView(R.id.iv_qrcode)
    ImageView mIvQrcode;

    @BindView(R.id.sc_change)
    SwitchCompat mScChange;

    @BindView(R.id.ll_normal_create)
    LinearLayout mLLNormalCreate;

    @BindView(R.id.ll_complex_create)
    LinearLayout mLLComplexCreate;
    @BindView(R.id.et_size)
    EditText mEtSize;
    @BindView(R.id.et_margin)
    EditText mEtMargin;
    @BindView(R.id.et_dotScale)
    EditText mEtDotScale;

    @BindView(R.id.cb_autoColor)
    CheckBox mCbAutoColor;
    @BindView(R.id.et_colorDark)
    EditText mEtColorDark;
    @BindView(R.id.et_colorLight)
    EditText mEtColorLight;

    @BindView(R.id.cb_whiteMargin)
    CheckBox mCbWhiteMargin;
    @BindView(R.id.cb_binarize)
    CheckBox mCbBinarize;
    @BindView(R.id.et_binarizeThreshold)
    EditText mEtBinarizeThreshold;


    private boolean isQRCodeCreated = false;

    public static QRCodeProduceFragment newInstance() {
        QRCodeProduceFragment fragment = new QRCodeProduceFragment();
        return fragment;
    }


    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_q_r_code_produce;
    }

    @Override
    protected void initViews() {
        mScChange.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mLLComplexCreate.setVisibility(View.VISIBLE);
                mLLNormalCreate.setVisibility(View.GONE);
            } else {
                mLLComplexCreate.setVisibility(View.GONE);
                mLLNormalCreate.setVisibility(View.VISIBLE);
            }
        });

        mCbAutoColor.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mEtColorDark.setEnabled(!isChecked);
            mEtColorLight.setEnabled(!isChecked);
        });

        mCbBinarize.setOnCheckedChangeListener((buttonView, isChecked) -> mEtBinarizeThreshold.setEnabled(isChecked));
    }




    @OnClick({R.id.btn_save, R.id.btn_create_no_logo, R.id.btn_create_with_logo, R.id.btn_background_image, R.id.btn_remove_background_image, R.id.btn_create})
    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                saveQRCode();
                break;
            case R.id.btn_create_no_logo:
                if (StringUtils.isSpace(mEtInput.getEditableText().toString())) {
                  showToast("请输入二维码内容!");
                    return;
                }

                createQRCodeWithLogo(null);

                break;
            case R.id.btn_create_with_logo:
                if (StringUtils.isSpace(mEtInput.getEditableText().toString())) {
                    showToast("请输入二维码内容!");
                    return;
                }

                createQRCodeWithLogo(ImageUtils.getBitmap(R.mipmap.ic_launcher));

                break;

            case R.id.btn_background_image:
//                startActivityForResult(IntentUtils.getDocumentPickerIntent(IntentUtils.DocumentType.IMAGE), SELECT_FILE_REQUEST_CODE);
                break;
            case R.id.btn_remove_background_image:
                backgroundImage = null;
                showToast("背景图片已被去除！");
                break;
            case R.id.btn_create:
                if (StringUtils.isSpace(mEtInput.getEditableText().toString())) {
                  showToast("请输入二维码内容!");
                    return;
                }

                createQRCodeWithBackgroundImage();

                break;
            default:
                break;
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_FILE_REQUEST_CODE && resultCode == RESULT_OK && data.getData() != null) {
            try {
//                Uri imageUri = data.getData();
//                backgroundImage = BitmapFactory.decodeFile(FileUtils.getFilePathByUri(getContext(), imageUri));
//                showToast("成功添加背景图片！");
            } catch (Exception e) {
                e.printStackTrace();
               showToast("添加背景图片失败！");
            }
        }
    }

 
    private void saveQRCode() {
//        if (isQRCodeCreated) {
//            boolean result = ImageUtils.save(ImageUtils.view2Bitmap(mIvQrcode), FileUtils.getDiskCacheDir() + File.separator + "XQRCode_" + DateUtils.getNowMills() + ".png", Bitmap.CompressFormat.PNG);
//            showToast("二维码保存" + (result ? "成功" : "失败") + "!");
//        } else {
//            showToast("请先生成二维码!");
//        }
    }

    /**
     * 生成简单的带logo的二维码
     * @param logo
     */
  
    private void createQRCodeWithLogo(Bitmap logo) {
        showQRCode(XQRCode.createQRCodeWithLogo(mEtInput.getText().toString(), 400, 400, logo));
        isQRCodeCreated = true;
    }

    @MainThread
    private void showQRCode(Bitmap QRCode) {
        mIvQrcode.setImageBitmap(QRCode);
    }


    /**
     * String转Int（防止崩溃）
     *
     * @param value
     * @param defValue 默认值
     * @return
     */
    public  int toInt(final String value, final int defValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /**
     * String转Float（防止崩溃）
     *
     * @param value
     * @param defValue 默认值
     * @return
     */
    public static float toFloat(final String value, final float defValue) {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /**
     * 生成复杂的带背景图案的二维码
     */
   
    private void createQRCodeWithBackgroundImage() {
        QRCodeProduceUtils.Builder builder = XQRCode.newQRCodeBuilder(mEtInput.getText().toString())
                .setAutoColor(mCbAutoColor.isChecked())
                .setWhiteMargin(mCbWhiteMargin.isChecked())
                .setBinarize(mCbBinarize.isChecked())
                .setBackgroundImage(backgroundImage);
        if (mEtSize.getText().length() != 0) {
            builder.setSize(toInt(mEtSize.getText().toString(), 400));
        }
        if (mEtMargin.getText().length() != 0) {
            builder.setMargin(toInt(mEtMargin.getText().toString(), 20));
        }
        if (mEtDotScale.getText().length() != 0) {
            builder.setDataDotScale(toFloat(mEtDotScale.getText().toString(), 0.3F));
        }
        if (mEtDotScale.getText().length() != 0) {
            builder.setDataDotScale(toFloat(mEtDotScale.getText().toString(), 0.3F));
        }
        if (!mCbAutoColor.isChecked()) {
            try {
                builder.setColorDark(Color.parseColor(mEtColorDark.getText().toString()));
                builder.setColorLight(Color.parseColor(mEtColorLight.getText().toString()));
            } catch (Exception e) {
                e.printStackTrace();
               showToast("色值填写出错!");
            }
        }
        if (mEtBinarizeThreshold.getText().length() != 0) {
            builder.setBinarizeThreshold(toInt(mEtBinarizeThreshold.getText().toString(), 128));
        }

        showQRCode(builder.build());
        isQRCodeCreated = true;
    }
    
}