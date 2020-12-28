package com.example.parkeazy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.parkeazy.DatabaseTables.User;


public class RegisterFragment extends Fragment {

    EditText et_name, et_email, et_password, et_repassword;
    Button btn_signup;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);

        et_name = (EditText) v.findViewById(R.id.register_et_name);
        et_email = (EditText) v.findViewById(R.id.register_et_email);
        et_password = (EditText) v.findViewById(R.id.register_et_password);
        et_repassword = (EditText) v.findViewById(R.id.register_et_repassword);
        btn_signup = (Button) v.findViewById(R.id.register_btn_register);

        btn_signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (et_name.getText().toString().trim().matches("")) {
                    Toast.makeText(getActivity(), "You need to enter a name!", Toast.LENGTH_SHORT).show();
                } else if (et_email.getText().toString().trim().matches("")) {
                    Toast.makeText(getActivity(), "You need to enter an e-mail address!", Toast.LENGTH_SHORT).show();
                } else if (et_password.getText().toString().trim().matches("")) {
                    Toast.makeText(getActivity(), "You need to enter a password!", Toast.LENGTH_SHORT).show();
                } else if (et_repassword.getText().toString().trim().matches("")) {
                    Toast.makeText(getActivity(), "You need to confirm your password!", Toast.LENGTH_SHORT).show();
                } else {
                    if (et_password.getText().toString().trim().matches(et_repassword.getText().toString().trim())) {
                        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
                        if (databaseHelper.checkUser(et_email.getText().toString().trim())) {
                            Toast.makeText(getActivity(),
                                    "The e-mail address is already in use!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            User user;
                            try {
                                user = new User(-1, et_name.getText().toString().trim(),
                                        et_email.getText().toString().trim(), et_password.getText().toString().trim());
                                databaseHelper.addUser(user);
                                databaseHelper.close();
                                Toast.makeText(getActivity(), "Registration Successful",
                                        Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception e) {
                                Toast.makeText(getActivity(),
                                        "Something went wrong!\n Error Message:" + e,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(getActivity(), "Passwords don't match!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

}