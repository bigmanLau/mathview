Blog URL: https://www.jianshu.com/p/562037fe0a5f
# studymap
自定义view之学习地图（二次函数）
github地址[链接](https://github.com/bigmanLau/mathview)
![Screenshot_20190219-145302.jpg](https://upload-images.jianshu.io/upload_images/12262980-a93c2f7a03c270dc.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


下面是绘制二次曲线的代码
````java
/**
     * 绘制映射表
     *
     * @param canvas 画笔
     */
    private void drawMap(Canvas canvas) {
        initDf();
        map();
        for (Float key : funMap.keySet()) {
            canvas.drawPoint(key, funMap.get(key).floatValue(), mPaint);
        }
    }
````
其他关键代码
````java
 /**
     * 初始化定义域
     */
    private void initDf() {
        for (float i = 0; i <= getMeasuredWidth(); i++) {
            Df.add(i);//初始化定义域
        }
    }

    /**
     * 对应法则
     *
     * @param x 原像(自变量)
     * @return 像(因变量)
     */
    private float f(Float x) {
        float h = getMeasuredHeight();
        float w = getMeasuredWidth();

        float y = h - m40 - (float) (Math.pow(x, 2) * (h - m80) / Math.pow(w, 2));
        return y;
    }

    /**
     * 遍历定义域,将原像x和像f(x)加入映射表
     */
    private void map() {
        for (Float x : Df) {
            funMap.put(x, f(x));
        }
        //添加所有点
    }
````


原理就是通过数学二次函数关系计算出函数点然后通过循环绘制到canvas上面，其他点和线条原理和这个代码一致

代码在我的github上面
