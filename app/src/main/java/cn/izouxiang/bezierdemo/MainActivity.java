package cn.izouxiang.bezierdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import cn.izouxiang.bezierdemo.bezier.BezierView;
import cn.izouxiang.bezierdemo.bezier.MathView;

public class MainActivity extends AppCompatActivity {
    private BezierView mBezierView;
    private EditText mEditText;
    private MathView bezier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bezier= (MathView) findViewById(R.id.math_view);
        bezier.setText1("LV.3 销售主管销售主管");
//        bezier.setText2("LV.4 销售主管销售主管");
        bezier.setProgress(0);
    }

}
