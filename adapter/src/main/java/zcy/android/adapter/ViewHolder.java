package zcy.android.adapter;

import android.content.res.Resources;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

/**
 * Create 2019/7/1 by zcy
 * QQ:1084204954
 * WeChat:ZCYzzzz
 * Email:1084204954@qq.com
 */
public class ViewHolder {

    private View rootView;

    private SparseArray<View> viewSparseArray;

    public View getRootView() {
        return rootView;
    }

    public void clear(){
        if (null != viewSparseArray)viewSparseArray.clear();
    }

    public ViewHolder(@NonNull View view) {
        rootView = view;
        viewSparseArray = new SparseArray<>();
    }

    public void runUi(Runnable action){
        rootView.post(action);
    }

    public void setOnClickListener(View.OnClickListener listener, @IdRes int... ids){
        for (int id : ids){
            findView(id).setOnClickListener(listener);
        }
    }

    public <E extends View> E findView(@IdRes int id){
        View view = viewSparseArray.get(id,null);
        if (null == view){
            viewSparseArray.put(id,view = rootView.findViewById(id));
        }
        return (E) view;
    }

    public void setText(@IdRes int id , CharSequence text){
        ((TextView)findView(id)).setText(text);
    }

    public void setText(@IdRes int id ,@StringRes int resId){
        ((TextView)findView(id)).setText(resId);
    }

    public CharSequence getText(@IdRes int id){
        View view = findView(id);
        if (view instanceof TextView){
            return ((TextView) view).getText();
        }
        return null;
    }

    public String getTextToString(@IdRes int id){
        View view = findView(id);
        if (view instanceof TextView){
            return ((TextView) view).getText().toString();
        }
        return null;
    }

    public void setTextColorInt(@IdRes int id , @ColorInt int color){
        ((TextView)findView(id)).setTextColor(color);
    }

    public void setTextColorRes(@IdRes int id , @ColorRes int color){
        ((TextView)findView(id)).setTextColor(rootView.getResources().getColor(color));
    }

    public void loadImageResource(@IdRes int id , @DrawableRes int resId){
        ((ImageView)findView(id)).setImageResource(resId);
    }

    public String getStringByRes(@StringRes  int id){
        return getResources().getString(id);
    }

    public Resources getResources(){
        return rootView.getResources();
    }

}
