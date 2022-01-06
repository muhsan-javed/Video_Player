package com.muhsantech.videoplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;
import com.muhsantech.videoplayer.databinding.ActivityMainBinding;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    MyAdapter  obj_adapter;
    public static int REQUEST_PERMISSION = 1;
    File directory;
    boolean boolean_permission;
    public static ArrayList<File> fileArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Phone And SD card
        directory = new File("/mnt/");
        // Phone And SD card // Storage
        //directory = new File("/storage/");
        // GridLayoutManager Show Videos grid from
        GridLayoutManager manager = new GridLayoutManager(MainActivity.this,2);
        binding.listVideoRecyclerView.setLayoutManager(manager);
        permissionForVideo();

        //SearchBTN(); //-- > Search Function Filter
    }
    //-- > Search Function Filter
    /*void SearchBTN(){
        //binding.edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                obj_adapter.getFilter().filter(charSequence);
                obj_adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }*/

    // This Function User asking Permissions
    private void permissionForVideo() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(getApplicationContext(),"", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION);
            }
        }else {
            boolean_permission = true;
            getFile(directory);
            obj_adapter = new MyAdapter(getApplicationContext(),fileArrayList);
            binding.listVideoRecyclerView.setAdapter(obj_adapter);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION){

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                boolean_permission = true;
                getFile(directory);
                obj_adapter = new MyAdapter(getApplicationContext(),fileArrayList);
                binding.listVideoRecyclerView.setAdapter(obj_adapter);
            }else {
                Toast.makeText(this, "Please Allow the Permission", Toast.LENGTH_SHORT).show();
            }

        }
    }
    // THis GET Position finding Path ....
    private void getFile(File directory) {

        File[] listFile = directory.listFiles();
        if (listFile != null && listFile.length > 0){
            for (File file : listFile) {
                if (file.isDirectory()) {
                    getFile(file);
                } else {
                    boolean_permission = false;
                    if (file.getName().endsWith(".mp4")) {
                        for (int j = 0; j < fileArrayList.size(); j++) {
                            if (fileArrayList.get(j).getName().equals(file.getName())) {
                                boolean_permission = true;
                            }
                        }
                        if (boolean_permission) {
                            boolean_permission = false;
                        } else {
                            fileArrayList.add(file);
                        }
                    }
                }
            }
        }
    }
}