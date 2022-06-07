package com.example.multitech.auto_estoque.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.multitech.auto_estoque.R;
import com.example.multitech.auto_estoque.adapters.AdapterProduct;
import com.example.multitech.auto_estoque.adapters.Onclick;
import com.example.multitech.auto_estoque.models.Product;
import com.example.multitech.auto_estoque.repository.FirebaseHelper;
import com.example.multitech.auto_estoque.repository.FirebaseProductDao;
import com.example.multitech.auto_estoque.repository.ProductDAO;
import com.tsuryo.swipeablerv.SwipeLeftRightCallback;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Onclick,ViewCallback{
    private SwipeableRecyclerView rvProduct;
    private AdapterProduct adapterRvproduct;
    private ProductDAO productDAO;
    private FirebaseProductDao fbDao;
    private List<Product> productList;
    private LinearLayoutCompat linearNotProduct;
    private TextView txtNotProduct;
    private ProgressBar progressBarNotProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar_app);
        setSupportActionBar(toolbar);

        this.setComponents();
        this.configRvProduct();
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.progressBarNotProduct.setVisibility(View.VISIBLE);
        this.linearNotProduct.setVisibility(View.VISIBLE);
        this.getProducts();
    }

    @Override // Super classe de criação de toolbar
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override // Pegando evento de clique em item da toolbar
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idMenu = item.getItemId();
        if(idMenu == R.id.menu_about){
            Toast.makeText(this, "Menu Sobre", Toast.LENGTH_SHORT).show();
        }else if(idMenu == R.id.menu_add){
            startActivity(new Intent(this, AddProductActivity.class));
        }else if(idMenu ==  R.id.menu_close){
            signOutUser();
        } else{
            Toast.makeText(this, "Não foi possivel encontrar essa opção", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override // um methodo da interface que captura eventos de click de cada item da recycler view
    public void onClickListener(Product product) {
        Intent intent = new Intent(this, AddProductActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);
    }

    private void setComponents() {
        this.rvProduct = findViewById(R.id.rv_products);
        this.linearNotProduct = findViewById(R.id.not_product);
        this.txtNotProduct = findViewById(R.id.txt_not_product);
        this.progressBarNotProduct = findViewById(R.id.progress_bar_not_product);

        this.productDAO = new ProductDAO(this);
        this.fbDao = new FirebaseProductDao();
        this.productList = new ArrayList<>();
//        this.productList = this.productDAO.getAll();
    }

    private void configRvProduct(){
        rvProduct.setLayoutManager(new LinearLayoutManager(this)); // seta orientaçao da recycler view
        rvProduct.setHasFixedSize(true); // performance de recycler view

        // Configurar o adapter para a recycler view;
        this.adapterRvproduct = new AdapterProduct(this.productList, this);
        rvProduct.setAdapter(adapterRvproduct);

        this.rvProduct.setListener(new SwipeLeftRightCallback.Listener() {

            @Override
            public void onSwipedLeft(int position) {
                Product product = productList.get(position);
                Intent intent = new Intent(getBaseContext(), AddProductActivity.class);
                intent.putExtra("product", product);
                startActivity(intent);
                adapterRvproduct.notifyItemChanged(position);

            }

            @Override
            public void onSwipedRight(int position) {
                Product product = productList.get(position);
                productList.remove(position);
                fbDao.deleteProduct(product.getId());
                adapterRvproduct.notifyItemRemoved(position);

                verifyListIsNotEmpty();
            }
        });
    }

    private void verifyListIsNotEmpty(){
        if(this.productList.size() <= 0){
            this.txtNotProduct.setText("Nenhum Produto Cadastrado");
            this.linearNotProduct.setVisibility(View.VISIBLE);
        }else{
            this.linearNotProduct.setVisibility(View.GONE);
        }
        this.progressBarNotProduct.setVisibility(View.GONE);

    }

    private void signOutUser() {
        Toast.makeText(this, "Saindo....", Toast.LENGTH_SHORT).show();
        FirebaseHelper.getAuth().signOut();
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void getProducts(){
        String userId = FirebaseHelper.getUid();
        this.fbDao.findProductByUser(this,userId);
    }

    @Override
    public void showData(List<Product> list) {
        this.productList.clear();
        for(Product pro: list){
            this.productList.add(pro);
        };

        verifyListIsNotEmpty();
        Collections.reverse(productList);
        adapterRvproduct.notifyDataSetChanged();

    }
}