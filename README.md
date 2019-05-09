The animated effect was inspired by the scene of the Avengers disappearing 
after the Thanos snaps fingers in the Avengers 3.

![在这里插入图片描述](https://img-blog.csdnimg.cn/2019050917545460.gif)

Here are Demo：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190509180130737.gif)

Instead of just animating the ImageView, we animate the parent view that contains the ImageView.

This animation can be used on most View and viewGroup. 
The above example is animating the Item in `Recyclerview`.

For Chinese explanation, visit [here](https://blog.csdn.net/cufelsd/article/details/90045564)

# Usage
## Import dependency
Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

```


Step 2. Add the dependency

```gradle
	dependencies {
	        implementation 'com.github.Brooks0129:ThanosSnap:v1.0'
	}
```
## Call method

Kotlin：
```kotlin
        val disappearView = DisappearView.attach(activity)
        disappearView.execute(view, 
            duration = 4000, 
            interpolator = AccelerateInterpolator(0.5f),
            needDisappear = true)

```

`DisappearView.attach(activity)`,here should pass an activity instance。
call `disappearView.execute` indicates that the animation is started, 
and the meaning of each parameter is explained below.

 - view, Indicates the view that needs to be animated. It can be a single View or a ViewGroup. 
 - duration, Indicates the time when the animation is executed. You can leave it blank. The default is 4m.
 - interpolator, Animated interpolator, you can leave it blank. The default is `AccelerateInterpolator(0.5f)`
 - needDisappear, Indicates whether the original view should disappear when the animation is executed. You can leave it blank and the default disappears.
 
If it is a java call, you can choose the corresponding overload method.


# Limit

`DisappearView.attach(activity)`,Only one activity instance can be passed, so if the current context is not an activity, 
then the animation is temporarily unavailable. For example, in Dialog.

# Todo
## Support Dialog and other non-Activity
## Various animation directions
## Diverse styles of each element
Currently every element of the motion is a rectangle, and then round and custom graphics are supported.
