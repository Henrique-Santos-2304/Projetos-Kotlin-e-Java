package com.example.multitech.auto_estoque.activitys;

import com.example.multitech.auto_estoque.models.Product;

import java.util.List;

public interface ViewCallback {
    void showData(List<Product> list);
}
