package com.siyann.shopcarttest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.HashSet;
import java.util.List;

import adapter.ShopcartAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import info.OnItemListener;
import widget.OrdersDetail;

/**
 * 购物车的activity
 */
public class ShopCartActivity extends AppCompatActivity {
    @Bind(R.id.shopcar_price)
    TextView shopcarPrice;
    @Bind(R.id.relative)
    RelativeLayout relative;
    @Bind(R.id.shopcar_num)
    TextView shopcarNum;
    @Bind(R.id.checkbox)
    CheckBox checkbox;

    @Bind(R.id.delete_img)
    ImageView deleteImg;

    List<OrdersDetail> mordersDetailList;

    @Bind(R.id.shopcart_recycler)
    RecyclerView shopcartRecycler;

    private Context mContext;

    //记录Menu的状态
    private boolean isShow;

    private boolean isSelectAll;

    ShopcartAdapter mshopCarAdapter;

    //记录选择的item
    private HashSet<Integer> positionSet;


    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_cart);
        mContext = this;
        ButterKnife.bind(this);
        manager = new LinearLayoutManager(this);
        initView();
        initData();
        setListener();

    }

    /**
     * 初始化recyclerview
     */
    private void initView() {
        shopcartRecycler.setLayoutManager(manager);
    }

    private void initData() {
        positionSet=new HashSet<>();
        List<OrdersDetail> ordersDetails= DataSupport.findAll(OrdersDetail.class);
        mordersDetailList=ordersDetails;
        for (int i=0;i<ordersDetails.size();i++){
            positionSet.add(i);
        }

        /**
         * 设置adapter
         */
        mshopCarAdapter=new ShopcartAdapter(mContext,mordersDetailList);
        shopcartRecycler.setAdapter(mshopCarAdapter);
    }


    /**
     * 监听的回调
     */
    private void setListener() {
        mshopCarAdapter.setOnItemListener(new OnItemListener() {
            /**
             * checkbox的选中事件
             * @param position
             */
            @Override
            public void checkBoxClick(int position) {
                //已经有Item被选择,执行选中或非选中操作
                RelativeLayout relativeLayout = (RelativeLayout) manager.findViewByPosition(position);
                TextView ed = (TextView) relativeLayout.findViewById(R.id.num_textview);
                ed.clearFocus();
                addOrRemove(position);
                /**
                 * 如果购物车为空显示默认页面
                 */
                getprices();
            }

            /**
             * iteml里面的删除
             * @param position
             */
            @Override
            public void deleteClick(int position) {
                DataSupport.deleteAll(OrdersDetail.class, "goodsId=?", "" + mordersDetailList.get(position).getGoodsId());

                Log.e("modersDetailist", "" + mordersDetailList.get(position).getGoodsId());

                Log.e("deleteClick", "deleteClick");
                mshopCarAdapter.remove(mordersDetailList.get(position));
                Log.e("deleteClick", "deleteClick1");
                mshopCarAdapter.notifyDataSetChanged();
                Log.e("deleteClick", "deleteClick2");
                positionSet.clear();
                for (int i = 0; i < mordersDetailList.size(); i++) {
                    positionSet.add(i);
                }
                /**
                 * 如果购物车为空显示默认页面
                 */
                getprices();
            }


            /**
             * 添加
             */
            @Override
            public void addClick(int position, Double num) {
                //拿到对应的item的商品单价
                Double price = mordersDetailList.get(position).getPrice();
                //拿到对应的item的商品数量
                mordersDetailList.get(position).setNum(num + 1);
                /**
                 * 数据转换成double
                 */
                Double d = new Double(String.format("%.2f", price * (num + 1)));
                mordersDetailList.get(position).setPrices(d);
                /**
                 * 将单个商品的总价插入进数据库
                 */
                mordersDetailList.get(position).save();
                getprices();
                /**
                 * 打印商品数量和商品总价
                 */
                Log.e("number", mordersDetailList.get(position).getNum() + "");
                Log.e("price", mordersDetailList.get(position).getPrices() + "");
            }


            /**
             * 移除数据
             */

            @Override
            public void removeClick(int position, Double num) {

                double pricess = mordersDetailList.get(position).getPrices();    //拿到对应的item的商品总价price*number
                num = mordersDetailList.get(position).getNum();        //拿到对应的item的商品数量
                double price = mordersDetailList.get(position).getPrice();         //拿到对应的item的商品的单价
                Log.e("pricess", pricess + "");
                Log.e("num", num + "");
                mordersDetailList.get(position).setNum(num - 1);
                /**
                 * 数据转换成double
                 */
                Double d = new Double(String.format("%.2f", price * (num - 1)));

                mordersDetailList.get(position).setPrices(d);

                mordersDetailList.get(position).save();

                getprices();
            }

        });
    }

    /**
     * 操作Item记录集合
     */
    private void addOrRemove(int position) {
        if (positionSet.contains(position)) {
            // 如果包含，则撤销选择
            Log.e("----", "remove");
            positionSet.remove(position);
            checkbox.setChecked(false);
        } else {
            // 如果不包含，则添加
            Log.e("----", "add");
            positionSet.add(position);
        }

        if (mordersDetailList == null || mordersDetailList.size() == 0) {
            isShow = false;
        }

    }

    /**
     * 全选的点击事件
     */
    @OnClick(R.id.checkbox)
    void selectAll() {
        /**
         * 全选
         */
        if (checkbox.isChecked()) {
            Log.e("selectAll", "selectAll");
            isSelectAll = true;
            for (int i = 0; i < mordersDetailList.size(); i++) {
                mordersDetailList.get(i).isSelect = true;
                positionSet.add(i);
            }
            mshopCarAdapter.notifyDataSetChanged();

        } else {
            /**
             * 取消全选
             */
            isSelectAll = false;
            for (int i = 0; i < mordersDetailList.size(); i++) {
                mordersDetailList.get(i).isSelect = false;
                positionSet.remove(i);
            }
            mshopCarAdapter.notifyDataSetChanged();
        }
        getprices();
    }


    /**
     * 删除全部
     */
    @OnClick(R.id.delete_img)
    void delete() {
        shopcartRecycler.clearFocus();
        SweetAlertDialog pdialog = new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE);
        pdialog.setTitleText("清空购物车？")
                .setContentText("清空购物车会删除所有商品信息")
                .setConfirmText("确定")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        HashSet<OrdersDetail> valueSet = new HashSet<>();
                        for (Integer integer : positionSet) {
                            valueSet.add(mshopCarAdapter.getItem(integer));
                        }
                        for (Integer position : positionSet) {
                            DataSupport.deleteAll(OrdersDetail.class, "goodsId=?", "" + mordersDetailList.get(position).getGoodsId());
                        }
                        for (OrdersDetail ordersDetail : valueSet) {
                            mshopCarAdapter.remove(ordersDetail);
                        }

                        mshopCarAdapter.notifyDataSetChanged();
                        positionSet.clear();
                        /**
                         * 如果购物车为空显示默认页面
                         */
                        if (mordersDetailList == null || mordersDetailList.size() == 0) {
                            shopcarPrice.setText("0.0");
                            relative.setVisibility(View.VISIBLE);
                        }
                        sweetAlertDialog.dismissWithAnimation();
                    }

                })
                .setCancelText("取消")
                .show();
    }




        /**
     * 初始化
     */
    @Override
    protected void onStart() {
        super.onStart();
        getprices();
    }

    /**
     * 改变总价和总数
     */
    public void getprices(){
        Double total = 0d;
        Double number = 0d;
        for (Integer p : positionSet) {
            if (mordersDetailList.get(p).getPrices() == 0) {
                total = total + mordersDetailList.get(p).getPrice();
            } else {
                total = total + mordersDetailList.get(p).getPrices();
            }
            number = number + mordersDetailList.get(p).getNum();
        }
        Log.e("getpricesnumber", number + "");
        shopcarPrice.setText(String.format("%.2f", total));
        shopcarNum.setText(String.format("%.0f",number));

        if (mordersDetailList == null || mordersDetailList.size() == 0) {
            shopcarPrice.setText("0.0");
            shopcarNum.setText("0");
            relative.setVisibility(View.VISIBLE);
        }
    }

}
