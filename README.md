# BadgeView
      compile 'com.allenliu.badgeview:library:1.0.6'

    BadgeFactory.create(this)
    .setTextColor(Color.White)
    .setWidthAndHeight(25,25)
    .setBadgeBackground(Color.Red)
    .setTextSize(10)
    .setBadgeGravity(Gravity.Right|Gravity.Top)
    .setBadgeCount(20)
    .setShape(BadgeView.SHAPE_CIRCLE)
    .setMargin(0,0,5,0)// change the position of badgeview,but the width of bindview or the height will be changed
    .bind(view);

 other constructer methods and you can easy to create your own shape : BadgeFactory.createDot(this).setBadgeCount(20).bind(imageView);
    BadgeFactory.createCircle(this).setBadgeCount(20).bind(imageView);
    BadgeFactory.createRectangle(this).setBadgeCount(20).bind(imageView);
    BadgeFactory.createOval(this).setBadgeCount(20).bind(imageView);
    BadgeFactory.createSquare(this).setBadgeCount(20).bind(imageView);
    BadgeFactory.createRoundRect(this).setBadgeCount(20).bind(imageView);
##unbind badgeView.unbind();



allprojects {
		repositories {
			maven { url "https://jitpack.io" }
		}
	}
	dependencies {
	        compile 'com.github.CymChad:BaseRecyclerViewAdapterHelper:v1.9.7'//#扩展库https://github.com/oubowu/PinnedSectionItemDecoration
}




一次生成单个渠道包：productFlavors {
wandoujia {}
baidu {}
c360 {}
uc {}
  
productFlavors.all { flavor ->
flavor.manifestPlaceholders = [UMENG_CHANNEL_VALUE: name]
}
  
}其中name的值对相对应各个productFlavors的选项值，这样就达到自动替换渠道值的目的了。
这样生成apk时，选择相应的Flavors来生成指定渠道的包就可以了，而且生成的apk会自动帮你加上相应渠道的后缀

一次生成所有渠道包： 在android studio底栏中有个命令行工具Terminal,打开后就CMD可以自动切换到当前项目的目录下。
有的项目下会有graldew.bat这个文件，你就可以输入这个命令：gradlew assembleRelease，不过我一般不建议大家使用这个命令，gradlew这个命令的gralde的版本无法控制，有时候会莫名其妙的下载老版本的gradle
所以我个人推荐大家使用以下的用法。
先找到gralde的根目录，在系统变量里添加两个环境变量：
变量名为：GRADLE_HOME，变量值就为gradle的根目录；
所以变量值为：C:\Users\yazhou\.gradle\wrapper\dists\gradle-2.1-all\27drb4udbjf4k88eh2ffdc0n55\gradle-2.1
还有一个在系统变量里PATH里面添加gradle的bin目录
我的就是C:\Users\yazhou\.gradle\wrapper\dists\gradle-2.1-all\27drb4udbjf4k88eh2ffdc0n55\gradle-2.1\bin
这里配置完成了，接着在Terminal中敲下 gradle assembleRelease就可以一次性生成所有的渠道包了。
所有生成的apk在项目的build\outputs\apk下。如果只是想生成单个渠道：打开Android Studio的Gradle tasks面板(右边侧边栏)，会发现模块多了很多任务双击该任务生成对应的apk，也可以用命令行单独生成，比如gradle assembleWandoujiaRelease。

