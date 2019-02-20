package id.co.ultrajaya.safeandro.model.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import id.co.ultrajaya.safeandro.R;
import id.co.ultrajaya.safeandro.module.contract._MainContract;

public class AlertDialogCustom {
    AlertDialog _alertDialog;

    public AlertDialogCustom(final Context icontext) { //tipe 1 save
        _alertDialog = new AlertDialog.Builder(icontext).create();
        _alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                }
        );
    }

    /*KHUSUS UNTUK ADAPTER RECYCLER VIEW*/
    public AlertDialogCustom(final Context icontext, boolean isTwoButton, final int position, final _MainContract.MainAdapterContract.MainAdapterPresenter ilistener) { //tipe 1 save
        _alertDialog = new AlertDialog.Builder(icontext).create();
        _alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        _alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "DELETE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //program listener delete untuk recycler view
                //fungsingya pasti onDeleteItemRV namanya berada di kontrak _MainContract.mainAdapter in heritance dengan presenter
                //listener ini berasal dari fragment yang memanggil, fragment memperikan inputan _Adapter = MainContract.MainAdapterContract
                //alurnya :
                // 1. fragment memberikan contract showAlertDialogWithOptions
                // 2. showAlertDialogWithOptions memanggil alert custom two option dialog dengan parameter _Adapter = MainContract.MainAdapterContract
                // 3. _Adapter = MainContract.MainAdapterContract berada di implementator/presenter inheritance dengan contract bagian presenter
                // 4. dipanggil di fragment dengan bantuan variable global impl
                // 5. di alert dialog custom ketika meilih delete dipanggillah onDeleteItemRV
                // 6. karena parameter merupakan variable impl maka akan memanggil yang berada di impl
                // 7. di impl terdapat onDeleteItemRV
                ilistener.onDeleteItemRV(position);
                dialog.dismiss();
            }
        });
    }

    /*UNTUK MAIN VIEW*/
    public AlertDialogCustom(final Context icontext, boolean isTwoButton, final _MainContract.MainView ilistener,final String itag) { //tipe 1 save
        _alertDialog = new AlertDialog.Builder(icontext).create();
        _alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        _alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "CONTINUE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ilistener.onPostAlertDialogAction(itag);
                dialog.dismiss();
            }
        });
    }

    /*UNTUK MODAL WITH LAYOUT PARAM*/
    public AlertDialogCustom(final Context icontext, View iview, final _MainContract.MainView ilistener, final String itag) { //tipe 1 save
        _alertDialog = new AlertDialog.Builder(icontext).create();
        _alertDialog.setView(iview);
        _alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        _alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "CONTINUE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ilistener.onPostAlertDialogAction(itag);
                dialog.dismiss();
            }
        });
    }

    public void showAlertDialog(String imsg, int itipe) {
        SpannableStringBuilder aStringBuilder = changeColorAlert(itipe);

        _alertDialog.setTitle(aStringBuilder);
        _alertDialog.setMessage(imsg);
        _alertDialog.setCanceledOnTouchOutside(false);
        _alertDialog.show();
    }

    public void hideAlertDialog() {
        _alertDialog.hide();
    }

    public static SpannableStringBuilder changeColorAlert(int icolor) {
        int acolor;
        String atitle;
        //1 red, 2 green, 3 blue
        switch (icolor) {
            case 1:
                atitle = "Error";
                acolor = Color.rgb(241, 3, 4);
                break;
            case 2:
                atitle = "Success";
                acolor = Color.rgb(27, 160, 225);
                break;
            default:
                atitle = "Info";
                acolor = Color.BLACK;
                break;
        }

        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(acolor);
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(atitle);
        ssBuilder.setSpan(
                foregroundColorSpan,
                0,
                atitle.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        return ssBuilder;
    }
}
