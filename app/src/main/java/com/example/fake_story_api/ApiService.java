package com.example.fake_story_api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("users")
    Call<List<User>> getUsers();

    @GET("products")
    Call<List<Product>> getProducts();

    @GET("products/category/{category}")
    Call<List<Product>> getProductsByCategory(@Path("category") String category);

}
