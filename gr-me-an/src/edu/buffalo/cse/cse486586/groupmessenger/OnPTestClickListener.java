package edu.buffalo.cse.cse486586.groupmessenger;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class OnPTestClickListener implements OnClickListener {
    private static final String TAG = OnPTestClickListener.class.getName();
    private static final int TEST_CNT = 50;
    private static final String KEY_FIELD = "key";
    private static final String VALUE_FIELD = "value";
    private final TextView mTextView;
    private final ContentResolver mContentResolver;
    private final Uri mUri;
    private final ContentValues[] mContentValues;

    public OnPTestClickListener(TextView _tv, ContentResolver _cr) {
        mTextView = _tv;
        mContentResolver = _cr;
        mUri = buildUri("content", "edu.buffalo.cse.cse486586.groupmessenger.provider");
        mContentValues = initTestValues();
    }
    private Uri buildUri(String scheme, String authority) {
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.authority(authority);
        uriBuilder.scheme(scheme);
        return uriBuilder.build();
    }
    private ContentValues[] initTestValues() {
        ContentValues[] cv = new ContentValues[TEST_CNT];
        for (int i = 0; i < TEST_CNT; i++) {
            cv[i] = new ContentValues();
            cv[i].put(KEY_FIELD, "key" + Integer.toString(i));
            cv[i].put(VALUE_FIELD, "val" + Integer.toString(i));
        }
        return cv;
    }
    @Override
    public void onClick(View v) {
        if (testInsert()) {
            mTextView.append("Insert success\n");
        } else {
            mTextView.append("Insert fail\n");
            return;
        }
        if (testQuery()) {
            mTextView.append("Query success\n");
        } else {
            mTextView.append("Query fail\n");
        }
    }
    private boolean testInsert() {
        try {
            for (int i = 0; i < TEST_CNT; i++) mContentResolver.insert(mUri, mContentValues[i]);
        } catch (Exception e) { return false; }
        return true;
    }
    private boolean testQuery() {
        try {
            for (int i = 0; i < TEST_CNT; i++) {
                String key = (String) mContentValues[i].get(KEY_FIELD);
                String val = (String) mContentValues[i].get(VALUE_FIELD);
                Cursor resultCursor = mContentResolver.query(mUri, null, key, null, null);
                if (resultCursor == null) throw new Exception("Result null");
                int keyIndex = resultCursor.getColumnIndex(KEY_FIELD);
                int valueIndex = resultCursor.getColumnIndex(VALUE_FIELD);
                if (keyIndex == -1 || valueIndex == -1) {
                    resultCursor.close();
                    throw new Exception("Wrong columns");
                }
                resultCursor.moveToFirst();
                if (!(resultCursor.isFirst() && resultCursor.isLast())) {
                    resultCursor.close();
                    throw new Exception("Wrong number of rows");
                }
                String returnKey = resultCursor.getString(keyIndex);
                String returnValue = resultCursor.getString(valueIndex);
                resultCursor.close();
                if (!(returnKey.equals(key) && returnValue.equals(val))) throw new Exception("Key/value mismatch");
            }
        } catch (Exception e) { return false; }
        return true;
    }
}