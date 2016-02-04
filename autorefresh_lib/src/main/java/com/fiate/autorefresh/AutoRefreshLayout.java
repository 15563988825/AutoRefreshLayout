package com.fiate.autorefresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by wangyunxiu on 16/2/4.
 */
public class AutoRefreshLayout extends RelativeLayout{

    private static final int V_LOADING = 1;
    private static final int V_LOADFAILD = 2;
    private static final int V_ORI = 3;

    private Context context;
    private AttributeSet attrs;
    private int defStyle;
    private LayoutInflater inflater;
    private int wait_refresh_layout_id =R.layout.default_refresh_view;//待刷新ViewID
    private View wait_refresh_view =null;//待刷新View

    private int refresh_touch_view_id=R.id.tv_refresh;//点击刷新的ViewID
    private View refresh_touch_view=null;//点击刷新View

    private int refreshing_view_id=R.layout.default_refreshing_view;//点击刷新的ViewID
    private View refreshing_view=null;//点击刷新View

    private OnAotoRefreshListener onAotoRefreshListener=null;


    private View ori_view=null;//原始View

    public AutoRefreshLayout(Context context) {
        super(context);
    }

    public AutoRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        this.attrs=attrs;
        this.defStyle=defStyleAttr;
        init();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ori_view=this.getChildAt(0);
        setCurrentView(V_ORI);
    }

    private void init() {

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AutoRefreshLayout, defStyle, 0);
            int n = a.getIndexCount();
            for (int i=0;i<n;i++){
                int attr=a.getIndex(i);
                if(attr == R.styleable.AutoRefreshLayout_loadfaild_layout){
                    //等待刷新Layout
                    wait_refresh_layout_id =a.getResourceId(attr,R.layout.default_refresh_view);
                }
                if(attr==R.styleable.AutoRefreshLayout_touchtoload_view){
                    //点击刷新Layout
                    refresh_touch_view_id =a.getResourceId(attr,R.id.tv_refresh);
                }
                if(attr==R.styleable.AutoRefreshLayout_refreshing_layout){
                    //正在刷新Layout
                    refreshing_view_id =a.getResourceId(attr,R.layout.default_refreshing_view);
                }
            }
            a.recycle();

        wait_refresh_view =inflater.inflate(wait_refresh_layout_id, null);
        refreshing_view =inflater.inflate(refreshing_view_id, null);


        //点击刷新View
        refresh_touch_view= wait_refresh_view.findViewById(refresh_touch_view_id);

        if(refresh_touch_view!=null)
        refresh_touch_view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onAotoRefreshListener!=null){
                    onAotoRefreshListener.OnRefreshStart(AutoRefreshLayout.this);
                }
                setCurrentView(V_LOADING);
            }
        });
    }

    private void setCurrentView(int CURRENT_VIEW){
        ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        switch (CURRENT_VIEW){
            case V_LOADING:
                //正在刷新
                removeAllViews();
                addView( refreshing_view,params );
                break;
            case V_LOADFAILD:
                //刷新失败
                removeAllViews();
                addView( wait_refresh_view,params );
                break;
            case V_ORI:
                //原布局
                removeAllViews();
                addView(ori_view,params);
                break;
        }
    }
    public void setOnAotoRefreshListener(OnAotoRefreshListener onAotoRefreshListener) {
        this.onAotoRefreshListener = onAotoRefreshListener;
    }

    /**
     * 结束刷新-成功
     */
    public void setRefreshSucceed(){
        if(onAotoRefreshListener!=null){
            onAotoRefreshListener.OnRefreshSucceed(AutoRefreshLayout.this);
        }
        setCurrentView(V_ORI);
    }

    /**
     * 结束刷新-失败
     */
    public void setRefreshFaild(){
        if(onAotoRefreshListener!=null){
            onAotoRefreshListener.OnRefreshFaild(AutoRefreshLayout.this);
        }
        setCurrentView(V_LOADFAILD);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View v = getChildAt(0);
        v.layout(l, t, r, b);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
