package android.cosmic.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileCommons {

    public static boolean copyFromAssets(Context ctx, String outPath, String fileName)
    {
        File file = new File(outPath);
        if (!file.exists()) {
            if (!file.mkdirs())
            {
                Log.e("COSMIC", "Can't create directory.");
                return false;
            }
        }
        try {
            InputStream inputStream = ctx.getAssets().open(fileName);
            File outFile = new File(file, fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024];
            int byteRead;
            while (-1 != (byteRead = inputStream.read(buffer)))
            {
                fileOutputStream.write(buffer, 0, byteRead);
            }
            inputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
