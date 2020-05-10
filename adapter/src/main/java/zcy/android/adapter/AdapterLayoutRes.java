package zcy.android.adapter;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ZCY
 * @date 2019-09-28
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AdapterLayoutRes {
    @LayoutRes int resId();
    @IdRes int[] onClick() default {};
}
