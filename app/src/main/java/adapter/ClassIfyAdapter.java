package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.siyann.shopcarttest.R;
import com.siyann.shopcarttest.ShopCartActivity;

import org.litepal.crud.DataSupport;

import java.util.List;

import widget.OrdersDetail;
import widget.VGoodsId;

/**
 * 商品分类的adapter
 */
public class ClassIfyAdapter extends RecyclerView.Adapter<ClassIfyAdapter.ViewHolder> {
    private List<VGoodsId> mvGoodsIdList;
    private Context mContext;
    private LayoutInflater inflater;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cabbage_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final VGoodsId mvGoodsId=mvGoodsIdList.get(position);
        Glide.with(mContext)
                .load(mvGoodsId.getGoodsUrl())
                .placeholder(R.drawable.plugin_nopicture)
                .into(holder.imageView);

        holder.tv_name.setText(mvGoodsId.getName()); //商品名称

        holder.tv_content.setText(mvGoodsId.getDescription()); //商品描述

        holder.tv_price.setText(mvGoodsId.getPrice().toString()); //商品价格

        holder.tv_number.setText(mvGoodsId.getCount().toString()); //商品已售数量


        /**
         * 购物车的点击事件
         */
        holder.mvGoodsIding_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShopCartActivity.class);
                mContext.startActivity(intent);

                /**
                 * 点击购物车按钮将商品信息插入到数据库中
                 * 这里暂时弄成这样子的，以后要从数据库中读取出来
                 */
                OrdersDetail mordersDetail = new OrdersDetail();
                mordersDetail.setGoodsId(mvGoodsId.getGoodsId());
                mordersDetail.setName(mvGoodsId.getName());
                mordersDetail.setGoodsUrl(mvGoodsId.getGoodsUrl());
                mordersDetail.setPrice(mvGoodsId.getPrice());
                mordersDetail.setDescription(mvGoodsId.getDescription());
                mordersDetail.setNum(1d);
                mordersDetail.setIsSelect(true);

                mordersDetail.setTotal(1d);
                mordersDetail.setIsSelect(true);

                List<OrdersDetail> ordersDetailList = DataSupport.where("goodsID=?", "" + mvGoodsId.getGoodsId()).find(OrdersDetail.class);
                if (ordersDetailList.size() > 0) {

                    Double num = ordersDetailList.get(0).getNum() + 1;

                    ordersDetailList.get(0).setNum(num);

                    Double price = new Double(String.format("%.2f", num * mvGoodsId.getPrice()));

                    ordersDetailList.get(0).setPrices(price);

                    ordersDetailList.get(0).save();
                } else {
                    mordersDetail.save();
                }
                Log.e("000000000", "" + ordersDetailList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mvGoodsIdList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        View CabbageView;
        ImageView imageView;
        TextView tv_name;
        TextView tv_content;
        TextView tv_price;
        TextView tv_number;
        ImageView mvGoodsIding_img;

        public ViewHolder(View view) {
            super(view);
            CabbageView = view;
            imageView = (ImageView) view.findViewById(R.id.cabbage_img);
            tv_name = (TextView) view.findViewById(R.id.cabbage_name);
            tv_content = (TextView) view.findViewById(R.id.cabbage_coment);
            tv_price = (TextView) view.findViewById(R.id.cabbage_price);
            tv_number = (TextView) view.findViewById(R.id.cabbage_numbersold);

            mvGoodsIding_img = (ImageView) view.findViewById(R.id.shoping_cart);
        }
    }

    public ClassIfyAdapter(Context mContext, List<VGoodsId> vGoodsIdList) {
        this.mContext = mContext;
        this.mvGoodsIdList = vGoodsIdList;
        inflater = LayoutInflater.from(mContext);
    }


}
