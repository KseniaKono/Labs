package com.example.firststep;

import android.widget.TextView;

public interface TransactionEvents {
    String enterPin(int ptc, String amount);
    void transactionResult(boolean result);
}