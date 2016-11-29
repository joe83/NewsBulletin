package com.mrym.newsbulletion.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bm.library.PhotoView;
import com.mrym.newsbulletion.R;
import com.mrym.newsbulletion.domain.constans.GlobalVariable;
import com.mrym.newsbulletion.domain.modle.NewsPhotoDetail;
import com.mrym.newsbulletion.domain.modle.PhotoGirl;
import com.mrym.newsbulletion.utils.GlideUtils;
import com.mrym.newsbulletion.utils.ImageLoaderUtils;
import com.mrym.newsbulletion.utils.ImmersedStatusbarUtils;
import com.mrym.newsbulletion.utils.common.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Jian on 2016/10/07.
 * Email: 798774875@qq.com
 * Github: https://github.com/moruoyiming
 */
public class ViewPagerActivity extends Activity {

    private ViewPager mPager;
    private NewsPhotoDetail newsPhotoDetail;
    private boolean isfullTag = true;
    @Bind(R.id.rl)
    RelativeLayout r1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            full(isfullTag);
            setContentView(R.layout.activity_view_pager);
            Intent intent = getIntent();
            newsPhotoDetail=(NewsPhotoDetail)intent.getSerializableExtra(GlobalVariable.PHOTO_DETAIL);
            mPager = (ViewPager) findViewById(R.id.pager);
            mPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
            mPager.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return newsPhotoDetail.getPictures().size();
                }

                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }

                @Override
                public Object instantiateItem(ViewGroup container, int position) {
                    PhotoView view = new PhotoView(ViewPagerActivity.this);
                    view.enable();
                    view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    ImageLoaderUtils.display(ViewPagerActivity.this,view,newsPhotoDetail.getPictures().get(position).getImgSrc(),R.mipmap.shouyetu, R.mipmap.shouyetu);
                    container.addView(view);
                    return view;
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView((View) object);
                }
            });
            r1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isfullTag) {
                        ToastUtils.show("显示状态栏");
                        full(false);
                    } else {
                        ToastUtils.show("隐藏状态栏");
                        full(true);
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 入口
     *
     * @param context
     * @param mNewsPhotoDetail
     */
    public static void startAction(Context context, NewsPhotoDetail mNewsPhotoDetail) {
        Intent intent = new Intent(context, ViewPagerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(GlobalVariable.PHOTO_DETAIL, mNewsPhotoDetail);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    private void full(boolean enable) {
        if (enable) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attr);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
}
