# MyUtils
# 使用方法
## step1:在全局build.gradle中添加
```
allprojects {
		repositories {
			...
		maven { url 'https://jitpack.io' }
	}
}
```

## step2:在工程中添加依赖
```
dependencies {
	   implementation 'com.github.sl2413:myutils:1.0.5'
}
```

## 使用px单位适配屏幕基于autoLayout方案
清单文件中指定application节点name = "com.shenl.utils.application.MyApp"
在application节点内部配置如下代码
```
<meta-data android:name="design_width" android:value="设计图宽度"></meta-data>
<meta-data android:name="design_height" android:value="设计图高度"></meta-data>
```
