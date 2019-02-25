package com.trs.aiweishi;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getTargetContext();
//
//        assertEquals("com.trs.aiweishi", appContext.getPackageName());

//        animatorTest();
//        sqliteHelperTest(appContext);
//        asyncTaska();
    }

    private void animatorTest() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        ValueAnimator va = ValueAnimator.ofFloat(0, 1f).setDuration(2000);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float currentValue = (float) animation.getAnimatedValue();
                        Log.d("TAG", "cuurent value is " + currentValue);
                    }
                });
        va.start();

        ObjectAnimator.ofFloat(new TextView(appContext),"alpha",0,3f)
                .setDuration(2000).start();
    }

    private void sqliteHelperTest(Context appContext) {
        SQLiteOpenHelper sqLiteOpenHelper = new SQLiteOpenHelper(appContext,"",null,1) {
            @Override
            public void onCreate(SQLiteDatabase db) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };
    }

    private void asyncTaska() {
        AsyncTask task = new AsyncTask() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                publishProgress(objects);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
            }

            @Override
            protected void onProgressUpdate(Object[] values) {
                super.onProgressUpdate(values);
            }
        };
        task.execute("param");
        task.cancel(true);
    }

}
