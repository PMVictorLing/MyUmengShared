package com.umengshared.lwc.myumengshared.activity.LruCachePractice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.umengshared.lwc.myumengshared.R;
import com.umengshared.lwc.myumengshared.Tools.MyLog;
import com.umengshared.lwc.myumengshared.base.BaseActivity;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description: GridViewLruCacheActivity 图片缓存练习
 * @Author: lingwancai
 * @Time: 2018/8/27 8:43
 */

public class GridViewLruCacheActivity extends BaseActivity {

    private static final String TAG = "GridViewLruCacheActivity";
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_other)
    ImageView ivOther;
    @BindView(R.id.gv_gridview)
    GridView gvGridview;
    private ImageStringAdapter mImageStringAdapter;

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_grid_view_lru_cache;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        tvTitleName.setText("lrucache practices");

        mImageStringAdapter = new ImageStringAdapter(this, 0, 0, mDataList, gvGridview);
        gvGridview.setAdapter(mImageStringAdapter);

    }

    String[] mDataList = new String[]{
            "http://b.hiphotos.baidu.com/zhidao/pic/item/a6efce1b9d16fdfafee0cfb5b68f8c5495ee7bd8.jpg",
            "http://pic47.nipic.com/20140830/7487939_180041822000_2.jpg",
            "http://pic41.nipic.com/20140518/4135003_102912523000_2.jpg",
            "http://img2.imgtn.bdimg.com/it/u=1133260524,1171054226&fm=21&gp=0.jpg",
            "http://h.hiphotos.baidu.com/image/pic/item/3b87e950352ac65c0f1f6e9efff2b21192138ac0.jpg",
            "http://pic42.nipic.com/20140618/9448607_210533564001_2.jpg",
            "http://pic10.nipic.com/20101027/3578782_201643041706_2.jpg",
            "http://picview01.baomihua.com/photos/20120805/m_14_634797817549375000_37810757.jpg",
            "http://img2.3lian.com/2014/c7/51/d/26.jpg",
            "http://img3.3lian.com/2013/c1/34/d/93.jpg",
            "http://b.zol-img.com.cn/desk/bizhi/image/3/960x600/1375841395686.jpg",
            "http://picview01.baomihua.com/photos/20120917/m_14_634834710114218750_41852580.jpg",
            "http://cdn.duitang.com/uploads/item/201311/03/20131103171224_rr2aL.jpeg",
            "http://imgrt.pconline.com.cn/images/upload/upc/tx/wallpaper/1210/17/c1/spcgroup/14468225_1350443478079_1680x1050.jpg",
            "http://pic41.nipic.com/20140518/4135003_102025858000_2.jpg",
            "http://www.1tong.com/uploads/wallpaper/landscapes/200-4-730x456.jpg",
            "http://pic.58pic.com/58pic/13/00/22/32M58PICV6U.jpg",
            "http://picview01.baomihua.com/photos/20120629/m_14_634765948339062500_11778706.jpg",
            "http://h.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=429e7b1b92ef76c6d087f32fa826d1cc/7acb0a46f21fbe09cc206a2e69600c338744ad8a.jpg",
            "http://pica.nipic.com/2007-12-21/2007122115114908_2.jpg",
            "http://cdn.duitang.com/uploads/item/201405/13/20140513212305_XcKLG.jpeg",
            "http://photo.loveyd.com/uploads/allimg/080618/1110324.jpg",
            "http://img4.duitang.com/uploads/item/201404/17/20140417105820_GuEHe.thumb.700_0.jpeg",
            "http://cdn.duitang.com/uploads/item/201204/21/20120421155228_i52eX.thumb.600_0.jpeg",
            "http://img4.duitang.com/uploads/item/201404/17/20140417105856_LTayu.thumb.700_0.jpeg",
            "http://img04.tooopen.com/images/20130723/tooopen_20530699.jpg",
            "http://www.qjis.com/uploads/allimg/120612/1131352Y2-16.jpg",
            "http://pic.dbw.cn/0/01/33/59/1335968_847719.jpg",
            "http://a.hiphotos.baidu.com/image/pic/item/a8773912b31bb051a862339c337adab44bede0c4.jpg",
            "http://h.hiphotos.baidu.com/image/pic/item/f11f3a292df5e0feeea8a30f5e6034a85edf720f.jpg",
            "http://img0.pconline.com.cn/pconline/bizi/desktop/1412/ER2.jpg",
            "http://pic.58pic.com/58pic/11/25/04/91v58PIC6Xy.jpg",
            "http://img3.3lian.com/2013/c2/32/d/101.jpg",
            "http://pic25.nipic.com/20121210/7447430_172514301000_2.jpg",
            "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
            "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
            "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
            "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
            "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
            "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
            "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
            "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
            "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
            "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
            "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
            "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
            "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
            "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
            "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
            "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
            "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
            "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
            "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
            "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
            "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
            "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
            "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
            "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
            "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg", "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
            "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
            "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
            "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
            "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
            "http://b.hiphotos.baidu.com/zhidao/pic/item/a6efce1b9d16fdfafee0cfb5b68f8c5495ee7bd8.jpg",
            "http://www.renyugang.cn/emlog/content/plugins/kl_album/upload/201004/852706aad6df6cd839f1211c358f2812201004120651068641.jpg",
            "http://b.hiphotos.baidu.com/zhidao/pic/item/a6efce1b9d16fdfafee0cfb5b68f8c5495ee7bd8.jpg",
            "http://b.hiphotos.baidu.com/zhidao/pic/item/a6efce1b9d16fdfafee0cfb5b68f8c5495ee7bd8.jpg",
            "http://b.hiphotos.baidu.com/zhidao/pic/item/a6efce1b9d16fdfafee0cfb5b68f8c5495ee7bd8.jpg",
            "http://b.hiphotos.baidu.com/zhidao/pic/item/a6efce1b9d16fdfafee0cfb5b68f8c5495ee7bd8.jpg",
            "http://b.hiphotos.baidu.com/zhidao/pic/item/a6efce1b9d16fdfafee0cfb5b68f8c5495ee7bd8.jpg"
    };

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }


    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }

    /**
     * 照片墙适配器
     */
    class ImageStringAdapter extends ArrayAdapter<String> implements AbsListView.OnScrollListener {

        private LruCache<String, Bitmap> mLruCache;
        /**
         * 工作线程集合
         */
        private HashSet<BitmapWorkerTask> mSet;


        private GridView mGridView;
        private int mFirstVisibleItem = 0;
        private int mVisibleItemCount = 0;
        private boolean isFirstLoad = true;

        public ImageStringAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
        }

        public ImageStringAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull String[] objects, GridView gridView) {
            super(context, resource, textViewResourceId, objects);
            mGridView = gridView;
            mSet = new HashSet<BitmapWorkerTask>();
            //获取应用最多可用内存
            int maxMemory = (int) Runtime.getRuntime().maxMemory();
            MyLog.e(TAG, "maxmemory=" + maxMemory);
            //设置可用缓存为最多内存的1/8
            int lruMemory = maxMemory / 8;
            mLruCache = new LruCache<String, Bitmap>(lruMemory) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getByteCount();
                }
            };
            //设置滚动监听
            mGridView.setOnScrollListener(this);

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            String url = getItem(position);
            View mView = null;
            if (convertView == null) {
                mView = LayoutInflater.from(getContext()).inflate(R.layout.image_item_list, null);
            } else {
                mView = convertView;
            }
            ImageView ivPhoto = (ImageView) mView.findViewById(R.id.iv_photo);
            //给imagevie设置tag
            ivPhoto.setTag(url);
            setImageView(url, ivPhoto);
            return mView;
        }

        /**
         * 显示imageview 从缓存中取出来bitmap
         *
         * @param url
         * @param ivPhoto
         */
        private void setImageView(String url, ImageView ivPhoto) {
            Bitmap bitmap = getBitmapToLruCache(url);
            if (bitmap != null) {
                ivPhoto.setImageBitmap(bitmap);
            } else {
                ivPhoto.setImageResource(R.drawable.sina_web_default);
            }
        }

        /**
         * 从缓存中取出bitmap
         *
         * @param url
         */
        private Bitmap getBitmapToLruCache(String url) {
            return mLruCache.get(url);
        }

        /**
         * @Description: ImageStringAdapter 将一张图片存入lruCache
         * @Author: lingwancai
         * @Time: 2018/8/27 9:26
         */
        private void addBitmapToRruCache(String string, Bitmap bitmap) {
            if (getBitmapToLruCache(string) == null) {
                mLruCache.put(string, bitmap);
            }
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            //当gridview静止时才去下载图片，否则取消
            if (scrollState == SCROLL_STATE_IDLE) {
                LoadImageBitmap(mFirstVisibleItem, mVisibleItemCount);
            } else {
                cancelAllTask();
            }
        }

        /**
         * 取消所有正在下载的线程
         */
        private void cancelAllTask() {
            if (mSet != null) {
                for (BitmapWorkerTask task : mSet) {
                    task.cancel(false);
                }
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            mFirstVisibleItem = firstVisibleItem;
            mVisibleItemCount = visibleItemCount;

            //首次进入开启下载任务
            if (isFirstLoad && visibleItemCount > 0) {
                LoadImageBitmap(mFirstVisibleItem, mVisibleItemCount);
                isFirstLoad = false;
            }

        }

        /**
         * 加载bitmap对象，
         *
         * @param mFirstVisibleItem
         * @param mVisibleItemCount
         */
        private void LoadImageBitmap(int mFirstVisibleItem, int mVisibleItemCount) {
            for (int i = mFirstVisibleItem; i < mFirstVisibleItem + mVisibleItemCount; i++) {
                //取出url
                String imageUrl = mDataList[i];
                Bitmap bitmap = getBitmapToLruCache(imageUrl);
                if (bitmap != null) {
                    ImageView imageView = (ImageView) mGridView.findViewWithTag(imageUrl);
                    imageView.setImageBitmap(bitmap);
                } else {
                    //bitmap 为空开启下载线程
                    BitmapWorkerTask task = new BitmapWorkerTask();
                    mSet.add(task);
                    task.execute(imageUrl);
                }

            }
        }

        /**
         * @Description: ImageStringAdapter 异步下载图片的任务
         * @Author: lingwancai
         * @Time: 2018/8/27 9:11
         */
        class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {

            private String imageUrl;

            @Override
            protected Bitmap doInBackground(String... strings) {
                //图片下载地址
                imageUrl = strings[0];
                //在后台开启下载任务
                Bitmap bitmap = downLoadImage(imageUrl);
                if (bitmap != null) {
                    addBitmapToRruCache(strings[0], bitmap);
                }
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);

                //显示下载好的图片,根据tag找到相应的imageview
                ImageView imageView = (ImageView) mGridView.findViewWithTag(imageUrl);
                if (imageView != null && bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }

                //下载完成后取消线程
                mSet.remove(this);

            }

            /**
             * @Description: BitmapWorkerTask 建立http请求
             * @Author: lingwancai
             * @Time: 2018/8/27 9:14
             */
            private Bitmap downLoadImage(String imageUrl) {
                Bitmap bitmap = null;
                HttpURLConnection con = null;
                try {
                    URL url = new URL(imageUrl);
                    con = (HttpURLConnection) url.openConnection();
                    con.setConnectTimeout(5 * 1000);
                    con.setReadTimeout(10 * 1000);
                    bitmap = BitmapFactory.decodeStream(con.getInputStream());
                    return bitmap;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (con != null) {
                        con.disconnect();
                    }
                }
                return bitmap;

            }
        }
    }
}
