package com.faustino.consultaapigithub.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.faustino.consultaapigithub.R;

public class CarregaLoading {

    private Context context;

    public CarregaLoading(Context context) {
        this.context = context;
    }

    Dialog dialog;

    public void carregaLoading(/*LayoutInflater inflater*/) {

        //View dialogView = inflater.inflate(R.layout.template_dialog_carrega_loading, null);
        //dialog = new Dialog(context, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar); ESSE FICOU BOM
        dialog = new Dialog(context, R.style.Tema_loading);
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final ImageView imageView = dialog.findViewById(R.id.image);

        //Glide.with(context).load(R.drawable.progress_gif).asGif().into(imageView);

        Glide.with(context).asGif().load(R.drawable.progress_gif).into(imageView);
        dialog.show();
    }

    public void fechaDialogLoading() {
        if (dialog.isShowing()){
            dialog.dismiss();
        }
    }

}
