package com.example.myapplication; // מגדיר את שם החבילה שהפרויקט שייך אליה

// ייבוא רכיבים וספריות נדרשים מאנדרואיד
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // הצהרה על משתנים גלובליים עבור רכיבי המסך (שדות טקסט, כפתור ותווית טקסט)
    private EditText etFirstName, etLastName;
    private TextView tvGreeting;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // מחבר בין קוד ה-Java לעיצוב שבקובץ ה-XML

        // קישור בין משתני ה-Java לרכיבים האמיתיים מתוך ה-XML לפי ה-ID שלהם
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        tvGreeting = findViewById(R.id.tvGreeting);
        btnSubmit = findViewById(R.id.btnSubmit);

        // --- טעינת הנתונים השמורים בעת פתיחת האפליקציה ---
        // SharedPreferences משמש לשמירת נתונים בזיכרון המכשיר גם כשהאפליקציה נסגרת
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String savedFirst = prefs.getString("first", ""); // שולף את השם הפרטי השמור (ברירת מחדל: מחרוזת ריקה)
        String savedLast = prefs.getString("last", "");   // שולף את שם המשפחה השמור

        // אם יש נתונים שמורים בזיכרון, נציג מיד את הברכה על המסך (תיבות הקלט יישארו ריקות)
        if (!savedFirst.isEmpty() || !savedLast.isEmpty()) {
            tvGreeting.setText("שלום " + savedFirst + " " + savedLast);
        }

        // --- הגדרת פעולה ללחיצה על הכפתור ---
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // קליטת הטקסט שהמשתמש הקליד בתיבות והמרתו למחרוזת (String)
                String fName = etFirstName.getText().toString();
                String lName = etLastName.getText().toString();

                // --- שמירת הנתונים החדשים בזיכרון המכשיר ---
                SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
                editor.putString("first", fName); // שומר את השם הפרטי תחת המפתח "first"
                editor.putString("last", lName);   // שומר את שם המשפחה תחת המפתח "last"
                editor.apply();                    // מבצע את השמירה בפועל (בצורה אסינכרונית)

                // הצגת ברכת השלום המעודכנת על המסך בתוך ה-TextView
                tvGreeting.setText("שלום " + fName + " " + lName);
            }
        });
    }
}