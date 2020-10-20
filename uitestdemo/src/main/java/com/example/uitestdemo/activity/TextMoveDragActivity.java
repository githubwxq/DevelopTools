package com.example.uitestdemo.activity;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uitestdemo.R;
import com.example.uitestdemo.adapter.PostItemTouchCallback;
import com.example.uitestdemo.bean.PicAndTextItem;
import com.example.uitestdemo.bean.PostItem;
import com.example.uitestdemo.adapter.PostPublishAdapter;
import com.example.uitestdemo.bean.TipItem;
import com.example.uitestdemo.bean.VideoItem;
import com.juziwl.uilibrary.MyItemTouchCallback;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TextMoveDragActivity extends BaseActivity {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    PostPublishAdapter adapter;
    @Override
    protected void initViews() {

        List<PostItem> list=new ArrayList<>();

        TipItem tipItem=new TipItem();
        tipItem.setTipMessage("我是提示栏目");
        list.add(tipItem);







        VideoItem videoItem=new VideoItem();
        list.add(videoItem);
        PicAndTextItem picAndTextItem=new PicAndTextItem();
        list.add(picAndTextItem);

        VideoItem videoItem1=new VideoItem();
        list.add(videoItem1);
        PicAndTextItem picAndTextItem1=new PicAndTextItem();
        list.add(picAndTextItem1);


        VideoItem videoItem2=new VideoItem();
        list.add(videoItem2);
        PicAndTextItem picAndTextItem2=new PicAndTextItem();
        list.add(picAndTextItem2);

        VideoItem videoItem3=new VideoItem();
        list.add(videoItem3);
        PicAndTextItem picAndTextItem3=new PicAndTextItem();
        list.add(picAndTextItem3);



        TipItem tipItem2=new TipItem();
        list.add(tipItem2);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter=new PostPublishAdapter(list);
        //1.自定义的ItemTouchHeloer.Callback
        PostItemTouchCallback simpleItemTouchHelper = new PostItemTouchCallback(adapter,list);
        //2.利用这个Callback构造ItemTouchHelper
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchHelper);
        //3.把ItemTouchHelper和RecyclerView关联起来.
        itemTouchHelper.attachToRecyclerView(recyclerView);


        recyclerView.setAdapter(adapter);


    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_text_move_drag;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }


}
