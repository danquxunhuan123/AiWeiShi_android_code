package com.trs.aiweishi.util;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Liufan on 2018/5/16.
 */

public class DisposedUtil {
    private static final DisposedUtil INSTANCE = new DisposedUtil();

    public static DisposedUtil getInstance() {
        return INSTANCE;
    }

    private CompositeDisposable compositeDisposable;

    /**
     * @param disposable 添加事件队列
     */
    public void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    /**
     * 销毁事件队列
     */
    public void dispose() {
        if (compositeDisposable != null) compositeDisposable.dispose();
    }
}
