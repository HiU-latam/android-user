package com.hiulatam.hiu.hiu.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.hiulatam.hiu.hiu.R;
import com.hiulatam.hiu.hiu.common.Config;

/**
 * Created by:  Shiny solutions
 * Created on:  5/12/18.
 */

public class PaymentConfirmationDialogFragment extends DialogFragment {

    private static final String TAG = "PaymentConfirmationDialogFragment - ";

    public static final String EXTRAS_PAYMENT_VALUE = "PaymentValue";

    private String paymentValue;

    @Override
    public void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);
        Config.logInfo(TAG + "onCreate");

        Bundle arguments = getArguments();
        if (arguments != null){
            if (arguments.containsKey(EXTRAS_PAYMENT_VALUE)){
                paymentValue = arguments.getString(EXTRAS_PAYMENT_VALUE);
            }
        }
        Config.logInfo(TAG + "onCreate - Payment Value: " + paymentValue);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Config.logInfo(TAG + "onCreateDialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.todoDialogLight);
        builder.setMessage(String.format(getString(R.string.payment_confirmation), paymentValue));
        builder.setCancelable(false);
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        return builder.create();
    }
}
