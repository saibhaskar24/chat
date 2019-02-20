package com.example.bhaskar.chat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class Settings extends AppCompatActivity {

    DatabaseReference databaseReference ;
    FirebaseUser currentUser;
    CircleImageView image;
    TextView name,status;
    Button change_pic,change_status;
    ProgressDialog progressDialog;
    Toolbar toolbar;
    CropImageView cropImageView;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        name = (TextView) findViewById(R.id.name);
        status = (TextView) findViewById(R.id.status);
        change_pic = (Button) findViewById(R.id.change_pic);
        change_status = (Button) findViewById(R.id.change_status);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        image = (CircleImageView) findViewById(R.id.profile_image);
        cropImageView = (CropImageView) findViewById(R.id.cropImageView);
        setTitle("Settings");
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            assert  getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Updating : ");
        progressDialog.setMessage("please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        storageReference = FirebaseStorage.getInstance().getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String n,s,im,im_t;
                n = dataSnapshot.child("name").getValue().toString();
                s = dataSnapshot.child("status").getValue().toString();
                name.setText(n);
                status.setText(s);
                progressDialog.hide();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public  void  change_status(View view) {
        Intent intent = new Intent(this, ChangeStatus.class);
        startActivity(intent);
    }

    public  void  change_pic(View view) {


        Intent gintent = new Intent();
        gintent.setType("image/*");
        gintent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(gintent.createChooser(gintent,"Select image"), 1);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String url = dataSnapshot.child("image").getValue().toString();
                Picasso.get().load(url).into(image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Settings.this,"Image not added", Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK) {
             Uri imageUri = data.getData();

            CropImage.activity(imageUri).setAspectRatio(1,1)
                    .start(this);
            }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                final Uri resultUri = result.getUri();
                final File file = new File(resultUri.getPath());

                try {
                    Bitmap ImageBitmap = new Compressor(this)
                            .setQuality(75)
                            .compressToBitmap(file);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100,baos);
                    byte[] bytes = baos.toByteArray();

                    final StorageReference firepath = storageReference.child("profileImages").child(currentUser.getUid()+".jpg");
                    final StorageReference thumb_fire = storageReference.child("profileImages").child("thumb").child(currentUser.getUid()+".jpg");
                    UploadTask uploadTask = (UploadTask) thumb_fire.putBytes(bytes).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            thumb_fire.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    final  String turl = uri.toString();
                                    databaseReference.child("thumb_image").setValue(turl).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            firepath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    firepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            final String url =  uri.toString();
                                                            databaseReference.child("image").setValue(url).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    Toast.makeText(Settings.this,"Image added to storage",Toast.LENGTH_LONG).show();
                                                                }
                                                            });
                                                        }
                                                    });


                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }

                }
                else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }


    }

}
