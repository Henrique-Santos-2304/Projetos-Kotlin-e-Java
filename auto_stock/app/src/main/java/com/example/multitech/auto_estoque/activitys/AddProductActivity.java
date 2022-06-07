package com.example.multitech.auto_estoque.activitys;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.multitech.auto_estoque.R;
import com.example.multitech.auto_estoque.models.Product;
import com.example.multitech.auto_estoque.repository.FirebaseProductDao;
import com.example.multitech.auto_estoque.repository.ProductDAO;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AddProductActivity extends AppCompatActivity {
    private static final int REQUEST_GALERY = 106;

    private EditText input_product, input_stock, input_value;
    private Button btn_save_product;
    private ProductDAO productDAO;
    private FirebaseProductDao fbDao;

    private Product product;
    private ImageView img_user_signup;
    private String urlImg;
    private Bitmap img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        Toolbar toolbar = findViewById(R.id.toolbar_app);
        setSupportActionBar(toolbar);

        this.productDAO = new ProductDAO(this);
        this.fbDao = new FirebaseProductDao();

        this.setComponents();
        this.btn_save_product.setOnClickListener(view -> {
            this.saveProduct();
        });
        this.img_user_signup.setOnClickListener(v -> this.checkPermission());


        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            this.product = (Product) bundle.getSerializable("product");
            this.editproduct();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_GALERY && data != null) {
            Uri urlImageLocale = data.getData();
            this.urlImg = urlImageLocale.toString();
            if (Build.VERSION.SDK_INT < 28) {
                try {
                    this.img = MediaStore.Images.Media.getBitmap(getBaseContext().getContentResolver(), urlImageLocale);
                    this.saveProduct();
                } catch (Exception err) {
                    Log.e("AUTOSTOCK", err.getMessage());
                }
            }
            else {
                ImageDecoder.Source imgDecode = ImageDecoder.createSource(getBaseContext().getContentResolver(), urlImageLocale);
                this.saveProduct();
                try {
                    this.img = ImageDecoder.decodeBitmap(imgDecode);
                } catch (Exception err) {
                    Log.e("AUTOSTOCK", err.getMessage());
                }
            }

            this.img_user_signup.setImageBitmap(this.img);

        }
    }
    
    private void setComponents() {
        this.input_product = findViewById(R.id.input_product);
        this.input_stock = findViewById(R.id.input_stock);
        this.input_value = findViewById(R.id.input_value);
        this.btn_save_product = findViewById(R.id.btn_save_product);
        this.img_user_signup = findViewById(R.id.img_signup);

    }
    //Checagens de inputs...
    private Boolean checkInputEmpty(String txt, EditText input, String msg){
        if(txt.isEmpty()){
            input.requestFocus();
            input.setError(msg);
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }

    private Boolean checkNumberGreaterThanZero(String txt, EditText input){
        double value = Double.parseDouble(txt);
        if(value < 1){
            input.requestFocus();
            input.setError("Informe um valor maior que zero");
            return Boolean.FALSE;
        }else{
            return Boolean.TRUE;
        }
    }

    private void saveProduct(){
        String name = this.input_product.getText().toString();
        String stock = this.input_stock.getText().toString();
        String value = this.input_value.getText().toString();

        boolean valueIsEmpty = this.checkInputEmpty(value, this.input_value, "Informe o valor do produto");
        boolean stockIsEmpty = this.checkInputEmpty(stock, this.input_stock, "Informe a quantidade em estoque");
        boolean nameIsEmpty = this.checkInputEmpty(name, this.input_product, "Informe o nome do produto");

        boolean valueIsValid = !valueIsEmpty && this.checkNumberGreaterThanZero(value, this.input_value);
        boolean stockIsValid = !stockIsEmpty && this.checkNumberGreaterThanZero(stock, this.input_stock);

        if(!nameIsEmpty && valueIsValid && stockIsValid){
            if(this.product == null) this.product =  new Product();

            this.product.setName(name);
            this.product.setValue(Double.parseDouble(value));
            this.product.setStock(Integer.parseInt(stock));

            if(this.urlImg == null){
                this.img_user_signup.requestFocus();
                Toast.makeText( this, "Selecione uma imagem", Toast.LENGTH_SHORT).show();
            }else{
                this.fbDao.saveImage(this.product, this.urlImg);
                Toast.makeText(this, "Dados inseridos com sucesso", Toast.LENGTH_LONG).show();
                finish();
            }



        }

    }

    private void editproduct(){
        String name = this.product.getName();
        String value = String.valueOf(this.product.getValue());
        String stock = String.valueOf(this.product.getStock());

        if(name.isEmpty() || value.isEmpty() || stock.isEmpty()){
            String txtMessage = "Valores Invalidos, Name: " + name + ", Value: " + value +", stock: " + stock;
            Log.i("AUTOSTOCK", txtMessage);
        }
        else{
            this.input_product.setText(name);
            this.input_stock.setText(stock);
            this.input_value.setText(value);

            Picasso.get().load(this.product.getUrlImage()).into(this.img_user_signup);
        }


    }

    private void checkPermission() {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                openGallery();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
            }
        };

        this.showDialogPermission(
                permissionListener,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
        );
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, this.REQUEST_GALERY);
    }

    private void showDialogPermission(PermissionListener listener, String[] permissions) {
        TedPermission.create()
                .setPermissionListener(listener)
                .setDeniedTitle("Permissão")
                .setDeniedMessage("Por favor permita acesso a galeria para poder ver as fotos")
                .setDeniedCloseButtonText("Não")
                .setGotoSettingButtonText("Sim")
                .setPermissions(permissions)
                .check();
    }

}