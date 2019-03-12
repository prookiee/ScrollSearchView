package com.hans.scrollsearchview;

import android.animation.IntEvaluator;
import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private static final float ENDMARGINLEFT = 50;
    private static final float ENDMARGINTOP = 5;
    private static final float STARTMARGINLEFT = 20;
    private static final float STARTMARGINTOP = 140;

    private RelativeLayout rv_bar;
    private RelativeLayout rv_search;
    private ImageView iv_search;

    /**
     * 顶部栏从透明变成不透明滑动的距离
     */
    private int scrollLength;
    private NestedScrollView sc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_bar = (RelativeLayout) findViewById(R.id.rv_bar);
        rv_search = (RelativeLayout) findViewById(R.id.rv_search);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        sc = ((NestedScrollView) findViewById(R.id.scrollView));


//        ObservableScrollView sv_search = (ObservableScrollView) findViewById(R.id.scrollView);
        MyListView lv_searchview = (MyListView) findViewById(R.id.listView);
        String[] strings = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",};
        lv_searchview.setAdapter(new searchAdapter(MainActivity.this, strings));
//        sv_search.smoothScrollTo(0, 0);

        sc.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {

            private int evaluatemargin;
            private int evaluatetop;
            private FrameLayout.LayoutParams layoutParams;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int absY = Math.abs(scrollY);
                if ((scrollLength - absY) > 0) {
                    IntEvaluator evaluator = new IntEvaluator();
                    float percent = (float) (scrollLength - absY) / scrollLength;
                    if (percent <= 1) {
                        // 透明度
                        int evaluate = evaluator.evaluate(percent, 255, 0);
                        rv_bar.getBackground().setAlpha(evaluate);
                        // 搜索栏左右的margin值
                        evaluatemargin = evaluator.evaluate(percent, DensityUtil.dip2px(MainActivity.this, ENDMARGINLEFT), DensityUtil.dip2px(MainActivity.this, STARTMARGINLEFT));
                        // 搜索栏顶部margin值
                        evaluatetop = evaluator.evaluate(percent, DensityUtil.dip2px(MainActivity.this, ENDMARGINTOP), DensityUtil.dip2px(MainActivity.this, STARTMARGINTOP));
                        layoutParams = (FrameLayout.LayoutParams) rv_search.getLayoutParams();
                        layoutParams.setMargins(evaluatemargin, evaluatetop, evaluatemargin, 0);
                        rv_search.requestLayout();
                    }
                } else {
                    rv_bar.getBackground().setAlpha(255);
                    if (layoutParams != null) {
                        layoutParams.setMargins(DensityUtil.dip2px(MainActivity.this, ENDMARGINLEFT), DensityUtil.dip2px(MainActivity.this, 5), DensityUtil.dip2px(MainActivity.this, ENDMARGINLEFT), 0);
                        rv_search.requestLayout();
                    }
                }

            }
        });


//        sv_search.setScrollViewListener(new ScrollViewListener() {
//
//            private int evaluatemargin;
//            private int evaluatetop;
//            private FrameLayout.LayoutParams layoutParams;
//
//            @Override
//            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
//                int absY = Math.abs(y);
//                if ((scrollLength - absY) > 0) {
//                    IntEvaluator evaluator = new IntEvaluator();
//                    float percent = (float) (scrollLength - absY) / scrollLength;
//                    if (percent <= 1) {
//                        // 透明度
//                        int evaluate = evaluator.evaluate(percent, 255, 0);
//                        rv_bar.getBackground().setAlpha(evaluate);
//                        // 搜索栏左右的margin值
//                        evaluatemargin = evaluator.evaluate(percent, DensityUtil.dip2px(MainActivity.this, ENDMARGINLEFT), DensityUtil.dip2px(MainActivity.this, STARTMARGINLEFT));
//                        // 搜索栏顶部margin值
//                        evaluatetop = evaluator.evaluate(percent, DensityUtil.dip2px(MainActivity.this, ENDMARGINTOP), DensityUtil.dip2px(MainActivity.this, STARTMARGINTOP));
//                        layoutParams = (FrameLayout.LayoutParams) rv_search.getLayoutParams();
//                        layoutParams.setMargins(evaluatemargin, evaluatetop, evaluatemargin, 0);
//                        rv_search.requestLayout();
//                    }
//                } else {
//                    rv_bar.getBackground().setAlpha(255);
//                    if (layoutParams != null) {
//                        layoutParams.setMargins(DensityUtil.dip2px(MainActivity.this, ENDMARGINLEFT), DensityUtil.dip2px(MainActivity.this, 5), DensityUtil.dip2px(MainActivity.this, ENDMARGINLEFT), 0);
//                        rv_search.requestLayout();
//                    }
//                }
//            }
//        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        int height_rv = rv_bar.getHeight();
        int height_iv = iv_search.getHeight();

        scrollLength = Math.abs(height
                _iv - height_rv);

        //把顶部bar设置为透明
        rv_bar.getBackground().setAlpha(0);

    }


    class searchAdapter extends BaseAdapter {
        Context context;
        String[] strings;
        private View view;
        private TextView tv_search;

        public searchAdapter(Context context, String[] strings) {
            this.context = context;
            this.strings = strings;
        }

        @Override
        public int getCount() {
            return strings.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                view = View.inflate(context, R.layout.layout_search, null);
                tv_search = (TextView) view.findViewById(R.id.tv_search);
            } else {
                view = convertView;
            }

            tv_search.setText(strings[position]);
            return view;
        }
    }
}
