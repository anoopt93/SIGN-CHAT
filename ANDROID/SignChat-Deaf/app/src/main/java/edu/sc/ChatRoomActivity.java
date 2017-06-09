package edu.sc;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

import edu.sc.db.DbProcess;
import edu.sc.models.ChatHistory;
import edu.sc.nw.ChatClient;
import edu.sc.nw.SignChatClient;
import edu.sc.utils.DateAndTime;
import edu.sc.utils.Variables;

public class ChatRoomActivity extends AppCompatActivity {
    Spinner toSP = null;
    public static EditText historyET, msgET;
    public static Context context = null;
    GridView signGV;
    ChatClient cc = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        context = getApplicationContext();
        cc = new ChatClient(Variables.chatServerIP, Variables.chatServerPORT, Variables.uname);
        ////////////////////////////////////////////////
        signGV = (GridView) findViewById(R.id.signGV);
        toSP = (Spinner) findViewById(R.id.toSP);
        historyET = (EditText) findViewById(R.id.historyET);
        msgET = (EditText) findViewById(R.id.msgET);
        signGV.setAdapter(new ImageAdapter(this));
        initCombo();
        signGV.setOnItemClickListener(new GridView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                System.out.println("Pos>>" + i);
                Html.ImageGetter imageGetter = new Html.ImageGetter() {
                    public Drawable getDrawable(String source) {

                        Drawable d = getResources().getDrawable(mThumbIds[i]);
                        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                        return d;
                    }
                };

                Spanned cs = Html.fromHtml("<img src ='" + mThumbIds[i] + "'/>", imageGetter, null);

                int cursorPosition = msgET.getSelectionStart();
                msgET.getText().insert(cursorPosition, cs);
            }
        });
        ////////////////////////////////////////////////
    }

    public void initCombo() {
        ArrayList<String> onlineUsers = SignChatClient.getAllOnlineUsers();
        onlineUsers.add(0, "--SELECT USER--");
        onlineUsers.add(1, "ALL");
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, onlineUsers);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSP.setAdapter(aa);


    }


    ////////////////////////////////////////////////////////////////////////////////
    private static Integer[] mThumbIds = {
            R.drawable.a, R.drawable.b,
            R.drawable.c, R.drawable.d,
            R.drawable.e, R.drawable.f,
            R.drawable.g, R.drawable.h,
            R.drawable.i, R.drawable.j,
            R.drawable.k, R.drawable.l,
            R.drawable.m, R.drawable.n,
            R.drawable.o, R.drawable.p,
            R.drawable.q, R.drawable.r,
            R.drawable.s, R.drawable.t,
            R.drawable.u, R.drawable.v,
            R.drawable.w, R.drawable.x,
            R.drawable.y, R.drawable.z,
            R.drawable.zero, R.drawable.one,
            R.drawable.two, R.drawable.three,
            R.drawable.four, R.drawable.five,
            R.drawable.six, R.drawable.seven,
            R.drawable.eight, R.drawable.nine,
            R.drawable.big, R.drawable.full,
            R.drawable.more, R.drawable.tall,
            R.drawable.bird, R.drawable.bug,
            R.drawable.cat, R.drawable.cow,
            R.drawable.dog, R.drawable.horse,
            R.drawable.pig, R.drawable.sheep,
            R.drawable.blue, R.drawable.brown,
            R.drawable.gold, R.drawable.green,
            R.drawable.orange, R.drawable.red,
            R.drawable.silver, R.drawable.yellow,
            R.drawable.aunt, R.drawable.baby,
            R.drawable.boy, R.drawable.brother,
            R.drawable.dad, R.drawable.divorse,
            R.drawable.girl, R.drawable.grandma,
            R.drawable.grandpa, R.drawable.marriage,
            R.drawable.mom, R.drawable.single,
            R.drawable.sister, R.drawable.uncle,
            R.drawable.angry, R.drawable.bad,
            R.drawable.cry, R.drawable.good,
            R.drawable.happy, R.drawable.like,
            R.drawable.love, R.drawable.sad,
            R.drawable.sorry, R.drawable.apple,
            R.drawable.candy, R.drawable.cheese,
            R.drawable.cookie,R.drawable.cup,
            R.drawable.cup, R.drawable.drink,
            R.drawable.fork, R.drawable.hungry,
            R.drawable.milk, R.drawable.spoon,
            R.drawable.water, R.drawable.bathroom,
            R.drawable.brsteeth, R.drawable.hurt,
            R.drawable.niceclean, R.drawable.sleep,
            R.drawable.wash,R.drawable.cent,
            R.drawable.cost,R.drawable.dollar,
            R.drawable.dollars2, R.drawable.church,
            R.drawable.come, R.drawable.go,
            R.drawable.home, R.drawable.school,
            R.drawable.store, R.drawable.work,
            R.drawable.excuse, R.drawable.please,
            R.drawable.help, R.drawable.stop,
            R.drawable.thankyou, R.drawable.what,
            R.drawable.when, R.drawable.where,
            R.drawable.who, R.drawable.why,
            R.drawable.cold, R.drawable.hot,
            R.drawable.finish, R.drawable.future,
            R.drawable.month, R.drawable.night,
            R.drawable.past, R.drawable.present,
            R.drawable.week, R.drawable.year,
            R.drawable.afternoon, R.drawable.goodbye,
            R.drawable.hello, R.drawable.how,
            R.drawable.morning, R.drawable.must,
            R.drawable.nicetomeetyou, R.drawable.no,
            R.drawable.pay, R.drawable.see,
            R.drawable.thanks, R.drawable.they,
            R.drawable.we, R.drawable.with,
            R.drawable.yes, R.drawable.you

    };
    private static String[] words = {
            "a", "b",
            "c", "d",
            "e", "f",
            "g", "h",
            "i", "j",
            "k", "l",
            "m", "n",
            "o", "p",
            "q", "r",
            "s", "t",
            "u", "v",
            "w", "x",
            "y", "z",
            "zero", "one",
            "two", "three",
            "four", "five",
            "six", "seven",
            "eight", "nine",
            "big", "full",
            "more", "tall",
            "bird", "bug",
            "cat", "cow",
            "dog", "horse",
            "pig", "sheep",
            "blue", "brown",
            "gold","green",
            "orange","red",
            "silver", "yellow",
            "aunt", "baby",
            "boy", "brother",
            "dad", "divorce",
            "girl", "grandma",
            "grandpa", "marriage",
            "mom", "single",
            "sister", "uncle",
            "angry" , "bad",
            "cry", "good",
            "happy", "like",
            "love", "sad",
            "sorry", "apple",
            "candy", "cheese",
            "cookie", "cup",
            "cup", "drink",
            "fork", "hungry",
            "milk", "spoon",
            "water", "bathroom",
            "brushteeth", "hurt",
            "nice", "sleep",
            "wash", "cent",
            "cost", "dollar",
            "dollars2", "church",
            "come", "go",
            "home", "school",
            "store", "work",
            "excuse", "please",
            "help", "stop",
            "thankyou", "what",
            "when", "where",
            "who", "why",
            "cold", "hot",
            "finish","future",
            "month", "night",
            "past", "present",
            "week","year",
            "afternoon", "goodbye",
            "hello", "how",
            "morning", "must",
            "nice to meet you","no",
            "pay","see",
            "thanks","they",
            "we","with",
            "yes","you"
    };

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return mThumbIds[position];
        }

        public long getItemId(int position) {
            return 0;
        }

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(60, 60));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }


    }


    ////////////////////////////////////////////////////////////////////////////////
    public void sendMSG(View view) {
        String first = Html.toHtml(msgET.getText());
        System.out.println(first);
        if (toSP.getSelectedItemPosition() <= 0) {
            Toast.makeText(ChatRoomActivity.this, "Must Select A User", Toast.LENGTH_SHORT).show();
        } else if ("".equals(first)) {
            msgET.setError("Enter A Message");
        } else {

            String to = toSP.getSelectedItem().toString();//userCB.getSelectedItem().toString();
            //////////////////////////////////////////////////////


            StringBuffer sb = new StringBuffer(first);
            for (int i = 0; i < mThumbIds.length; i++) {
                String im = "<img src=\"" + mThumbIds[i] + "\">";
                System.out.println(im);
                while (true) {
                    int pos = sb.indexOf(im);
                    if (pos != -1) {
                        sb.replace(pos, pos + im.length(), words[i] + " ");
                    }
                    if (pos == -1) break;
                }
            }
            String st = sb.substring(3, sb.length() - 5);


            String msg = st;

            if (to.equals("ALL")) {
                DbProcess dbp = new DbProcess(ChatRoomActivity.context);
                String ctDate = DateAndTime.getDate();
                String ctTime = DateAndTime.getTime();
                ChatHistory ch = new ChatHistory(0, Variables.uname, "all", msg, "send", ctDate, ctTime);
                dbp.insertChatHistory(ch);
                dbp.close();
                cc.sender.sendToAll(msg);


            } else {
                DbProcess dbp = new DbProcess(ChatRoomActivity.context);
                String ctDate = DateAndTime.getDate();
                String ctTime = DateAndTime.getTime();
                ChatHistory ch = new ChatHistory(0, Variables.uname, to, msg, "send", ctDate, ctTime);
                dbp.insertChatHistory(ch);
                dbp.close();
                cc.sender.sendToOne(msg, to);
            }
            clear();
        }

    }

    public void clear(View view) {
        clear();
    }

    private void clear() {
        msgET.setText(null);
    }

    public static Spanned getSpanned(String word) {

        Spanned cs = null;
        for (int i = 0; i < words.length; i++) {

            if (words[i].equals(word)) {
                final int pos = i;
                Html.ImageGetter imageGetter = new Html.ImageGetter() {
                    public Drawable getDrawable(String source) {

                        Drawable d = context.getResources().getDrawable(mThumbIds[pos]);
                        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                        return d;
                    }
                };

                cs = Html.fromHtml("<img src ='" + mThumbIds[i] + "'/>", imageGetter, null);
            }
        }
        if (cs == null) cs = Html.fromHtml(word);
        return cs;
    }

    public static void updateMsg(String msg) {


        int pos = msg.lastIndexOf(">");
        String st1 = msg.substring(0, pos);
        Spanned cs = getSpanned("<br>" + st1);
        int cursorPosition = historyET.getSelectionStart();
        historyET.getText().insert(cursorPosition, cs);
        String st2 = msg.substring(pos + 1);
        StringTokenizer stk = new StringTokenizer(st2, " ");
        while (stk.hasMoreTokens()) {
            String temp = stk.nextToken();
            cs = getSpanned(temp);
            cursorPosition = historyET.getSelectionStart();
            historyET.getText().insert(cursorPosition, cs);
        }

    }

    public static Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            updateMsg(Variables.chatMessage);


        }
    };

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ChatRoomActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
