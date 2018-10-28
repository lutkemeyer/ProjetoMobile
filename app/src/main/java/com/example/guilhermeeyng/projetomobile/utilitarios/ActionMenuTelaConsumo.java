package com.example.guilhermeeyng.projetomobile.utilitarios;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.animation.Interpolator;

public class ActionMenuTelaConsumo {

    private View conteudoLayout;
    private Interpolator interpolator;

    private AnimatorSet animatorSet = new AnimatorSet();

    private boolean menuAberto = false;

    public ActionMenuTelaConsumo(View conteudoLayout, Interpolator interpolator) {
        this.conteudoLayout = conteudoLayout;
        this.interpolator = interpolator;
    }

    public void abrirOuFechar() {
        animatorSet.removeAllListeners();
        animatorSet.end();
        animatorSet.cancel();
        menuAberto = !menuAberto;
        //int translateY = height - 200;
        ObjectAnimator animator = ObjectAnimator.ofFloat(conteudoLayout, "translationY", menuAberto? 170 : 0);
        animator.setDuration(1000);
        if(interpolator != null){
            animator.setInterpolator(interpolator);
        }
        animatorSet.play(animator);
        animator.start();
    }

    public void fechar(){
        if(menuAberto){
            abrirOuFechar();
        }
    }
}
