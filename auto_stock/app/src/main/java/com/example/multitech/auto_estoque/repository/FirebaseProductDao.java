package com.example.multitech.auto_estoque.repository;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.multitech.auto_estoque.activitys.ViewCallback;
import com.example.multitech.auto_estoque.models.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class FirebaseProductDao {
    private List<Product> getListByUser;

    public FirebaseProductDao() {
        this.getListByUser = new ArrayList<>();
    }

    public void saveProduct(Product product){
        String idProduct = product.getId();
        DatabaseReference dbRef =
                FirebaseHelper
                        .getDbReference()
                        .child("product")
                        .child(FirebaseHelper.getUid())
                        .child(idProduct);

        dbRef.setValue(product);



    }

    public void findProductByUser(ViewCallback view, String userId){
        List<Product> mData = new ArrayList<>();
        DatabaseReference dbRef =
                FirebaseHelper
                        .getDbReference()
                        .child("product")
                        .child(userId);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mData.clear();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    // Data parsing is being done within the extending classes.
                    Product product = snap.getValue(Product.class);
                    mData.add(product);
                }
                view.showData(mData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        };

        dbRef.addValueEventListener(eventListener);

    }

    public void deleteProduct(String productId){
        DatabaseReference dbRef =
                FirebaseHelper
                        .getDbReference()
                        .child("product")
                        .child(FirebaseHelper.getUid())
                        .child(productId);

        StorageReference storageReference = FirebaseHelper.getStorageReference()
                .child("images")
                .child("products")
                .child(FirebaseHelper.getUid())
                .child(productId + ".jpeg");

        dbRef.removeValue();
        storageReference.delete();
    }

    public void saveImage(Product product, String urlImg){
        StorageReference storageReference = FirebaseHelper.getStorageReference()
                .child("images")
                .child("products")
                .child(FirebaseHelper.getUid())
                .child(product.getId() + ".jpeg");

        UploadTask uploadTask = storageReference.putFile(Uri.parse(urlImg));
        uploadTask.addOnSuccessListener(task ->
                storageReference.getDownloadUrl().addOnCompleteListener(value -> {
                    product.setUrlImage(value.getResult().toString());
                    this.saveProduct(product);
                })).addOnFailureListener(err -> Log.e("AUTOSTOCK", err.getMessage() ));
    }

}
