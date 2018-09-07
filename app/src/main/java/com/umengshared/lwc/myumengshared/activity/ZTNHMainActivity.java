package com.umengshared.lwc.myumengshared.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.IntentService;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.umengshared.lwc.myumengshared.R;
import com.umengshared.lwc.myumengshared.Tools.Config;
import com.umengshared.lwc.myumengshared.Tools.DownLoadTools.DownLoadFileService;
import com.umengshared.lwc.myumengshared.Tools.ImageUtils;
import com.umengshared.lwc.myumengshared.Tools.MyLog;
import com.umengshared.lwc.myumengshared.Tools.MyToast;
import com.umengshared.lwc.myumengshared.Tools.PopupwindownUtils;
import com.umengshared.lwc.myumengshared.base.BaseActivity;
import com.umengshared.lwc.myumengshared.fragment.FollowFragment;
import com.umengshared.lwc.myumengshared.fragment.HomeFragment;
import com.umengshared.lwc.myumengshared.fragment.MeFragment;
import com.umengshared.lwc.myumengshared.fragment.ZhiBoFragment;
import com.umengshared.lwc.myumengshared.umeng.ShareTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description: ZTNHMainActivity
 * @Author: lingwancai
 * @Time: 2018/7/3 15:06
 */

public class ZTNHMainActivity extends BaseActivity {

