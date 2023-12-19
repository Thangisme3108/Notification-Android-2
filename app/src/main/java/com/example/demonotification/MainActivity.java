package com.example.demonotification;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = findViewById(R.id.btn_notify);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuiThongBao();
            }
        });
    }

    private void GuiThongBao() {
        // Khai báo intent để nhận tương tác khi bấm vào notify
        Intent intentDemo = new Intent(getApplicationContext(), MessageActivity.class);
        // gắn cờ để intent hoạt động phạm vi cao nhất
        intentDemo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        // Nếu muốn gửi dữ liệu vào intent cho activity message thì put vào
        intentDemo.putExtra("duLieu", "Nội dung gửi từ Notify của main vào activity  msg .... ");

        // tạo stack để gọi Activity
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(MainActivity.this);
        stackBuilder.addNextIntentWithParentStack(intentDemo);

        // lấy pendingIntent để gửi vào notify
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // khởi tạo bitmap: Viết trước don
        Bitmap anh = BitmapFactory.decodeResource(getResources(), R.drawable.logo);

        // Khởi tạo layout cho Notify:
        Notification customNofitycation = new NotificationCompat.Builder(
                MainActivity.this, NotifyConfig.CHANEL_ID)
                .setSmallIcon(android.R.drawable.ic_delete)
                .setContentTitle("Tiêu đề câu thông báo")
                .setContentText("Nội dung thông báo: Bạn có đơn hàng mới")
                .setContentIntent(resultPendingIntent) // gán đối tượng nhận tương tác
                //thiết lập style
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(anh).bigLargeIcon(anh))
                .setLargeIcon(anh)
                .setColor(Color.RED)
                .setAutoCancel(true)
                .build();

        // Khởi tạo trình quản lý notify:
        NotificationManagerCompat notificationManagerCompat
                = NotificationManagerCompat.from(MainActivity.this);

        // kiểm tra quyền có được gửi thông báo hay không
        if (ActivityCompat.checkSelfPermission(
                MainActivity.this,
                Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // chưa được ấp quyền: Gọi activity xin quyền
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS}, 99999);// phải dùng manifest trong android chứ không được dùng manifest của pakeer
            return; // thoát khỏi hàm nếu chưa cáp quyền

        }
        // hết phần IF: nếu đã cấp quyền thì tạo ID thông báo và hiển thị
        int id_notify = (int) new Date().getTime(); // tránh bị trùng lặp ID

        // hiển thị notify
        notificationManagerCompat.notify(id_notify, customNofitycation);
    }


}