package info;

/**
 * 监听的接口回调
 */
public interface OnItemListener {

    //单选
    void checkBoxClick(int position);

    //删除
    void deleteClick(int position);

    //删除焦点
//    void deleteFocus(int focusPosition);


    //添加数量
    void addClick (int position,Double num);

    //减少数量
    void removeClick(int position,Double num);


    //改变文本框的输入值
//    void OnFocusChangeClick(int position,Double num);
}
