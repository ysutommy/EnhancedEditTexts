[![](https://jitpack.io/v/ysutommy/EnhancedEditTexts.svg)](https://jitpack.io/#ysutommy/EnhancedEditTexts)

# EnhancedEditTexts
一个自定义EditText的合集，目前只有2个：

`DemicalEditText` 可自定义小数位数的输入框 

`SpilttedEditText` 自带分隔符的输入框，默认支持以空格（" "），逗号（,），下划线（_）分隔，可自定义

# 使用
### Step1 Add it in your root build.gradle at the end of repositories:
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
### Step2 Add the dependency
	dependencies {
		compile 'com.github.ysutommy:EnhancedEditTexts:v1.0'
	}