    private static final int INT_REQUEST_CODE = 1000;
    private static final String RADIO_GROUP_ITEM_ID = "radio_group_item_id";
    private static final String TAG = "ZTNHMainActivity";
    private static final String ITEM_HOME_BT = "item_home_bt";
    private static final String ITEM_ZHIBO_BT = "item_zhibo_bt";
    private static final String ITEM_FOLLOW_BT = "item_follow_bt";
    private static final String ITEM_ME_BT = "item_me_bt";
    private static final int TAKE_PHOTO = 0001;
    private static final int CHOOSE_PHOTO = 0002;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_zhibo)
    RadioButton rbZhibo;
    @BindView(R.id.rb_guanzhu)
    RadioButton rbGuanzhu;
    @BindView(R.id.rb_me)
    RadioButton rbMe;
    @BindView(R.id.rg_bottom_group)
    RadioGroup rgBottomGroup;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_other)
    ImageView ivOther;

    private HomeFragment homeFragment;
    private ZhiBoFragment zhiBoFragment;
    private FollowFragment followFragment;
    private MeFragment meFragment;

    public DownLoadFileService.DownLoadBinder mDownloadBinder;
    /**
     * 获取下载的binder
     */
    public ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mDownloadBinder = (DownLoadFileService.DownLoadBinder) service;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private Uri imageUri;
    private ImageView ivUserPhoto;
    private String imageFilePath;

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        //启动下载服务
        Intent intent = new Intent(this, DownLoadFileService.class);
        startService(intent);
        bindService(intent, mConnection, BIND_AUTO_CREATE);

        //初始化fragment
        initFragments(savedInstanceState);
        //异常情况activity被回收，取出临时数据
        if (savedInstanceState != null) {
//            int radioId = savedInstanceState.getInt(RADIO_GROUP_ITEM_ID);
            boolean itemhomebt = savedInstanceState.getBoolean(ITEM_HOME_BT);
            boolean itemzhibobt = savedInstanceState.getBoolean(ITEM_ZHIBO_BT);
            boolean itemfollowbt = savedInstanceState.getBoolean(ITEM_FOLLOW_BT);
            boolean itemmebt = savedInstanceState.getBoolean(ITEM_ME_BT);
            if (itemhomebt) {
                showFragment(homeFragment);
            }
            if (itemzhibobt) {
                showFragment(zhiBoFragment);
            }
            if (itemfollowbt) {
                showFragment(followFragment);
            }
            if (itemmebt) {
                showFragment(meFragment);
            }

        } else {//正常加载页面
            showFragment(homeFragment);
        }

        //设置drawerLayout
        setDrawerLayoutView();

    }

    /**
     * @Description: 显示fragment
     * @Author: lingwancai
     * @Time: 2018/8/7 10:50
     */
    private void showFragment(Fragment fragment) {
        android.support.v4.app.FragmentManager fragmentManager = this.getSupportFragmentManager();
        if (fragment instanceof HomeFragment) {
            fragmentManager.beginTransaction().show(homeFragment).hide(zhiBoFragment).hide(followFragment).hide(meFragment).commit();
        } else if (fragment instanceof ZhiBoFragment) {
            fragmentManager.beginTransaction().show(zhiBoFragment).hide(homeFragment).hide(followFragment).hide(meFragment).commit();
        } else if (fragment instanceof FollowFragment) {
            fragmentManager.beginTransaction().show(followFragment).hide(homeFragment).hide(zhiBoFragment).hide(meFragment).commit();
        } else {
            fragmentManager.beginTransaction().show(meFragment).hide(homeFragment).hide(zhiBoFragment).hide(followFragment).commit();
        }

    }

    /**
     * @Description: 初始化fragment
     * @Author: lingwancai
     * @Time: 2018/8/7 10:27
     */
    private void initFragments(Bundle savedInstanceState) {
        android.support.v4.app.FragmentManager fragmentManager = this.getSupportFragmentManager();
        if (savedInstanceState != null) {
            homeFragment = (HomeFragment) fragmentManager.getFragment(savedInstanceState, HomeFragment.class.getSimpleName());
            zhiBoFragment = (ZhiBoFragment) fragmentManager.getFragment(savedInstanceState, ZhiBoFragment.class.getSimpleName());
            followFragment = (FollowFragment) fragmentManager.getFragment(savedInstanceState, FollowFragment.class.getSimpleName());
            meFragment = (MeFragment) fragmentManager.getFragment(savedInstanceState, MeFragment.class.getSimpleName());
        } else {
            homeFragment = HomeFragment.newInstance("", "");
            zhiBoFragment = ZhiBoFragment.newInstance("", "");
            followFragment = FollowFragment.newInstance("", "");
            meFragment = MeFragment.newInstance("", "");
            if (!homeFragment.isAdded()) {
                fragmentManager.beginTransaction().add(R.id.fl_content, homeFragment, HomeFragment.class.getSimpleName()).commit();
            }
            if (!zhiBoFragment.isAdded()) {
                fragmentManager.beginTransaction().add(R.id.fl_content, zhiBoFragment, ZhiBoFragment.class.getSimpleName()).commit();
            }
            if (!followFragment.isAdded()) {
                fragmentManager.beginTransaction().add(R.id.fl_content, followFragment, FollowFragment.class.getSimpleName()).commit();
            }
            if (!meFragment.isAdded()) {
                fragmentManager.beginTransaction().add(R.id.fl_content, meFragment, MeFragment.class.getSimpleName()).commit();
            }

        }
    }

    ArrayList<String> listTab = new ArrayList<String>();

    /**
     * 设置drawerLayout内容
     */
    private void setDrawerLayoutView() {

        //修改头像
        View headView = this.navView.getHeaderView(0);
        ivUserPhoto = headView.findViewById(R.id.imageView);
        listTab.clear();
        listTab.add("拍照");
        listTab.add("相册");
        ivUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupwindownUtils.showPops(ZTNHMainActivity.this, ZTNHMainActivity.this, contentView, new PopupwindownUtils.OnPopupWindowListener() {
                    @Override
                    public void OnClickListener(Object obj) {
                        String tabString = (String) obj;
                        if ("拍照".equals(tabString)) {
                            File outPutImage = new File(getExternalCacheDir(), "output_image.jpg");
                            if (outPutImage.exists()) {
                                outPutImage.delete();
                            }
                            try {
                                outPutImage.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            imageFilePath = outPutImage.getAbsolutePath();

                            //文件权限
                            if (Build.VERSION.SDK_INT >= 24) {
                                imageUri = FileProvider.getUriForFile(ZTNHMainActivity.this, "com.umengshared.lwc.myumengshared.fileprovider",
                                        outPutImage);
                            } else {
                                imageUri = Uri.fromFile(outPutImage);
                            }

                            //启动相机
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            startActivityForResult(intent, TAKE_PHOTO);

                        } else {//相册
                            //动态请求 写入文件权限
                            if (ContextCompat.checkSelfPermission(ZTNHMainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(ZTNHMainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                            } else {
                                openAlum();
                            }
                        }

                    }
                }, listTab, 1);
            }
        });

        final MenuItem fontTextSize = navView.getMenu().findItem(R.id.nav_fonts);
        if (getFontSize() == 2.0f) {
            fontTextSize.setTitle("小号字体");
        } else {
            fontTextSize.setTitle("大号字体");
        }
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setCheckable(false);
                switch (item.getItemId()) {
                    case R.id.nav_setting:
                        MyToast.showToast("设置");
                        break;
                    case R.id.nav_services://夜间模式
                        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
                            @Override
                            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

                            }

                            @Override
                            public void onDrawerOpened(@NonNull View drawerView) {

                            }

                            @Override
                            public void onDrawerClosed(@NonNull View drawerView) {

                            }

                            @Override
                            public void onDrawerStateChanged(int newState) {

                            }
                        });
                        //存储变量
                        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(ZTNHMainActivity.this);
                        //判断模式
                        int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                        if (mode == Configuration.UI_MODE_NIGHT_YES) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            preference.edit().putBoolean(Config.ITEM_MODE_TYPE, false);
                        } else {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            preference.edit().putBoolean(Config.ITEM_MODE_TYPE, true);
                        }
                        getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
                        //调用该方法，必须对数据进行保存，如fragment，CheckBox, RadioBox 等
                        recreate();
                        break;
                    case R.id.nav_fonts:
                        MyToast.showToast("设置成功");
                        if (getFontSize() == 2.0) {
                            fontTextSize.setTitle("大号字体");
                            setFontSize(1.0f);
                        } else {
                            fontTextSize.setTitle("小号字体");
                            setFontSize(2.0f);
                        }
                        recreate();
                        break;
                }
