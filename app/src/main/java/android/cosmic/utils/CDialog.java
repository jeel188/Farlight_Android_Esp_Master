package android.cosmic.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.cosmic.ui.R;

public class CDialog {

        public static void message(Context context, CharSequence message, boolean setCancel, View.OnClickListener onClickListener){
            ((Activity)context).runOnUiThread(() -> {
                View layout = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);
                ImageView image = layout.findViewById(R.id.image);
                image.setImageResource(R.drawable.icon);
                TextView text = layout.findViewById(R.id.text);
                CardView cv_ok = layout.findViewById(R.id.cv_ok);
                text.setText(message);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                AlertDialog alertDialog = builder.create();
                alertDialog.setCancelable(setCancel);
                alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                alertDialog.setView(layout);
                alertDialog.show();
                if (onClickListener == null){
                    cv_ok.setOnClickListener(view -> alertDialog.dismiss());
                } else {
                    cv_ok.setOnClickListener(onClickListener);
                    alertDialog.dismiss();
                }
            });
        }

        public static void errorMessage(Context context, CharSequence message, boolean setCancel, View.OnClickListener onClickListener){
            ((Activity)context).runOnUiThread(() -> {
                View layout = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);
                ImageView image = layout.findViewById(R.id.image);
                image.setImageResource(R.drawable.ic_baseline_error_24);
                TextView text = layout.findViewById(R.id.text);
                CardView cv_ok = layout.findViewById(R.id.cv_ok);
                text.setText(message);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                AlertDialog alertDialog = builder.create();
                alertDialog.setCancelable(setCancel);
                alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                alertDialog.setView(layout);
                alertDialog.show();
                if (onClickListener == null){
                    cv_ok.setOnClickListener(view -> alertDialog.dismiss());
                } else {
                    cv_ok.setOnClickListener(onClickListener);
                    alertDialog.dismiss();
                }
            });
        }

        public static void warningMessage(Context context, CharSequence message, boolean setCancel, View.OnClickListener onClickListener){
            ((Activity)context).runOnUiThread(() -> {
                View layout = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);
                ImageView image = layout.findViewById(R.id.image);
                image.setImageResource(R.drawable.ic_baseline_warning_24);
                TextView text = layout.findViewById(R.id.text);
                CardView cv_ok = layout.findViewById(R.id.cv_ok);
                text.setText(message);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                AlertDialog alertDialog = builder.create();
                alertDialog.setCancelable(setCancel);
                alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                alertDialog.setView(layout);
                alertDialog.show();
                if (onClickListener == null){
                    cv_ok.setOnClickListener(view -> alertDialog.dismiss());
                } else {
                    cv_ok.setOnClickListener(onClickListener);
                }
            });
        }
        public static void checkMessage(Context context, CharSequence message, boolean setCancel, View.OnClickListener onClickListener){
            ((Activity)context).runOnUiThread(() -> {
                View layout = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);
                ImageView image = layout.findViewById(R.id.image);
                image.setImageResource(R.drawable.ic_baseline_check_24);
                TextView text = layout.findViewById(R.id.text);
                CardView cv_ok = layout.findViewById(R.id.cv_ok);
                text.setText(message);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                AlertDialog alertDialog = builder.create();
                alertDialog.setCancelable(setCancel);
                alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                alertDialog.setView(layout);
                alertDialog.show();
                if (onClickListener == null){
                    cv_ok.setOnClickListener(view -> alertDialog.dismiss());
                } else {
                    cv_ok.setOnClickListener(onClickListener);
                }
            });
        }

}
