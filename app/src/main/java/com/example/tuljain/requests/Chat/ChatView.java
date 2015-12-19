
package com.example.tuljain.requests.Chat;

public interface ChatView {
    void setActivityTitle(String title);

    void notifyItemInserted(int messageCount);

    void startAddReminderActivityForContact(String uuid);

    void notifyDataSetChanged();
}