package com.mrym.newsbulletion.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.mrym.newsbulletion.R;
import com.mrym.newsbulletion.mvp.activity.skin.ChageSkinPresenter;
import com.mrym.newsbulletion.mvp.activity.skin.SkinView;
import com.mrym.newsbulletion.ui.BaseActivity;
import com.mrym.newsbulletion.utils.statusbar.StatusBarCompat;

import butterknife.BindView;

/**
 * Created by Jian on 2016/10/10.
 * Email: 798774875@qq.com
 * Github: https://github.com/moruoyiming
 */
public class SkinChangeActivity extends BaseActivity<ChageSkinPresenter> implements SkinView {
    public static final String TAG = SkinChangeActivity.class.getCanonicalName();
    private MaterialDialog dialog;
    @BindView(R.id.btn_from_not)
    Button btnnot;
    @BindView(R.id.btn_from_net)
    Button skin;
    @BindView(R.id.activity_changeskin_rc)
    XRecyclerView mSkinGrid;
    @BindView(R.id.header)
    LinearLayout header;
    @BindView(R.id.leftback_toobar_l1)
    RelativeLayout back;
    @BindView(R.id.left_back_title)
    TextView mTitle;

    @Override
    protected ChageSkinPresenter createPresenter() {
        return new ChageSkinPresenter(this);
    }

    @Override
    protected String getTag() {
        return TAG;
    }


    @Override
    public void showLoading(String message) {
        dialog.setContent(message);
        dialog.show();
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_changeskin;
    }

    @Override
    protected void setUpView() {
        StatusBarCompat.translucentStatusBar(SkinChangeActivity.this, true);
//        dynamicAddView(header, "background", R.color.primary_dark);
        dialog = new MaterialDialog.Builder(SkinChangeActivity.this)
                .title("换肤中")
                .content("请耐心等待")
                .canceledOnTouchOutside(false)
                .progress(false, 100, true).build();
        btnnot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.restoreDefaultTheme();
            }
        });
        mTitle.setText("换肤");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        skin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mvpPresenter.chageSkinFromLocal("theme.skin");
            }
        });

    }

    @Override
    protected void destroyActivityBefore() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mvpPresenter = null;
        dialog.dismiss();
        dialog = null;
    }

    @Override
    public void showMessage(String message) {
        dialog.setContent(message);
    }

    @Override
    public void hideLoading() {
        dialog.dismiss();
    }

    @Override
    public void showProgress(int progress) {
        dialog.setProgress(progress);
    }
}