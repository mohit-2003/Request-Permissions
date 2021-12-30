package com.tgm.getpermission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.tgm.getpermission.databinding.ActivityMainBinding;

import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String[] permissions = {"ALL_PERMISSIONS", Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA, Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.ANSWER_PHONE_CALLS};

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new PermissionAdapter(permissions, this, position -> {
            checkOrRequestPermission(permissions[position], position==0 ? Constant.ALL_PERMISSIONS : position);
        }));

    }

    /**
     * //  This function is called when user accept or decline the permission.
     * //  Request Code is used to check which permission called this function.
     * //  This request code is provided when user is prompt for permission.
     */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Constant.WRITE_EXTERNAL_STORAGE) {
            // If request is cancelled, the result arrays are empty.

            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted. Continue the action or workflow in your app.
                Toast.makeText(getApplicationContext(), "Permission Granted..", Toast.LENGTH_SHORT).show();

            } else {
                // Explain to the user that the feature is unavailable because
                // the features requires a permission that the user has denied.
                // At the same time, respect the user's decision. Don't link to
                // system settings in an effort to convince the user to change their decision.
                showDialogWhenPermissionDenied(Constant.WRITE_EXTERNAL_STORAGE);
                Toast.makeText(getApplicationContext(), "Permission Not Granted...", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == Constant.READ_EXTERNAL_STORAGE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Permission Granted..", Toast.LENGTH_SHORT).show();

            } else {
                showDialogWhenPermissionDenied(Constant.WRITE_EXTERNAL_STORAGE);
                Toast.makeText(getApplicationContext(), "Permission Not Granted...", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == Constant.CAMERA) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Permission Granted..", Toast.LENGTH_SHORT).show();

            } else {
                showDialogWhenPermissionDenied(Constant.WRITE_EXTERNAL_STORAGE);
                Toast.makeText(getApplicationContext(), "Permission Not Granted...", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == Constant.ACCESS_BACKGROUND_LOCATION) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Permission Granted..", Toast.LENGTH_SHORT).show();

            } else {
                showDialogWhenPermissionDenied(Constant.WRITE_EXTERNAL_STORAGE);
                Toast.makeText(getApplicationContext(), "Permission Not Granted...", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == Constant.ANSWER_PHONE_CALLS) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Permission Granted..", Toast.LENGTH_SHORT).show();

            } else {
                showDialogWhenPermissionDenied(Constant.WRITE_EXTERNAL_STORAGE);
                Toast.makeText(getApplicationContext(), "Permission Not Granted...", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == Constant.ALL_PERMISSIONS) {

            int i=0;
            if (grantResults.length > 0) {

                for (i = 0; i<grantResults.length; i++)
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getApplicationContext(), permissions[i] + " Permission Granted..", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getApplicationContext(), permissions[i] + " Permission Not Granted..", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getApplicationContext(), "No Any Permissions Granted..", Toast.LENGTH_SHORT).show();
            }
            if (i>1){
                Snackbar.make(binding.mainLyt, "अगर कोई Permission मांगे तो बिना सोचे Permission मत दिया कीजिये, धन्यवाद !",
                        BaseTransientBottomBar.LENGTH_LONG).show();
            }
        }
    }

    /**
     * // Register the permissions callback, which handles the user's response to the
     * // system permissions dialog. Save the return value, an instance of
     * // ActivityResultLauncher, as an instance variable.
     */

//    private ActivityResultLauncher<String> requestPermissionLauncher =
//            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
//                if (isGranted) {
//                    // Permission is granted. Continue the action or workflow in your app.
//                    Toast.makeText(getApplicationContext(), "Permission Granted..", Toast.LENGTH_SHORT).show();
//                } else {
//                    // Explain to the user that the feature is unavailable because the
//                    // features requires a permission that the user has denied. At the
//                    // same time, respect the user's decision. Don't link to system
//                    // settings in an effort to convince the user to change their
//                    // decision.
//                    Toast.makeText(getApplicationContext(), "Permission Not Granted...", Toast.LENGTH_SHORT).show();
//                }
//            });
    private void showDialogWhenPermissionDenied(int requestCode) {

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setCancelable(false);
        builder.setMessage("Without this permission you can't access feature.\n" +
                "Press Okay to give permission");
        builder.setTitle("About Permission");
        builder.setPositiveButton("Okay", (dialogInterface, i) -> {
            checkOrRequestPermission(permissions[requestCode], requestCode);
        });
        builder.setNegativeButton("No Thanks", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        builder.show();
    }

    private void checkOrRequestPermission(String permission, int requestCode) {

        if (requestCode == Constant.ALL_PERMISSIONS){
            ActivityCompat.requestPermissions(MainActivity.this, permissions, requestCode);
            return;
        }

        if (ContextCompat.checkSelfPermission(this,
                permission) == PackageManager.PERMISSION_GRANTED) {
            // permission granted, execute code
            Toast.makeText(this, "Permission Already Granted...", Toast.LENGTH_SHORT).show();

//        } else if (shouldShowRequestPermissionRationale())
////            showDialogToRequestPermission(Constant.WRITE_EXTERNAL_STORAGE_FEATURE)
//            // In an educational UI, explain to the user why your app requires this
//            // permission for a specific feature to behave as expected. In this UI,
//            // include a "cancel" or "no thanks" button that allows the user to
//            // continue using your app without granting the permission.
//            Log.d("mht", "onCreate: " + Constant.WRITE_EXTERNAL_STORAGE + " " + Constant.WRITE_EXTERNAL_STORAGE_FEATURE);
//            requestPermission(Constant.WRITE_EXTERNAL_STORAGE);
//
        } else {
            // permission not granted, request again
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{permissions[requestCode]}, requestCode);

            // This is the other way to request permission where you don't need to send permission code,
            // The registered ActivityResultCallback gets the result of this request.
            // It returns callback with isGranted boolean value to check permission granted or not

            //  requestPermissionLauncher.launch(permissions[0]);
        }
    }
}