package me.chen_wei.samples.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.kotlintestdemo.R


class CustomDialogFragment: DialogFragment() {

    private var titleTv: TextView? = null
    private var messageTv: TextView? = null
    private var leftButton: TextView? = null
    private var rightButton: TextView? = null

    private var leftClicks: (() -> Unit)? = null
    private var rightClicks: (() -> Unit)? = null

    var cancelOutside: Boolean = true

    var title: String? = null
    var message: String? = null
    var leftKey: String? = null
    var leftButtonDismissAfterClick = true
    var rightKey: String? = null
    var rightButtonDismissAfterClick = true

    companion object {
        fun newInstance(): CustomDialogFragment {
            return CustomDialogFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Material_Light_Dialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.dialog_custom, container)
        titleTv = view?.findViewById(R.id.title_tv)
        messageTv = view?.findViewById(R.id.message_tv)
        leftButton = view?.findViewById(R.id.left_button)
        rightButton = view?.findViewById(R.id.right_button)

        init()
        return view
    }

    private fun init() {
        dialog?.setCancelable(cancelOutside)

        title?.let { text ->
            titleTv?.visibility = View.VISIBLE
            titleTv?.text = text
        }

        message?.let { text ->
            messageTv?.visibility = View.VISIBLE
            messageTv?.text = text
        }

        leftClicks?.let { onClick ->
            leftButton?.text = leftKey
            leftButton?.visibility = View.VISIBLE
            leftButton?.setOnClickListener {
                onClick()
                if (leftButtonDismissAfterClick) {
                    dismissAllowingStateLoss()
                }
            }
        }

        rightClicks?.let { onClick ->
            rightButton?.text = rightKey
            rightButton?.setOnClickListener {
                onClick()
                if (rightButtonDismissAfterClick) {
                    dismissAllowingStateLoss()
                }
            }
        }
    }

    fun leftClicks(key: String = "取消", dismissAfterClick: Boolean = true, callback: () -> Unit) {
        leftKey = key
        leftButtonDismissAfterClick = dismissAfterClick
        leftClicks = callback
    }

    fun rightClicks(key: String = "确定", dismissAfterClick: Boolean = true, callback: () -> Unit) {
        rightKey = key
        rightButtonDismissAfterClick = dismissAfterClick
        rightClicks = callback
    }
}
