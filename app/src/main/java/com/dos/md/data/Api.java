package com.dos.md.data;


import com.dos.md.SF;
import com.dos.md.data.bean.HttpData;
import com.dos.md.data.bean.Repo;
import com.dos.md.data.bean.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by DOS on 026/26/3/2016.    https://api.github.
 * com
 */
public interface Api {
    @GET("users/{user}/repos")
    Observable<HttpData<List<Repo>>> getRepos(@Path(SF.USER) String user);//can also specify query parameters in the URL.filed,@Query("sort") String sort/@QueryMap Map<String, String> options

    @Headers("Cache-Control: max-age=640000")
// TODO: 2016/4/27 0027     @Headers({"Accept: application/vnd.github.v3.full+json", "User-Agent: Retrofit-Sample-App"})
    @GET("widget/list")
    Call<List<User>> getWidgetList();

    @GET("user")
    Call<User> getUser(@Header("Authorization") String authorization);

    @GET
    Call<List<User>> nextPage(@Url String url);// TODO: 2016/4/27 eg.从上次返回的header里拿下页的url

    @POST("users/new")
    Call<User> createUser(@Body Object user);// TODO: 2016/5/4 0004 json.toString()/bean

    @FormUrlEncoded
    @POST("user/edit")
    Call<User> updateUser(@Field("first_name") String first, @Field("last_name") String last);// TODO: 2016/4/27 0027 表单的方式传参 @FieldMap Map<String, String> map

    @Multipart
    @POST("user/photo")
    Call<User> upload(@Part MultipartBody.Part photo, @Part("username") RequestBody username, @Part("password") RequestBody password);//todo:文件上传
     /*File file = new File(Environment.getExternalStorageDirectory(), "icon.png");
    RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), file);
    MultipartBody.Part photo = MultipartBody.Part.createFormData("photos", "icon.png", photoRequestBody);
updateUser(photo, RequestBody.create(null, "abc"), RequestBody.create(null, "123"));*/

     /* @PartMap Map<String, RequestBody> params);RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), imgFile);
    params.put("image\"; filename=\""+imgFile.getName()+"", fileBody);*/


}