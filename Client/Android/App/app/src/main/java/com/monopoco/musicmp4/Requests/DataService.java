package com.monopoco.musicmp4.Requests;

import com.monopoco.musicmp4.Models.LoadProductIntoBin;
import com.monopoco.musicmp4.Models.LoginModel;
import com.monopoco.musicmp4.Models.LoginResponse;
import com.monopoco.musicmp4.Models.NewPlaylistModel;
import com.monopoco.musicmp4.Models.PageResponse;
import com.monopoco.musicmp4.Models.PickProductFromBin;
import com.monopoco.musicmp4.Models.PlayListAddSongModel;
import com.monopoco.musicmp4.Models.PlayListModel;
import com.monopoco.musicmp4.Models.PurchaseOrderDetail;
import com.monopoco.musicmp4.Models.PurchaseOrderModel;
import com.monopoco.musicmp4.Models.RegisterModel;
import com.monopoco.musicmp4.Models.ResponseCommon;
import com.monopoco.musicmp4.Models.SearchModel;
import com.monopoco.musicmp4.Models.SongModel;
import com.monopoco.musicmp4.Models.UserModel;
import com.monopoco.musicmp4.Models.WarehouseModel;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DataService {


    @GET("/purchase_orders/employees")
    Call<ResponseCommon<PageResponse<List<PurchaseOrderModel>>>> getPOEmployee();

    @GET("/purchase_orders/{poId}")
    Call<ResponseCommon<PurchaseOrderModel>> getPoById(@Path("poId") UUID poId);
    @GET("/purchase_orders/{poId}/products")
    Call<ResponseCommon<PageResponse<List<PurchaseOrderDetail>>>> getProductInPo(@Path("poId") UUID poId);

    @POST("/inventories/products/pick")
    Call<ResponseCommon<String>> pickProductFromBin(@Body PickProductFromBin body);

    @GET("/warehouses/{warehouseId}")
    Call<ResponseCommon<WarehouseModel>> getWarehouseInfo(@Path("warehouseId") UUID warehouseId);


    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("auth/login")
    Call<ResponseCommon<LoginResponse>> Login(@Body LoginModel body);


    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("inventories/products/loads")
    Call<ResponseCommon<String>> loadProductIntoBin(@Body LoadProductIntoBin body);


    @GET("User/get_new_password")
    Call<Object> GetNewPassword(@Query("email") String email);

}
