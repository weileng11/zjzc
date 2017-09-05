package cn.bs.zjzc;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import cn.bs.zjzc.base.BaseActivity;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;


public class MainActivity extends BaseActivity {


    private PtrClassicFrameLayout ptr;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        View v = findViewById(R.id.v);
//        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB)
//        {
//            v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        }
//        ImageView imageview = (ImageView) findViewById(R.id.image);
//        Bitmap bitmap = Bitmap.createBitmap(36, 36, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        Paint paint = new Paint();
//        paint.setColor(0xff999999);
//        paint.setTextSize(24);
//        paint.setTextAlign(Paint.Align.CENTER);
//        canvas.drawText("？", 0, "？".length(), 24, 27, paint);
//        paint.setColor(0xff999999);
//        paint.setAntiAlias(true);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(2);
//        canvas.drawCircle(18, 18, 17, paint);
//
//        imageview.setImageBitmap(bitmap);
//        try {
//            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "1234.png");
//            FileOutputStream outputStream = new FileOutputStream(file);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
//            outputStream.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void click(View view) {
    }
}
