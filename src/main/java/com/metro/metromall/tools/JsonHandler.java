package com.metro.metromall.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.metro.metromall.utils.cacheImage.AsyncImageLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guhf on 2017/11/24.
 */

public class JsonHandler {
    Context context;
    private AsyncImageLoader imageLoader;//异步组件
    //轮播图图片资源
    private List<String> banner_images_url ;
    private List<ImageView> banner_images ;
    private List<String> middle_images_url ;
    //品质图片资源
    private List<String> mai_quality_images_url ;
    private List<Bitmap> mai_quality_images;
    //限量抢图片
    private List<String> snap_up_images_url ;
    //超值优品图片
    private List<String> high_quality_images_url ;
    //精品优选图片
    private List<String> boutique_images_url ;
    //麦德龙自有品牌图片
    private List<String> own_brand_images_url ;
    private List<String> goods_name_url ;
    private List<String> goods_price_url ;
    private List<String> notices_url ;

    public JsonHandler(Context context) {
       this.context = context;
    }

    public List<String> BannerData(){
        banner_images_url = new ArrayList<String>();
        banner_images = new ArrayList<ImageView>();
        String quality_url1 = "http://pic.metromall.cn/Resource/AdFlashPic/1711201513251.jpg";
        String quality_url2 = "http://pic.metromall.cn/Resource/AdFlashPic/1711201514151.jpg";
        String quality_url3 = "http://pic.metromall.cn/Resource/AdFlashPic/1711201514421.jpg";
        String quality_url4 = "http://pic.metromall.cn/Resource/AdFlashPic/1711201515086.jpg";
        String quality_url5 = "http://pic.metromall.cn/Resource/AdFlashPic/1711160929170.jpg";
        String quality_url6 = "http://pic.metromall.cn/Resource/AdFlashPic/1707171223439.jpg";
        banner_images_url.add(quality_url1);
        banner_images_url.add(quality_url2);
        banner_images_url.add(quality_url3);
        banner_images_url.add(quality_url4);
        banner_images_url.add(quality_url5);
        banner_images_url.add(quality_url6);

        return banner_images_url;
    }
    public List<String> MiddleData(){
        middle_images_url = new ArrayList<String>();
        String quality_url1 = "http://pic.metromall.cn/Resource/FixedPic/17112016345118.jpg";
        String quality_url2 = "http://pic.metromall.cn/Resource/FixedPic/17112016351660.jpg";
        String quality_url3 = "http://pic.metromall.cn/Resource/FixedPic/17112016354112.jpg";
        middle_images_url.add(quality_url1);
        middle_images_url.add(quality_url2);
        middle_images_url.add(quality_url3);
        return middle_images_url;
    }
    public List<String> MaiQualityData(){
        mai_quality_images_url = new ArrayList<String>();
        String quality_url1 = "http://pic.metromall.cn/Resource/FixedPic/17033018072419.jpg";
        String quality_url2 = "http://pic.metromall.cn/Resource/FixedPic/17033018073188.jpg";
        String quality_url3 = "http://pic.metromall.cn/Resource/FixedPic/17033018073863.jpg";
        String quality_url4 = "http://pic.metromall.cn/Resource/FixedPic/17033018074648.jpg";
        String quality_url5 = "http://pic.metromall.cn/Resource/FixedPic/17033018075188.jpg";
        String quality_url6 = "http://pic.metromall.cn/Resource/FixedPic/17091817061727.jpg";
        mai_quality_images_url.add(quality_url1);
        mai_quality_images_url.add(quality_url2);
        mai_quality_images_url.add(quality_url3);
        mai_quality_images_url.add(quality_url4);
        mai_quality_images_url.add(quality_url5);
        mai_quality_images_url.add(quality_url6);
        return mai_quality_images_url;

    }
    public List<String> GoodsImageData(){
        snap_up_images_url = new ArrayList<String>();
        String image_url1 = "http://pic.metromall.cn/resource/ProductPic/1/154136-800-01s.jpg";
        String image_url2 = "http://pic.metromall.cn/resource/ProductPic/1/183398-800-01s.jpg";
        String image_url3 = "http://pic.metromall.cn/resource/ProductPic/1/149684-800-01s.jpg";
        String image_url4 = "http://pic.metromall.cn/resource/ProductPic/1/149682-800-01s.jpg";
        snap_up_images_url.add(image_url1);
        snap_up_images_url.add(image_url2);
        snap_up_images_url.add(image_url3);
        snap_up_images_url.add(image_url4);
        return snap_up_images_url;
    }
    public List<String> GoodsNameData(){
        goods_name_url = new ArrayList<String>();
        String name_url1 = "三全儿童鱼肉蔬菜馄饨 210G";
        String name_url2 = "大鱼市河虾仁71-90粒650G";
        String name_url3 = "埃德华兹酒庄(LUIS FELIPE EDWARDS)珍藏美乐红葡萄酒 750ML*1<不参与无理由退换货政策>";
        String name_url4 = "埃德华兹酒庄(LUIS FELIPE EDWARDS)珍藏赤霞珠红葡萄酒 750ML*1<不参与无理由退换货政策>";
        goods_name_url.add(name_url1);
        goods_name_url.add(name_url2);
        goods_name_url.add(name_url3);
        goods_name_url.add(name_url4);

        return goods_name_url;
    }
    public List<String> GoodsPriceData(){
        goods_price_url = new ArrayList<String>();
        String price_url1 = "21.8";
        String price_url2 = "135";
        String price_url3 = "108";
        String price_url4 = "108";
        goods_price_url.add(price_url1);
        goods_price_url.add(price_url2);
        goods_price_url.add(price_url3);
        goods_price_url.add(price_url4);
        return goods_price_url;
    }
    public  List<String> NoticesData(){
        notices_url = new ArrayList<String>();
        String price_url1 = "同一商品6件最低可享85折";
        String price_url2 = "麦德龙，坚守品质承诺";
        for (int i =0;i<32;i++) {
            notices_url.add(price_url1 + i);
            //notices_url.add(price_url2);
        }
        notices_url.add("");
        return notices_url;
    }
}
