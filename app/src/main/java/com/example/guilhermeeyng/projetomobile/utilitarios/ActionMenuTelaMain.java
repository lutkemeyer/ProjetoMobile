package com.example.guilhermeeyng.projetomobile.utilitarios;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;

import com.example.guilhermeeyng.projetomobile.R;

/*
classe responsável por gerenciar a animação, abrir e fechar o menu da tela MainActivity
 */
public class ActionMenuTelaMain {

    private Context context;
    private View conteudoLayout;
    private Interpolator interpolator;

    private ActionBar actionBar;

    private AnimatorSet animatorSet = new AnimatorSet();

    private boolean menuAberto = false;

    private AnimatedVectorDrawable animacaoAbrirMenu;
    private AnimatedVectorDrawable animacaoFecharMenu;

    public ActionMenuTelaMain(AppCompatActivity activity) {
        this.context = activity;
        this.conteudoLayout = activity.findViewById(R.id.conteudoLayoutMain);
        this.interpolator = new BounceInterpolator();
        this.actionBar = activity.getSupportActionBar();
        this.animacaoAbrirMenu = ((AnimatedVectorDrawable) context.getResources().getDrawable(R.drawable.ic_menu_anim_bolinhas_para_riscos));
        this.animacaoFecharMenu = ((AnimatedVectorDrawable) context.getResources().getDrawable(R.drawable.ic_menu_anim_riscos_para_bolinhas));
    }

    public void abrirOuFechar() {
        animarIcone();
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

    private void animarIcone(){
        if(menuAberto){
            actionBar.setHomeAsUpIndicator(animacaoFecharMenu);
            animacaoFecharMenu.stop();
            animacaoFecharMenu.start();
        }else{
            actionBar.setHomeAsUpIndicator(animacaoAbrirMenu);
            animacaoAbrirMenu.stop();
            animacaoAbrirMenu.start();
        }
    }

    public void setIconColor(int cor){
        animacaoAbrirMenu.setTint(cor);
        animacaoFecharMenu.setTint(cor);
    }
}
