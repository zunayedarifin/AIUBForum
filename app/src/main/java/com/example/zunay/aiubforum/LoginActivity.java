package com.example.zunay.aiubforum;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Toolbar toolbar;
    Button login_btn;
    EditText l_email;
    EditText l_password;
    private ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();
        getSupportActionBar().setTitle("Login");
        progressDialog=new ProgressDialog(this);
        l_email=findViewById(R.id.editText_email_login);
        l_password=findViewById(R.id.editText_password_login);
        login_btn=findViewById(R.id.login_button);
        imageView=findViewById(R.id.imageView);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= l_email.getText().toString();
                String password=l_password.getText().toString();
                if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password))
                {
                    progressDialog.setTitle("Loging In");
                    progressDialog.setMessage("Please Wait for a While");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    loginUser(email,password);
                }
            }
        });
        imageView.setImageBitmap(getBitmapFromResources(getResources(), R.mipmap.aiublogo));
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Intent intent= new Intent(LoginActivity.this,MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//Now it will not go back to login page when user submit the back button
                            startActivity(intent);
                            finish();

                        } else {
                            progressDialog.hide();
                            Toast.makeText(getApplicationContext(), "Error ! Please Check the details",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    public static Bitmap getBitmapFromResources(Resources resources, int resImage) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inSampleSize = 1;
        options.inScaled = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        return BitmapFactory.decodeResource(resources, resImage, options);
    }
}
