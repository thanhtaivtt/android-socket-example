package com.toidicode.socket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private EditText txtContent;
    protected Button btnSend;
    protected ListView listMessage;

    protected ArrayList<Message> arrList;
    protected MessageListView arrayAdapter;

    private Socket mSocket;

    String socketID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initalize();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSend();
            }
        });

    }


    private void initalize() {
        txtContent = findViewById(R.id.textContent);
        btnSend = findViewById(R.id.btnSend);
        listMessage = findViewById(R.id.listMessage);
        arrList = new ArrayList<>();
        arrayAdapter = new MessageListView(arrList, this);
        listMessage.setAdapter(arrayAdapter);
        listMessage.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listMessage.setStackFromBottom(true);

        {
            try {
                mSocket = IO.socket("https://tdc-android.herokuapp.com/");
            } catch (URISyntaxException e) {
                //
            }
        }

        mSocket.connect();

        mSocket.on("message", onRecieveMessage);
        mSocket.on("send id", onRecieveSocketID);

    }

    private void attemptSend() {
        String message = txtContent.getText().toString().trim();

        if (TextUtils.isEmpty(message)) {
            return;
        }

        txtContent.setText("");
        mSocket.emit("chat message", message);

    }

    private Emitter.Listener onRecieveMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String message, id;
                    try {
                        message = data.getString("data");
                        id = data.getString("id");
                    } catch (JSONException e) {
                        return;
                    }

                    pushMessage(message, id);
                }
            });
        }
    };

    private Emitter.Listener onRecieveSocketID = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String message;
                    try {
                        socketID = data.getString("id");
                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };

    protected void pushMessage(String message, String id) {
        arrList.add(new Message(message, socketID.equals(id)));

        //auto scroll to bottom
        listMessage.setSelection(arrayAdapter.getCount() - 1);
        arrayAdapter.notifyDataSetChanged();
    }

}
