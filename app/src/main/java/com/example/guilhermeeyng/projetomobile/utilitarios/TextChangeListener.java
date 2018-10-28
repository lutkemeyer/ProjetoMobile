package com.example.guilhermeeyng.projetomobile.utilitarios;

import android.text.Editable;
import android.text.TextWatcher;

/*
classe criada para implementar apenas 1 dos 3 metodos onde for necessario (apenas para reduzir linhas de codigo em outras classes)
 */
public class TextChangeListener implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }
    @Override
    public void afterTextChanged(Editable s) {
    }
}
