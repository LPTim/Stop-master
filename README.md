# 不用涉及到各种冲突常规打造酷炫下拉视差效果SmartRefreshLayout+ViewPager+RecyclerView

## 查看具体实现方式简介，移步到 [简书地址](https://www.jianshu.com/p/d35b5fa37b26) 

## 快照
![](https://github.com/LPTim/Stop-master/tree/master/snapshot/snapshot.gif)

## 背景
在很多APP上很常见，当我们真正想着手开发时，可能会遇到很多冲突问题，很难实现完美效果,于是我本着无聊的心态做了下，下面讲述下实现的一种常规方案，如果有不足的地方请大家提出，我将继续完善

## 演示程序
下载 [app-debug](https://github.com/LPTim/Stop-master/tree/master/apk/app-debug.apk)

## 功能点简单说明
1. 下拉整体刷新，上拉加载（上拉隶属于单个fragment）
2. 下拉图片视差效果，开始图片整体放大，向下平移，达到阀值时（可自定义），下拉只保留向下平移效果，顶部title左右俩侧按钮渐变隐藏
3. 上滑顶部title停留
4. 上滑顶部title左右俩次按钮改变颜色，顶部title背景颜色渐变显示
5. Tablayout停留

## 布局设计分析
```
-FrameLayout(最外层)
    -ImageView(头部背景图)
        -SmartRefreshLayout(头部刷新控件)
            -CoordinatorLayout
                -AppBarLayout
                     -CollapsingToolbarLayout
                         -Toolbar
                         ...省略中间巴拉巴拉布局
                     -Tablayout
                     -ViewPager
   -Toolbar
```

## 项目提供几个轮子演示
1. 指示器轮子 
2. 刷新框架轮子
3. adapter适配器轮子
4. 屏幕适配轮子，俩种实现方法
5. 多字体轮子，因为页面不好看，改了一种字体

# 祝大家开发！

