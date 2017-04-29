package com.ajou.jinwoo.lab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class ConfirmDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String date = getArguments().getString("DATE");

        int hour = getArguments().getInt("HOUR");
        int minute = getArguments().getInt("MINUTE");
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_confirm,null);
        TextView confirmTextView = (TextView) view.findViewById(R.id.confirm_text_view);

//        DateFormat df = new SimpleDateFormat("yyyy년 MM월 dd일");



        confirmTextView.setText(date +hour+"시 "+ minute+"분에 예약하시겠습니까?");

        return new AlertDialog.Builder(getActivity()).setView(view).setNegativeButton("취소",null).setPositiveButton("예약",null).create();

    }

    public static ConfirmDialog newInstance(String date,int hour,int minute){
        Bundle args = new Bundle();
        args.putString("DATE",date);
        args.putInt("HOUR",hour);
        args.putInt("MINUTE",minute);
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setArguments(args);
        return dialog;
    }
}
