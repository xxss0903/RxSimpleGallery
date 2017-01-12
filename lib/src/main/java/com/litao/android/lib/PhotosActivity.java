package com.litao.android.lib;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.litao.android.lib.entity.PhotoEntry;
import com.litao.android.lib.rx.RxBus;

import java.util.List;

/**
 * Created by 李涛 on 16/4/29.
 */
public class PhotosActivity extends BaseGalleryActivity implements
        View.OnClickListener {

    private TextView mTextViewOpenAlbum;
    private TextView mTextViewSelectedCount;
    private TextView mTextViewSend;
    private LinearLayout mLlBottomBar;

    private List<PhotoEntry> mSelectedPhotos;

    private Configuration mConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rx_simple_activity_photos);

        initConfig();

        setTitle("Photos");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initView();

        attachFragment(R.id.gallery_root);
    }

    private void initConfig() {
        Intent intent = getIntent();
        Configuration config = intent.getParcelableExtra(RxChoosePhoto.ARGUMENT_CONFIG);
        if (config != null) {
            mConfig = config;
        } else {
            mConfig = new Configuration.Builder().build();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        mLlBottomBar = (LinearLayout) findViewById(R.id.ll_bottom_bar);

        mTextViewOpenAlbum = (TextView) findViewById(R.id.album);
        mTextViewSelectedCount = (TextView) findViewById(R.id.selected_count);
        mTextViewSend = (TextView) findViewById(R.id.send_photos);

        mTextViewOpenAlbum.setOnClickListener(this);
        mTextViewSelectedCount.setOnClickListener(this);
        mTextViewSend.setOnClickListener(this);

        // change color
        mLlBottomBar.setBackgroundColor(mConfig.bottomBarColor);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(mConfig.topBarColor));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.album) {
            openAlbum();
        } else if (view.getId() == R.id.send_photos) {
            sendPhotos();
        }
    }

    /**
     * @return
     */
    @Override
    public Configuration getConfiguration() {
        return mConfig;
    }

    @Override
    public List<PhotoEntry> getSelectPhotos() {
        return mSelectedPhotos;
    }

    @Override
    public void onSelectedCountChanged(int count) {
        mTextViewSelectedCount.setVisibility(count > 0 ? View.VISIBLE : View.INVISIBLE);
        if (mConfig.singleSelect) {
            mTextViewSelectedCount.setText(String.valueOf(count) + "/" + 1);
        } else {
            mTextViewSelectedCount.setText(String.valueOf(count) + "/" + mConfig.maximum);
        }
    }

    @Override
    public void onAlbumChanged(String name) {
        getSupportActionBar().setSubtitle(name);
    }

    @Override
    public void onTakePhoto(PhotoEntry entry) {
        finish();
    }

    @Override
    public void onChoosePhotos(List<PhotoEntry> entries) {
        Photos photos = new Photos();
        photos.setPhotos(entries);
        RxBus.getDefault().post(photos);
        finish();
    }

    /**
     * @param entry
     */
    @Override
    public void onPhotoClick(PhotoEntry entry) {

    }
}
