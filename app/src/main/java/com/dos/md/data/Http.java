package com.dos.md.data;


import android.util.Log;

import com.dos.md.SF;
import com.dos.md.data.bean.HttpData;
import com.dos.md.data.bean.Repo;
import com.dos.md.data.bean.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by DOS on 026/26/3/2016.
 */
public class Http {

    private Api githubApi;

    private Http() {
        githubApi = new Retrofit.Builder()
                //.client(client).delay()单例化
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(SF.BASE_URL)
                .build().create(Api.class);




        /*

OkHttpClient client = new OkHttpClient.Builder()
        .addInterceptor(logInterceptor)
        .retryOnConnectionFailure(true)
        .connectTimeout(15, TimeUnit.SECONDS)
        .addNetworkInterceptor(mTokenInterceptor)
        .build();

HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor mTokenInterceptor = new Interceptor() {//log，统一的header.token等
    @Override public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        if (Your.sToken == null || alreadyHasAuthorizationHeader(originalRequest)) {
            return chain.proceed(originalRequest);
        }
        Request authorised = originalRequest.newBuilder()
            .header("Authorization", Your.sToken)
            .build();
        return chain.proceed(authorised);
    }
};*/
    } //在访问Http时创建单例

    private static class Holder {
        private static final Http INSTANCE = new Http();
    }    //获取单例

    public static Http getHttp() {
        return Holder.INSTANCE;
    }

    /*Subscriber一旦调用了unsubscribe方法之后，就没有用了。且当事件传递到onError或者onCompleted之后，也会自动的解绑。这样出现的一个问题就是每次发送请求都要创建新的Subscriber对象。Observer本身是一个接口，他的特性是不管你怎么用，没有解绑的方法。所以就达到了复用的效果. 如果你用的是Observer，在调用Observable对象的subscribe方法的时候，会自动的将Observer对象转换成Subscriber对象*/

    public void getRepos(Subscriber<List<Repo>> subscriber, String... param) {
        //参数检测及封装
        githubApi.getRepos(param[0]).map(new HttpResultFunc<List<Repo>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    private class HttpResultFunc<T> implements Func1<HttpData<T>, T> {// TODO: 2016/4/27 0027 //预处理非主要data，剥离出主data 

        @Override
        public T call(HttpData<T> httpResult) {
            if (httpResult.resultCode != SF.ZERO) {
                Log.e("netErroCode", String.valueOf(httpResult.resultCode));
                return null;
            }
            return httpResult.data;
        }
    }

    public void updateUser() {
        Call call = githubApi.getWidgetList();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                response.headers().get("Link");
                response.isSuccessful();
                response.errorBody();
                response.raw();
                handleResponseMessage(response.body());// TODO: 2016/4/27 0027 封装过滤出data主体，同RxAndroid. Obsevable.map()
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                t.getMessage();
            }
        });
//        call.cancel();

    }

    private void handleResponseMessage(List<User> body) {

    }

}
