package com.example.thodasukoon;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AiSupport extends Fragment {

    private static final String TAG = "AiSupportFragment";
    private RecyclerView rvMessages;
    private EditText etMessage;
    private ImageButton btnSend;
    private ImageButton btnAlert;
    private MessageAdapter messageAdapter;
    private List<Message> messageList = new ArrayList<>();
    private PrefManager prefManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ai_support, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize PrefManager to get the token
        prefManager = new PrefManager(requireContext());

        // Initialize views
        rvMessages = view.findViewById(R.id.rvMessages);
        etMessage = view.findViewById(R.id.etMessage);
        btnSend = view.findViewById(R.id.btnSend);
        btnAlert = view.findViewById(R.id.btnAlert);

        // Setup RecyclerView
        messageAdapter = new MessageAdapter(messageList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvMessages.setLayoutManager(layoutManager);
        rvMessages.setAdapter(messageAdapter);

        // Add a welcome message
        addMessage("Hello! How are you feeling today? I'm here to listen.", Message.TYPE_RECEIVED);

        // Set click listener for the send button
        btnSend.setOnClickListener(v -> sendMessage());

        // Set click listener for the alert button
        btnAlert.setOnClickListener(v -> showCrisisAlert());
    }

    private void sendMessage() {
        String messageText = etMessage.getText().toString().trim();
        if (messageText.isEmpty()) {
            return; // Don't send empty messages
        }

        // Add the user's message to the UI
        addMessage(messageText, Message.TYPE_SENT);
        etMessage.setText(""); // Clear the input field

        // Create the API request
        ChatRequest request = new ChatRequest(messageText);
        String token = prefManager.getToken();
        ApiService apiService = ApiClient.getClient(token).create(ApiService.class);
        Call<ChatResponse> call = apiService.getChatReply(request);

        call.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                if (!isAdded()) return; // Safety check

                if (response.isSuccessful() && response.body() != null) {
                    ChatResponse chatResponse = response.body();
                    // Add the bot's reply to the UI
                    addMessage(chatResponse.getReply(), Message.TYPE_RECEIVED);

                    // Check for urgent referral flag
                    if (chatResponse.isUrgentReferral()) {
                        showUrgentReferralDialog();
                    }
                } else {
                    addMessage("Sorry, I'm having trouble connecting. Please try again later.", Message.TYPE_RECEIVED);
                    Log.e(TAG, "API Error: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                if (!isAdded()) return; // Safety check

                addMessage("Network error. Please check your connection and try again.", Message.TYPE_RECEIVED);
                Log.e(TAG, "Network Failure: ", t);
            }
        });
    }

    // Helper method to add a message to the list and update the adapter
    private void addMessage(String text, int type) {
        messageList.add(new Message(text, type));
        messageAdapter.notifyItemInserted(messageList.size() - 1);
        rvMessages.scrollToPosition(messageList.size() - 1); // Auto-scroll to the latest message
    }

    // Shows the dialog for crisis hotlines
    private void showCrisisAlert() {
        new AlertDialog.Builder(getContext())
                .setTitle("Crisis Support Hotlines")
                .setMessage("If you are in a crisis or any other person may be in danger, please contact a crisis hotline.\n\n• Vandrevala Foundation: 9999666555\n• iCALL: 9152987821")
                .setPositiveButton("OK", null)
                .show();
    }

    // Shows a dialog when the AI detects an urgent situation
    private void showUrgentReferralDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("Urgent Support Recommended")
                .setMessage("It sounds like you are going through a lot right now. It may be helpful to speak with a professional. Consider booking a session with a counselor.")
                .setPositiveButton("OK", (dialog, which) -> {
                    // Optional: You could navigate the user to the BookSession fragment here
                })
                .show();
    }
}
