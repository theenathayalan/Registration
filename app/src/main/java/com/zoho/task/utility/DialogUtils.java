
package com.zoho.task.utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;


public class DialogUtils {
    private AlertDialog alertDialog;
    private Context currentContext;
    private String ok = "OK";
    private Activity activity;

    /**
     * Constructor for DialogUtils.
     */
    public DialogUtils(Context context) {
        super();
        this.currentContext = context;
        this.activity = (Activity) context;
    }


    /**
     * This is the method for create an alert box with Yes and No buttons.
     *
     * @param message message to display
     * @param title   title
     * @return alert dialog
     */
    public AlertDialog createAlert(String title,String message) {
        Builder builder = new Builder(currentContext);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton(ok, this.new OkOnClickListener());
        alertDialog = builder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setCancelable(true);
        if (null != activity && !activity.isFinishing()) {
            alertDialog.show();
        }
        return alertDialog;
    }


    /**
     * Class for Dialog OK button Click.
     */
    private final class CancelOnClickListener implements
            DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dismissDialog(dialog);
        }
    }

    /**
     * Class for Dialog No button Click.
     */
    private final class OkOnClickListener implements
            DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dismissDialog(dialog);
        }
    }

    /**
     * This is the method for dismiss the dialog.
     *
     * @param dialog DialogInterface
     */
    private void dismissDialog(DialogInterface dialog) {
        if (null != dialog) {
            dialog.dismiss();
        }
    }

}
