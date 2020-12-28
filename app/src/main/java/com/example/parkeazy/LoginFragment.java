package com.example.parkeazy;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginFragment extends Fragment {

    EditText et_email, et_password;
    Button btn_login;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_login, container, false);

        et_email = (EditText) v.findViewById(R.id.login_et_email);
        et_password = (EditText) v.findViewById(R.id.login_et_password);
        btn_login = (Button) v.findViewById(R.id.login_btn_login);

        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (et_email.getText().toString().trim().matches("")) {
                    Toast.makeText(getActivity(), "You need to enter an e-mail address!", Toast.LENGTH_SHORT).show();
                } else if (et_password.getText().toString().trim().matches("")) {
                    Toast.makeText(getActivity(), "You need to enter a password!", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
                    if (databaseHelper.checkUser(et_email.getText().toString().trim(), et_password.getText().toString().trim())) {
                        Intent intent = new Intent(getActivity(), CitiesActivity.class);
                        intent.putExtra("email", et_email.getText().toString().trim());
                        databaseHelper.close();
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "Incorrect E-mail/Password!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
        // Inflate the layout for this fragment
        return v;
    }

}