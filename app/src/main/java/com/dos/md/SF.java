package com.dos.md;

import android.os.Environment;

/**
 * Created by DOS on 2015/12/9.
 */
public interface SF {
    int ZERO = 0;
    String MSG = "msg";
    String BASE_URL = "";

    String SUCCESS = "success";
    String FAIL = "fail";

    String HOST = "";
    String FIRST_IN_FLAG = "first";

    String U_MENGAppkey = "5699aaabe0f55a54dc0013b8";
    int SUCESSCONNECTED = 200;
    String GET = "GET";
    String POST = "POST";
    int fiveThousand = 5000;
    String DATA_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    int tenThousand = 10000;





    //应用缓存根目录
    String ROOT_CACHE_PATH = Environment.getExternalStorageDirectory() + "/md/";
    //缓存用户信息key
    String CACHE_USER_KEY = "user_model";

    String ROOT_DATA_CACHE_PATH = ROOT_CACHE_PATH + "data/";
String gradlebuild="import com.android.build.gradle.AppPlugin\n" +
        "        import proguard.gradle.ProGuardTask\n" +
        "\n" +
        "        apply plugin: 'com.android.application'\n" +
        "\n" +
        "        android {\n" +
        "        compileSdkVersion 23\n" +
        "        buildToolsVersion \"23.0.2\"\n" +
        "\n" +
        "        defaultConfig {\n" +
        "        applicationId \"org.chaos.demo.jar\"\n" +
        "        minSdkVersion 19\n" +
        "        targetSdkVersion 22\n" +
        "        versionCode 1\n" +
        "        versionName \"1.0\"\n" +
        "        }\n" +
        "        buildTypes {\n" +
        "        release {\n" +
        "        minifyEnabled true\n" +
        "        proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'\n" +
        "        }\n" +
        "        }\n" +
        "        }\n" +
        "\n" +
        "        dependencies {\n" +
        "        compile fileTree(dir: 'libs', include: ['*.jar'])\n" +
        "        }\n" +
        "\n" +
        "        //dependsOn 可根据实际需要增加或更改\n" +
        "        task buildJar(dependsOn: ['compileReleaseJavaWithJavac'], type: Jar) {\n" +
        "\n" +
        "        appendix = \"demo\"\n" +
        "        baseName = \"androidJar\"\n" +
        "        version = \"1.0.0\"\n" +
        "        classifier = \"release\"\n" +
        "\n" +
        "        //后缀名\n" +
        "        extension = \"jar\"\n" +
        "        //最终的 Jar 包名，如果没设置，默认为 [baseName]-[appendix]-[version]-[classifier].[extension]\n" +
        "        archiveName = \"AndroidJarDemo.jar\"\n" +
        "\n" +
        "        //需打包的资源所在的路径集\n" +
        "        def srcClassDir = [project.buildDir.absolutePath + \"/intermediates/classes/release\"];\n" +
        "        //初始化资源路径集\n" +
        "        from srcClassDir\n" +
        "\n" +
        "        //去除路径集下部分的资源\n" +
        "        // exclude \"org/chaos/demo/jar/MainActivity.class\"\n" +
        "        // exclude \"org/chaos/demo/jar/MainActivity\\$*.class\"\n" +
        "        // exclude \"org/chaos/demo/jar/BuildConfig.class\"\n" +
        "        // exclude \"org/chaos/demo/jar/BuildConfig\\$*.class\"\n" +
        "        // exclude \"**/R.class\"\n" +
        "        // exclude \"**/R\\$*.class\"\n" +
        "\n" +
        "        //只导入资源路径集下的部分资源\n" +
        "        include \"org/chaos/demo/jar/**/*.class\"\n" +
        "\n" +
        "        //注: exclude include 支持可变长参数\n" +
        "        }\n" +
        "\n" +
        "        task proguardJar(dependsOn: ['buildJar','processReleaseResources'], type: ProGuardTask) {\n" +
        "        //Android 默认的 proguard 文件\n" +
        "        configuration android.getDefaultProguardFile('proguard-android.txt')\n" +
        "        //会根据该文件对 Jar 进行混淆，注意：需要在 manifest 注册的组件也要加入该文件中\n" +
        "        configuration 'proguard-rules.pro'\n" +
        "\n" +
        "        String inJar = buildJar.archivePath.getAbsolutePath()\n" +
        "        //输入 jar\n" +
        "        injars inJar\n" +
        "        //输出 jar\n" +
        "        outjars inJar.substring(0, inJar.lastIndexOf('/')) + \"/proguard-${buildJar.archiveName}\"\n" +
        "\n" +
        "        //设置不删除未引用的资源(类，方法等)\n" +
        "        dontshrink\n" +
        "\n" +
        "        AppPlugin appPlugin = getPlugins().findPlugin(AppPlugin)\n" +
        "        if (appPlugin != null) {\n" +
        "        List String runtimeJarList\n" +
        "        if (appPlugin.getMetaClass().getMetaMethod(\"getRuntimeJarList\")) {\n" +
        "        runtimeJarList = appPlugin.getRuntimeJarList()\n" +
        "        } else if (android.getMetaClass().getMetaMethod(\"getBootClasspath\")) {\n" +
        "        runtimeJarList = android.getBootClasspath()\n" +
        "        } else {\n" +
        "        runtimeJarList = appPlugin.getBootClasspath()\n" +
        "        }\n" +
        "\n" +
        "        for (String runtimeJar : runtimeJarList) {\n" +
        "        //给 proguard 添加 runtime\n" +
        "        libraryjars(runtimeJar)\n" +
        "        }\n" +
        "        }\n" +
        "        }";

}
