package com.hacknife.refreshlayout.activity.example;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hacknife.refresh.core.api.Refresh;
import com.hacknife.refreshlayout.R;
import com.hacknife.refresh.adapter.MaterialHeader;
import com.hacknife.refresh.core.constant.RefreshState;
import com.hacknife.refresh.core.constant.SpinnerStyle;
import com.hacknife.refresh.core.footer.BallPulseFooter;
import com.hacknife.refresh.core.listener.SimpleMultiPurposeListener;

/**
 * 在Java代码中指定Header和Footer
 */
public class AssignCodeExampleActivity extends AppCompatActivity {

    private static boolean isFirstEnter = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_assign_code);

        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        final Refresh refreshLayout = (Refresh) findViewById(R.id.refreshLayout);
        //设置 Header 为 Material风格
        refreshLayout.setRefreshHeader(new MaterialHeader(this).setShowBezierWave(true));
        //设置 Footer 为 球脉冲
        refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));

        /*
         * 以下代码仅仅为了演示效果而已，不是必须的
         */
        //设置主题颜色
        refreshLayout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);

        if (isFirstEnter) {
            isFirstEnter = false;
//            //触发上拉加载
//            refreshLayout.autoLoadMore(250, 250, 1.5f);
            //通过多功能监听接口实现 在第一次加载完成之后 自动刷新
            refreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener(){
                @Override
                public void onStateChanged(@NonNull Refresh refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
                    if (oldState == RefreshState.LoadFinish && newState == RefreshState.None) {
                        refreshLayout.autoRefresh();
                        refreshLayout.setOnMultiPurposeListener(null);
                    }
                }
                @Override
                public void onLoadMore(@NonNull Refresh refreshLayout) {
                    refreshLayout.finishLoadMore(2000);
                }
                @Override
                public void onRefresh(@NonNull Refresh refreshLayout) {
                    refreshLayout.finishRefresh(3000);
                }
            });
        }
    }

}
