package com.example.nitinsatpal.cleverbelly.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nitinsatpal.cleverbelly.BitmapWorkerTask;
import com.example.nitinsatpal.cleverbelly.HomeActivity;
import com.example.nitinsatpal.cleverbelly.R;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class MyProfile extends Fragment {
    ImageView mProfileImage;
    TextView mProfileName;
    EditText mProfileNameEdit;
    TextView mProfileGender;
    EditText mProfileGenderEdit;
    TextView mProfileAge;
    EditText mProfileAgeEdit;
    TextView mProfileWeight;
    EditText mProfileWeightEdit;
    TextView mProfileHeightFt;
    EditText mProfileHeightFtEdit;
    TextView mProfileHeightIn;
    EditText mProfileHeightInEdit;
    TextView mProfileBmi;
    Button mProfileEditSaveBtn;
    TextView mProfileChangePhoto;
    TextView mProfileMsg;

    private static final String NAME = "name";
    private static final String AGE = "age";
    private static final String GENDER = "gender";
    private static final String WEIGHT = "weight";
    private static final String HEIGHT_FEET = "height_feet";
    private static final String HEIGHT_INCH = "height_inch";
    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;
    private static final String IMAGE_URI = "image_uri";

    SharedPreferences sharedpreferences;
    boolean sharedPrefIsEmpty = false;
    float mBMI = 0;

    public MyProfile(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_ptofile, container, false);
        mProfileImage = (ImageView) rootView.findViewById(R.id.profile_photo);
        mProfileName = (TextView) rootView.findViewById(R.id.profile_name_text);
        mProfileNameEdit = (EditText) rootView.findViewById(R.id.profile_name_edit);
        mProfileGender = (TextView) rootView.findViewById(R.id.profile_gender_text);
        mProfileGenderEdit = (EditText) rootView.findViewById(R.id.profile_gender_edit);
        mProfileAge = (TextView) rootView.findViewById(R.id.profile_age_text);
        mProfileAgeEdit = (EditText) rootView.findViewById(R.id.profile_age_edit);
        mProfileWeight = (TextView) rootView.findViewById(R.id.profile_weight_text);
        mProfileWeightEdit = (EditText) rootView.findViewById(R.id.profile_weight_edit);
        mProfileHeightFt = (TextView) rootView.findViewById(R.id.profile_height_ft_text);
        mProfileHeightFtEdit = (EditText) rootView.findViewById(R.id.profile_height_ft_edit);
        mProfileHeightIn = (TextView) rootView.findViewById(R.id.profile_height_in_text);
        mProfileHeightInEdit = (EditText) rootView.findViewById(R.id.profile_height_in_edit);
        mProfileBmi = (TextView) rootView.findViewById(R.id.profile_bmi);
        mProfileEditSaveBtn = (Button) rootView.findViewById(R.id.edit_save);
        mProfileChangePhoto = (TextView) rootView.findViewById(R.id.change_photo);
        mProfileMsg = (TextView) rootView.findViewById(R.id.profile_msg);

        mProfileEditSaveBtn.setText("SAVE_EDIT");
        sharedpreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        if(sharedpreferences.getString(NAME, "Name").equals("Name"))
            sharedPrefIsEmpty = true;

        if(sharedPrefIsEmpty)
            showEditablescreen();
        else
            showSavedScreen();

        mProfileChangePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        //mProfileImage.setImageDrawable(getActivity().getApplicationContext().getResources().getDrawable(R.drawable.beverage_item1));
        return rootView;
    }

    private void showEditablescreen() {
        mProfileEditSaveBtn.setText("Save");

        mProfileNameEdit.setVisibility(View.VISIBLE);
        mProfileName.setVisibility(View.GONE);
        mProfileGenderEdit.setVisibility(View.VISIBLE);
        mProfileGender.setVisibility(View.GONE);
        mProfileAgeEdit.setVisibility(View.VISIBLE);
        mProfileAge.setVisibility(View.GONE);
        mProfileWeightEdit.setVisibility(View.VISIBLE);
        mProfileWeight.setVisibility(View.GONE);
        mProfileHeightFtEdit.setVisibility(View.VISIBLE);
        mProfileHeightFt.setVisibility(View.GONE);
        mProfileHeightInEdit.setVisibility(View.VISIBLE);
        mProfileHeightIn.setVisibility(View.GONE);
        mProfileChangePhoto.setVisibility(View.VISIBLE);
        mProfileMsg.setVisibility(View.INVISIBLE);

        if(!sharedPrefIsEmpty) {
            mProfileNameEdit.setText(sharedpreferences.getString(NAME, "Name"));
            mProfileGenderEdit.setText(sharedpreferences.getString(GENDER, "Gender"));
            mProfileAgeEdit.setText(""+sharedpreferences.getInt(AGE, 0));
            mProfileWeightEdit.setText(""+sharedpreferences.getFloat(WEIGHT, 0));
            mProfileHeightFtEdit.setText(""+sharedpreferences.getInt(HEIGHT_FEET, 0));
            mProfileHeightInEdit.setText(""+sharedpreferences.getInt(HEIGHT_INCH, 0));
        }
        mProfileBmi.setText("BMI: ");
        mProfileEditSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(NAME, mProfileNameEdit.getText().toString());
                editor.putString(GENDER, mProfileGenderEdit.getText().toString());
                editor.putInt(AGE, Integer.parseInt(mProfileAgeEdit.getText().toString()));
                editor.putFloat(WEIGHT, Float.parseFloat(mProfileWeightEdit.getText().toString()));
                editor.putInt(HEIGHT_FEET, Integer.parseInt(mProfileHeightFtEdit.getText().toString()));
                editor.putInt(HEIGHT_INCH, Integer.parseInt(mProfileHeightInEdit.getText().toString()));
                editor.commit();
                showSavedScreen();
            }
        });
    }

    public void showSavedScreen() {
        mProfileEditSaveBtn.setText("Edit Profile");

        mProfileNameEdit.setVisibility(View.GONE);
        mProfileName.setVisibility(View.VISIBLE);
        mProfileGenderEdit.setVisibility(View.GONE);
        mProfileGender.setVisibility(View.VISIBLE);
        mProfileAgeEdit.setVisibility(View.GONE);
        mProfileAge.setVisibility(View.VISIBLE);
        mProfileWeightEdit.setVisibility(View.GONE);
        mProfileWeight.setVisibility(View.VISIBLE);
        mProfileHeightFtEdit.setVisibility(View.GONE);
        mProfileHeightFt.setVisibility(View.VISIBLE);
        mProfileHeightInEdit.setVisibility(View.GONE);
        mProfileHeightIn.setVisibility(View.VISIBLE);
        mProfileChangePhoto.setVisibility(View.GONE);
        mProfileMsg.setVisibility(View.VISIBLE);

        mProfileName.setText(sharedpreferences.getString(NAME, "Name"));
        mProfileGender.setText(sharedpreferences.getString(GENDER, "Gender"));
        mProfileAge.setText(sharedpreferences.getInt(AGE, 0) + " Years");
        mProfileWeight.setText(sharedpreferences.getFloat(WEIGHT, 0) + " Kg");
        mProfileHeightFt.setText(sharedpreferences.getInt(HEIGHT_FEET, 0) + "'");
        mProfileHeightIn.setText(sharedpreferences.getInt(HEIGHT_INCH, 0) + "'' Ft");
        mProfileBmi.setText("BMI: " + calculateBMI());
        mProfileMsg.setText(msgForBMI(mBMI));

        if(!sharedpreferences.getString(IMAGE_URI, "").equals("")) {
            setProfilePhoto(Uri.parse(sharedpreferences.getString(IMAGE_URI, "")));
        }

        mProfileEditSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditablescreen();
            }
        });
    }

    private float calculateBMI() {
        float heightInMtrs = (float) (0.0254*(float)(12*sharedpreferences.getInt(HEIGHT_FEET, 0)+sharedpreferences.getInt(HEIGHT_INCH, 0)));
        mBMI = sharedpreferences.getFloat(WEIGHT, 0) / (heightInMtrs*heightInMtrs);
        BigDecimal bmiBD = new BigDecimal(Float.toString(mBMI));
        bmiBD = bmiBD.round(new MathContext(2, RoundingMode.HALF_UP));
        return bmiBD.floatValue();
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment
                            .getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                File f = new File(Environment.getExternalStorageDirectory()
                        .toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bm;
                    BitmapFactory.Options btmapOptions = new BitmapFactory.Options();

                    bm = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            btmapOptions);

                    // bm = Bitmap.createScaledBitmap(bm, 70, 70, true);
                    mProfileImage.setImageBitmap(bm);

                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream fOut = null;
                    File file = new File(path, String.valueOf(System
                            .currentTimeMillis()) + ".jpg");
                    try {
                        fOut = new FileOutputStream(file);
                        bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                        fOut.flush();
                        fOut.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                setProfilePhoto(selectedImageUri);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(IMAGE_URI, selectedImageUri.toString());
                editor.commit();
            }
        }
    }

    public String getPath(Uri uri, Activity activity) {
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void setProfilePhoto(Uri selectedImageUri) {
        Bitmap bm;
        String tempPath = getPath(selectedImageUri, getActivity());
        BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
        bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
        mProfileImage.setImageBitmap(bm);
    }

    public String msgForBMI(float bmi) {
        String msg = "";
        if(bmi <= 15)
            msg = "You are Very Severely Under-Weight!";
        else if(bmi > 15 && bmi <= 16)
            msg = "You are Severely Under-Weight!";
        else if(bmi > 16 && bmi <= 18.5)
            msg = "You are Under-Weight!";
        else if(bmi > 18.5 && bmi <= 25)
            msg = "You are Healthy-Weight! Do Maintain.";
        else if(bmi > 25 && bmi <= 30)
            msg = "You are Over-Weight!";
        else if(bmi > 30 && bmi <= 35)
            msg = "You are Moderately Obese!";
        else if(bmi > 35 && bmi <= 40)
            msg = "You are Severely Obese!";
        else if(bmi > 40)
            msg = "You are Very Severely Obese!";
        return msg+"\n \nTrack your healthy routine diet with Clever Belly's 'Track My Diet'.";
    }
}