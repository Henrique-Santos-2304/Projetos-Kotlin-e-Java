package com.example.multitech.auto_estoque.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multitech.auto_estoque.R;
import com.example.multitech.auto_estoque.models.Product;

import java.util.List;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ProductViewHolder> {
    private List<Product> productList;
    private Onclick onclick;

    // Construtor do Adapter recycler view...
    public AdapterProduct(List<Product> productList, Onclick onclick) {
        this.productList = productList;
        this.onclick = onclick;
    }

    @NonNull
    @Override       //Infalte o layout da recycler view
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_product,parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override       //seta os dados paracada item da lista
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        String productName = product.getName();
        String productStock = String.valueOf(product.getStock());
        String productPrice = String.valueOf(product.getValue());

        holder.txt_name.setText(productName);
        holder.txt_stock.setText(productStock);
        holder.txt_price.setText("R$ " +productPrice);

        holder.itemView.setOnClickListener(view -> {
            onclick.onClickListener(product);
        });
    }

    @Override        // retorno o numero de itens na lista
    public int getItemCount() {
        return this.productList.size();
    }

    // class static da recycler view de inicialização dos componentes essa é a view da recycler view;
    static class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView txt_name, txt_stock, txt_price;
        public ProductViewHolder(@NonNull View itemView){
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_item);
            txt_stock = itemView.findViewById(R.id.txt_stock);
            txt_price = itemView.findViewById(R.id.price_item);


        }
    }
}
