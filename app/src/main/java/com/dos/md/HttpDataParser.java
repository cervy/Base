import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by  Lynch
 * <p>
 * * 默认响应体最外层为jsonObject
 * <p>
 * 若异常则没传jsonObject或array的类型参数，
 * 否则传了或只是常规字符串（含'空'）
 */

public abstract class CallbackForasynchttp<T> extends TextHttpResponseHandler {
    /**
     * 灵活匹配最外层字段
     *
     * @param fieldCode 状态码字段名
     * @param fieldData 主要数据字段名
     */
    public CallbackForasynchttp(String fieldCode, String fieldData) {
        this.FieldCode = fieldCode;
        this.FieldData = fieldData;
    }

//    private String url;

    protected void dismissProgress() {

    }

    public CallbackForasynchttp() {

    }

    @Override
    public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
        MyLogUtil.e("async请求错误可能网络原因", S.EMPTY);
        onFailur();
        dismissProgress();

    }

    protected abstract void onFailur();

    String FieldCode = "code", FieldData = "data";

    @Override
    public void onSuccess(int i, Header[] headers, String s) {
        MyLogUtil.e("asynchttp请求结果==", s);

//        if (!TextUtils.isEmpty(s)) {
        String data = null;

        JSONTokener jsonParser = new JSONTokener(s);
        JSONObject httpdata;// 最外层一般为对象
        try {
            httpdata = (JSONObject) jsonParser.nextValue();
            int code = httpdata.getInt(this.FieldCode);
            if (code != S.ZERO) {
                MyLogUtil.e("async请求'失败' : " + httpdata.toString());// TODO: 2017/5/27 下一步依赖上一步的网络数据时，比如由于上步网络出错，所以有必要复写

                switch (code) {
                    case S.MINUSix:
                        onMinusSix();
                        break;
                    case S.MINUSseven:
                        onMinusSeven();
                        break;
                    case S.MINUSeight:
                        onMinusEight();

                        break;
                    case S.MINUSfour:
                        onMinusFour();
                        break;
                    case S.MINUSthree:
                        onMinusThree();
                        break;
                    case S.MINUSNine:
                        onMinusNine();
                        break;
                    case S.MINUSEleven:
                        onMinusEleven();
                        break;
                    case S.MINUSTen:
                        onMinusTen();
                        break;
                    case S.MINUSTwelve:
                        onMinusTwelve();
                        break;
                    case S.MINUSThirteen:
                        onMinusThirteen();
                        break;

                    case S.MINUSTwo: //{"code":-2,"msg":"没检测到上载的文件，请确认后上载!","data":null}
                        onMinusTwo();
                        break;
                    default:
                        onElse(code);//   综合

                        break;
                }
                dismissProgress();
                return;
            }

            data = httpdata.getString(FieldData);

        } catch (//JSON
                Exception e) {
            onSucceedString(s);//可用于 顶层 其它结构（非jsonObject）拓展    ResultString
//            onFailur();
            dismissProgress();
            MyLogUtil.e(e);
            return;
        }

        if (TextUtils.equals(data, S.nulldata) || TextUtils.equals(data, "[]") || TextUtils.equals(data, "null") || TextUtils.isEmpty(data)) {//   返回null时  定义字符串返回类型

            MyLogUtil.e("async请求成功但data为空哇：" + data);
            onNullData();


        } else {
            MyLogUtil.e("async请求到的data：" + data);

            if (data.startsWith("{")) {
                Log.d("setData", "setData=" + data);
                onSucceed(gson.fromJson(data,
//                        getType()
                        getTypeAuto()
                ));
            } else if (data.startsWith("[")) {
               /* if (TextUtils.equals(url, "GetTutorSelfTime&COID=")) {
                    data = data.replace("[", "").replace("]", "");
                    onSucceedString(data);
                } else*/
                onSuccesss(jsonToArrayList(data,
//                        getType()
                        getTypeAuto()

                ));


            } else {
                onSucceedString(data);//    DataString
            }


//            }


//            }

        }
        dismissProgress();

    }

    /**
     * 非典型状态
     *
     * @param code
     */
    protected void onElse(int code) {

    }


    protected void onMinusThirteen() {

    }

    protected void onMinusTwelve() {

    }

    protected void onMinusNine() {

    }

    protected void onMinusTen() {

    }

    protected void onMinusEleven() {
    }

    /**
     * 其它顶层结构（第一级非jsonobject）或’data‘下的常规字符串
     *
     * @param data
     */
    protected void onSucceedString(String data) {
    }

    protected void onMinusSix() {

    }

    protected void onMinusTwo() {

    }

    /**
     * 一般为服务器异常
     */
    protected void onMinusThree() {
    }

    protected void onMinusFour() {
    }

    protected void onMinusEight() {

    }

    protected void onMinusSeven() {

    }

    /**
     * data字段下成功返回jsonarray
     *
     * @param data
     */
    protected void onSuccesss(ArrayList<T> data) {
    }


    /**
     * 已经自动化了
     * data字段下类型
     * data或result为常规字符串可返回null
     *
     * @return
     */
    @Deprecated
    protected Class<T> getType() {
        return null;
    }
//    protected Class<T> getType() {
//        return null;
//    }

    /**
     * data字段下为空
     */
    protected abstract void onNullData();

    /*若异常则没传jsonObject或array的类型参数，
    否则传了或只是常规字符串（含'空'）
     * @return
     */
    private Class<T> getTypeAuto() {
        Type genType = this.getClass().getGenericSuperclass();//
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();//
        Class<T> c = (Class) params[S.ZERO];
        MyLogUtil.e("请求的数据(数组)的 类型", c.getName());
        return c;

    }

    /**
     * data字段下jsonobject
     *
     * @param data
     */
    protected void onSucceed(T data) {

    }


    Gson gson = new Gson();

    private ArrayList<T> jsonToArrayList(String json, Class<T> clazz) {//
        Type type = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();
        ArrayList<JsonObject> jsonObjects = gson.fromJson(json, type);

        ArrayList<T> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects) {
            arrayList.add(gson.fromJson(jsonObject, clazz));
        }
        return arrayList;
    }


}
