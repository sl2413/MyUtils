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
	   implementation 'com.github.sl2413:myutils:1.0.2'
}
```
