package com.davidmarian_buzatu.bookster.activity.ui.search.helper;

import android.app.ProgressDialog;
import android.content.Context;

import com.davidmarian_buzatu.bookster.R;

public class DialogShow {
    private static DialogShow mDialogShow;

    private DialogShow() {
        mDialogShow = this;
    }

    public static DialogShow getInstance() {
        if(mDialogShow == null) {
            mDialogShow = new DialogShow();
        }
        return mDialogShow;
    }

    public ProgressDialog getDisplayDialog(Context context, int stringResource, int titleResource) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle(context.getString(titleResource));
        dialog.setMessage(context.getString(stringResource));
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);

        return dialog;
    }

    public ProgressDialog getDisplayDialog(Context context, int stringResource) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage(context.getString(stringResource));
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);

        return dialog;
    }
}