//                drawerLayout.closeDrawers();
                drawerLayout.closeDrawer(Gravity.START);
                return true;
            }
        });
    }

    /**
     * 打开相册
     */
    private void openAlum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlum();
                } else {
                    MyToast.showToast("拒绝权限将无法使用该功能！");
                }
                break;
            case INT_REQUEST_CODE:
                ShareTools share = new ShareTools(ZTNHMainActivity.this, contentView, "http://www.baidu.com", "title测试");
                share.init();
                break;
            default:
        }

    }

    /**
     * @Description: 显示首页
     * @Author: lingwancai
     * @Time: 2018/7/3 15:41
     */
    private void showHomeFragment() {
        tvTitleName.setText("首页");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        if (homeFragment == null) {
            homeFragment = HomeFragment.newInstance("", "");
            fragmentTransaction.add(R.id.fl_content, homeFragment);
        }
        commitShowFragment(fragmentTransaction, homeFragment);
    }

    private void commitShowFragment(FragmentTransaction fragmentTransaction, Fragment fragment) {
        fragmentTransaction.show(fragment);
        //加入返回栈中 不起作用
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        hideFragment(fragmentTransaction, homeFragment);
        hideFragment(fragmentTransaction, zhiBoFragment);
        hideFragment(fragmentTransaction, followFragment);
        hideFragment(fragmentTransaction, meFragment);
    }

    private void hideFragment(FragmentTransaction fragmentTransaction, Fragment fragment) {
        if (fragment != null) {
            fragmentTransaction.hide(fragment);
        }
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }

    @OnClick({R.id.rb_home, R.id.rb_zhibo, R.id.rb_guanzhu, R.id.rb_me,
            R.id.iv_back, R.id.iv_other})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.iv_other://分享
                //权限申请
                if (Build.VERSION.SDK_INT >= 23) {
                    String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
                    ActivityCompat.requestPermissions(ZTNHMainActivity.this, mPermissionList, INT_REQUEST_CODE);
                } else {
                    ShareTools share = new ShareTools(ZTNHMainActivity.this, contentView, "http://www.baidu.com", "title测试");
                    share.init();
                }

                break;
            case R.id.rb_home:
                showFragment(homeFragment);
                tvTitleName.setText("首页");
                break;
            case R.id.rb_zhibo:
                showFragment(zhiBoFragment);
                tvTitleName.setText("直播");
                break;
            case R.id.rb_guanzhu:
                showFragment(followFragment);
                tvTitleName.setText("关注");
                break;
            case R.id.rb_me:
                showFragment(meFragment);
                tvTitleName.setText("我的");
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setTitle("提示");
            b.setMessage("确认退出吗？");
            b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            b.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            b.create().show();
            return true;

        }

        return super.onKeyDown(keyCode, event);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
