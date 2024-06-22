package com.julian.proyectofinalpsp.Dialogos;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.julian.proyectofinalpsp.R;

public class DialogoCond extends DialogFragment {
        private View view;
    private Button btn_link;
    private Button btn_twitter;
    private String url = "https://doctorstrangetridente.herokuapp.com/formUser";


        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setView(view);
            btn_link = view.findViewById(R.id.boton_link);
            acciones();


            return builder.create();
        }

        @Override
        public void onAttach(@NonNull Context context) {
            super.onAttach(context);
            view = View.inflate(context, R.layout.activity_cond, null);

        }
    public void acciones () {
        btn_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri link = Uri.parse(url);
                Intent i = new Intent(Intent.ACTION_VIEW,link);
                startActivity(i);
            }
        });



    }

}
