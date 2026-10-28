package edu.buffalo.cse.cse486586.groupmessenger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

public class ExProvider extends ContentProvider {
    public static final String AUTHORITY = "edu.buffalo.cse.cse486586.groupmessenger.provider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    private static final UriMatcher match = new UriMatcher(UriMatcher.NO_MATCH);
    static { match.addURI(AUTHORITY, null, 1); }

    @Override public int delete(Uri uri, String selection, String[] selectionArgs) { return 0; }
    @Override public String getType(Uri uri) { return null; }

    @Override public Uri insert(Uri uri, ContentValues values) {
        String fileName = values.getAsString("key");
        String valueInFile = values.getAsString("value");
        Context c = getContext();
        if (match.match(uri) == 1) {
            try (FileOutputStream fos = c.openFileOutput(fileName, Context.MODE_PRIVATE)) {
                fos.write(valueInFile.getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return uri;
    }
    @Override public boolean onCreate() { return true; }

    @Override public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        String[] sar = new String[2];
        MatrixCursor m = new MatrixCursor(new String[]{"key", "value"});
        String fileName = selection;
        Context c = getContext();
        String op = null;
        if (match.match(uri) == 1) {
            try (FileInputStream fis = c.openFileInput(fileName)) {
                byte[] b = new byte[fis.available()];
                int offset = 0, n;
                while (offset < b.length && (n = fis.read(b, offset, b.length-offset)) != -1) offset += n;
                op = new String(b);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        sar[0] = selection;
        sar[1] = op;
        m.addRow(sar);
        return m;
    }
    @Override public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) { return 0; }
}