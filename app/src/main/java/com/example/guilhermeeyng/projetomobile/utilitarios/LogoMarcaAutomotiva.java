package com.example.guilhermeeyng.projetomobile.utilitarios;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.guilhermeeyng.projetomobile.R;

/*
enum criado para mapear o nome da marca com a logo armazenada nos drawables
 */
public enum LogoMarcaAutomotiva {
    AUDI(R.drawable.logo_audi),
    BENTLEY(R.drawable.logo_bentley),
    BMW(R.drawable.logo_bmw),
    CHANGAN(R.drawable.logo_changan),
    CHERY(R.drawable.logo_chery),
    CHEVROLET(R.drawable.logo_chevrolet),
    CHRYSLER(R.drawable.logo_chrysler),
    CITROËN(R.drawable.logo_citroen),
    DODGE(R.drawable.logo_dodge),
    EFFA(R.drawable.logo_effa),
    FERRARI(R.drawable.logo_ferrari),
    FIAT(R.drawable.logo_fiat),
    FORD(R.drawable.logo_ford),
    GM(R.drawable.logo_gm),
    HAFEI(R.drawable.logo_hafei),
    HAIMA(R.drawable.logo_haima),
    HONDA(R.drawable.logo_honda),
    HYUNDAI(R.drawable.logo_hyundai),
    JAC(R.drawable.logo_jac),
    JAGUAR(R.drawable.logo_jaguar),
    JEEP(R.drawable.logo_jeep),
    JINBEI(R.drawable.logo_jinbei),
    KIA(R.drawable.logo_kia),
    LAMBORGHINI(R.drawable.logo_lamborghini),
    LANDROVER(R.drawable.logo_landrover),
    LEXUS(R.drawable.logo_lexus),
    MASERATI(R.drawable.logo_maserati),
    MERCEDESBENZ(R.drawable.logo_mercedesbenz),
    MINI(R.drawable.logo_mini),
    MITSUBISHI(R.drawable.logo_mitsubishi),
    NISSAN(R.drawable.logo_nissan),
    PEUGEOT(R.drawable.logo_peugeot),
    PORSCHE(R.drawable.logo_porsche),
    RELY(R.drawable.logo_rely),
    RENAULT(R.drawable.logo_renault),
    ROLLSROYCE(R.drawable.logo_rollsroyce),
    SMART(R.drawable.logo_smart),
    SSANGYONG(R.drawable.logo_ssangyong),
    SUBARU(R.drawable.logo_subaru),
    SUZUKI(R.drawable.logo_suzuki),
    TOYOTA(R.drawable.logo_toyota),
    TROLLER(R.drawable.logo_troller),
    VOLKSWAGEN(R.drawable.logo_volkswagen),
    VOLVO(R.drawable.logo_volvo);

    private int resourceLogoId;

    LogoMarcaAutomotiva(int resourceLogoId){
        this.resourceLogoId = resourceLogoId;
    }

    public Drawable getLogo(Context context){
        return context.getResources().getDrawable(resourceLogoId);
    }

    @Override
    public String toString() {
        return this.name();
    }
}
