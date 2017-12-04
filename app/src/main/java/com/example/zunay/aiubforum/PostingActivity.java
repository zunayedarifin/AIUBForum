package com.example.zunay.aiubforum;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zunay.aiubforum.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

import static java.text.DateFormat.getDateTimeInstance;

public class PostingActivity extends AppCompatActivity {

    private Button mSelectImage,mSubmit,mUploadFile,mCameraPic;
    private EditText mTitleView,mDescription;
    private TextView mViewPicInfo,mViewFileInfo,mPicInfoTitle,mFileInfoTitle;
    private ImageView mPreviewImage;
    private StorageReference mStorageRef;
    private static final int GALLERY_INTENT = 2;
    private static final int CAMERA_REQUEST_CODE = 1;
    private ProgressDialog mProgressDialog;
    private Uri Iuri,Furi=null;
    private DatabaseReference mDatabase;
    private static final int FILE_SELECT_CODE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mSelectImage = findViewById(R.id.selectImage);
        mPreviewImage = findViewById(R.id.previewImage);
        mSubmit = findViewById(R.id.submit);
        mTitleView = findViewById(R.id.titleView);
        mDescription = findViewById(R.id.descriptionText);
        mViewPicInfo = findViewById(R.id.pic_info);
        mViewFileInfo = findViewById(R.id.file_info);
        mProgressDialog = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Post");
        mUploadFile = findViewById(R.id.fileUploadButton);
        mPicInfoTitle = findViewById(R.id.pic_view_info);
        mFileInfoTitle = findViewById(R.id.file_view_info);
        // mCameraPic = findViewById(R.id.button2);

        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentSelectImage = new Intent(Intent.ACTION_PICK);
                intentSelectImage.setType("image/*");
                startActivityForResult(intentSelectImage,GALLERY_INTENT);

            }
        });
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                starPosting();

            }
        });

        mUploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        /*mCameraPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });*/
    }

    private void starPosting() {
        mProgressDialog.setMessage("Posting...");
        mProgressDialog.show();
        Intent intent= new Intent(PostingActivity.this,MainActivity.class);

        final DatabaseReference newPost = mDatabase.push();
        String titleValue = mTitleView.getText().toString().trim();
        String descriptionValue = mDescription.getText().toString().trim();
        if(!TextUtils.isEmpty(titleValue)&& !TextUtils.isEmpty(descriptionValue) && Iuri != null && Furi !=null){
            StorageReference Imagefilepath = mStorageRef.child("User").child("PostedImage").child(Iuri.getLastPathSegment());
            Imagefilepath.putFile(Iuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    newPost.child("Image").setValue(downloadUrl.toString());
                }
            });

            StorageReference filepath = mStorageRef.child("User").child("PostedFile").child(Furi.getLastPathSegment());
            filepath.putFile(Furi).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    newPost.child("File").setValue(downloadUrl.toString());
                }
            });
            String currentDateTimeString = getDateTimeInstance().format(new Date());
            newPost.child("Time").setValue(currentDateTimeString);
            newPost.child("Title").setValue(titleValue);
            newPost.child("Description").setValue(descriptionValue);
            startActivity(intent);
            mProgressDialog.dismiss();
            Toast.makeText(this, "Posted", Toast.LENGTH_SHORT).show();
        }
        else if(!TextUtils.isEmpty(titleValue)&& !TextUtils.isEmpty(descriptionValue) && Iuri != null && Furi ==null){
            StorageReference Imagefilepath = mStorageRef.child("User").child("PostedImage").child(Iuri.getLastPathSegment());
            Imagefilepath.putFile(Iuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    newPost.child("Image").setValue(downloadUrl.toString());
                }
            });
            String currentDateTimeString = getDateTimeInstance().format(new Date());
            newPost.child("Time").setValue(currentDateTimeString);
            newPost.child("Title").setValue(titleValue);
            newPost.child("Description").setValue(descriptionValue);
            newPost.child("File").setValue("");
            startActivity(intent);
            mProgressDialog.dismiss();
            Toast.makeText(this, "Posted", Toast.LENGTH_SHORT).show();
        }
        else if(!TextUtils.isEmpty(titleValue)&& !TextUtils.isEmpty(descriptionValue) && Iuri == null && Furi !=null){
            String currentDateTimeString = getDateTimeInstance().format(new Date());
            newPost.child("Time").setValue(currentDateTimeString);
            newPost.child("Title").setValue(titleValue);
            newPost.child("Description").setValue(descriptionValue);
            newPost.child("Image").setValue("");
            StorageReference filepath = mStorageRef.child("User").child("PostedFile").child(Furi.getLastPathSegment());
            filepath.putFile(Furi).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    newPost.child("File").setValue(downloadUrl.toString());
                }
            });
            mProgressDialog.dismiss();
            startActivity(intent);
            Toast.makeText(this, "Posted", Toast.LENGTH_SHORT).show();
        }

        else if(!TextUtils.isEmpty(titleValue)&& !TextUtils.isEmpty(descriptionValue)){
            String currentDateTimeString = getDateTimeInstance().format(new Date());
            newPost.child("Time").setValue(currentDateTimeString);
            newPost.child("Title").setValue(titleValue);
            newPost.child("Description").setValue(descriptionValue);
            newPost.child("File").setValue("");
            newPost.child("Image").setValue("");
            mProgressDialog.dismiss();
            startActivity(intent);
            Toast.makeText(this, "Posted", Toast.LENGTH_SHORT).show();
        }
        else{
            mProgressDialog.dismiss();
            Toast.makeText(this, "Title & Description Can't Be Empty", Toast.LENGTH_LONG).show();
        }
    }

    private void showFileChooser() {

        Intent intentFileSelect = new Intent(Intent.ACTION_GET_CONTENT);
        intentFileSelect.setType("*/*");
        intentFileSelect.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intentFileSelect, "Select a File to Upload"),FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(PostingActivity.this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){

            Iuri = data.getData();
            mPreviewImage.setImageURI(Iuri);
            String imageName= Iuri.getLastPathSegment().toString();
            mPicInfoTitle.setVisibility(View.VISIBLE);
            mViewPicInfo.setVisibility(View.VISIBLE);
            mViewPicInfo.setText(imageName);
        }
        if (requestCode == FILE_SELECT_CODE && resultCode == RESULT_OK) {

            Furi = data.getData();
            String fileName = Furi.getLastPathSegment().toString();
            mViewFileInfo.setVisibility(View.VISIBLE);
            mFileInfoTitle.setVisibility(View.VISIBLE);
            mViewFileInfo.setText(fileName);
        }
        /*if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {

            Iuri = data.getData();
            mPreviewImage.setImageURI(Iuri);
            String imageName= Iuri.getLastPathSegment().toString();
            mPicInfoTitle.setVisibility(View.VISIBLE);
            mViewPicInfo.setVisibility(View.VISIBLE);
            mViewPicInfo.setText(imageName);

        }*/
    }
}
