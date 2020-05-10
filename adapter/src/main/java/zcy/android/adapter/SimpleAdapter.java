package zcy.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.lang.annotation.Annotation;
import java.util.List;


/**
 * 极简化适配器
 * Created by ChengYan Zhang
 * on 2019/5/23
 */
//@AdapterLayoutRes(resId = R.layout.item_no_init)
public abstract class SimpleAdapter<T> extends android.widget.BaseAdapter {

    private List<T> dataList;
    private final int[] clickIds;

    protected OnClickViewListener<T> onItemViewClickListener;
    protected OnLongClickViewListener<T> onItemViewLongClickListener;

    public void setOnItemViewClickListener(OnClickViewListener<T> onItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener;
    }

    public void setOnItemViewLongClickListener(OnLongClickViewListener<T> onItemViewLongClickListener) {
        this.onItemViewLongClickListener = onItemViewLongClickListener;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void addDataList(List<T> dataList){
        if (null == this.dataList) setDataList(dataList);
        else {
            this.dataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    private final int layoutId;

    public SimpleAdapter() {
        AdapterLayoutRes annotation = findAnnotation(getClass(), AdapterLayoutRes.class);
        if (null == annotation){
            layoutId = R.layout.item_no_init;
            clickIds = new int[]{};
        }else {
            layoutId = annotation.resId();
            clickIds = annotation.onClick();
        }
    }

    private <T extends Annotation>T findAnnotation(Class<? extends SimpleAdapter> clazz , Class<T> cAnnotation){
        T annotation = clazz.getAnnotation(cAnnotation);
        if (null != annotation){
            return annotation;
        }
        Class<?> superclass = clazz.getSuperclass();
        if (null != superclass && SimpleAdapter.class.isAssignableFrom(superclass)){
            annotation = findAnnotation((Class<? extends SimpleAdapter>) superclass,cAnnotation);
        }
        return annotation;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getCount() {
        return null == dataList ? 0 : dataList.size();
    }

    @Override
    public T getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VH holder = getHolder(convertView,parent);
        holder.setPosition(position);
        return holder.getRootView();
    }

    private VH getHolder(View convertView, ViewGroup parent){
        if (null == convertView){
            return new VH(LayoutInflater.from(parent.getContext()).inflate(layoutId,parent,false));
        }else {
            return (VH) convertView.getTag(R.id.adapter_tag_view_key);
        }
    }

    public class VH extends ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private int position;

        public int getPosition() {
            return position;
        }

        public VH(@NonNull View view) {
            super(view);
            view.setTag(R.id.adapter_tag_view_key,this);
            if (null != clickIds){
                setOnClickListener(this,clickIds);
            }
        }

        private void setPosition(int position) {
            this.position = position;
            if (R.layout.item_no_init != layoutId)bindData(this , getItem(position));
        }

        @Override
        public void onClick(View v) {
            if (null != onItemViewClickListener)onItemViewClickListener.onClick(v,getItem(position));
        }

        @Override
        public boolean onLongClick(View v) {
            if (null != onItemViewLongClickListener){
                onItemViewLongClickListener.onLongClick(v,getItem(position));
                return true;
            }
            return false;
        }
    }

    protected abstract void bindData(VH holder , T t);

}