//           drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 保存临时数据，如fragment，NavigationView, checkbox等
     * <p>
     * 注意不要重写 activity的onsaveInstanceState方法
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //保存radioGroup 选项卡id
//        outState.putInt(RADIO_GROUP_ITEM_ID, rgBottomGroup.getCheckedRadioButtonId());
        if (rbHome.isChecked()) {
            outState.putBoolean(ITEM_HOME_BT, true);
        }
        if (rbZhibo.isChecked()) {
            outState.putBoolean(ITEM_ZHIBO_BT, true);
        }
        if (rbGuanzhu.isChecked()) {
            outState.putBoolean(ITEM_FOLLOW_BT, true);
        }
        if (rbMe.isChecked()) {
            outState.putBoolean(ITEM_ME_BT, true);
        }
        //保存fragment
        android.support.v4.app.FragmentManager fragmentManager = this.getSupportFragmentManager();
        if (homeFragment.isAdded()) {
            fragmentManager.putFragment(outState, HomeFragment.class.getSimpleName(), homeFragment);
        }
        if (zhiBoFragment.isAdded()) {
            fragmentManager.putFragment(outState, ZhiBoFragment.class.getSimpleName(), zhiBoFragment);
        }
        if (followFragment.isAdded()) {
            fragmentManager.putFragment(outState, FollowFragment.class.getSimpleName(), followFragment);
        }
        if (meFragment.isAdded()) {
            fragmentManager.putFragment(outState, MeFragment.class.getSimpleName(), meFragment);
        }
        Log.d(TAG, "onSaveInstanceState: ");

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO://拍照
                if (resultCode == RESULT_OK) {
                    try {
                        //取出显示照片 ***需做压缩处理，不然会出现内存问题***
                        Bitmap bitmaps = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
//                        ivUserPhoto.setImageBitmap(bitmap);
                        MyLog.e(TAG,"bitmaps size="+ImageUtils.getBitmapSize(bitmaps));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }



                    //取出文件后缀
                    String subIndex = imageFilePath.substring(imageFilePath.lastIndexOf("."));
                    //从file取出bitmap
                    Bitmap bitmap = ImageUtils.readBitmapFromPath(this, imageFilePath);
                    MyLog.e(TAG,"bitmap size="+ImageUtils.getBitmapSize(bitmap));
                    //判断是否有旋转角度
                    int degree = ImageUtils.getExifOrientation(imageFilePath);
                    if (degree != 0) {
                        bitmap = ImageUtils.rotaingImageView(bitmap, degree);
                        File photoFile = ImageUtils.saveBitmaptoSdCard(bitmap, this, subIndex);
//                        imageFilePath = Uri.fromFile(photoFile).getPath();
                        MyLog.e(TAG, "degree != 0 photoFile = " + photoFile.getAbsolutePath() +" bitmap="+bitmap.toString());
                        //文件权限
                        if (Build.VERSION.SDK_INT >= 24) {
                            imageFilePath = FileProvider.getUriForFile(ZTNHMainActivity.this, "com.umengshared.lwc.myumengshared.fileprovider",
                                    photoFile).getPath();
                        } else {
                            imageFilePath = Uri.fromFile(photoFile).getPath();
                        }
                        MyLog.e(TAG, "degree != 0 picFileFullName = " + imageFilePath);
                    }
                    ivUserPhoto.setImageBitmap(bitmap);

                }
                break;
            case CHOOSE_PHOTO://相册
                if (resultCode == RESULT_OK) {
                    //判断手机版本 做相应的数据处理
                    if (Build.VERSION.SDK_INT >= 19) {//4.4以上的版本
                        handleImageAfterKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }

                }
                break;
        }
    }

    /**
     * This method is used to get real path of file from from uri<br/>
     * http://stackoverflow.com/questions/11591825/how-to-get-image-path-just-captured-from-camera
     *
     * @param contentUri
     * @return String
     */
    public String getRealPathFromURI(Uri contentUri) {
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            // Do not call Cursor.close() on a cursor obtained using this method,
            // because the activity will do that for you at the appropriate time
            Cursor cursor = this.managedQuery(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            return contentUri.getPath();
        }
    }

    /**
     * 旋转图片角度
     *
     * @param angle
     * @param bitmap
     * @return
     */
    public Bitmap setRotateAngle(int angle, Bitmap bitmap) {

        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(angle);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;

    }

    /**
     * 获取图片的旋转角度
     *
     * @param filePath
     * @return
     */
    public int getRotateAngle(String filePath) {
        int rotate_angle = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(filePath);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate_angle = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate_angle = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate_angle = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate_angle;
    }

    /**
     * 4.4之后的系统处理
     *
     * @param data
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void handleImageAfterKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果document类型的uri,则通过documentid处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                //解析出数字格式的id
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是普通类型的uri，则使用普通处理方式
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的uri，直接获取图片路径
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    /**
     * 4.4之前系统处理
     *
     * @param data
     */
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    /**
     * 显示图片
     *
     * @param imagePath
     */
    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            int degree = getRotateAngle(imagePath);
            if (degree != 0) {
                //旋转照片角度，防止头像横着显示
                bitmap = setRotateAngle(degree, bitmap);
            }
            ivUserPhoto.setImageBitmap(bitmap);
        } else {
            MyToast.showToast("图片获取失败！");
        }
    }

    /**
     * 获取图片路径
     *
     * @param uri
     * @param selection
     * @return
     */
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //根据uri和selection获取图片的真实路径
        Cursor cursor = this.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

}
