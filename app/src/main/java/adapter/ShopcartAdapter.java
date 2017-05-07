package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.siyann.shopcarttest.R;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import info.OnItemListener;
import widget.OrdersDetail;

/**
 * 购物车的adapter
 */
public class ShopcartAdapter extends RecyclerView.Adapter<ShopcartAdapter.ViewHolder> {

    private List<OrdersDetail> mordersDetails;

    private Context mContext;

    private LayoutInflater inflater;

    private OnItemListener mOnItemListener;

    private Integer focusPosition;


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView shop_imag;
        TextView shop_name;
        TextView shop_details;
        TextView shop_price;
        CheckBox checkbox;
        ImageView add;
        ImageView remove;
        TextView textview_num;
        ImageView shop_delete;
        View recy_view;

        public ViewHolder(View view) {
            super(view);
            recy_view = view;
            shop_imag = (ImageView) view.findViewById(R.id.shop_imag);
            shop_name = (TextView) view.findViewById(R.id.shop_name);
            shop_details = (TextView) view.findViewById(R.id.shop_details);
            shop_price = (TextView) view.findViewById(R.id.shop_price);
            checkbox = (CheckBox) view.findViewById(R.id.shop_checkbox);
            add = (ImageView) view.findViewById(R.id.add_img);
            remove = (ImageView) view.findViewById(R.id.remove);
            textview_num = (TextView) view.findViewById(R.id.num_textview);
            shop_delete = (ImageView) view.findViewById(R.id.shop_delete);

        }
    }

    /**
     * 移除页面显示
     *
     * @param ordersDetail
     */
    public void remove(OrdersDetail ordersDetail) {
        mordersDetails.remove(ordersDetail);
        Log.e("Adapterremove", "Adapterremove");
    }

    public OrdersDetail getItem(int pos) {
        return mordersDetails.get(pos);
    }


    /**
     * ShopCarAdapter的构造
     */
    public ShopcartAdapter(Context mContext, List<OrdersDetail> oredelist) {
        this.mContext = mContext;
        this.mordersDetails = oredelist;
        inflater = LayoutInflater.from(mContext);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopcar_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final OrdersDetail ordersDetail = mordersDetails.get(position);
        Glide.with(mContext)
                .load(ordersDetail.getGoodsUrl())
                .placeholder(R.drawable.plugin_nopicture)
                .into(holder.shop_imag);
        holder.shop_name.setText(ordersDetail.getName());
        holder.shop_details.setText(ordersDetail.getDescription());
        if (ordersDetail.getPrices() == 0) {
            holder.shop_price.setText(ordersDetail.getPrice().toString());
        } else {
            holder.shop_price.setText(ordersDetail.getPrices().toString());
        }
        holder.textview_num.setText(String.format("%.0f", ordersDetail.getNum()));

        /**
         * checkbox的点击事件
         * @return
         */
        holder.checkbox.setChecked(ordersDetail.isSelect);
        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ordersDetail.isSelect = holder.checkbox.isChecked();
                mOnItemListener.checkBoxClick(position);
            }
        });


        /**
         * 添加数据
         */
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double num = Double.parseDouble(holder.textview_num.getText().toString());

                mOnItemListener.addClick(position, num);
                holder.textview_num.setText(String.format("%.0f", mordersDetails.get(position).getNum()));
                /**
                 * 设置商品总价
                 */
                String d = String.format("%.2f", mordersDetails.get(position).getPrices());
                holder.shop_price.setText(d);
            }
        });


        /**
         * 移除数据
         */
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.textview_num.getText().toString().trim().equals("1")) {
                    Double num = Double.parseDouble(holder.textview_num.getText().toString());
                    mOnItemListener.removeClick(position, num);
                    holder.textview_num.setText(String.format("%.0f", mordersDetails.get(position).getNum()));

                    /**
                     * 设置商品总价
                     */
                    String d = String.format("%.2f", mordersDetails.get(position).getPrices());
                    holder.shop_price.setText(d);
                }
            }
        });





        /**
         * item里面的删除
         */
        holder.shop_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetAlertDialog pdialog = new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE);
                pdialog.setTitleText("确认删除？")
                        .setContentText("删除后从购物车中清除")
                        .setConfirmText("确定")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                mOnItemListener.deleteClick(position);
                                Log.e("position",position+"");
                                sweetAlertDialog.dismissWithAnimation();
                            }

                        })
                        .setCancelText("取消")
                        .show();
            }
        });

    }


    public void setOnItemListener(OnItemListener onItemListener) {
        this.mOnItemListener = onItemListener;
    }


    @Override
    public int getItemCount() {
        return mordersDetails.size();
    }


}
