package com.example.guillaume.feedley;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by Martin on 2014-11-16.
 */
public class Fx {
   static int x = 0;
    public static void slide_up(Context ctx, View v) {
        if (x == 0) {
            Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_up);
            if (a != null) {
                a.reset();
                if (v != null) {
                    v.clearAnimation();
                    v.startAnimation(a);
                }
                x = 1;
            }
        }
    }

//...

    /**
     *
     * @param ctx
     * @param v
     */
    public static void slide_down(Context ctx, View v){

        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_down);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }
}
