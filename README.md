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



